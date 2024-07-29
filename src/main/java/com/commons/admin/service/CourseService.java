package com.commons.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.admin.models.Course;
import com.commons.admin.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> getAllCourseForPublic(){
		List<Course> finalList = new ArrayList<>();
		List<Course> all = courseRepository.findAll();
				
		for(Course c : all) {
			if (c.isShowCourse()) {
				finalList.add(c);
			}
		}
		
		if(!finalList.isEmpty()) {
			return finalList;
		}
		
		
		return null;
	}
	
	public List<Course> getAllCourseForAdmin(){
		List<Course> all = courseRepository.findAll();
		return all;
	}
	
	
}
