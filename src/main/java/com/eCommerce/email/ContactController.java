package com.eCommerce.email;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.eCommerce.entity.Product;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.services.UsersDetails;

@Controller
public class ContactController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	ProductRepository prodRepo;

	@GetMapping("/contact")
	public String showContactForm(Model model, @AuthenticationPrincipal UsersDetails userD) {
		if (userD != null) {
			model.addAttribute("user", userD.getUser());
		}
		return "/contactForm";
	}

	@PostMapping("/contact2")
	public String submitContact22(HttpServletRequest request) {
		String fullName = request.getParameter("fullname");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("contact@unusual.com");
		message.setTo("dziklet@gmail.com");

		String mailSubject = fullName + " has sent a message";
		String mailContent = "Sender Name: " + fullName + "\n";
		mailContent += "Email: " + email + "\n";
		mailContent += "Subject: " + subject + "\n";
		mailContent += "Content: " + content + "\n";

		message.setSubject(mailSubject);
		message.setText(mailContent);

		mailSender.send(message);

		return "message";
	}

	@PostMapping("/contact")
	public String submitContact(HttpServletRequest request) throws MessagingException, IOException {
		String fullName = request.getParameter("fullname");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");


		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		String mailSubject = fullName + " has sent a message";
		String mailContent = "<p><b>Sender Name:</b> " + fullName + "</p>";
		mailContent += "<p><b>Email: " + email + "</p>";
		mailContent += "<p><b>Subject: " + subject + "</p>";
		mailContent += "<p><b>Content: " + content + "</p>";
		mailContent += "<hr><img align='center' src='cid:crnamajca'>";
		
		

		helper.setFrom("dziklet@gmail.com", "Contact");
		helper.setTo("dziklet@gmail.com");
		helper.setSubject(mailSubject);
		helper.setText(mailContent, true);
		ClassPathResource resource = new ClassPathResource("/static/photos/palto.jpg");
		helper.addInline("palto", resource);

		mailSender.send(message);

		return "message";
	}
}
