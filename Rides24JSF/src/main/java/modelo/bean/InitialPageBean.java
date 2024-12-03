
package modelo.bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named; 

@Named("initialPage")
@SessionScoped
public class InitialPageBean  implements Serializable{

	public InitialPageBean() {}
	
	public String loginButton() {return "login";}
	
	public String registerButton() {return "register";}
}

