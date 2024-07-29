package com.commons.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.commons.admin.models.Course;
import com.commons.admin.models.MessageResponse;
import com.commons.admin.models.PaymentInfo;
import com.commons.admin.models.User;
import com.commons.admin.repository.CourseRepository;
import com.commons.admin.repository.PaymentInfoRepository;
import com.commons.admin.repository.UserRepository;
import com.commons.admin.service.NewStripeService;
import com.commons.admin.service.Response;
import com.commons.admin.service.StripeService;
import com.stripe.model.Charge;
import com.stripe.model.Coupon;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PaymentController {
	
	@Autowired
    private StripeService stripeService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private NewStripeService newStripeService;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Below endpoints are going to be secure under user/admin/teacher
	 * @param courseId
	 * @return
	 */
	
	@PostMapping("/cart/1/course/{courseId}")
	public ResponseEntity<?> addCourseToCart(@PathVariable("courseId") Long courseId) {
		
		Course course = courseRepository.findById(courseId).get();
		User user = userRepository.findByUsername("nazib0").get();
		
		
		
		return ResponseEntity.ok().body(new MessageResponse(true, course.getCourseName() + " added to your cart", course, user));
	}
	
	@GetMapping("/cart/1/price")
	public ResponseEntity<?> cart() {
		String amount = "2000";
		Course course = courseRepository.findById((long) 3.0).get();
		Map<String, Object> list = new HashMap<>();
		list.put("courseName", course.getCourseName());
		list.put("courseDesc", course.getCourseDescription());
		list.put("coursePrice", "2000");
		
//		RestTemplate restTemplate = new RestTemplate();
//		Object obj = restTemplate.postForObject("localhost:8080/api/public/create-checkout-session/2000",null,  Object.class);
//		ResponseEntity<PaymentIntention> obj = restTemplate.postForEntity("http://localhost:8080/api/public/create-checkout-session/{amount}", null, PaymentIntention.class, 2000);
//		list.put("payment_intent_id", obj);
		
//		Map map = new HashMap();
//	      map.put("client_secret", paymentIntentId.getClientSecret());
//
//	      return new ModelAndView(map, "checkout.hbs");
		
		
//		String paymentIntentId = stripeService.createPaymentIntent(Long.valueOf(amount));
//		list.put("intent", paymentIntentId);
		
		
		return ResponseEntity.ok().body(new MessageResponse(true, course.getCourseName() + " added to your cart", list));
	}
	
	private void practiceStripe() {
		
	}
	
	
	String url;
	
	@PostMapping("/create-checkout-session/{amount}/{courseName}/{email}")
	public ResponseEntity<?> checkout(@PathVariable("amount") String amount, @PathVariable("courseName") String courseName, @PathVariable("email") String email) {
//		paymentRequest.getAmount()
		String clientSecret = stripeService.createPaymentIntent(Long.valueOf(amount) * 100);
		if (clientSecret != null) {
			String url = stripeService.testCheckout(Long.valueOf(amount) * 100, courseName, email);
			
			return ResponseEntity.ok().body(new MessageResponse(true, url));
		}
		
		
		return ResponseEntity.ok().body(new MessageResponse(false, "cancel"));
	}
	
	@GetMapping("/isItSucceed/{clientSecret}")
	public ResponseEntity<?> getSuccess(@PathVariable("clientSecret") String clientSecret) {
		String paymentIntent = stripeService.succesfulPayment(clientSecret);
		return ResponseEntity.ok().body(new MessageResponse(true, paymentIntent));
	}
	
	//no use
	@PostMapping("/charge")
	public Charge chargeCard(
			@RequestHeader(value="token") String token, 
			@RequestHeader(value="amount") Double amount, 
			ServletRequest request, 
			ServletResponse response) throws Exception {
		
        return this.stripeService.chargeNewCard(token, amount);
    }

	
//	has been moved into create checkout session with amount
	@PostMapping("/testcheckout")
    public ResponseEntity<?> checkoutM() {
		String url = stripeService.testCheckout(100L, "","");
		System.out.println("Token = " + url);
        return ResponseEntity.ok().body(new MessageResponse(true, url));
    }
	
	
	//==========================================================================================================================
	
	@Value("${stripe.api.key}")
	private String API_PUBLIC_KEY;
	
	@GetMapping("/subscription")
	public String subscriptionPage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "subscription";
	}

	@GetMapping("/charge")
	public String chargePage(Model model) {
		model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
		return "charge";
	}

	@PostMapping("/create-subscription")
	public @ResponseBody Response createSubscription(String email, String token, String plan, String coupon) {

		if (token == null || plan.isEmpty()) {
			return new Response(false, "Stripe payment token is missing. Please try again later.");
		}

		String customerId = newStripeService.createCustomer(email, token);

		if (customerId == null) {
			return new Response(false, "An error accurred while trying to create customer");
		}

		String subscriptionId = newStripeService.createSubscription(customerId, plan, coupon);

		if (subscriptionId == null) {
			return new Response(false, "An error accurred while trying to create subscription");
		}

		return new Response(true, "Success! your subscription id is " + subscriptionId);
	}

	@PostMapping("/cancel-subscription")
	public @ResponseBody Response cancelSubscription(String subscriptionId) {

		boolean subscriptionStatus = newStripeService.cancelSubscription(subscriptionId);

		if (!subscriptionStatus) {
			return new Response(false, "Faild to cancel subscription. Please try again later");
		}

		return new Response(true, "Subscription cancelled successfully");
	}

	@PostMapping("/coupon-validator")
	public @ResponseBody Response couponValidator(String code) {

		Coupon coupon = newStripeService.retriveCoupon(code);

		if (coupon != null && coupon.getValid()) {
			String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
					: coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
			return new Response(true, details);
		}
		return new Response(false, "This coupon code is not available. This may be because it has expired or has "
				+ "already been applied to your account.");
	}

	@PostMapping("/create-charge")
	public @ResponseBody Response createCharge(String email, String token) {

		if (token == null) {
			return new Response(false, "Stripe payment token is missing. please try again later.");
		}

		String chargeId = newStripeService.createCharge(email, token, 999);// 9.99 usd

		if (chargeId == null) {
			return new Response(false, "An error accurred while trying to charge.");
		}

		// You may want to store charge id along with order information

		return new Response(true, "Success your charge id is " + chargeId);
	}

}
