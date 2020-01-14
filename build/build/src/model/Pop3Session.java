package model;

import java.util.Properties;

import javax.mail.Session;

public class Pop3Session implements SessionFactory{
	private static Pop3Session pop3session;
	private static Session session;
	private static Properties prop;
	
	private Pop3Session() {
		prop = new Properties();
		prop.put("mail.store.protocol", "pop3s");
	    prop.put("mail.pop3s.host", "pop.163.com");
	    prop.put("mail.pop3s.port", "995");
	    prop.put("mail.pop3s.starttls.enable", "true");
	    prop.put("mail.pop3s.socketFactory.class",
	            "javax.net.ssl.SSLSocketFactory" ); 
	    prop.put("mail.pop3s.auth", "true"); 
	}

	@Override
	public Session getSession() {
		session = Session.getDefaultInstance(prop,Authority.getAuthor());
		session.setDebug(true);
		return session;
	}

	@Override
	public Properties getProperties() {
		return prop;
	}

	public static Pop3Session getFactorySession() {
		if (pop3session == null) {
			pop3session = new Pop3Session();
		}
		return pop3session;
	}
}
