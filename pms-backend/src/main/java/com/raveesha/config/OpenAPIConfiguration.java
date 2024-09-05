package com.raveesha.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Development");
        
        Contact myContact = new Contact();
        myContact.setName("Raveesha Induwara");
        myContact.setEmail("raveeshe.abeysinghe@gmail.com");
        
        Info information = new Info()
                                   .title("Project Management System API")
                                   .version("1.0")
                                   .description("This API exposes endpoints to manage project management system.")
                                   .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
