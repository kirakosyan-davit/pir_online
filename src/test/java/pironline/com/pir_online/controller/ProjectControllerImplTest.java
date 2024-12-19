package pironline.com.pir_online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;
import pironline.com.pir_online.entity.type.ProjectStatus;
import pironline.com.pir_online.repository.ProjectRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectRepository projectRepository;

    private ProjectDto projectDto;

    private Project project;

    private ProjectDto projectUpdate;

    @BeforeEach
    void setUp() {
        projectRepository.deleteAll();

        projectDto = ProjectDto.builder()
                .name("Test Project")
                .code("TP001")
                .startDate(LocalDateTime.parse("2024-12-17T14:48"))
                .endDate(LocalDateTime.parse("2024-12-17T15:48"))
                .sections(new HashSet<>())
                .build();

        project = Project.builder()
                .name("Test Project")
                .code("TP001")
                .startDate(LocalDateTime.parse("2024-12-17T14:48"))
                .endDate(LocalDateTime.parse("2024-12-17T15:48"))
                .projectStatus(ProjectStatus.NEW)
                .sections(new HashSet<>())
                .build();

        projectUpdate = ProjectDto.builder()
                .name("Updated Project")
                .code("UP001")
                .startDate(LocalDateTime.parse("2024-12-17T14:48"))
                .endDate(LocalDateTime.parse("2024-12-17T15:48"))
                .projectStatus(ProjectStatus.NEW)
                .sections(new HashSet<>())
                .build();

    }

    @Test
    void addProject_ShouldReturnCreatedProject() throws Exception {

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Project")))
                .andExpect(jsonPath("$.code", is("TP001")))
                .andExpect(jsonPath("$.projectStatus", is("NEW")));
    }

    @Test
    void getProject_ShouldReturnProject() throws Exception {
        projectRepository.save(project);

        mockMvc.perform(get("/api/projects/{id}", project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Project")))
                .andExpect(jsonPath("$.code", is("TP001")))
                .andExpect(jsonPath("$.projectStatus", is("NEW")));
    }

    @Test
    void updateProject_ShouldReturnUpdatedProject() throws Exception {
        projectRepository.save(project);

        mockMvc.perform(put("/api/projects/{id}", project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Project")))
                .andExpect(jsonPath("$.code", is("UP001")));
    }

    @Test
    void deleteProject_ShouldMarkProjectAsDeleted() throws Exception {
        projectRepository.save(project);

        mockMvc.perform(delete("/api/projects/{id}", project.getId()))
                .andExpect(status().isNoContent());

        Project deletedProject = projectRepository.findById(project.getId()).orElseThrow();
        assert deletedProject.getProjectStatus() == ProjectStatus.DELETED;
    }
}
