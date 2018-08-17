package com.efive.services;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class Mailalert {
	
	public void sendalert(String toemail,String mailsubject,String msgbody, String path, String filename) {
		
	//	String AdminmailId="efivetesting@gmail.com";
		/*String DefaultmailId="efivedemo@gmail.com";      // allow Low security  from gmail setting  before using this mailid
		String Password="efive12345";*/
		String AdminmailId="efivedemo@gmail.com";
		String DefaultmailId="demotesting81@gmail.com";
		String Password="!123456!";
		
		
		
		List<InternetAddress> toList = new ArrayList<InternetAddress>();
		List<InternetAddress> bccList = new ArrayList<InternetAddress>();
		//System.out.println("filename :  =======  "+filename);
		/*try {
			bccList.add(new InternetAddress("harshad99@gmail.com"));
			bccList.add(new InternetAddress("karnpatel@gmail.com"));
		} catch (AddressException e1) {
			e1.printStackTrace();
		}*/
		/*
		 * for (int i = 0; i < mail24List.size(); i++) { if (null !=
		 * mail24List.get(i)) { try { toList.add(new
		 * InternetAddress(mail24List.get(i))); } catch (AddressException e) {
		 * e.printStackTrace(); } }
		 * 
		 * }
		 */
		try {
			
		//	System.out.println("tomail ========="+toemail);
			toList.add(new InternetAddress(toemail));
			toList.add(new InternetAddress(AdminmailId));
			
		/*	HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(
					"demotesting81@gmail.com", "!123456!"));
			email.setSSLOnConnect(true);
			email.setFrom("demotesting81@gmail.com");
			email.setSubject("Requisitions not processed after 24 hours");
			email.setMsg("Test");
			email.setTo(toList);
			//email.setBcc(bccList);
			email.send();*/
			
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//	Calendar calendar = Calendar.getInstance();
			//calendar.add(Calendar.DATE, -1);
		//	String bkupdate = dateFormat.format(calendar.getTime());
			//System.out.println("bkupdate=" + bkupdate);
			
			EmailAttachment attachment = new EmailAttachment();
			if(null!=filename){
			attachment.setName(filename);
			attachment.setPath(path);}
		//	List<InternetAddress> bkuptoList = new ArrayList<InternetAddress>();
		//	bkuptoList.add(new InternetAddress("demotesting81@gmail.com"));
			//bkuptoList.add(new InternetAddress("harshad99@gmail.com"));
			HtmlEmail emailbkup = new HtmlEmail();
			emailbkup.setHostName("smtp.gmail.com");
			//emailbkup.setSmtpPort(587);
			emailbkup.setSmtpPort(465);
			emailbkup.setAuthenticator(new DefaultAuthenticator(DefaultmailId, Password));
			emailbkup.setSSLOnConnect(true);
			if(null!=filename){
			emailbkup.attach(attachment);
			}
			emailbkup.setFrom(DefaultmailId);
			emailbkup.setSubject(mailsubject);
			emailbkup.setMsg(msgbody);
			//emailbkup.setTo(bkuptoList);
			emailbkup.setTo(toList);
			emailbkup.send();
			System.out.println("email Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
