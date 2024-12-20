package modelo.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import principal.BLFacade;
import principal.BLFacadeImplementation;
import principal.HibernateDataAccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.omnifaces.cdi.ViewScoped;


import modelo.dominio.Ride;
import modelo.dominio.Driver;
import org.primefaces.event.SelectEvent;

@Named("queryRides")
@ViewScoped
public class QueryRidesBean implements Serializable{
	BLFacade facade = BLFacadeImplementation.getInstance() ;
	private List<String> departCities;
	private List<String> arrivalCities;
	private List<Ride> concreteRides = new ArrayList<Ride>();
	private String selectedDepartCity;
	private String selectedArriveCity;
	private Date fecha = new Date();
	private Driver driver;
	
	
	public QueryRidesBean() {}
	
	
	@Inject
    private LoginBean loginBean;
		
	@PostConstruct
    public void init() {
		initializeDriver();
		this.departCities = getDepartingCities();
		if (departCities != null && !departCities.isEmpty()) {
			this.arrivalCities = getArrivalCities(departCities.get(0));	
			this.selectedDepartCity = departCities.get(0);
			this.selectedArriveCity = arrivalCities.get(0);
		}		
	}
	
	
	public List<String> getDepartCities() {
		return departCities;
	}
	public void setDepartCities(ArrayList<String> departingCities) {
		this.departCities = departingCities;
	}
	public List<String> getArrivalCities() {
		return arrivalCities;
	}
	public void setArrivalCities(ArrayList<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}
	public String getSelectedDepartCity() {
		return selectedDepartCity;
	}
	public void setSelectedDepartCity(String selectedDepartCity) {
		this.selectedDepartCity = selectedDepartCity;
	}
	public String getSelectedArriveCity() {
		return selectedArriveCity;
	}
	public void setSelectedArriveCity(String selectedArriveCity) {
		this.selectedArriveCity = selectedArriveCity;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public List<Ride> getConcreteRides() {
		return concreteRides;
	}
	public void setConcreteRides(ArrayList<Ride> concreteRides) {
		this.concreteRides = concreteRides;
	}
	public void onDateSelect(SelectEvent event) {
		event.getFacesContext().addMessage("calendario",
				 new FacesMessage("Fecha escogida: "+event.getObject()));
		validateDate();
		queryRides();
	}
	public void validateDate() {
		 if (fecha != null && fecha.before(new Date())) {
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage("form:fecha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha debe ser posterior a hoy.", null));
	        }
	}
	
	public List<String> getDepartingCities() {	//Desde db
		try {
			this.departCities = facade.getDepartCities(this.driver.getEmail());
			return departCities;
	}  catch (Exception e){
		e.printStackTrace();
		return null;
	}
	}
	public List<String> getArrivalCities(String from) {	//Desde db
		try {
			this.arrivalCities = facade.getDestinationCities(from, this.driver.getEmail());
			return arrivalCities;
	}  catch (Exception e){
		e.printStackTrace();
		return null;
	}
	}
	public void queryRides() {
		try {	    
			this.concreteRides = facade.getRides(selectedDepartCity, selectedArriveCity, fecha, this.driver.getEmail());
	}  catch (Exception e){
		e.printStackTrace();
	}
	}
	public void onChange() {		//Se ejecutara cuando se escoja un departCity
		this.arrivalCities.clear();
		this.arrivalCities = getArrivalCities(this.selectedDepartCity);
		this.selectedArriveCity = arrivalCities.get(0);
		this.departCities = getDepartCities();
		queryRides();
	}
	
	public void dateSelectQueryRides(SelectEvent event) {
		onDateSelect(event);
		queryRides();
	}
	
	public void initializeDriver() {
        String email = loginBean.getEmail();
        String password = loginBean.getPassword();
        Driver d = facade.getDriver(email, password);
        if (d != null) {
            this.driver = d;
        } else {
            //no existe driver
        }
    }
	
	
	
	public String close() {
		return "close";
	}
}
