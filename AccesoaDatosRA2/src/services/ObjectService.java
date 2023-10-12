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
	private final String tablaProducto = "producto";
	private final String tablaProveedor = "proveedor";
	private final String tablaEmpleado = "empleado";
	private final String tablaTransacion = "transaccion";

	public void saveProducto(Connection conexion, Producto product, int cual) throws SQLException {
		try {
			PreparedStatement consulta;
			if (cual == 1) {
				consulta = conexion.prepareStatement(
						"INSERT INTO producto (idproducto ,nombre , marca, precio, img, proveedorid, stock, categoria) VALUES(?, ?,?, ?, ?, ?, ?, ?)");
				consulta.setInt(1, product.getIdproducto());
				consulta.setString(2, product.getNombre());
				consulta.setString(3, product.getMarca());
				consulta.setFloat(4, product.getPrecio());
				consulta.setString(5, product.getImg());
				consulta.setInt(6, product.getProveedorid());
				consulta.setInt(7, product.getStock());
				consulta.setString(8, product.getCategoria());
				consulta.execute();
			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tablaProducto
						+ " SET idproducto = idproducto, nombre = ?, marca = ?, precio = ?, img = ?, proveedorid = ? ,stock =?, categoria = ? WHERE idproducto = "
						+ product.getIdproducto());
				consulta.setString(1, product.getNombre());
				consulta.setString(2, product.getMarca());
				consulta.setFloat(3, product.getPrecio());
				consulta.setString(4, product.getImg());
				consulta.setInt(5, product.getProveedorid());
				consulta.setInt(6, product.getStock());
				consulta.setString(7, product.getCategoria());
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Producto getProduct(Connection conexion, int idproducto) throws SQLException {
		Producto product = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT nombre , marca, precio, img, proveedorid, stock, categoria" + " FROM "
							+ this.tablaProducto + " WHERE idproducto = ?");
			consulta.setInt(1, idproducto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				product = new Producto(idproducto, resultado.getString("nombre"),resultado.getString("marca"),resultado.getFloat("precio"),
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
					.prepareStatement("DELETE FROM " + this.tablaProducto + " WHERE idproducto = ?");
			consulta.setInt(1, product.getIdproducto());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}
	
	public void removeProductoID(Connection conexion, int id) throws SQLException {
		try {
			
			  // Eliminar registros relacionados en la tabla transaccion
	        PreparedStatement eliminarTransacciones = conexion.prepareStatement("DELETE FROM transaccion WHERE idproducto = ?");
	        eliminarTransacciones.setInt(1, id);
	        eliminarTransacciones.executeUpdate();
			
			// Eliminar el producto
	        PreparedStatement eliminarProducto = conexion.prepareStatement("DELETE FROM " + this.tablaProducto + " WHERE idproducto = ?");
	        eliminarProducto.setInt(1, id);
	        eliminarProducto.executeUpdate();

		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		
	}

	public List<Producto> getAllProducts(Connection conexion) throws SQLException {
		List<Producto> ListaProductos = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idproducto,nombre ,marca, precio, img, proveedorid, stock, categoria "
							+ " FROM " + this.tablaProducto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaProductos.add(new Producto(resultado.getInt("idproducto"), resultado.getString("nombre"), resultado.getString("marca"),
						resultado.getFloat("precio"), resultado.getString("img"), resultado.getInt("proveedorid"),
						resultado.getInt("stock"), resultado.getString("categoria")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaProductos;
	}

	// Proveedor
	public void saveProveedor(Connection conexion, Proveedor proveedor, int cual) throws SQLException {
		try {
			PreparedStatement consulta = null;
			if (cual == 1) {
				consulta = conexion.prepareStatement(
						"INSERT INTO proveedor (idproveedor, nombre, direccion, numero) VALUES (?, ?, ?, ?)");
				consulta.setInt(1, proveedor.getIdproveedor());
				consulta.setString(2, proveedor.getNombre());
				consulta.setString(3, proveedor.getDireccion());
				consulta.setInt(4, proveedor.getNumero());
				consulta.execute();
				JOptionPane.showMessageDialog(null, "Proveedor Guardado");
			} else {
				consulta = conexion.prepareStatement(
						"UPDATE proveedor SET idproveedor= idproveedor, nombre = ?, direccion = ?, numero = ? WHERE id_proveedor = "
								+ proveedor.getIdproveedor());
				consulta.setString(1, proveedor.getNombre());
				consulta.setString(2, proveedor.getDireccion());
				consulta.setInt(3, proveedor.getNumero());
				consulta.executeUpdate();
				JOptionPane.showMessageDialog(null, "Proveedor Actualizado");
			}
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
			PreparedStatement consulta = conexion.prepareStatement("DELETE FROM proveedor WHERE idproveedor = ?");
			consulta.setInt(1, proveedor.getIdproveedor());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Proveedor> getAllProveedor(Connection conexion) throws SQLException {
		List<Proveedor> ListaProveedor = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idproveedor,nombre,direccion,numero " + " FROM " + this.tablaProveedor);
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
	public void saveEmpleado(Connection conexion, Empleado emple, int cual) throws SQLException {
		try {
			PreparedStatement consulta;
			if (cual == 1) {
				consulta = conexion
						.prepareStatement("INSERT INTO empleado (idempleado, username, password) VALUES(?,?,?)");
				consulta.setInt(1, emple.getIduser());
				consulta.setString(2, emple.getUsername());
				consulta.setString(3, emple.getPassword());
				consulta.execute();
			} else {
				consulta = conexion.prepareStatement(
						"UPDATE empleado SET idempleado = idempleado, username = ?, password = ? WHERE idempleado = "
								+ emple.getIduser());
				consulta.setString(1, emple.getUsername());
				consulta.setString(2, emple.getPassword());
				consulta.executeUpdate();
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	public Empleado getExisteEmpleado(Connection conexion, String username) throws SQLException {
		Empleado e = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT idempleado, username,password" + " FROM " + this.tablaEmpleado + " WHERE username = ?");
			consulta.setString(1, username);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				e = new Empleado(resultado.getInt("idempleado"), resultado.getString("username"), resultado.getString("password"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return e;
	}

	public Empleado getEmpleado(Connection conexion, int idempleado) throws SQLException {
		Empleado empleado = null;
		try {
			PreparedStatement consulta = conexion.prepareStatement(
					"SELECT idempleado, username,password" + " FROM " + this.tablaEmpleado + " WHERE idempleado = ?");
			consulta.setInt(1, idempleado);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				empleado = new Empleado(idempleado, resultado.getString("username"), resultado.getString("password"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return empleado;
	}

	public void removeEmpleado(Connection conexion, Empleado empleado) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaEmpleado + " WHERE idempleado = ?");
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
					.prepareStatement("SELECT idempleado,username,password" + " FROM " + this.tablaEmpleado);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaEmpleados.add(new Empleado(resultado.getInt("idempleado"), resultado.getString("username"),
						resultado.getString("password")));
			}

		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaEmpleados;
	}

	// Transaccion
	public void saveTransaccion(Connection conexion, Transaccion transaccion, int cual) throws SQLException {
		try {
			PreparedStatement consulta;
			if (cual == 1) {
				consulta = conexion.prepareStatement("INSERT INTO " + this.tablaTransacion
						+ "(idinventario,fecha, idproducto, idproveedor,cantidad,idempleado) VALUES(?,?,?,?,?,?)");
				consulta.setInt(1, transaccion.getIdinventario());
				consulta.setDate(2, (Date) transaccion.getFecha());
				consulta.setInt(3, transaccion.getIdproducto());
				consulta.setInt(4, transaccion.getIdproveedor());
				consulta.setInt(5, transaccion.getCantidad());
				consulta.setInt(6, transaccion.getIdempleado());
				consulta.execute();
			} else {
				consulta = conexion.prepareStatement("UPDATE " + this.tablaTransacion
						+ " SET fecha = ?, idproducto = ?, idproveedor = ?, cantidad = ?, idempleado = ? WHERE idinventario = "
						+ transaccion.getIdinventario());
				consulta.setDate(1, (Date) transaccion.getFecha());
				consulta.setInt(2, transaccion.getIdproducto());
				consulta.setInt(3, transaccion.getIdproveedor());
				consulta.setInt(4, transaccion.getCantidad());
				consulta.setInt(5, transaccion.getIdempleado());
				consulta.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Transaccion getTransaccion(Connection conexion, int idinventario) throws SQLException {
		Transaccion transaccion = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT idinventario,fecha, idproducto, idproveedor,cantidad,idempleado"
							+ " FROM " + this.tablaTransacion + " WHERE idinventario = ?");
			consulta.setInt(1, idinventario);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				transaccion = new Transaccion(idinventario, resultado.getDate("fecha"), resultado.getInt("idproducto"),
						resultado.getInt("idproveedor"), resultado.getInt("cantidad"), resultado.getInt("idempleado"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return transaccion;
	}

	public void removeTransaccion(Connection conexion, Transaccion transaccion) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaTransacion + " WHERE idinventario = ?");
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
					.prepareStatement("SELECT idinventario,fecha, idproducto, idproveedor,cantidad,idempleado "
							+ " FROM " + this.tablaTransacion);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaTransacciones.add(new Transaccion(resultado.getInt("idinventario"), resultado.getDate("fecha"),
						resultado.getInt("idproducto"), resultado.getInt("idproveedor"), resultado.getInt("cantidad"),
						resultado.getInt("idempleado")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaTransacciones;
	}

}