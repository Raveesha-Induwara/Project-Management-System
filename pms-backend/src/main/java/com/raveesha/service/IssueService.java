package com.raveesha.service;

import com.raveesha.model.Issue;
import com.raveesha.model.User;
import com.raveesha.request.IssueRequestDto;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId) throws Exception;
    
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    
    Issue createIssue(IssueRequestDto issue, User user) throws Exception;

//    Optional<Issue> updateIssue(Long issueId, IssueRequestDto updatedIssue, Long userId) throws Exception;
    
    void deleteIssue(Long issueId) throws Exception;

//    List<Issue> getIssuesByAssigneeId(Long assigneeId) throws Exception;

//    List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception;

//    List<User> getAssigneeForIssue(Long issueId) throws Exception;
    
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    
    Issue updateStatus(Long issueId, String status) throws Exception;
}
