package models;

public class Empleado {
	private int iduser;
	private String username, password;

	public Empleado(int iduser, String username, String password) {
		super();
		this.iduser = iduser;
		this.username = username;
		this.password = password;
	}
	
	public Empleado(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Empleado() {
		super();
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Empleado [iduser=" + iduser + ", username=" + username + ", password=" + password + "]";
	}
}
