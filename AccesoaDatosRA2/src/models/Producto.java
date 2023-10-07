package models;

public class Producto {
	private int id;
	private String nombre;
	private String marca;
	private Float precio;
	private String img;
	private int proveedorid;
	private int stock;
	private String categoria;
	
	//constructores
	public Producto(int id, String nombre, String marca, Float precio, String img, int proveedorid, int stock,
			String categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.img = img;
		this.proveedorid = proveedorid;
		this.stock = stock;
		this.categoria = categoria;
	}
	
	public Producto(String nombre, String marca, Float precio, String img, int proveedorid, int stock,
			String categoria) {
		super();
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.img = img;
		this.proveedorid = proveedorid;
		this.stock = stock;
		this.categoria = categoria;
	}

	//getter-setters

	public Producto() {
		super();
	}

	public int getIdproducto() {
		return id;
	}

	public void setIdproducto(int idproducto) {
		this.id = idproducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getProveedorid() {
		return proveedorid;
	}

	public void setProveedorid(int proveedorid) {
		this.proveedorid = proveedorid;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	//to-string

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", marca=" + marca + ", precio=" + precio + ", img=" + img
				+ ", proveedorid=" + proveedorid + ", stock=" + stock + ", categoria=" + categoria + "]";
	}
	
	
	
	
}
