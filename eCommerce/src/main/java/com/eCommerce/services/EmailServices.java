package com.eCommerce.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eCommerce.email.Message;
import com.eCommerce.entity.Product;
import com.eCommerce.entity.proccesedShoppingCart;
import com.eCommerce.entity.soldProduct;
import com.eCommerce.repository.EmailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailServices {

	@Value("${destination.email}")
	String destinationEmail;
	@Autowired
	EmailRepository mailRepo;

	@Autowired
	JavaMailSender mailSender;

	public void sendEmailToBuyer(proccesedShoppingCart userCart){

		Message message = createMessage(userCart);

		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(userCart.getEmail());
			msg.setFrom(destinationEmail);
			msg.setSubject(message.getSubject());
			msg.setText(message.getContent());
			mailSender.send(msg);
		} catch (Exception e) {
			System.err.println(e);
		}
		mailRepo.save(message);
	}

	private Message createMessage(proccesedShoppingCart proCart) {
		Message message = new Message();
		String listProducts = "The Order: \r\n";
		
		for (soldProduct product : proCart.getProducts()) {
			listProducts=(new StringBuilder()).append(listProducts).append(product.getProduct().getName() +" = ").append(product.getProduct().getPrice()).append("\r\n").toString();  
		}
		message.setToEmail(proCart.getEmail());
		message.setName("Order details for " + proCart.getUser().getFirstName());
		message.setSubject("Order details for " + proCart.getUser().getFirstName() + " " + proCart.getUser().getLastName()
				+ " from Unusual.inc");
		message.setContent(listProducts + "\r\n" + " <h1>The total sum: </h1>" + proCart.getTotal() +"\r\n paid by  "+ proCart.getPayment());
		message.setFromEmail(destinationEmail);
		return message;
	}

}
