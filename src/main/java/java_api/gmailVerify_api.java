package java_api;

import model.User;

import javax.mail.*;
import java.util.Properties;
import java.util.Random;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class gmailVerify_api {
    public String getRandom() {
        Random rd = new Random();
        int num = rd.nextInt(999999);
        return String.format("%06d", num);
    }

    public boolean sendEmail(User user, String code) {
        boolean sended = false;

        String toEmail = user.getUserGmail();
        String fromEmail = "tmtbank69@gmail.com";
        String passEmail = "lcfm spsk dbal igwm";

        try {

            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, passEmail);
                }
            });

            String content = "Dear user,<br><br>Your unique verification code for completing the authentication process is <b>" + code + "</b>.<br>Please enter this code within the designated field to proceed.<br>Ensure the confidentiality of this code for your security.<br><br>Thank you for your cooperation.";
            Message mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setSubject("Henix Anime Film Web Verification Code:");
            mess.setFrom(new InternetAddress(fromEmail, "HENIX Anime Film"));
            mess.setContent(content, "text/html");

            Transport.send(mess);
            sended = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sended;
    }
}
