package com.ecom.util;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ecom.model.ProductOrder;
import com.ecom.model.UserDtls;
import com.ecom.service.UserService;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil 
{
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserService userService;
	
	public Boolean sendMail(String url, String email)
	{
		try 
		{
			MimeMessage mime = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mime);
			helper.setFrom("sawanchhetri57@gmail.com", "Shopping Cart");
			helper.setTo(email);
			
			String content = "<p>Hello,</p>"+"<p>You have reuested to reset your password.</p>"
			                +"<p>Click the link below to change your password:</p>"+"<p><a href=\""+url
			                +"\">Change my password</a></p>";
			
			helper.setSubject("Password Reset");
			helper.setText(content, true);
		
	        javaMailSender.send(mime);
	        return true; // Successfully sent
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception to the console or to a file
	        return false; // Return false if sending failed
	    }
	}
	
	public static String generateUrl(HttpServletRequest req)
	{
		String url = req.getRequestURL().toString();
		return url.replace(req.getServletPath(), "");
	}
	
	String msg = null;
	
	public Boolean sendMailForProductOrder(ProductOrder order, String statusCode) throws Exception
	{
		
		msg = "<p>Thank you [[name]] for your order. Your Order <b>[[orderStatus]]</b>.</p>"
				+"<p><b>Product Details : </b></p>"
				+"<p>Name : [[productName]]</p>"
				+"<p>Category : [[category]]</p>"
				+"<p>Quantity : [[quantity]]</p>"
				+"<p>Price : [[price]]</p>"
				+"<p>Payment Type : [[paymentType]]</p>";
		
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime);
		helper.setFrom("sawanchhetri57@gmail.com", "Shopping Cart");
		helper.setTo(order.getBillingAddress().getEmail());
		
		msg = msg.replace("[[name]]", order.getBillingAddress().getFirstName());
		msg = msg.replace("[[orderStatus]]", statusCode);
		msg = msg.replace("[[productName]]", order.getProduct().getTitle());
		msg = msg.replace("[[category]]", order.getProduct().getCategory());
		msg = msg.replace("[[quantity]]", order.getProduct().toString());
		msg = msg.replace("[[price]]", order.getPrice().toString());
		msg = msg.replace("[[paymentType]]", order.getPaymentType());
		
		helper.setSubject("Product Order Status");
		helper.setText(msg, true);
		javaMailSender.send(mime);
		
		return true;
	}
	
	public UserDtls getLoggedInUserDetails(Principal p)
	{
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}
}