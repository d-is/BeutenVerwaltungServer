package model.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.User;

public class UserImpl {

	/* Method to CREATE an employee in the database */
	public static Integer addUser(User newUser, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userId = null;
		try {
			tx = session.beginTransaction();
			userId = (Integer) session.save(newUser);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userId;
	}

	/* Method to READ all the employees */
	public static List<User> getAllUsers(SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("from model.User");
			users = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	public static void updateUser(User u, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User temp = (User) session.get(User.class, u.getUserId());
			
			temp.setAktiv(u.isAktiv());
			temp.setAnmeldeversuche(u.getAnmeldeversuche());
			temp.setLogin(u.getLogin());
			temp.setNachname(u.getNachname());
			temp.setOrt(u.getOrt());
			temp.setPassword(u.getPassword());
			temp.setPlz(u.getPlz());
			temp.setSalt(u.getSalt());
			temp.setStrasse(u.getStrasse());
			temp.setTelefonnummer(u.getTelefonnummer());
			temp.setVersion(u.getVersion()+1);
			session.update(temp);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void deleteUser(Integer userId, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User temp = (User) session.get(User.class, userId);
			//Wegen der Konsistenz sollte der User nicht geloescht werden
			session.delete(temp);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
