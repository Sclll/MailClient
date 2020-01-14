package model;

import java.util.Properties;

import javax.mail.Session;

public class SmtpSession implements SessionFactory {
	private static SmtpSession smtpsession;
	private static Session session;
	private static Properties props;
	
	private SmtpSession() {
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.163.com");   
        props.put("mail.smtp.port","25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");	
	}

	@Override
	public Session getSession() {
		session = Session.getInstance(props, Authority.getAuthor());
		session.setDebug(true);
		return session;
	}

	@Override
	public Properties getProperties() {
		return props;
	}

	public static SmtpSession getFactorySession() {
		if (smtpsession == null) {
			smtpsession = new SmtpSession();
		}
		return smtpsession;
	}
	
}
