package logik;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import rest.REST;
import security.Crypto;
import model.impl.FreigabeImpl;
import model.impl.UserImpl;
import model.Freigabe;
import model.User;

public class StartServer {

	public static SessionFactory factory;

	private static Thread restServer;
	public final static Logger logger = Logger.getLogger(StartServer.class);

	public static void main(String[] args) {
		if (logger.isInfoEnabled()) {
			logger.info("This is info : ");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("BeutenverwaltungServer wird gestartet" + new Date().toString());
		}
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		
		// Restserver wird gestartet
		restServer = new Thread(new REST());
		restServer.start();

		/* Add few employee records in database */
		// Integer id1 = UserImpl.addUser("test@test1.de", "Hugo", factory);
		// Integer id2 = UserImpl.addUser("tim@sport1.de", "Tim", factory);

		//FreigabeImpl.addFreigabe(new Freigabe(1, 1, 1, 2), factory);
		//FreigabeImpl.addFreigabe(new Freigabe(1, 1, 2), factory);
		//FreigabeImpl.getAllFreigabe(factory);
		
		
		logger.warn("This is warn : ");
		logger.error("This is error : ");
		logger.fatal("This is fatal : ");

	}

	// User Static Methoden
	public static List getUsers() {
		return UserImpl.getAllUsers(factory);
	}

	public static User getUserByLogin(String login) {
		List<User> temp = getUsers();
		for (User elem : temp) {
			if (elem.getLogin().equals(login))
				return elem;
		}
		logger.warn("User wurde nicht gefunden: UserId=" + login);
		return null;
	}

	public static int addUser(User u) {
		return UserImpl.addUser(u, factory);
	}

	public static void updateUser(User u) {
		UserImpl.updateUser(u, factory);
	}
	//Ueberprüft die Ligin Daten und sperrt bei mehrfacher Falscheingabe den jeweiligen Nutzer
	public static boolean checkLogin(String login, String password) {
		List<User> temp = getUsers();
		for (User elem : temp) {	
			if (elem.getLogin().equals(login) && elem.isAktiv()) {
				if (Crypto.hash(elem.getPassword(), elem.getSalt()).equals(password)) {
				//if (elem.getPassword().equals(password)) {
					elem.setAnmeldeversuche(0);
					updateUser(elem);
					return true;
				} else {
					// Hier wird nur das gehashte Passwort geloggt -> für Online-Bruteforce-Angriffe
					logger.warn("Falscher Anmeldeversuch: Login=" + login + " PW=" + password);
					elem.inkrementAnmeldeversuche();
					System.out.println(elem.getAnmeldeversuche());
					if (elem.getAnmeldeversuche() == configuration.Konfiguration.SPERRGRENZE) {
						elem.setAktiv(false);
						logger.warn("User mit ID="+elem.getUserId()+" wurde gesperrt!");
					}
					updateUser(elem);
				}
			}
		}

		return false;
	}

}
