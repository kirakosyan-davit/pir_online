package pironline.com.pir_online.entity;

import jakarta.persistence.*;
import lombok.*;
import pironline.com.pir_online.entity.type.ProjectStatus;
import pironline.com.pir_online.entity.type.SectionType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sections_type", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "section")
    @Enumerated(EnumType.STRING)
    private Set<SectionType> sections = new HashSet<>();
}
