package io.github.braayy.forum.features.course;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ShowCourseDTO> register(
        @Valid @RequestBody RegisterCourseDTO body,
        UriComponentsBuilder uriBuilder
    ) {
        Course course = this.courseService.register(body);

        URI uri = uriBuilder
            .path("/courses/{id}")
            .build(course.getId());

        return ResponseEntity.created(uri)
                .body(new ShowCourseDTO(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowCourseDTO> show(
        @PathVariable Long id
    ) {
        Course course = this.courseService.getById(id);

        return ResponseEntity.ok(new ShowCourseDTO(course));
    }

    @GetMapping
    public ResponseEntity<Page<ListCourseDTO>> list(
        @PageableDefault Pageable pageable
    ) {
        Page<Course> page = this.courseService.listAll(pageable);

        return ResponseEntity.ok(
            page
                .map(ListCourseDTO::new)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ShowCourseDTO> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateCourseDTO body
    ) {
        Course course = this.courseService.update(id, body);

        return ResponseEntity.ok(new ShowCourseDTO(course));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(
        @PathVariable Long id
    ) {
        this.courseService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
