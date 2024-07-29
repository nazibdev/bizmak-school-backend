package com.commons.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.commons.admin.models.ContactsForm;
import com.commons.admin.models.Course;
import com.commons.admin.models.MessageResponse;
import com.commons.admin.models.PaymentIntention;
import com.commons.admin.models.Subscriptions;
import com.commons.admin.models.User;
import com.commons.admin.payloads.ContactUsRequest;
import com.commons.admin.payloads.SubcriptionRequest;
import com.commons.admin.repository.ContactUsRepository;
import com.commons.admin.repository.CourseRepository;
import com.commons.admin.repository.SubcriptionRepository;
import com.commons.admin.repository.UserRepository;
import com.commons.admin.service.CourseService;
import com.commons.admin.service.StripeService;
import com.stripe.model.Charge;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class CommonController {
	
	@Autowired
	private ContactUsRepository contactUsRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubcriptionRepository subcriptionRepository;
	
	@GetMapping("/ping")
	public String setupReady() {
		System.out.println("ping ping");
		return "pong";
	}
	
//	@GetMapping("/calendly")
//	public String getCalendly() {
//		fix it asap change &copy to c
//		"Copyright 2024 &copy; BCAMP It Solutions | All rights reserved";
//		setup a method to get current year and change it in the response
//		return "https://calendly.com/bizmakitsolutions/30min?back=1&month=2024-07";
//	}
	
	
	@GetMapping("/copyright")
	public String getCopyright() {
//		fix it asap change &copy to c
//		"Copyright 2024 &copy; BCAMP It Solutions | All rights reserved";
//		setup a method to get current year and change it in the response
		return "Copyright 2024 BCAMP It Solutions | All rights reserved";
	}
	
	@PostMapping("/contactus")
	public ResponseEntity<?> contactUsThroughPortal(@RequestBody ContactUsRequest contactUsRequest) {
		
		if (contactUsRequest.getEmailAddress() == null || contactUsRequest.getFullname() == null
				|| contactUsRequest.getMessage() == null || contactUsRequest.getPhoneNumber() == null ) {
			return ResponseEntity.badRequest().body(new MessageResponse(false, "Please Enter values for All Field"));
		}
		
//		if(contactUsRepository.findByfullname(contactUsRequest.getFullname()) != null) {
//			return ResponseEntity.ok().body(new MessageResponse(false, "Your message is not delivered to us, try again please"));
//		}
		
//		if(contactUsRepository.findByMessage(contactUsRequest.getMessage()) != null) {
//			return ResponseEntity.ok().body(new MessageResponse(false, "Your message is not delivered to us, try again please"));
//		}
//		
//		if(contactUsRepository.findByPhoneNumber(contactUsRequest.getPhoneNumber()) != null) {
//			return ResponseEntity.ok().body(new MessageResponse(false, "Your message is not delivered to us, try again please"));
//		}
//		
//		if(contactUsRepository.findByEmailAddress(contactUsRequest.getEmailAddress()) != null) {
//			return ResponseEntity.ok().body(new MessageResponse(false, "You have a request with us associated with email"));
//		}
				
		
		ContactsForm newContactsForm = contactUsRepository.save(new ContactsForm(
					contactUsRequest.getFullname(), 
					contactUsRequest.getEmailAddress(),
					contactUsRequest.getPhoneNumber(),
					contactUsRequest.getMessage()));
		
		if (newContactsForm.getId() != null) {
			return ResponseEntity.ok().body(new MessageResponse(true, "You will be contacted by one of our VP"));
		}
		
		return null;
	}
	
	//always common and can be used in the authorized one
	@GetMapping("/viewcourses")
	public ResponseEntity<?> allCourses() {

		List<Course> finalList = courseService.getAllCourseForPublic();
		return ResponseEntity.ok().body(finalList);
	}
	
	@PostMapping("/subscription")
	public ResponseEntity<?> subscription(@Valid @RequestBody SubcriptionRequest subcriptionRequest){

		if(!subcriptionRequest.getSubscriptionsEmail().contains("@") || !subcriptionRequest.getSubscriptionsEmail().contains(".com")){
			return ResponseEntity.ok().body(new MessageResponse(false, "Please provide valid email address"));
		}
		
		boolean match = false;
		Subscriptions newSubscriptions = null;
		
		List<Subscriptions> savedSubs = subcriptionRepository.findAll();
		for (Subscriptions s: savedSubs) {
			if (s.getSubscriptionsEmail().equalsIgnoreCase(subcriptionRequest.getSubscriptionsEmail())) {
				match = true;
			}
		}
		
		if (!match) {
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setSubscriptionsEmail(subcriptionRequest.getSubscriptionsEmail());
		
		newSubscriptions = subcriptionRepository.save(subscriptions);
		return ResponseEntity.ok(new MessageResponse(true, "you are subscribed with " +subcriptionRequest.getSubscriptionsEmail()));
		}else {
			return ResponseEntity.ok(new MessageResponse(false, "Your Email is Already subscribed with us"));
		}		
	}
	
	@PostMapping("/subscription/cancel/{subcriptionId}")
	public ResponseEntity<?> cancelSubscription(@PathVariable("subcriptionId") Long id){
		
		boolean match = false;
		List<Subscriptions> savedSubs = subcriptionRepository.findAll();
		for (Subscriptions s: savedSubs) {
			if (s.getSubscriptionsId().equals(id)) {
				subcriptionRepository.delete(s);
				match = true;
				break;
			}
		}
		
		if (!match) {
			return ResponseEntity.ok(new MessageResponse(false, "Id not found"));
		}else {
			return ResponseEntity.ok(new MessageResponse(true, "you are unsubscribed from our newsletter"));
		}		
	}
	
	


}
