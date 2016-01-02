package at.geyerritter.dezsys07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Entry point for the spring web application. <br>
 * Do not run via IntelliJ, build with maven install and run with tomcat7:deploy
 *
 * @author Stefan Geyer
 * @author Mathias Ritter
 * @version 20151217.1
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
