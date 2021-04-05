package com.soickm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.soickm.discovery.Discover;


public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        try {
            Discover.start(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Discover.start(args);
//    }
}
