package pironline.com.pir_online.service;

import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;

public interface ProjectService {

    Project addProject(ProjectDto projectDto);

    Project getProject(int id);

    Project updateProject(int id, ProjectDto projectDto);

    void deleteProject(int id);
}
