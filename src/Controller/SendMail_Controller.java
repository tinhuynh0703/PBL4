package Controller;

import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import Model.Hose;
import javafx.collections.ObservableList;

public class SendMail_Controller {

   public static void sendmail(String s, String kq) {
	   
       try {
    	   	Properties props = new Properties();
   			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
   			props.put("mail.smtp.port", "587"); //TLS Port
   			props.put("mail.smtp.auth", "true"); //enable authentication
   			props.put("mail.smtp.starttls.enable", "true"); //enable 
           Email email = new SimpleEmail();

           // Cấu hình thông tin Email Server
           email.setHostName("smtp.googlemail.com");
           email.setSmtpPort(465);
           email.setAuthenticator(new DefaultAuthenticator(SMailConstant.MY_EMAIL,
                   SMailConstant.MY_PASSWORD));

           // Với gmail cái này là bắt buộc.
           email.setSSLOnConnect(true);

           // Người gửi
           email.setFrom(SMailConstant.MY_EMAIL);

           // Tiêu đề
           email.setSubject("Test Email");

           // Nội dung email

           email.setMsg(kq);        	   

           // Người nhận
           email.addTo(s);
           email.send();
           System.out.println("Sent!!");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}