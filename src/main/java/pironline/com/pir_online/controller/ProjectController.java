package pironline.com.pir_online.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pironline.com.pir_online.dto.ProjectDto;
import pironline.com.pir_online.entity.Project;

@RequestMapping("/api/projects")
public interface ProjectController {

    @PostMapping
    ResponseEntity<Project> addProject(@RequestBody @Valid ProjectDto projectDto);

    @GetMapping("/{id}")
    ResponseEntity<Project> getProject(@PathVariable int id);

    @PutMapping("/{id}")
    ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody  @Valid ProjectDto projectDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable int id);

}
