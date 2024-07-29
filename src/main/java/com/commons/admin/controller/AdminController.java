package com.commons.admin.controller;

import java.text.Format;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.admin.models.ContactsForm;
import com.commons.admin.models.Course;
import com.commons.admin.models.ERole;
import com.commons.admin.models.MessageResponse;
import com.commons.admin.models.Role;
import com.commons.admin.models.User;
import com.commons.admin.payloads.CourseRequest;
import com.commons.admin.payloads.LoginRequest;
import com.commons.admin.payloads.UserRequest;
import com.commons.admin.repository.ContactUsRepository;
import com.commons.admin.repository.CourseRepository;
import com.commons.admin.repository.RoleRepository;
import com.commons.admin.repository.SubcriptionRepository;
import com.commons.admin.repository.UserRepository;
import com.commons.admin.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	/**
	 * As a admin
	 * 	1. add course - done
	 * 	2. delete course - done
	 *  3. update course - done
	 *  4. get all course - done
	 *  
	 *  5. assign course to user
	 *  6. remove course from user
	 *  7. update course for user
	 *  
	 *  8. money part
	 *  
	 *  
	 *  9. read contact us 
	 *  10. complete task once assigned
	 */
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	ContactUsRepository contactUsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SubcriptionRepository subcriptionRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@PostMapping("addCourse")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addCourse(@Valid @RequestBody CourseRequest courseRequest) {
		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		LoginRequest loggedUser = (LoginRequest) auth.getPrincipal();
//		
//		System.out.println(loggedUser.getUsername());
		
		//course doesnt exist in the database
		
		String message = String.format("Course "+courseRequest.getCourseName()+" already exist");
		boolean courseExist = true;
		List<Course> listOfCourse = courseRepository.findAll();
		for(Course c : listOfCourse) {
			if(c.getCourseName().equals(courseRequest.getCourseName())){
				courseExist = true;
			}else {
				courseExist = false;
			}
		}
		
		if (!courseExist) {
			Course course = new Course();
			course.setCourseName(courseRequest.getCourseName());
			course.setCourseRate(courseRequest.getCourseRate());
			course.setCourseDescription(courseRequest.getCourseDescription());
			course.setCoursePrice(Long.parseLong(String.valueOf(courseRequest.getCoursePrice())));
			course.setShowCourse(courseRequest.isShowCourse());
			
			Course newCourse = courseRepository.save(course);
			
			if (newCourse.getCourseId() != null) {
				return ResponseEntity.ok(new MessageResponse(true, message.replace("already exist", "added")));
			}
		}else {
			return ResponseEntity.ok(new MessageResponse(false, message));
		}
		
		return null;
	}
	
	
	@PostMapping("removeCourse/{courseId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> removeCourse(@PathVariable("courseId") Long courseId){
		
		Course course = courseRepository.findById(courseId).get();
		if (course != null )
			courseRepository.delete(course);
		
		boolean deleted = courseRepository.findById(courseId).isPresent();
		
		return new ResponseEntity(new MessageResponse(!deleted, "Course " + course.getCourseName() + " has been deleted"), HttpStatus.OK);
	}
	
	@PostMapping("updateCourse/{courseId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseRequest courseRequest){
		
		Map<String, String> map = new HashMap<>();
		
		Course course = courseRepository.findById(courseId).get();
		
//		Date date = new Date();
//		map.put("Date", date.toString());
		
		//course name should not change
		if(!course.getCourseName().equals(courseRequest.getCourseName())) {
//			course.setCourseName(courseRequest.getCourseName());
			map.put("Course Name", "you are not suppose to change course Name!!!");
		}
		
		if(!course.getCourseDescription().equals(courseRequest.getCourseDescription())) {
			course.setCourseDescription(courseRequest.getCourseDescription());
			map.put("Course Description", "Course description has changed");
		}
		
		if(!course.getCoursePrice().equals(courseRequest.getCoursePrice())) {
			course.setCoursePrice(Long.parseLong(String.valueOf(courseRequest.getCoursePrice())));
			map.put("Course Price", "You have changed Course Price Successfully");
		}
		
		if(!course.isShowCourse() == courseRequest.isShowCourse()) {
			course.setShowCourse(courseRequest.isShowCourse());
			map.put("Course Visibility", "You have changed visibility of Show");
		}
		
		map.put("Course Details", course.toString());
		
//		if (course != null ) 
			courseRepository.save(course);
			
		
//		boolean updated = false;
		
//		if (!courseNameOld.equals(updatedCourse.getCourseName())) {
//			updated = true;
//		}
		
		return new ResponseEntity(new MessageResponse(true, "Course " + course.getCourseName() + " has been updated.", map), HttpStatus.OK);
	}
	
	@GetMapping("viewcourses")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> allCourses() {
		return ResponseEntity.ok(courseService.getAllCourseForAdmin());
	}


	/**
	 * Contact Us for Admin Panel
	 * 
	 * To see who requested to contact with them regarding some issues
	 */
	
	
	@GetMapping("requestedcontact")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> requestedContact() {
		
		List<ContactsForm> listOfContact = contactUsRepository.findAll();
		
		return ResponseEntity.ok(listOfContact);
	}
	
	/**
	 * Need to done some workaround for not finding the contactsForm
	 * @param contactid
	 * @return
	 */
	@PostMapping("completedContacts/{contactid}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> compeleteRequestedContact(@PathVariable("contactid") Long contactid){
		
		String message = "Could not find contact with provided id";
		boolean deleted = false;
		
		List<ContactsForm> listOfContact = contactUsRepository.findAll();
		for(ContactsForm contacts : listOfContact) {
			if(contacts.getId().equals(contactid)) {
				contactUsRepository.delete(contacts);
				deleted = true;
				message = "Contact was made and resolved";
			}
		}
		
//		ContactsForm contactsForm = contactUsRepository.findById(contactid).get();
//		contactUsRepository.delete(contactsForm);
//		if (contactUsRepository.findById(contactid).get() == null) {
//			deleted = true;
//		}
		return ResponseEntity.ok().body(new MessageResponse(deleted, message));
	}


	
	
	@GetMapping("alluser")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> alluser() {
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@PostMapping("updateuser/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody UserRequest userRequest){
		
		User existedUser = null;
		if (userRepository.getById(userId) != null ) {
			existedUser = userRepository.getById(userId);
			
			existedUser.setFirstName(userRequest.getFirstName());
			existedUser.setLastName(userRequest.getLastName());
			
			courseRepository.findById(Long.parseLong(userRequest.getCourse()));
			
//			existedUser.setCourse(userRequest.getCourse());
//			existedUser.setPassword(userRequest.getPassword());
//			existedUser.setRoles(userRequest.getRoles());
			Set<String> strRoles = userRequest.getRoles();
			
			Set<Role> roles = new HashSet<>();

		    if (strRoles == null) {
		      Role userRole = roleRepository.findByName(ERole.ROLE_SUBSCRIBER)
		          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      roles.add(userRole);
		    } else {
		      strRoles.forEach(role -> {
		        switch (role.toLowerCase()) {
		        case "role_admin":
		          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(adminRole);

		          break;
		        case "role_teacher":
		          Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(teacherRole);

		          break;
		        case "role_student":
		        	Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(studentRole);
		          break;
		        default:
		          Role subscriberRole = roleRepository.findByName(ERole.ROLE_SUBSCRIBER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(subscriberRole);
		          break;
		        }
		      });
		    }

		    existedUser.setRoles(roles);
			
			User updatedUser = userRepository.save(existedUser);
			
			return new ResponseEntity(new MessageResponse(true, "User has been updated", updatedUser), HttpStatus.OK);
		}
		
		return new ResponseEntity(new MessageResponse(true, "User has been updated", existedUser), HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("updateuser/password")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateUserPassword(@RequestBody LoginRequest loginRequest){
		
		User existedUser = null;
		if (userRepository.findByUsername(loginRequest.getUsername()) != null ) {
			
			existedUser = userRepository.findByUsername(loginRequest.getUsername()).get();
			String oldPassword = existedUser.getPassword();
			existedUser.setPassword(encoder.encode(loginRequest.getPassword()));
			
			User updatedUser = userRepository.save(existedUser);
			if (!oldPassword.equals(updatedUser.getPassword()))			
				return new ResponseEntity(new MessageResponse(true, "User passowrd has been updated", updatedUser), HttpStatus.OK);
		}

		return new ResponseEntity(new MessageResponse(true, "User password has not been updated", existedUser), HttpStatus.BAD_REQUEST);
	}

	
	@GetMapping("subscribed")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> allSubscribed() {
		return ResponseEntity.ok(subcriptionRepository.findAll());
	}
}


