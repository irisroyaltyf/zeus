package top.yulegou.zeus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import top.yulegou.zeus.manager.DbTableDataResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
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
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        List<HttpMessageReader<?>> readers=new ArrayList<HttpMessageReader<?>>();
        //添加Http消息编解码器
        readers.add(new DecoderHttpMessageReader<>(new Jackson2JsonDecoder()));
        //消息编解码器与Resolver绑定
        configurer.addCustomResolver(new DbTableDataResolver(readers));
    }

}