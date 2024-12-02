package modelo.bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named; 

@Named("optionsPage")
@SessionScoped
public class InitialPageBean  implements Serializable{
	
	public String loginButton() {return "login";}
	
	public String registerButton() {return "register";}
}
