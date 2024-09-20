package com.raveesha.service;

import com.raveesha.model.Issue;
import com.raveesha.model.Project;
import com.raveesha.model.User;
import com.raveesha.repo.IssueRepository;
import com.raveesha.request.IssueRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService{
    @Autowired
    private IssueRepository issueRepo;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    
    @Override
    public Issue getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepo.findById(issueId);
        if(issue.isPresent()) {
            return issue.get();
        }
        throw new Exception("No issue found with issueId " + issueId);
    }
    
    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return issueRepo.findByProjectId(projectId);
    }
    
    @Override
    public Issue createIssue(IssueRequestDto issueDto, User user) throws Exception {
        Project project = projectService.getProjectById(issueDto.getProjectId());
        Issue issue = new Issue();
        
        issue.setTitle(issueDto.getTitle());
        issue.setDescription(issueDto.getDescription());
        issue.setStatus(issueDto.getStatus());
        issue.setProject(project);
        issue.setProjectID(issueDto.getProjectId());
        issue.setPriority(issueDto.getPriority());
        issue.setDueDate(issueDto.getDueDate());
        issue.setTags(issueDto.getTags());
        issue.setAssignee(issueDto.getAssignee());
        return issueRepo.save(issue);
    }
    
    @Override
    public void deleteIssue(Long issueId) throws Exception {
        getIssueById(issueId);
        issueRepo.deleteById(issueId);
    }
    
    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        User user = userService.findById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepo.save(issue);
    }
    
    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
