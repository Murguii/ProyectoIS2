package modelo.bean;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import modelo.dominio.Driver;
import principal.HibernateDataAccess;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
		
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
		HibernateDataAccess hda = new HibernateDataAccess();
		Driver d = hda.getDriver(email, password);
		if (d != null) {
			return "ok";
		}
		else {
			//mensaje de error de que no existe usuario con esos datos
			
			return "error";
		}
		
	}
	
	public String close() {
		return "close";
	}
	
	
}
