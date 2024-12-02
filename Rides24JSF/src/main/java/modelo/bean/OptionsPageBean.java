package modelo.bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named; 

@Named("optionsPage")
@SessionScoped
public class OptionsPageBean  implements Serializable{
	
	public String createRideButton() {return "createRide";}
	
	public String queryRideButton() {return "queryRide";}
}
