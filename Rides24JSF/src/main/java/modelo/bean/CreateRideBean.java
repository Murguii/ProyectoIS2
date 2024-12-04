package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named; 
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
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
	private String error = null;
	
	//private Driver driver = new Driver("driver3@gmail.com","Test Driver");
	
	//private static BLFacade appFacadeInterface;

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
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public String close() {
		return "close";
	}
	
	public void validateDate() {
		 if (fecha != null && fecha.before(new Date())) {
			 /*FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage("form:fecha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha debe ser posterior a hoy.", null));*/
	        error = "La fecha es anterior a hoy, los viajes en el tiempo aún no existen sabes?";
		 }
		 
	}
	
	
	public void onDateSelect(SelectEvent event) {
		System.out.println(event.getObject());
		event.getFacesContext().addMessage("calendario",
				 new FacesMessage("Fecha escogida: "+event.getObject()));
		/*FacesContext.getCurrentInstance().addMessage("calendario",
				 new FacesMessage("Fecha escogida: "+event.getObject()));
				 */
		} 

	/*
	public String createRide() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			BLFacade facade = appFacadeInterface;
			Ride r = facade.createRide(from, to, fecha, nPlaces, price, driver.getEmail());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ride creado exitosamente", null));
			return "ok";
		} catch (RideMustBeLaterThanTodayException e1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e1.getMessage(), null));
			return "error";
		} catch (RideAlreadyExistException e1) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e1.getMessage(), null));
			return "error";
		}

	}
	*/
}
