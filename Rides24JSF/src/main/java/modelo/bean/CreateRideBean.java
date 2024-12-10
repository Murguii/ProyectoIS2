package modelo.bean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import principal.HibernateDataAccess;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.event.SelectEvent;

import principal.BLFacade;
import principal.BLFacadeImplementation;
import principal.HibernateDataAccess;
import modelo.dominio.*;

@Named("createRide")
@SessionScoped
public class CreateRideBean implements Serializable{
	
	private String departCity;
	private String arrivalCity;
	private int nPlaces = 0;
	private Date fecha;
	private float price = 0;
	private Driver driver;
	private String error = null;
	private String mensaje = null;
	BLFacade facade = new BLFacadeImplementation();
	
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
		 }else {error = null;}
		 
	}		 
	
	
	
	public void onDateSelect(SelectEvent event) {
		//System.out.println(event.getObject());
		event.getFacesContext().addMessage("calendario",
				 new FacesMessage("Fecha escogida: "+event.getObject()));
		/*FacesContext.getCurrentInstance().addMessage("calendario",
				 new FacesMessage("Fecha escogida: "+event.getObject()));
				 */
		validateDate();

		} 
	
	public boolean validateForm() {
		if(fecha == null || departCity == null || arrivalCity==null || nPlaces == 0 || price == 0.0) {
			return false;
		}
		return true;
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


	
	public void createRide() {
		try {
			 String email = loginBean.getEmail();
			 
			 //mensaje diciendo que se ha creado el ride
			 if(validateForm()) {
				 facade.storeRide(departCity, arrivalCity, fecha, nPlaces, price, email);
					 FacesContext.getCurrentInstance().addMessage("Ride creado", new FacesMessage(FacesMessage.SEVERITY_INFO, "El viaje se ha creado correctamente.", null));
					 setMensaje("Ride creado");
			 }else {
						 FacesContext.getCurrentInstance().addMessage("No ha sido posible crear el ride", new FacesMessage(FacesMessage.SEVERITY_INFO, "El viaje ya existe por lo que no se puede crear de nuevo.", null));
						 setMensaje("El ride no se puede crear si no rellenas el formulario colega");
			 }
		} catch (Exception e){
			e.printStackTrace();
			//mensaje indicando error
			setMensaje("ERR0R");
		}
		 

	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
