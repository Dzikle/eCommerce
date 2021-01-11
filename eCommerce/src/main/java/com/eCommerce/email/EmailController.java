package com.eCommerce.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.repository.EmailRepository;



@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	EmailRepository mailRepo;

	@Autowired
	JavaMailSender mailSender;

	@Value("${destination.email}")
	String destinationEmail;

	@SuppressWarnings("rawtypes")
	@PostMapping("/sendToAdmin")
	public ResponseEntity sendEmailToAdmin(@RequestBody Message message) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(destinationEmail);
		msg.setFrom("noreply@baeldung.com");
		msg.setSubject(message.getSubject());
		msg.setText(message.getContent());

		mailRepo.save(message);

		mailSender.send(msg);

		return ResponseEntity.ok("Email sent from: " + message.getFromEmail() + " successfully! ");
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/send")
	public ResponseEntity sendEmail(@RequestBody Message message) {

		SimpleMailMessage msg = new SimpleMailMessage();
				
		msg.setTo(message.getToEmail());

		msg.setSubject(message.getSubject());
		msg.setText(message.getContent());

		mailRepo.save(message);

		mailSender.send(msg);

		return ResponseEntity.ok("Email sent from: " + message.getFromEmail() + " successfully! ");
	}

}
