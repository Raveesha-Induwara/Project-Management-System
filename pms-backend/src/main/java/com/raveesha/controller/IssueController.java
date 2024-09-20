package com.raveesha.controller;

import com.raveesha.model.Issue;
import com.raveesha.model.User;
import com.raveesha.repo.IssueRepository;
import com.raveesha.request.IssueRequestDto;
import com.raveesha.service.IssueService;
import com.raveesha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/issue")
public class IssueController {
    @Autowired
    private UserService userService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueRepository issueRepo;
    
    @GetMapping("/{issuedId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issuedId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issuedId));
    }
    
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }
    
    @PostMapping("/")
    public ResponseEntity<Issue> createIssue(@RequestBody IssueRequestDto issueDto,
                                             @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        Issue createdIssue = issueService.createIssue(issueDto, user);
        return ResponseEntity.ok(createdIssue);
    }
    
    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long issueId,
                                              @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId);
        return ResponseEntity.ok("Issue deleted");
    }
    
    @PatchMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                 @PathVariable Long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }
    
    @PatchMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateStatus(@PathVariable Long issueId,
                                                @PathVariable String status) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
    
}
