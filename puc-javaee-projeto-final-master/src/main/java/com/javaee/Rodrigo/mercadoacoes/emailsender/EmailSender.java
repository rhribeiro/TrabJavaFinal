package src.main.java.com.javaee.Rodrigo.mercadoacoes.emailsender;

public class EmailSender {

	public void SendEmail(String toEmail, String subject, String body) {
		final String fromEmail = "example@gmail.com";
		final String password = "example";
		
		System.out.println("Initializing email send");
		
		EmailConfig config = new EmailConfig();
		
		config.sendEmail(fromEmail, password, toEmail, subject, body);
	}

}
