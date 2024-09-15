package com.raveesha.service;

import com.raveesha.model.Invitation;
import com.raveesha.repo.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepo;
    @Autowired
    private EmailService emailService;
    
    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation(email, invitationToken, projectId);
        invitationRepo.save(invitation);
        
        String invitationLink = "http://localhost:5173/accept_invitation?token=" + invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);
    }
    
    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepo.findByToken(token);
        if(invitation == null) {
            throw new Exception("Invalid invitation token");
        }
        return invitation;
    }
    
    @Override
    public String getTokenByUserEmail(String userEmail) {
        Invitation invitation = invitationRepo.findByEmail(userEmail);
        return invitation.getToken();
    }
    
    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepo.findByToken(token);
        invitationRepo.delete(invitation);
    }
}
