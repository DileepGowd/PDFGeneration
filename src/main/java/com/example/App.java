package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
/**
 * Application Starting Point
 * @author dileep
 *
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
        SpringApplication sa = new SpringApplication(App.class);
        //sa.setBannerMode(Mode.OFF);
        sa.run(args);
    }
}
