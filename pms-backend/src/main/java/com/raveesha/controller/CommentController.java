package com.raveesha.controller;

import com.raveesha.model.Comment;
import com.raveesha.model.User;
import com.raveesha.request.CommentRequestDto;
import com.raveesha.service.CommentService;
import com.raveesha.service.IssueService;
import com.raveesha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController(value = "api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;
    
    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CommentRequestDto commentDto,
            @RequestHeader("Authorization") String token
            ) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Comment newComment = commentService.createComment(
                commentDto.getIssueId(),
                user.getId(),
                commentDto.getContent());
        
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") java.lang.String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        commentService.deleteComment(commentId, user.getId());
        
        return ResponseEntity.ok("Comment deleted successfully");
    }
    
    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable Long issueId) {
        List<Comment> commentList = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
}
