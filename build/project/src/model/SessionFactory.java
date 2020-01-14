package model;

import java.util.Properties;

import javax.mail.Session;

public interface SessionFactory {
	Session getSession();
	Properties getProperties();
}
