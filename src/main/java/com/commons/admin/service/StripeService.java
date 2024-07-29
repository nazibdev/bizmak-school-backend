package com.commons.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.commons.admin.models.PaymentInfo;
import com.commons.admin.models.PaymentIntention;
import com.commons.admin.repository.PaymentInfoRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.model.checkout.Session.PhoneNumberCollection;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.BillingAddressCollection;
import com.stripe.Stripe;
import com.stripe.param.PaymentIntentListParams;
@Service
public class StripeService {
	
	
	@Value("${stripe.api.key}")
    private String stripeApiKey;
	
	@Autowired
    private PaymentInfoRepository paymentInfoRepository;
	
//	static class CreatePayment {
//	    @SerializedName("items")
//	    Object[] items;
//
//	    public Object[] getItems() {
//	      return items;
//	    }
//	  }
//
//	  static class CreatePaymentResponse {
//		
//	    private String clientSecret;
//	    public CreatePaymentResponse(String clientSecret) {
//	      this.setClientSecret(clientSecret);
//	    }
//		public String getClientSecret() {
//			return clientSecret;
//		}
//		public void setClientSecret(String clientSecret) {
//			this.clientSecret = clientSecret;
//		}
//	  }
//
//	  static int calculateOrderAmount(Object[] items) {
//	    // Replace this constant with a calculation of the order's amount
//	    // Calculate the order total on the server to prevent
//	    // people from directly manipulating the amount on the client
//	    return 1400;
//	  }

    public String createPaymentIntent(long amount) {
        Stripe.apiKey = stripeApiKey;

        List<String> response = new ArrayList<>();
        
        Map<String, Object> automaticPaymentMethods =
        		  new HashMap<>();
        		automaticPaymentMethods.put("enabled", true);
        		Map<String, Object> params = new HashMap<>();
        		params.put("amount", amount);
        		params.put("currency", "usd");
        		params.put(
        		  "automatic_payment_methods",
        		  automaticPaymentMethods
        		);

        		PaymentIntent paymentIntent = null;
				try {
					paymentIntent = PaymentIntent.create(params);
				} catch (StripeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
				
				PaymentIntention intention = new PaymentIntention();
				intention.setIntentId(paymentIntent.getId());
				intention.setClientSecret(paymentIntent.getClientSecret());
				System.out.println(paymentIntent.getClientSecret());
				response.add(paymentIntent.getClientSecret());
				response.add(paymentIntent.getId());
				
//				CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
				
        		return paymentIntent.getClientSecret();
        
        
        
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", amount);
//        params.put("currency", "usd");
//      
//        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
//
//        			.setAmount(amount)
//        			.setCurrency("usd")
//        			.build();
//        
//        
//        
//
//        try {
//            PaymentIntent paymentIntent = PaymentIntent.create(createParams);
//            return paymentIntent.getClientSecret();
//        } catch (StripeException e) {
//            // Handle any errors
//            e.printStackTrace();
//            return null;
//        }
    }
    
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }
    
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }
    
    public Charge chargeNewCard(String token, double amount) throws Exception {
    	Stripe.apiKey = stripeApiKey;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        System.out.println(" New Card Charge Status is " + charge.getStatus());
        return charge;
    }
    
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

	public String testCheckout(long amount, String courseName, String email) {
		SessionCreateParams params =
		        SessionCreateParams.builder()
		          .setMode(SessionCreateParams.Mode.PAYMENT)
		          .setCustomerEmail(email)
		          .setInvoiceCreation(
		        	      SessionCreateParams.InvoiceCreation.builder().setEnabled(true).build()
		        	    )
		          .setSuccessUrl("http://localhost:3000/success?session_id={CHECKOUT_SESSION_ID}")
		          .setCancelUrl("http://localhost:3000/cancel")
		          .addLineItem(
		        		  		SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
		        		  				SessionCreateParams.LineItem.PriceData.builder()
		        		  											.setCurrency("usd")
		        		  											.setUnitAmount(amount)
		        		  											.setProductData(
		        		  		SessionCreateParams.LineItem.PriceData.ProductData.builder()
		                    										.setName(courseName)
		                    										.setDescription("If you want to make payment arrangement please contact us "
		                    												+ "by Go back to Contact Us form and in the Subject enter "
		                    												+ "Recurring payment and In description how you want to plan payment")
		                    .build())
		                .build())
		            .build())
		          .setBillingAddressCollection(BillingAddressCollection.REQUIRED)
		          .build();

		      Session session = null;
			try {
				session = Session.create(params);
				
			} catch (StripeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		      response.redirect(session.getUrl(), 303);
		      return session.getUrl();
		
	}

	
	public String createCharge(String email, String token, Long amount) {
		
		String chargeId = null;
		
		try {
			Stripe.apiKey = stripeApiKey;
			
			Map<String, Object> chargeParams = new HashMap<>();
			chargeParams.put("description","Charge for "+email);
			chargeParams.put("currency","usd");
			chargeParams.put("amount",amount);
			chargeParams.put("source",token);
			
			Charge charge = Charge.create(chargeParams);
			
		    chargeId = charge.getId();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chargeId;
	}
	
	
	public String succesfulPayment(String clientSecret) {
		Stripe.apiKey = stripeApiKey;

//        try {
//            // Create parameters to retrieve successful payments
//            Map<String, Object> params = new HashMap<>();
//            params.put("payment_intent.status", "succeeded");
//
//            // Retrieve a list of successful PaymentIntents
//            PaymentIntentListParams listParams = PaymentIntentListParams.builder()
//                    .putAllExtraParam(params)
//                    .build();
//
//            Iterable<PaymentIntent> paymentIntents = PaymentIntent.list(listParams).autoPagingIterable();
//
//            // Process retrieved payment information
//            for (PaymentIntent paymentIntent : paymentIntents) {
//                System.out.println("PaymentIntent ID: " + paymentIntent.getId());
//                System.out.println("Amount: " + paymentIntent.getAmount());
//                // Add more information as needed
//            }
//
//        } catch (StripeException e) {
//            e.printStackTrace();
//        }
		
		String paymentIntentId = "your_payment_Intent_Id";

        try {
            // Retrieve the Checkout Session
            Session session = Session.retrieve(clientSecret);

            // Extract the payment ID from the Checkout Session
            paymentIntentId = session.getPaymentIntent();
            
            System.out.println("customer info " +session.getCustomer());
            System.out.println("customer email " +session.getCustomerEmail());
            System.out.println("customer invoice " +session.getInvoice());

            // You can also access other payment-related information in the Session
            // such as session.getPaymentStatus(), session.getPaymentMethod(), etc.

            System.out.println("Payment Intent ID: " + paymentIntentId);
            
            
            
            PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setAmount(session.getInvoice());
			paymentInfo.setCourseName(session.getInvoice());
			paymentInfo.setEmailAddress(session.getCustomerEmail());
			paymentInfo.setClientSecret(paymentIntentId);
			
			paymentInfoRepository.save(paymentInfo);

        } catch (StripeException e) {
            System.err.println("Error retrieving payment ID: " + e.getMessage());
            // Handle the exception as needed
        }
        return paymentIntentId;
	}
	
	
}
