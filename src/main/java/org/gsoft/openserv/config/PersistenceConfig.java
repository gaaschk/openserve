package org.gsoft.openserv.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"org.gsoft.openserv.domain","org.gsoft.openserv.repositories"})
public class PersistenceConfig{
}
