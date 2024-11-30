package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named; 
import domain.Ride;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.event.SelectEvent;

import domain.Driver;

@Named("createRide")
@SessionScoped
public class CreateRideBean  implements Serializable{
	private String from;
	private String to;
	private int nPlaces = 0;
	private Date fecha;
	private float price = 0;
	
	private Driver driver;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getnPlaces() {
		return nPlaces;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	} 
	
	public String close() {
		return "close";
	}
	
	public void validateDate() {
		 if (fecha != null && fecha.before(new Date())) {
	            FacesContext.getCurrentInstance().addMessage("fecha", 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha debe ser posterior a hoy.", null));
	        }
	}
	
	public void onDateSelect(SelectEvent event) {
		 System.out.println("OnDateSelect activado");
		 FacesContext.getCurrentInstance().addMessage("fecha",
		 new FacesMessage("Fecha escogida: "+event.getObject()));
		} 


	
}
