package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;

public class ObjectService {
	private final String tablaProducto = "Producto";
	private final String tablaProveedor = "Proveedor";
	private final String tablaEmpleado = "Empleado";
	private final String tablatransacion = "Transacion";

	public void saveProducto(Connection conexion, Producto product, Empleado emple) throws SQLException {
		try {
			PreparedStatement consulta;
			consulta = conexion.prepareStatement(
					"INSERT INTO Producto (idproducto ,nombre , precio, img, proveedorid, stock, categoria) VALUES(?, ?, ?, ?, ?, ?, ?)");
			consulta.setInt(1, product.getIdproducto());
			consulta.setString(2, product.getNombre());
			consulta.setFloat(3, product.getPrecio());
			consulta.setString(4, product.getImg());
			consulta.setInt(5, product.getProveedorid());
			consulta.setFloat(6, product.getStock());

			consulta = conexion.prepareStatement("UPDATE " + this.tablaProducto
					+ " SET idproducto = idproducto, nombre = ?, precio = ?, img = ?, proveedorid = ? ,stock =?, categoria = ? WHERE id_producto = "
					+ product.getIdproducto());
			consulta.setString(1, product.getNombre());
			consulta.setFloat(2, product.getPrecio());
			consulta.setString(3, product.getImg());
			consulta.setInt(4, product.getProveedorid());
			consulta.setFloat(5, product.getStock());

			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Producto getProduct(Connection conexion, int id_Producto) throws SQLException {
		Producto product = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT ,nombre , precio, img, proveedorid, stock, categoria" + " FROM "
							+ this.tablaProducto + " WHERE id_Producto = ?");
			consulta.setInt(1, id_Producto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				product = new Producto(id_Producto, resultado.getString("nombre"), resultado.getFloat("precio"),
						resultado.getString("img"), resultado.getInt("proveedorid"), resultado.getInt("stock"),
						resultado.getString("categoria"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return product;
	}

	public void removeProducto(Connection conexion, Producto product) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaProducto + " WHERE id_Producto = ?");
			consulta.setInt(1, product.getIdproducto());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Producto> getAllProducts(Connection conexion) throws SQLException {
		List<Producto> ListaProductos = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT Id_Producto,Nombre,Imagen,Descripcion,Categoria,Precio,Cant_Stock, Id_Proveedor " + " FROM "
							+ this.tablaProducto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaProductos.add(new Producto(resultado.getInt("id_producto"), resultado.getString("nombre"),
						resultado.getFloat("precio"), resultado.getString("img"), resultado.getInt("proveedorid"),
						resultado.getInt("stock"), resultado.getString("categoria")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaProductos;
	}

	// Proveedor
	public void saveProveedor(Connection conexion, Proveedor proveedor) throws SQLException {
		try {
			PreparedStatement consulta = null;
			consulta = conexion.prepareStatement(
					"INSERT INTO Proveedor (idproveedor, nombre, direccion, numero) VALUES (?, ?, ?, ?)");
			consulta.setInt(1, proveedor.getIdproveedor());
			consulta.setString(2, proveedor.getNombre());
			consulta.setString(3, proveedor.getDireccion());
			consulta.setInt(4, proveedor.getNumero());
			JOptionPane.showMessageDialog(null, "Proveedor Guardado");

			consulta = conexion.prepareStatement(
					"UPDATE Proveedor SET idproveedor= idproveedor, nombre = ?, direccion = ?, numero = ? WHERE id_proveedor = "
							+ proveedor.getIdproveedor());
			consulta.setString(1, proveedor.getNombre());
			consulta.setString(2, proveedor.getDireccion());
			consulta.setInt(3, proveedor.getNumero());
			JOptionPane.showMessageDialog(null, "Proveedor Actualizado");
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Proveedor getProveedor(Connection conexion, int idproveedor) throws SQLException {
		Proveedor proveedor = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT nombre,direccion,numero" + " FROM " + this.tablaProveedor + " WHERE idproveedor = ?");
			consulta.setInt(1, idproveedor);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				proveedor = new Proveedor(idproveedor, resultado.getString("nombre"), resultado.getString("direccion"),
						resultado.getInt("numero"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return proveedor;
	}

	public void removeProveedor(Connection conexion, Proveedor proveedor) throws SQLException {
		try {
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM Proveedor WHERE idproveedor = ?");
			consulta.setInt(1, proveedor.getIdproveedor());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Proveedor> getAllProveedor(Connection conexion) throws SQLException {
		List<Proveedor> ListaProveedor = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT Id_Proveedor,Nombre,Direccion,Contrasena " + " FROM " + this.tablaProveedor);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaProveedor.add(new Proveedor(resultado.getInt("idproveedor"), resultado.getString("nombre"),
						resultado.getString("direccion"), resultado.getInt("numero")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaProveedor;
	}

	// Usuario
	public void saveEmpleado(Connection conexion, Empleado emple) throws SQLException {
		try {
			PreparedStatement consulta;
			consulta = conexion.prepareStatement("INSERT INTO Usuario (iduser, username, password) VALUES(?,?,?)");
			consulta.setInt(1, emple.getIduser());
			consulta.setString(2, emple.getUsername());
			consulta.setString(3, emple.getPassword());

			consulta = conexion.prepareStatement(
					"UPDATE Usuario SET iduser = iduser, username = ?, password = ? WHERE Id_Usuario = "
							+ emple.getIduser());
			consulta.setString(1, emple.getUsername());
			consulta.setString(2, emple.getPassword());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	public Empleado getEmpleado(Connection conexion, int iduser) throws SQLException {
		Empleado empleado = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement("SELECT nombre,direccion,es_admin, activo,contrasena"
					+ " FROM " + this.tablaEmpleado + " WHERE Id_Usuario = ?");
			consulta.setInt(1, iduser);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				empleado = new Empleado(iduser, resultado.getString("username"), resultado.getString("password"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return empleado;
	}

	public void removeEmpleado(Connection conexion, Empleado empleado) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaEmpleado + " WHERE iduser = ?");
			consulta.setInt(1, empleado.getIduser());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Empleado> getAllEmpleados(Connection conexion) throws SQLException {
		List<Empleado> ListaEmpleados = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT iduser,username,password" + " FROM " + this.tablaEmpleado);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaEmpleados.add(new Empleado(resultado.getInt("iduser"), resultado.getString("username"),
						resultado.getString("password")));
			}

		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaEmpleados;
	}

	// Transaccion
	public void saveTransaccion(Connection conexion, Transaccion transaccion) throws SQLException {
		try {
			PreparedStatement consulta;
				consulta = conexion.prepareStatement("INSERT INTO " + this.tablatransacion
						+ "(idinventario,idproducto,idproveedor,fecha) VALUES(?,?,?,?)");
				consulta.setInt(1, transaccion.getIdinventario());
				consulta.setInt(2, transaccion.getIdproducto());
				consulta.setInt(3, transaccion.getIdproveedor());
				consulta.setDate(4, (Date) transaccion.getFecha());
				
				consulta = conexion.prepareStatement("UPDATE " + this.tablatransacion
						+ " SET idproducto = ?, idproveedor = ?, fecha = ? WHERE idinventario = "
						+ transaccion.getIdinventario());
				consulta.setInt(1, transaccion.getIdproducto());
				consulta.setInt(2, transaccion.getIdproveedor());
				consulta.setDate(3, (Date) transaccion.getFecha());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Transaccion getTransaccion(Connection conexion, int idinventario) throws SQLException {
		Transaccion transaccion = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idinventario,idproducto,idproveedor,fecha"
							+ " FROM " + this.tablatransacion + " WHERE idinventario = ?");
			consulta.setInt(1, idinventario);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				transaccion = new Transaccion(idinventario, resultado.getInt("idproducto"),
						resultado.getInt("idproveedor"),resultado.getDate("fecha"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return transaccion;
	}

	public void removeTransaccion(Connection conexion, Transaccion transaccion) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablatransacion + " WHERE idinventario = ?");
			consulta.setInt(1, transaccion.getIdinventario());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Transaccion> getAllTransacciones(Connection conexion) throws SQLException {
		List<Transaccion> ListaTransacciones = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idinventario, idproducto, idproveedor, fecha"
							+ " FROM " + this.tablatransacion);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaTransacciones.add( new Transaccion(resultado.getInt("idinventario"), resultado.getInt("idproducto"),
						resultado.getInt("idproveedor"),resultado.getDate("fecha")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaTransacciones;
	}

}