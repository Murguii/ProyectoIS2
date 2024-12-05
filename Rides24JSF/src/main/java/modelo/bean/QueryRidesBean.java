package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import domain.Driver;
import jakarta.inject.Named;
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
	private List<String> departCities = new ArrayList<String>();
	private List<String> arrivalCities = new ArrayList<String>();
	private List<Ride> concreteRides = new ArrayList<Ride>();
	private String selectedDepartCity;
	private String selectedArriveCity;
	private Date fecha = new Date();
	
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
	}
	public void validateDate() {
		 if (fecha != null && fecha.before(new Date())) {
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage("form:fecha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha debe ser posterior a hoy.", null));
	        }
	}
	
	public void getDepartingCities() {	//Desde db
		try {
			HibernateDataAccess hda = new HibernateDataAccess();
			this.departCities = hda.getDepartCities();
	}  catch (Exception e){
		e.printStackTrace();
	}
	}
	public void getArrivalCities(String from) {	//Desde db
		try {
			HibernateDataAccess hda = new HibernateDataAccess();
			this.arrivalCities = hda.getArrivalCities(from);
	}  catch (Exception e){
		e.printStackTrace();
	}
	}
	public void queryRides() {
		try {
			HibernateDataAccess hda = new HibernateDataAccess();
			this.concreteRides = hda.getRides(selectedDepartCity, selectedArriveCity, fecha);
	}  catch (Exception e){
		e.printStackTrace();
	}
	}
	
	
	
	public String close() {
		return "close";
	}
}
