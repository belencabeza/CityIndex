package CI.City_Index;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Ignore;
import org.junit.Test;

public class TryStuff {

	@Test
	public void test() {
		//final String from = "pdelgado@beltrixsf.com";
		//String to = "pdelgado@beltrixsf.com";
		//EmailContent emailContent = new EmailContent();
		//ManageFiles manageFiles = new ManageFiles();
		//PropertiesManager propertyManager = new PropertiesManager();
		//File dir = new File(propertyManager.getProperty("properties.xml", "excel location")+"Local\\");
		//String zipDirName = "na";
		//manageFiles.zipDirectory(dir, zipDirName);
		
		String messageBody="Hola Belen,\n\nCobro barato x explicar el codigo. Te veo a las 9 en la 9.\n\nSaludos,\nPablo";
		

		String host = "65.254.250.100";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "65.254.250.100");
		props.put("mail.smtp.port", "465");
		props.put("mail.debug", "true");  
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("mcabeza", "sanra-1");
					}
				});
		
		
		Properties properties = System.getProperties();

		// Mail server
		properties.setProperty("mail.smtp.host", host);
		
		// Get the default Session object.
		//Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress("mcabeza@belatrixsf.com"));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"pdelgado@belatrixsf.com"));
			
			
			//String messageBody = emailContent.getContent(project);
			
			// Set Subject
			
			message.setSubject("Este soy yo careteando q me anda el codigo");
			
			

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(messageBody); /*args[3].replace("\\n", "\n")*///Use replace because Jenkins escapes the \

			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			//String filename = "c:\\Local Automation\\Results.zip";
			//DataSource source = new FileDataSource(filename);
			//messageBodyPart.setDataHandler(new DataHandler(source));
			//messageBodyPart.setFileName("results.zip");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
	

}
