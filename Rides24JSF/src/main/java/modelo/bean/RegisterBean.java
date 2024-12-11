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

@Named("register")
@SessionScoped

public class RegisterBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String password;
	BLFacade facade = BLFacadeImplementation.getInstance() ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
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
	
	public String doRegister() {
		if(validateEmail(email)) {
			Driver d = facade.getDriver(email, password);
			if (d==null) {
				try {
					facade.storeDriver(email, name, password);
					return "ok";
				} catch(Exception e) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Error durante el registro, inténtelo de nuevo");
			        FacesContext.getCurrentInstance().addMessage(null, message);	
			        return "error";
				}
			}
			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Ya existe un usuario con ese correo electrónico");
		        FacesContext.getCurrentInstance().addMessage(null, message);			
		        return "error";
			}
		}
		else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Introduce un formato de correo electrónico válido");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return "error";
		}
		
		
	}
	
	public boolean validateEmail(String email) {
		if (email == null || email.isEmpty() || (email.indexOf("@") < 1)  ) {
            return false;
        }
		else return true;
		
	}
	
	public String close() {
		return "close";
	}

}
