package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class ParseMail {
	public static String getFrom(MimeMessage message) throws MessagingException, UnsupportedEncodingException {
		Address[] froms = message.getFrom();
		if (froms.length < 1) return "null";
		InternetAddress address = (InternetAddress) froms[0];
		String from;
		String person = address.getPersonal();  
        if (person != null) {  
            person = MimeUtility.decodeText(person) + " ";  
        } else {  
            person = "NoName";  
        }  
        from = person; 
        return from;
	}
	
	public static String getFromAddress(MimeMessage message) throws MessagingException, UnsupportedEncodingException {
		Address[] froms = message.getFrom();
		if (froms.length < 1) return "null";
		InternetAddress address = (InternetAddress) froms[0]; 
        return address.toString();
	}
	
	public static String getDate(MimeMessage msg) throws MessagingException {
		Date receivedDate = msg.getSentDate();  
        if (receivedDate == null)  return "";  
        
        String pattern = "yyyy年MM月dd日 E HH:mm ";  
          
        return new SimpleDateFormat(pattern).format(receivedDate);  
	}
	
	public static void deleteMessage(Message message) throws MessagingException {  
        if (message == null ) throw new MessagingException("未找到要解析的邮件!");  
          
            String subject = message.getSubject();
            // set the DELETE flag to true
            message.setFlag(Flags.Flag.DELETED, true);
            System.out.println("Marked DELETE for message: " + subject);
    } 

	
	 public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {  
        return MimeUtility.decodeText(msg.getSubject());  
    }
	
	 public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {  
	        StringBuffer receiveAddress = new StringBuffer();  
	        Address[] addresss = null;  
	        if (type == null) {   
	            addresss = msg.getAllRecipients();  
	        } else {  
	            addresss = msg.getRecipients(type);  
	        }  
	          
	        if (addresss == null || addresss.length < 1)  
	            throw new MessagingException("没有收件人!");  
	        for (Address address : addresss) {  
	            InternetAddress internetAddress = (InternetAddress)address;  
	            receiveAddress.append(internetAddress.toUnicodeString()).append(",");  
	        }  
	          
	        receiveAddress.deleteCharAt(receiveAddress.length()-1); //删除最后一个逗号  
	          
	        return receiveAddress.toString();  
	    }
	
	public static boolean isContainAttachment(Part part) throws MessagingException, IOException{  
        boolean flag = false;  
        if (part.isMimeType("multipart/*")) {  
            MimeMultipart multipart = (MimeMultipart) part.getContent();  
            int partCount = multipart.getCount();  
            for (int i = 0; i < partCount; i++) {  
                BodyPart bodyPart = multipart.getBodyPart(i);  
                String disp = bodyPart.getDisposition();  
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {  
                    flag = true;  
                } else if (bodyPart.isMimeType("multipart/*")) {  
                    flag = isContainAttachment(bodyPart);  
                } else {  
                    String contentType = bodyPart.getContentType();  
                    if (contentType.indexOf("application") != -1) {  
                        flag = true;  
                    }    
                      
                    if (contentType.indexOf("name") != -1) {  
                        flag = true;  
                    }   
                }  
                  
                if (flag) break;  
            }  
        } else if (part.isMimeType("message/rfc822")) {  
            flag = isContainAttachment((Part)part.getContent());  
        }  
        return flag;  
    }
	
	public static boolean isSeen(MimeMessage msg) throws MessagingException {  
        return msg.getFlags().contains(Flags.Flag.SEEN);  
    } 
	
	public static void getContent(Part part,StringBuffer content) throws MessagingException, IOException {
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;   
        if (part.isMimeType("text/plain") && !isContainTextAttach) {  
            content.append(part.getContent().toString());  
        } else if (part.isMimeType("text/html") && !isContainTextAttach) {
        	content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {   
            getContent((Part)part.getContent(),content);  
        } else if (part.isMimeType("multipart/*")) {  
            Multipart multipart = (Multipart) part.getContent();  
            int partCount = multipart.getCount();  
            for (int i = 0; i < partCount; i++) {  
                BodyPart bodyPart = multipart.getBodyPart(i);  
                getContent(bodyPart,content);  
            }  
        }  
	}
	
	 private static void saveFile(InputStream is, String destDir, String fileName) throws FileNotFoundException, IOException {  
        BufferedInputStream bis = new BufferedInputStream(is);  
        BufferedOutputStream bos = new BufferedOutputStream(  
                new FileOutputStream(new File(destDir + fileName)));  
        int len = -1;  
        while ((len = bis.read()) != -1) {  
            bos.write(len);  
            bos.flush();  
        }  
        bos.close();  
        bis.close();  
	 }  
	 
	 public static String decodeText(String encodeText) throws UnsupportedEncodingException {  
        if (encodeText == null || "".equals(encodeText)) {  
            return "";  
        } else {  
            return MimeUtility.decodeText(encodeText);  
        }  
    }
	 
	 public static void saveAttachment(Part part, String destDir) throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {  
		 if (part.isMimeType("multipart/*")) {  
		     Multipart multipart = (Multipart) part.getContent();    //复杂体邮件  
		     //复杂体邮件包含多个邮件体  
		     int partCount = multipart.getCount();  
		     for (int i = 0; i < partCount; i++) {  
		         //获得复杂体邮件中其中一个邮件体  
		         BodyPart bodyPart = multipart.getBodyPart(i);  
		         //某一个邮件体也有可能是由多个邮件体组成的复杂体  
		         String disp = bodyPart.getDisposition();  
		         if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {  
		             InputStream is = bodyPart.getInputStream();  
		             saveFile(is, destDir, decodeText(bodyPart.getFileName()));  
		         } else if (bodyPart.isMimeType("multipart/*")) {  
		             saveAttachment(bodyPart,destDir);  
		         } else {  
		             String contentType = bodyPart.getContentType();  
		             if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {  
		                 saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()));  
		             }  
		         }  
		     }  
		 } else if (part.isMimeType("message/rfc822")) {  
		     saveAttachment((Part) part.getContent(),destDir);  
		 }  
	} 
}
