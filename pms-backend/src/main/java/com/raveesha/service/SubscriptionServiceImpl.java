package com.raveesha.service;

import com.raveesha.common.PlanType;
import com.raveesha.model.Subscription;
import com.raveesha.model.User;
import com.raveesha.repo.SubscriptionRepository;
import com.raveesha.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepo;
    @Autowired
    private UserRepository userRepo;
    
    @Override
    public void createSubscription(User user) {;
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscriptionRepo.save(subscription);
    }
    
    @Override
    public Subscription getUserSubscription(Long userId) throws Exception {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new Exception("User not fount with id: " + userId));
        
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        if(!subscription.isValid()) {
            return upgradeSubscription(userId, PlanType.FREE);
        }
        return subscription;
    }
    
    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws Exception {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new Exception("User not fount with id: " + userId));
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        
        if(planType.equals(PlanType.MONTHLY))
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        else
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        
        return subscriptionRepo.save(subscription);
    }
    
    @Override
    public Boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE))
            return true;
        
        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
