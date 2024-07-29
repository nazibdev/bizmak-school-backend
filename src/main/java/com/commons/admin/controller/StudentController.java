package com.commons.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.admin.models.MessageResponse;
import com.commons.admin.models.User;
import com.commons.admin.payloads.LoginRequest;
import com.commons.admin.repository.UserRepository;
import com.commons.admin.security.jwt.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth/student")
public class StudentController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/getuserinfo")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<?> getStudentInfo(@CookieValue(name = "bizmakSchool") String bizmakSchool) {
		
		String username = jwtUtils.getUserNameFromJwtToken(bizmakSchool);
		User user = userRepository.findByUsername(username).get();
		String courseName = user.getCourse().getCourseName();
		System.out.println(courseName);
		Map<String, String> list = new HashMap<>();
		list.put("courseName", courseName);
		return ResponseEntity.ok(new MessageResponse(true, "retrieved user info", list));
		
	}
	
	@PostMapping("/updateuser/password")
//	@PreAuthorize("hasRole('ROLE_STUDENT')")
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

}
