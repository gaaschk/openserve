package org.gsoft.openserv.config.web;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@Import(WebConfig.class)
public class SpringBootConfig {
    private static Log logger = LogFactory.getLog(SpringBootConfig.class);

    public static void main(String[] args) throws Exception {
            SpringApplication application = new SpringApplication(SpringBootConfig.class);
            
            ApplicationContext applicationContext = application.run(args);
            String[] names = applicationContext.getBeanDefinitionNames();
            Arrays.sort(names);
            for(String name: names){
            	logger.info(name);
            }
    }
    
}
