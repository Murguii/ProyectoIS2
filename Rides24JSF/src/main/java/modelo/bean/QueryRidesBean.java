package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import domain.Driver;
import jakarta.inject.Named;
import principal.BLFacade;
import principal.BLFacadeImplementation;
import principal.HibernateDataAccess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.dominio.Ride;
import org.primefaces.event.SelectEvent;

@Named("queryRides")
@SessionScoped
public class QueryRidesBean implements Serializable{
	BLFacade facade = new BLFacadeImplementation();
	private List<String> departCities;
	private List<String> arrivalCities;
	private List<Ride> concreteRides = new ArrayList<Ride>();
	private String selectedDepartCity;
	private String selectedArriveCity;
	private Date fecha = new Date();
	
	
	public QueryRidesBean() {
		this.departCities = getDepartingCities();
		if (departCities.size() > 0) this.arrivalCities = getArrivalCities(departCities.get(0));
		this.selectedDepartCity = departCities.get(0);
		this.selectedArriveCity = arrivalCities.get(0);
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
		System.out.println(event.getObject());
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
			System.out.print("Se ejecuta getDepartingCities");
			//HibernateDataAccess hda = new HibernateDataAccess();
			this.departCities = facade.getDepartCities();
			return departCities;
	}  catch (Exception e){
		e.printStackTrace();
		return null;
	}
	}
	public List<String> getArrivalCities(String from) {	//Desde db
		try {
			System.out.print("Se ejecuta getArrivalCities");
			//HibernateDataAccess hda = new HibernateDataAccess();
			this.arrivalCities = facade.getDestinationCities(from);
			return arrivalCities;
	}  catch (Exception e){
		e.printStackTrace();
		return null;
	}
	}
	public void queryRides() {
		try {	    
			System.out.print("Se ejecuta queryRides");
			this.concreteRides = facade.getRides(selectedDepartCity, selectedArriveCity, fecha);
	}  catch (Exception e){
		e.printStackTrace();
	}
	}
	public void onChange(String nuevoDc) {		//Se ejecutara cuando se escoja un departCity
		System.out.print("Se ejecuta onChange");
		setSelectedDepartCity(nuevoDc);
		getArrivalCities(this.selectedDepartCity);
		queryRides();
	}
	
	public void dateSelectQueryRides(SelectEvent event) {
		System.out.print("Se ejecuta calendario");
		onDateSelect(event);
		queryRides();
	}
	
	
	
	public String close() {
		return "close";
	}
}
