package io.github.braayy.forum.features.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course register(RegisterCourseDTO dto) {
        Course course = Course.builder()
                .name(dto.name())
                .category(dto.category())
                .build();

        return this.courseRepository.save(course);
    }

    public Course getById(Long courseId) {
        return this.courseRepository.getReferenceById(courseId);
    }

    public Course update(Long courseId, UpdateCourseDTO dto) {
        Course course = this.courseRepository.getReferenceById(courseId);

        if (dto.name() != null) {
            course.setName(dto.name());
        }

        if (dto.category() != null) {
            course.setCategory(dto.category());
        }

        return course;
    }

    public void deleteById(Long courseId) {
        this.courseRepository.deleteById(courseId);
    }

    public Page<Course> listAll(Pageable pageable) {
        return this.courseRepository.findAll(pageable);
    }
}
