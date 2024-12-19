package pironline.com.pir_online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pironline.com.pir_online.entity.Project;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByCode(String code);
}
