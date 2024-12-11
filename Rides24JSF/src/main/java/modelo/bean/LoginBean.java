package modelo.bean;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.dominio.Driver;
import principal.BLFacade;
import principal.BLFacadeImplementation;
import principal.HibernateDataAccess;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
	BLFacade facade = new BLFacadeImplementation();
		
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String doLogin() {
		Driver d = facade.getDriver(email, password);
		if (d != null) {
			return "ok";
		}
		else {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "El nombre de usuario y contraseña no coinciden");
	        FacesContext.getCurrentInstance().addMessage(null, message);
			return "error";
		}
		
	}
	
	public String close() {
		return "close";
	}
	
	
}
