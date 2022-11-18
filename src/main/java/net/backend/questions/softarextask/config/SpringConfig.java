package net.backend.questions.softarextask.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringConfig{
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
   /* @Bean
    public MailSender mailSender(){
        return new JavaMailSenderImpl();
    }*/
}