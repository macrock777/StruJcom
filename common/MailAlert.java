package com.efive.agencyonline.common;

import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;


public class MailAlert {
	public  static boolean  singleMail(String frommail, String pwd , String toemail, String msgsubject, String msgbody, String attechment,String filename){		// attechment == path of Attechment
		try{
			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			List<InternetAddress> bccList = new ArrayList<InternetAddress>();
			List<InternetAddress> ccList = new ArrayList<InternetAddress>();
			toList.add(new InternetAddress(toemail));
			
			EmailAttachment attachment = new EmailAttachment();
			if(null!=attechment && attechment.length()>0){
				if(null!=filename && filename.length()>0){
					String[] extention = attechment.split("\\.");
					attachment.setName(filename+"."+extention[extention.length -1]);
				}
				attachment.setPath(attechment);
				}
			
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			//email.setSmtpPort(587);
			email.setSmtpPort(465);
			
			email.setAuthenticator(new DefaultAuthenticator(frommail, pwd));
			email.setSSLOnConnect(true);
			if(null!=attechment  && attechment.trim().length()>0){
				email.attach(attachment);
				}
			email.setFrom(frommail);
			email.setSubject(msgsubject);
			email.setMsg(msgbody);
			email.setTo(toList);
			//email.setCc(ccList);
			//email.setBcc(bccList);
			email.send();
			System.out.println("email Sent");
			return true;
		}catch(Exception e){
			System.out.println("Email sending Fail!");
			e.printStackTrace();
			return false;
		}
		
}
	
	public static  boolean  multipleMail(String frommail, String pwd , List toemail, String msgsubject, String msgbody, String attechment ,String filename){			// attechment == path of Attechment
		
		try{
			if(null !=toemail && toemail.size()>0){
						List<InternetAddress> toList = new ArrayList<InternetAddress>();
						//List<InternetAddress> bccList = new ArrayList<InternetAddress>();
						//List<InternetAddress> ccList = new ArrayList<InternetAddress>();
				
									for(int i=0; i<toemail.size(); i++){
										System.out.println(i+" "+toemail.get(i).toString());
									toList.add(new InternetAddress(toemail.get(i).toString()));
									}
									
									EmailAttachment attachment = new EmailAttachment();
									if(null!=attechment && attechment.length()>0){
										if(null!=filename && filename.length()>0){
											String[] extention = attechment.split("\\.");
											attachment.setName(filename+"."+extention[extention.length -1]);
										}
									attachment.setPath(attechment);
									}
									
									HtmlEmail email = new HtmlEmail();
									email.setHostName("smtp.gmail.com");
									//email.setSmtpPort(587);
									email.setSmtpPort(465);
									
									email.setAuthenticator(new DefaultAuthenticator(frommail, pwd));
									email.setSSLOnConnect(true);
									if(null!=attechment  && attechment.trim().length()>0){
										email.attach(attachment);
										}
									email.setFrom(frommail);
									email.setSubject(msgsubject);
									email.setMsg(msgbody);
									email.setTo(toList);
									//email.setCc(ccList);
									//email.setBcc(bccList);
									email.send();
									System.out.println("email Sent");
									return true;
			}else{
					System.out.println("There is not any mail id in List");
					return true;
			}
		}catch(Exception e){
			System.out.println("Email sending Fail!");
			e.printStackTrace();
			return false;
		}
	}

	// If mail host  is not gmail 
	public  static boolean  singleMailwithOtherHost(String frommail, String pwd , String toemail, String msgsubject, String msgbody, String attechment,String filename, String hostName, String portNumber){		// attechment == path of Attechment
		
		try{
			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			List<InternetAddress> bccList = new ArrayList<InternetAddress>();
			List<InternetAddress> ccList = new ArrayList<InternetAddress>();
			toList.add(new InternetAddress(toemail));
			
			EmailAttachment attachment = new EmailAttachment();
			if(null!=attechment && attechment.length()>0){
				if(null!=filename && filename.length()>0){
					String[] extention = attechment.split("\\.");
					attachment.setName(filename+"."+extention[extention.length -1]);
				}
				attachment.setPath(attechment);
				}
			
			HtmlEmail email = new HtmlEmail();
			
			if(null != hostName && hostName.trim().length()>0)
				email.setHostName(hostName.trim());
			else
				email.setHostName("	smtp.gmail.com");
			
			//email.setSmtpPort(587);
			if(null != portNumber && portNumber.trim().length()>0 &&  portNumber.matches("\\d+"))
				email.setSmtpPort(Integer.parseInt(portNumber.trim()));
			else
				email.setSmtpPort(465);
			
			email.setAuthenticator(new DefaultAuthenticator(frommail, pwd));
			email.setSSLOnConnect(true);
			if(null!=attechment  && attechment.trim().length()>0){
				email.attach(attachment);
				}
			email.setFrom(frommail);
			email.setSubject(msgsubject);
			email.setMsg(msgbody);
			email.setTo(toList);
			//email.setCc(ccList);
			//email.setBcc(bccList);
			email.send();
			System.out.println("email Sent");
			return true;
		}catch(Exception e){
			System.out.println("Email sending Fail!");
			e.printStackTrace();
			return false;
		}
		
}
	
	
}
