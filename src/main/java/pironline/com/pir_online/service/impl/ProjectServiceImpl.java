package pironline.com.pir_online.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;
import pironline.com.pir_online.entity.type.ProjectStatus;
import pironline.com.pir_online.repository.ProjectRepository;
import pironline.com.pir_online.service.ProjectService;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public Project addProject(ProjectDto projectDto) {
        Optional<Project> byCode = projectRepository.findByCode(projectDto.getCode());
        if (byCode.isPresent()) {
            throw new EntityNotFoundException("Project already exists with Code: " + projectDto.getCode());
        }
        return projectRepository.save(Project.builder()
                .name(projectDto.getName())
                .code(projectDto.getCode())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .projectStatus(ProjectStatus.NEW)
                .sections((projectDto.getSections() == null) ? new HashSet<>() : new HashSet<>(projectDto.getSections()))
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProject(int id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
        Hibernate.initialize(project.getSections());
        return project;
    }

    @Override
    @Transactional
    public Project updateProject(int id, ProjectDto projectDto) {
        Project project = getProject(id);

        return projectRepository.save(Project.builder()
                .id(project.getId())
                .name(projectDto.getName())
                .code(projectDto.getCode())
                .startDate(projectDto.getStartDate())
                .endDate(projectDto.getEndDate())
                .sections((projectDto.getSections() == null) ? new HashSet<>() : new HashSet<>(projectDto.getSections()))
                .build());
    }

    @Override
    @Transactional
    public void deleteProject(int id) {
        Project project = getProject(id);
        project.setProjectStatus(ProjectStatus.DELETED);
        projectRepository.save(project);

    }
}
