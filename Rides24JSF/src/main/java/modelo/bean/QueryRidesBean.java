package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import domain.Driver;
import jakarta.inject.Named; 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import domain.Ride;
import org.primefaces.event.SelectEvent;

@Named("queryRides")
@SessionScoped
public class QueryRidesBean implements Serializable{
	private ArrayList<String> departCities = new ArrayList<String>();
	private ArrayList<String> arrivalCities = new ArrayList<String>();
	private ArrayList<Ride> concreteRides = new ArrayList<Ride>();
	private Date fecha = new Date();
	
	public ArrayList<String> getDepartCities() {
		return departCities;
	}
	public void setDepartCities(ArrayList<String> departingCities) {
		this.departCities = departingCities;
	}
	public ArrayList<String> getArrivalCities() {
		return arrivalCities;
	}
	public void setArrivalCities(ArrayList<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public ArrayList<Ride> getConcreteRides() {
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
	public String close() {
		return "close";
	}
}
