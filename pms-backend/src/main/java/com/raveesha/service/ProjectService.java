package com.raveesha.service;

import com.raveesha.model.Chat;
import com.raveesha.model.Project;
import com.raveesha.model.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;
    
    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;
    
    Project getProjectById(Long projectId) throws Exception;
    
    void deleteProject(Long projectId, Long userId) throws Exception;
    
    Project updateProject(Project updatedProject, Long id) throws Exception;
    
    void addUserToProject(Long projectId, Long userId) throws Exception;
    
    void removeUserFromProject(Long projectId, Long userId) throws Exception;
    
    Chat getChatByProjectId(Long projectId) throws Exception;
    
    List<Project> searchProject(String keyWord, User user) throws Exception;
}
