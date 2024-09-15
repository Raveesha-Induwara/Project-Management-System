package com.raveesha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Chat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @OneToOne
    private Project project;
    
    @JsonIgnore
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;
    
    @ManyToMany
    private List<User> users = new ArrayList<>();
}
