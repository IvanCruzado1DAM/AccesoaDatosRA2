package models;

public class Proveedor {
	private int idproveedor;
	private String nombre, direccion;
	private int numero;
	public Proveedor(int idproveedor, String nombre, String direccion, int numero) {
		super();
		this.idproveedor = idproveedor;
		this.nombre = nombre;
		this.direccion = direccion;
		this.numero = numero;
	}
	public Proveedor(String nombre, String direccion, int numero) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.numero = numero;
	}
	public Proveedor() {
		super();
	}
	public int getIdproveedor() {
		return idproveedor;
	}
	public void setIdproveedor(int idproveedor) {
		this.idproveedor = idproveedor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "Proveedor [Id proveedor=" + idproveedor + ", Nombre=" + nombre + ", Direccion=" + direccion + ", Numero="
				+ numero + "]";
	}
	
	
	
}
