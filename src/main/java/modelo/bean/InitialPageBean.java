package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named; 

@Named("initialPage")
@SessionScoped
public class InitialPageBean {

	public InitialPageBean() {}
	
	public String createRideButton() {return "createRide";}
	
	public String queryRideButton() {return "queryRide";}
}
