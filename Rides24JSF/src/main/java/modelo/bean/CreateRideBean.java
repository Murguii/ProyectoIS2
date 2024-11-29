package modelo.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named; 
import domain.Ride;

import java.io.Serializable;
import java.util.Date;

import domain.Driver;

@Named("createRide")
@SessionScoped
public class CreateRideBean  implements Serializable{
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private float price;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	
}
