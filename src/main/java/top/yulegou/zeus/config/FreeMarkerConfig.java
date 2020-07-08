//package top.yulegou.zeus.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import java.util.Properties;
//
//@Configuration
//public class FreeMarkerConfig {
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer() {
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
////        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/ftls");
//
//        Properties pro = new Properties();
//        /*
//        缓存更新时间配置
//         */
////        pro.setProperty("template_update_delay", "1800");
//        pro.setProperty("default_encoding", "UTF-8");
//        pro.setProperty("locale", "zh_CN");
//        freeMarkerConfigurer.setFreemarkerSettings(pro);
//
//        return freeMarkerConfigurer;
//    }
//}
