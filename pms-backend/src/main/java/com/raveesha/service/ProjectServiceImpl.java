package com.raveesha.service;

import com.raveesha.model.Chat;
import com.raveesha.model.Project;
import com.raveesha.model.User;
import com.raveesha.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{
    
    @Autowired
    private ProjectRepository projectRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    
    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project newProject = new Project();
        newProject.setOwner(user);
        newProject.getTeam().add(user);
        newProject.setName(project.getName());
        newProject.setTags(project.getTags());
        newProject.setCategory(project.getCategory());
        newProject.setDescription(project.getDescription());
        
        Project savedProject = projectRepo.save(newProject);
        
        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);
        
        return savedProject;
    }
    
    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);
        
        if(category != null) {
            projects = projects.stream()
                               .filter(project -> project.getCategory().equals(category))
                               .collect(Collectors.toList());
        }
        if(tag != null) {
            projects = projects.stream()
                               .filter(project -> project.getTags().contains(tag))
                               .collect(Collectors.toList());
        }
        return projects;
    }
    
    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepo.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new Exception("Project not found");
        }
        return optionalProject.get();
    }
    
    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        projectRepo.deleteById(projectId);
    }
    
    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());
        
        return projectRepo.save(project);
    }
    
    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findById(userId);
        
        if(!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepo.save(project);
    }
    
    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findById(userId);
        
        if(project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepo.save(project);
    }
    
    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        return getProjectById(projectId).getChat();
    }
    
    @Override
    public List<Project> searchProject(String keyWord, User user) throws Exception {
        String partialName = "%" + keyWord + "%";
        return projectRepo.findByNameContainingAndTeamContains(keyWord, user);
    }
}
