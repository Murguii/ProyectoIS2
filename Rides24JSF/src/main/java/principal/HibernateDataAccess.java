package principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.UtilDate;
import modelo.dominio.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import modelo.JPAUtil;

public class HibernateDataAccess {

	public HibernateDataAccess() {}

	public Driver storeDriver(String email, String name, String password) { // register
		 EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			Driver d = new Driver();
			d.setEmail(email);
			d.setName(name);
			d.setPassword(password);
			em.persist(d);

			em.getTransaction().commit();
			return d;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}
	
	public Driver getDriver(String email, String password) {
		 EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Driver> d = em.createQuery("SELECT d FROM Driver d WHERE d.email =:email AND d.password =:password", Driver.class);
			d.setParameter("email", email);
			d.setParameter("password", password);
			//return d.getSingleResult();
			List<Driver> result = d.getResultList();
	        if (result.isEmpty()) {
	            return null;
	        } else {
	            return result.get(0);
	        }
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public Ride storeRide(String from, String to, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {// createRide
		 EntityManager em = JPAUtil.getEntityManager();
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException();
			}
			em.getTransaction().begin();
			
			Driver d = em.find(Driver.class, driverEmail);
			
			if (d == null) {
	            throw new IllegalArgumentException("Driver not found with email: " + driverEmail);
			}
			
			if (d.doesRideExists(from, to, date)) {
				em.getTransaction().commit();
				throw new RideAlreadyExistException();
			}
			
			Ride ride = d.addRide(from, to, date, nPlaces, price);
			//next instruction can be obviated
			em.persist(d); 
			
			//em.persist(ride);
			em.getTransaction().commit();
			return ride;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		
		} finally {
			em.close();
		}
	}

	public List<Ride> getRides(String dc, String ac, Date d) { // queryRides
		 EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<Ride> q = em.createQuery("SELECT r FROM Ride r WHERE r.\"from\" =:f AND r.to =:t AND r.date =:d",
					Ride.class);
			q.setParameter("f", dc);
			q.setParameter("t", ac);
			q.setParameter("d", d);
			List<Ride> result = q.getResultList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}
	
	public List<String> getDepartCities() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<String> q = em.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		throw e;
	} finally {
		em.close();
	}
	}
	
	public List<String> getArrivalCities(String from) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			TypedQuery<String> q = em.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from =:f ORDER BY r.to", String.class);
			q.setParameter("f", from);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		throw e;
	} finally {
		em.close();
	}
	}
	
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		EntityManager em = JPAUtil.getEntityManager();
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
		try {		
		em.getTransaction().begin();
		TypedQuery<Date> query = em.createQuery("SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",Date.class);   
		
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
		   res.add(d);
		  }
	 	return res;
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e;
			
		} finally {
			em.close();
		}
	}
	
	public void close(){
		EntityManager em = JPAUtil.getEntityManager();
		em.close();
		System.out.println("DataAcess closed");
	}
	
}
