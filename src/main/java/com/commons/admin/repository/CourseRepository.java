package com.commons.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commons.admin.models.Course;
import com.commons.admin.models.User;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	Optional<Course> findByCourseName(String courseName);
}
