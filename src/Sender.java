import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;


public class Sender {

	private String username = null;
	private String password = null;
	private Properties props;

	public Sender(String username, String password) {

		this.username = username; 
		this.password = password;

		props = new Properties();

		//props.put("mail.smtp.auth"					,"true"			);
		//props.put("mail.smtp.startssl.enable"		,"true"			);
        //props.put("mail.smtp.host"					,"smtp.mail.ru"	);
        //props.put("mail.smtp.port"					,"465"			);
        //props.put("mail.smtp.user"					,"email@yandex.ru");
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        props.put("mail.smtp.user", "email@yandex.ru");
        props.put("mail.smtp.host", "smtp.yandex.ru" );
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
	}



	public boolean send (String text, String emailTo, String emailFrom, String subject) {
		Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
         });

            try {
            	 Message message = new MimeMessage(session);
                 //io eiai
                 message.setFrom(new InternetAddress(username));
                 //eiio
                 message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
                 //Caaieiaie ienuia
                 message.setSubject(subject);
                 //Niaa??eiia
                 message.setText(text);

                 //Ioi?aaeyai niiauaiea
                 Transport.send(message);

                 System.out.println("Send mail =D I'am happy!!!");
                 return false;
                 
            }catch(Exception e) {
            	System.out.println("\n Возникла ошибка при отправке сообщения:");
            	System.out.println(e);
            	return true;
            }
         }
}