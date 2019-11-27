package HDBMS;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendMail {
    public static void sendit(String to,String Usr, String pss) {

        String from = "mohammadanas.cse@gmail.com";
        String username = "mohammadanas.cse";
        String password = "Anas.1311";
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("UserName-PassWord");
            message.setText("UserName : " + Usr + "\n PassWord : " + pss);

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException var15) {
            throw new RuntimeException(var15);
        }
    }
}
