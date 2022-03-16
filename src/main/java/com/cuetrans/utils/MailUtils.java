package com.cuetrans.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class MailUtils {

	private static Logger LOGGER = Logger.getLogger(MailUtils.class);

	public static void sendEmail(String host, String port, final String userName, final String password,
			String recipient, String subject, String message, File attachFile)
			throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "false");
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		// creates a new session with an authenticator
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(recipient) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachFile != null) {
			MimeBodyPart attachPart = new MimeBodyPart();

			try {
				attachPart.attachFile(attachFile);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			multipart.addBodyPart(attachPart);
		}
		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);
	}

	public static void sendEmailWithAttachment(String host, String port, String smtpAuth, final String userName,
			final String password, String fromAddress, String toAddress, String ccAddress , String subject, String messageText , String filename, String filename2 ) {
		LOGGER.info("Sending Mail");
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", smtpAuth);
		properties.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		// 2) compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddress));
			
			InternetAddress[] parseTO = InternetAddress.parse(toAddress , true);
			message.setRecipients(javax.mail.Message.RecipientType.TO,  parseTO);
			
			InternetAddress[] parseCC = InternetAddress.parse(ccAddress , true);
			message.setRecipients(javax.mail.Message.RecipientType.CC,  parseCC);
			
			//message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			message.setSubject(subject);

			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(messageText);

			// 4) create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(new File(filename).getName());

			DataSource source2 = new FileDataSource(filename2);
			messageBodyPart3.setDataHandler(new DataHandler(source2));
			messageBodyPart3.setFileName(new File(filename2).getName());
			
			// 5) create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
			multipart.addBodyPart(messageBodyPart3);
			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			Transport.send(message);
			LOGGER.info("Sending Mail.....");

			LOGGER.info("Sent Mail to " + toAddress + " from " + fromAddress);
		} catch (MessagingException ex) {
			LOGGER.error("Problem sending mail :" + ex.getMessage());
			ex.printStackTrace();
		}

	}
}
