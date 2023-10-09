package models;

import java.sql.Date;

public class Transaccion {
	private int idinventario, idproducto, idproveedor, cantidad, idempleado;
	private Date fecha;
	
	public Transaccion(int idinventario, Date fecha, int idproducto, int idproveedor, int cantidad, int idempleado) {
		super();
		this.idinventario = idinventario;
		this.fecha = fecha;
		this.idproducto = idproducto;
		this.idproveedor = idproveedor;
		this.cantidad = cantidad;
		this.idempleado = idempleado;
	}
	
	public Transaccion( Date fecha, int idproducto, int idproveedor, int cantidad, int idempleado) {
		super();
		this.fecha = fecha;
		this.idproducto = idproducto;
		this.idproveedor = idproveedor;
		this.cantidad = cantidad;
		this.idempleado = idempleado;
	}

	public int getIdinventario() {
		return idinventario;
	}

	public void setIdinventario(int idinventario) {
		this.idinventario = idinventario;
	}

	public int getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public int getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(int idproveedor) {
		this.idproveedor = idproveedor;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdempleado() {
		return idempleado;
	}

	public void setIdempleado(int idempleado) {
		this.idempleado = idempleado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Transaccion [idinventario=" + idinventario + ", idproducto=" + idproducto + ", idproveedor="
				+ idproveedor + ", cantidad=" + cantidad + ", idempleado=" + idempleado + ", fecha=" + fecha + "]";
	}

}