package models;

import java.sql.Date;

public class Transaccion {
	private int idinventario, idproducto, idproveedor;
	private Date fecha;

	public Transaccion(int idinventario, int idproducto, int idproveedor, Date fecha) {
		super();
		this.idinventario = idinventario;
		this.idproducto = idproducto;
		this.idproveedor = idproveedor;
		this.fecha = fecha;
	}

	public Transaccion() {
		super();
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Transaccion [idinventario=" + idinventario + ", idproducto=" + idproducto + ", idproveedor="
				+ idproveedor + ", fecha=" + fecha + "]";
	}
}
