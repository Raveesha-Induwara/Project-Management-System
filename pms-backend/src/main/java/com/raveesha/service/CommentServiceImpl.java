package com.raveesha.service;

import com.raveesha.model.Comment;
import com.raveesha.model.Issue;
import com.raveesha.model.User;
import com.raveesha.repo.CommentRepository;
import com.raveesha.repo.IssueRepository;
import com.raveesha.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private UserRepository userRepo;
    
    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issue = issueRepo.findById(issueId);
        Optional<User> user = userRepo.findById(userId);
        
        if(issue.isEmpty()) throw new Exception("Issue not found with id: " + issueId);
        if(user.isEmpty()) throw new Exception("User not found with id: " + userId);
        
        Comment comment = new Comment();
        comment.setUser(user.get());
        comment.setIssue(issue.get());
        comment.setContent(content);
        comment.setCreatedDateTime(LocalDateTime.now());
        
        Comment savedComment = commentRepo.save(comment);
        issue.get().getComments().add(savedComment);
        return savedComment;
    }
    
    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> comment = commentRepo.findById(commentId);
        Optional<User> user = userRepo.findById(userId);
        
        if(comment.isEmpty()) throw new Exception("Issue not found with id: " + commentId);
        if(user.isEmpty()) throw new Exception("User not found with id: " + userId);
        
        if(comment.get().getUser().equals(user.get()))
            commentRepo.delete(comment.get());
        else
            throw new Exception("You do not have permission to delete this comment");
    }
    
    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepo.findByIssueId(issueId);
    }
}
