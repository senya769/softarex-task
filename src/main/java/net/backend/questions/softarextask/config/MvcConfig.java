package net.backend.questions.softarextask.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry){
        viewControllerRegistry.addViewController("/").setViewName("pages/main");
//        viewControllerRegistry.addViewController("/successLogin").setViewName("redirect:/");
//        viewControllerRegistry.addViewController("/login").setViewName("pages/login");
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}