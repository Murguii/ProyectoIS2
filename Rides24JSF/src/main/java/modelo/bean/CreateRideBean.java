package modelo.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import principal.HibernateDataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import modelo.dominio.*;

@Named("createRide")
@SessionScoped
public class CreateRideBean  implements Serializable{
	
	private String departCity;
	private String arrivalCity;
	private int nPlaces = 0;
	private Date fecha;
	private float price = 0;
	private Driver driver;
	private String error = null;
	
	@Inject
    private LoginBean loginBean;
	
	 @PostConstruct
	    public void init() {
		 initializeDriver();
	    }
	
	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
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
	
	public void initializeDriver() {
        HibernateDataAccess hda = new HibernateDataAccess();
        String email = loginBean.getEmail();
        String password = loginBean.getPassword();
        Driver d = hda.getDriver(email, password);
        if (d != null) {
            this.driver = d;
        } else {
            //no existe driver
        }
    }

	
	public String createRide() {
		try {
			HibernateDataAccess hda = new HibernateDataAccess();
			 String email = loginBean.getEmail();
			 hda.storeRide(departCity, arrivalCity, fecha, nPlaces, price, email);
			 //mensaje diciendo que se ha creado el ride
			 return "ok";
		} catch (Exception e){
			//mensaje indicando error
			return "error";
		}
		 

	}
	
}
