package org.sid;

import org.apache.catalina.User;
import org.sid.dao.TaskRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Task;
import org.sid.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.AccessibleObject;
import java.util.stream.Stream;

@SpringBootApplication
public class JwtSpringSecApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {

        SpringApplication.run(JwtSpringSecApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder getBCPE()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        accountService.saveUser(new AppUser(null,"admin","1234",null));
        accountService.saveUser(new AppUser(null,"user","1234",null));
        accountService.saveRole(new AppRole(null,"ADMIN"));
        accountService.saveRole(new AppRole(null,"USER"));
        accountService.AddRoleToUser("admin","ADMIN");
        accountService.AddRoleToUser("user","USER");
        Stream.of("T1","T2","T3").forEach(t->{
            taskRepository.save(new Task(null,t));
        });
        taskRepository.findAll().forEach(t->{
            System.out.println(t.getTaskName());
        });

    }
}
