package pironline.com.pir_online.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pironline.com.pir_online.controller.ProjectController;
import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;
import pironline.com.pir_online.service.ProjectService;

@RestController
public class ProjectControllerImpl implements ProjectController {

    @Autowired
    private final ProjectService projectService;


    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ResponseEntity<Project> addProject(ProjectDto projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addProject(projectDto));
    }

    @Override
    public ResponseEntity<Project> getProject(int id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @Override
    public ResponseEntity<Project> updateProject(int id, ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDto));
    }

    @Override
    public ResponseEntity<Void> deleteProject(int id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
