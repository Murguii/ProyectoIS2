package principal;

import java.util.Date;
import java.util.List;

import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import modelo.dominio.Driver;
import modelo.dominio.Ride;

public class BLFacadeImplementation implements BLFacade{
	
	HibernateDataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new HibernateDataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(HibernateDataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    public List<String> getDepartCities(){
		 List<String> departLocations=dbManager.getDepartCities();				
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	public List<String> getDestinationCities(String from){
		 List<String> targetCities=dbManager.getArrivalCities(from);				
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   public Ride storeRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	  	Ride ride=dbManager.storeRide(from, to, date, nPlaces, price, driverEmail);		
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	public List<Ride> getRides(String from, String to, Date date){
		List<Ride>  rides=dbManager.getRides(from, to, date);
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		return dates;
	}
	
	 /**
	    * {@inheritDoc}
	    */
		public Driver getDriver(String email, String password){
			Driver driver=dbManager.getDriver(email, password);
			return driver;
		}
		
		 /**
		    * {@inheritDoc}
		    */
			public Driver storeDriver(String email, String name, String password){
				Driver driver=dbManager.storeDriver(email, name, password);
				return driver;
			}
	
	
	public void close() {
		HibernateDataAccess dB4oManager=new HibernateDataAccess();
		dB4oManager.close();

	}

}
