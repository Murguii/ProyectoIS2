package modelo.bean;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nombre;
	private String password;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String doLogin() {
		return "cambiar";
	}
	
	public String close() {
		return "close";
	}
	
	
}
