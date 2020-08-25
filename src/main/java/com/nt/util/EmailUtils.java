package com.nt.util;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.nt.domain.UserAccount;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;
	 
	public boolean send(String to,String subject,String body) {
		try {
		SimpleMailMessage msg=new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		mailSender.send(msg);
		return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean sendEmailNew(String to,String subject,String body ) {
		try {
			MimeMessage mimeMsg=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMsg);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			mailSender.send(mimeMsg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public  boolean sendUserAccUnlockEmail(UserAccount userAcc) {
		boolean isSent=false;
		try {
			
			MimeMessage msg=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(msg);
			helper.setTo(userAcc.getEmailId());
			helper.setSubject("Unlock your account");
			helper.setText(getUnlockAccEmailBody(userAcc),true);
		mailSender.send(msg);
			isSent=true;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSent;
	}
	
			private String getUnlockAccEmailBody(UserAccount userAcc) throws Exception{
		
				StringBuffer sb=new StringBuffer("");
				
							FileReader file=new FileReader("UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt");
							BufferedReader br=new BufferedReader(file);
							String line=br.readLine();
							
							while(line!=null) {
								sb.append(line);
								line=br.readLine();
							}
							//format mailBody to String
							String mailBody=sb.toString();
							mailBody=mailBody.replace("{FNAME}", userAcc.getFirstName());
							mailBody=mailBody.replace("{LNAME}", userAcc.getLastName());
							mailBody=mailBody.replace("{TEMP-PWD}",userAcc.getPassword());
							mailBody=mailBody.replace("{EMAIL}", userAcc.getEmailId());
							
							br.close();
				return mailBody;
	}
}
