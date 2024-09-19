package com.raveesha.service;

import com.raveesha.common.PlanType;
import com.raveesha.model.Subscription;
import com.raveesha.model.User;

public interface SubscriptionService {

    void createSubscription(User user);
    
    Subscription getUserSubscription(Long userId) throws Exception;
    
    Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception;
    
    Boolean isValid(Subscription subscription);
}
