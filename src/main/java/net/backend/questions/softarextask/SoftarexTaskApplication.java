package net.backend.questions.softarextask;

import net.backend.questions.softarextask.model.Question;
import net.backend.questions.softarextask.model.TypeAnswer;
import net.backend.questions.softarextask.model.User;
import net.backend.questions.softarextask.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoftarexTaskApplication {

    public static void main(String[] args) {

        SpringApplication.run(SoftarexTaskApplication.class, args);
    }
}
