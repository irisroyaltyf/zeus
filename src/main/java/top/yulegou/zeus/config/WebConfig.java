package top.yulegou.zeus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.config.*;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;
import top.yulegou.zeus.config.resolver.DbTableDataResolver;
import top.yulegou.zeus.config.resolver.ListDataResolver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebFlux //WebFluxConfig 如果没有会自动配置WebFluxAutoConfiguration
@EnableConfigurationProperties(WebFluxProperties.class)
public class WebConfig implements WebFluxConfigurer {
    @Autowired
    FreeMarkerViewResolver freeMarkerViewResolver;
    @Autowired
    ResourceProperties resourceProperties;
    @Autowired
    WebFluxProperties webFluxProperties;

    //    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.freeMarker();
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js").addResourceLocations("classpath:/resources/static/");
//    }
//    // Configure FreeMarker...
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer() {
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        configurer.setTemplateLoaderPath("classpath:/templates");
//
//        return configurer;
//    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(freeMarkerViewResolver);
    }
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        List<HttpMessageReader<?>> readers=new ArrayList<HttpMessageReader<?>>();
        //添加Http消息编解码器
        readers.add(new DecoderHttpMessageReader<>(new Jackson2JsonDecoder()));
        //消息编解码器与Resolver绑定
        configurer.addCustomResolver(new DbTableDataResolver(readers));
        configurer.addCustomResolver(new ListDataResolver(readers));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.resourceProperties.isAddMappings()) {
            return;
        }
        if (!registry.hasMappingForPattern("/webjars/**")) {
            ResourceHandlerRegistration registration = registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
            configureResourceCaching(registration);
//            customizeResourceHandlerRegistration(registration);
        }
        String staticPathPattern = this.webFluxProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            ResourceHandlerRegistration registration = registry.addResourceHandler(staticPathPattern)
                    .addResourceLocations(this.resourceProperties.getStaticLocations());
            configureResourceCaching(registration);
//            customizeResourceHandlerRegistration(registration);
        }
        registry.addResourceHandler("/data/images/**")
                .addResourceLocations("file:/data/zeus/");
    }

    private void configureResourceCaching(ResourceHandlerRegistration registration) {
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        ResourceProperties.Cache.Cachecontrol cacheControl = this.resourceProperties.getCache().getCachecontrol();
        if (cachePeriod != null && cacheControl.getMaxAge() == null) {
            cacheControl.setMaxAge(cachePeriod);
        }
        registration.setCacheControl(cacheControl.toHttpCacheControl());
    }
}