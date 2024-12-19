package pironline.com.pir_online.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;
import pironline.com.pir_online.entity.type.ProjectStatus;
import pironline.com.pir_online.repository.ProjectRepository;
import pironline.com.pir_online.service.impl.ProjectServiceImpl;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProjectServiceImplTest {


    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    ProjectDto projectDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectDto = new ProjectDto("Project 1", "PRJ001", null, null, null,null);
    }

    @Test
    void addProject_success() {
        when(projectRepository.findByCode(projectDto.getCode())).thenReturn(Optional.empty());

        Project project = Project.builder()
                .name("Project 1")
                .code("PRJ001")
                .projectStatus(ProjectStatus.NEW)
                .sections(new HashSet<>())
                .build();

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectService.addProject(projectDto);

        assertNotNull(result);
        assertEquals("Project 1", result.getName());
        assertEquals("PRJ001", result.getCode());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void addProject_alreadyExists() {
        when(projectRepository.findByCode(projectDto.getCode())).thenReturn(Optional.of(new Project()));

        assertThrows(EntityNotFoundException.class, () -> projectService.addProject(projectDto));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void getProject_success() {
        Project project = Project.builder().id(1).name("Project 1").build();
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));

        Project result = projectService.getProject(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(projectRepository).findById(1);
    }

    @Test
    void getProject_notFound() {
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> projectService.getProject(1));
        verify(projectRepository).findById(1);
    }

    @Test
    void updateProject_success() {
        projectDto = new ProjectDto("Updated Project", "PRJ002", null, null, null,null);
        Project existingProject = Project.builder().id(1).name("Old Project").build();

        when(projectRepository.findById(1)).thenReturn(Optional.of(existingProject));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project updatedProject = projectService.updateProject(1, projectDto);

        assertNotNull(updatedProject);
        assertEquals("Updated Project", updatedProject.getName());
        assertEquals("PRJ002", updatedProject.getCode());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void deleteProject_success() {
        Project project = Project.builder().id(1).projectStatus(ProjectStatus.NEW).build();
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        projectService.deleteProject(1);

        assertEquals(ProjectStatus.DELETED, project.getProjectStatus());
        verify(projectRepository).save(any(Project.class));
    }
}
