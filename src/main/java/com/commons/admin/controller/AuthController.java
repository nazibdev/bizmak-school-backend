package com.commons.admin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.commons.admin.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.admin.payloads.LoginRequest;
import com.commons.admin.payloads.SignupRequest;
import com.commons.admin.repository.CourseRepository;
import com.commons.admin.repository.RoleRepository;
import com.commons.admin.repository.UserRepository;
import com.commons.admin.security.jwt.JwtUtils;
import com.commons.admin.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	  
	  @Autowired
	  AuthenticationManager authenticationManager;

	  @Autowired
	  UserRepository userRepository;

	  @Autowired
	  RoleRepository roleRepository;

	  @Autowired
	  PasswordEncoder encoder;

	  @Autowired
	  JwtUtils jwtUtils;
	  
	  @Autowired
	  CourseRepository courseRepository;

	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	    Authentication authentication = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
	    
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());

	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
	        .body(new UserInfoResponse(userDetails.getId(),
	                                   userDetails.getUsername(),
	                                   userDetails.getEmail(),
				   				roles, jwtCookie.getValue()));
	    
//	    return ResponseEntity.ok(new UserInfoResponse(userDetails.getId(),
//		                                   userDetails.getUsername(),
//		                                   userDetails.getEmail(),
//		                                   roles, jwtCookie.getValue()));
	  }

	  @PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		  
		String responseMessage = "Your Registration was not successful";  
		  
	    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	      return ResponseEntity.badRequest().body(new MessageResponse(false, "Error: Username is already taken!"));
	    }

	    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	      return new ResponseEntity(new MessageResponse(false, "Error: Email is already in use!"), HttpStatus.BAD_REQUEST);
	    }

	    // Create new user's account
	    User user = new User(signUpRequest.getUsername(),
	                         signUpRequest.getEmail(),
	                         encoder.encode(signUpRequest.getPassword()));
	    
	    user.setFirstName(signUpRequest.getFname());
	    user.setLastName(signUpRequest.getLname());
	    
	    Course course = courseRepository.findById(Long.parseLong(signUpRequest.getCourse())).get();
	    user.setCourse(course);
//	    user.setCourse(signUpRequest.getCourse());

	    Set<String> strRoles = signUpRequest.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	      Role userRole = roleRepository.findByName(ERole.ROLE_SUBSCRIBER)
	          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	      roles.add(userRole);
	    } else {
	      strRoles.forEach(role -> {

			 System.out.println(role);

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

	    user.setRoles(roles);
	    User registeredUser = userRepository.save(user);
	    if (registeredUser.getId() != null ) {
	    	responseMessage = "User registered successfully!";
	    	
	    	
	    	
//	    	strRoles.forEach(role -> {
//		        switch (role.toLowerCase()) {
//		        case "admin":
//
//		          break;
//		        case "teacher":
//
//		          break;
//		        case "student":
//
//		          break;
//		        default:
//
//		          break;
//		        }
//		      });
	    }

	    return ResponseEntity.ok(new MessageResponse(true, responseMessage));
	  }

	  @PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {  
	    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body(new MessageResponse(true, "You've been signed out!"));
	  }
	  
}
