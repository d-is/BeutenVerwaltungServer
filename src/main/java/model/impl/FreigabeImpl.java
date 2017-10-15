package model.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Freigabe;
import model.User;

public class FreigabeImpl {

	public static Integer addFreigabe(Freigabe newFreigabe, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer freigabeId = null;
		//Hier muss eine Authentifizierung für die Beute rein -> sonts kann jeder eine Freigabe erstellen
		try {
			tx = session.beginTransaction();
			freigabeId = (Integer) session.save(newFreigabe);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return freigabeId;
	}
	public static List<Freigabe> getAllFreigabe(SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Freigabe> freigaben = null;
		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("from model.Freigabe");
			freigaben = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return freigaben;
	}

	public static void updateFreigabe(Freigabe u, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Freigabe temp = (Freigabe) session.get(Freigabe.class, u.getFreigabeId());
			
			temp.setBeuteId(u.getBeuteId());
			temp.setUserId(u.getUserId());
			temp.setRechtenummer((u.getRechtenummer()));
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

	public void deleteFreigabe(Integer freigabeId, SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Freigabe temp = (Freigabe) session.get(Freigabe.class, freigabeId);
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
