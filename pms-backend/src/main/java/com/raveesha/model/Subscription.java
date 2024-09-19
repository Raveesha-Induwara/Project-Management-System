package com.raveesha.model;

import com.raveesha.common.PlanType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private boolean isValid;
    private PlanType planType;
    
    @OneToOne
    private User user;
}
