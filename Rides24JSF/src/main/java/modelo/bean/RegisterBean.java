package modelo.bean;
import java.io.Serializable;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import modelo.dominio.Driver;
import principal.HibernateDataAccess;

@Named("register")
@SessionScoped

public class RegisterBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String password;
	
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
		HibernateDataAccess hda = new HibernateDataAccess();
		Driver d = hda.getDriver(email, password);
		if (d==null) {
			try {
				System.out.println("Entra aqui");
				hda.storeDriver(email, name, password);
				System.out.println("Sale aqui");
				return "ok";
			} catch(Exception e) {
				//mensaje de error
				return "error";
			}
		}
		else {
			//mensaje de error diciendo que ya existe un usuario con esos datos
			return "error";
		}
		
	}
	
	public String close() {
		return "close";
	}

}
