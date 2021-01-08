package com.eCommerce.email;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String subject;
	
	private String content;
	
	private String fromEmail;
	
	private String toEmail;

	public Message(String name, String subject, String content, String fromEmail, String toEmail) {
		super();
		this.name = name;
		this.subject = subject;
		this.content = content;
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
	}
	
	

}
