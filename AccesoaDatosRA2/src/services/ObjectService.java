package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.Compra;
import models.Producto;
import models.Proveedor;
import models.Usuario;
import views.InterfazClienteEditar;


public class ObjectService {
	private final String tablaProducto = "Producto";
	private final String tablaProveedor = "Proveedor";
	private final String tablaUsuario = "Usuario";
	private final String tablaCompra = "Compra";

	public void saveProducto(Connection conexion, Producto product, Usuario user, String p, int x) throws SQLException {
		try {
			PreparedStatement consulta;
			if (x==0) {
				consulta = conexion
						.prepareStatement("INSERT INTO Producto (Id_Producto,nombre, Imagen, Descripcion, Categoria, Precio, Cant_Stock, Id_Proveedor) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
				consulta.setString(1, product.getId_Producto());
				consulta.setString(2, product.getNombre());
				consulta.setString(3, product.getImagen());
				consulta.setString(4, product.getDescripcion());
				consulta.setString(5, product.getCategoria());
				consulta.setFloat(6, product.getPrecio());
				consulta.setInt(7, product.getCant_Stock());
				consulta.setString(8, p);
			} else {
				consulta = conexion
						.prepareStatement("UPDATE " + this.tablaProducto + " SET Id_Producto = Id_Producto, nombre = ?, Imagen = ?, Descripcion = ?, Categoria = ? ,Precio =?, Cant_Stock = ?, Id_Proveedor = ? WHERE id_producto = " + product.getId_Producto());
				consulta.setString(1, product.getNombre());
				consulta.setString(2, product.getImagen());
				consulta.setString(3, product.getDescripcion());
				consulta.setString(4, product.getCategoria());
				consulta.setFloat(5, product.getPrecio());
				consulta.setInt(6, product.getCant_Stock());
				consulta.setString(7, p);
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Producto getProduct(Connection conexion, String Id_Producto) throws SQLException {
		Producto product = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT nombre,imagen,descripcion,categoria,precio,cant_stock, id_proveedor" + " FROM "
							+ this.tablaProducto + " WHERE id_Producto = ?");
			consulta.setString(1, Id_Producto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				product = new Producto(Id_Producto, resultado.getString("Nombre"), resultado.getString("Imagen"),
						resultado.getString("Descripcion"),resultado.getString("Categoria"), resultado.getFloat("Precio"),
						resultado.getInt("Cant_Stock"), resultado.getString("Id_Proveedor"));
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
			consulta.setString(1, product.getId_Producto());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Producto> getAllProducts(Connection conexion) throws SQLException {
		List<Producto> ListaProductos = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT Id_Producto,Nombre,Imagen,Descripcion,Categoria,Precio,Cant_Stock, Id_Proveedor "
							+ " FROM " + this.tablaProducto);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaProductos.add(new Producto(resultado.getString("Id_Producto"), resultado.getString("Nombre"),
						resultado.getString("Imagen"), resultado.getString("Descripcion"),resultado.getString("Categoria"), resultado.getFloat("Precio"),
						resultado.getInt("Cant_Stock"), resultado.getString("Id_Proveedor")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaProductos;
	}

	// Proveedor
	public void saveProveedor(Connection conexion, Proveedor proveedor, int x) throws SQLException {
		try {
			PreparedStatement consulta = null;
			if (x == 0) {
				consulta = conexion
						.prepareStatement("INSERT INTO Proveedor (Id_Proveedor, Nombre, Direccion, Contrasena) VALUES (?, ?, ?, ?)");
				consulta.setString(1, proveedor.getId_Proveedor());
				consulta.setString(2, proveedor.getNombre());
				consulta.setString(3, proveedor.getDireccion());
				consulta.setString(4, proveedor.getContrasena());
                JOptionPane.showMessageDialog(null, "Proveedor Guardado");
			} else if(x == 1) {
				consulta = conexion.prepareStatement("UPDATE Proveedor SET Id_Proveedor= Id_Proveedor, nombre = ?, direccion = ?, contrasena = ? WHERE id_proveedor = " + proveedor.getId_Proveedor());
				consulta.setString(1, proveedor.getNombre());
				consulta.setString(2, proveedor.getDireccion());
				consulta.setString(3, proveedor.getContrasena());
				JOptionPane.showMessageDialog(null, "Proveedor Actualizado");
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Proveedor getProveedor(Connection conexion, String Id_Proveedor) throws SQLException {
		Proveedor proveedor = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT nombre,direccion,contrasena" + " FROM "
							+ this.tablaProveedor + " WHERE Id_Proveedor = ?");
			consulta.setString(1, Id_Proveedor);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				proveedor = new Proveedor(Id_Proveedor, resultado.getString("Nombre"),
						resultado.getString("Direccion"),resultado.getString("Contrasena"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return proveedor;
	}

	public void removeProveedor(Connection conexion, Proveedor proveedor) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM Proveedor WHERE id_proveedor = ?");
			consulta.setString(1, proveedor.getId_Proveedor());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Proveedor> getAllProveedor(Connection conexion) throws SQLException {
		List<Proveedor> ListaProveedor = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT Id_Proveedor,Nombre,Direccion,Contrasena "
							+ " FROM " + this.tablaProveedor);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaProveedor.add(new Proveedor(resultado.getString("Id_Proveedor"), resultado.getString("Nombre"),
						resultado.getString("Direccion"),resultado.getString("contrasena")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaProveedor;
	}

	// Usuario
	public void saveUsuario(Connection conexion, Usuario usuario, int x) throws SQLException {
		try {
			PreparedStatement consulta;
			if (x==0) {
				consulta = conexion
						.prepareStatement("INSERT INTO Usuario (id_usuario,nombre, direccion,es_admin,activo,contrasena) VALUES(?,?,?,?,?,?)");
				consulta.setString(1, usuario.getId_Usuario());
				consulta.setString(2, usuario.getNombre());
				consulta.setString(3, usuario.getDireccion());
				consulta.setBoolean(4, usuario.isEs_Admin());
				consulta.setBoolean(5, usuario.isActivo());
				consulta.setString(6, usuario.getContrasena());
			} else {
				consulta = conexion.prepareStatement("UPDATE Usuario SET Id_usuario = Id_usuario, nombre = ?, direccion = ?, es_admin = ?, activo = ?, contrasena = ? WHERE Id_Usuario = " + usuario.getId_Usuario());
				consulta.setString(1, usuario.getNombre());
				consulta.setString(2, usuario.getDireccion());
				consulta.setBoolean(3, usuario.isEs_Admin());
				consulta.setBoolean(4, usuario.isActivo());
				consulta.setString(5, usuario.getContrasena());
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}

	public Usuario getUsuario(Connection conexion, String Id_Usuario) throws SQLException {
		Usuario usuario = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT nombre,direccion,es_admin, activo,contrasena" + " FROM "
							+ this.tablaUsuario + " WHERE Id_Usuario = ?");
			consulta.setString(1, Id_Usuario);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				usuario = new Usuario(Id_Usuario, resultado.getString("Nombre"),
						resultado.getString("Direccion"), resultado.getBoolean("Es_Admin"), resultado.getBoolean("Activo"),resultado.getString("Contrasena"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return usuario;
	}

	public void removeUsuario(Connection conexion, Usuario usuario) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaUsuario + " WHERE Id_Usuario = ?");
			consulta.setString(1, usuario.getId_Usuario());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Usuario> getAllUsuarios(Connection conexion) throws SQLException {
		List<Usuario> ListaUsuarios = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT Id_Usuario,Nombre,Direccion,Es_Admin,Activo,Contrasena"
							+ " FROM " + this.tablaUsuario);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaUsuarios.add(new Usuario(resultado.getString("Id_Usuario"), resultado.getString("Nombre"), resultado.getString("Direccion")
						,resultado.getBoolean("Es_Admin"),resultado.getBoolean("Activo"),resultado.getString("contrasena")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaUsuarios;
	}
	
	//Compra
	public void saveCompra(Connection conexion, Compra compra, int x) throws SQLException {
		try {
			PreparedStatement consulta;
			if (x== 0) {
				consulta = conexion
						.prepareStatement("INSERT INTO " + this.tablaCompra + "(id_compra, fecha, hora,Id_UsuarioC,Id_Producto, Cantidad_pedida) VALUES(?,?,?,?,?,?)");
				consulta.setString(1, compra.getId_Compra());
				consulta.setDate(2, compra.getFecha());
				consulta.setTime(3, compra.getHora());
				consulta.setString(4, compra.getId_UsuarioC());
				consulta.setString(5, compra.getId_Producto());
				consulta.setInt(6, compra.getCantidad_pedida());
			} else {
				consulta = conexion.prepareStatement(
						"UPDATE " + this.tablaCompra + " SET fecha = ?, hora = ?, Id_UsuarioC = ?, Id_Producto = ?, Cantidad_pedida = ? WHERE Id_Compra = "+compra.getId_Compra());
				consulta.setDate(1, compra.getFecha());
				consulta.setTime(2, compra.getHora());
				consulta.setString(3, compra.getId_UsuarioC());
				consulta.setString(4, compra.getId_Producto());
				consulta.setInt(5, compra.getCantidad_pedida());
			}
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public Compra getCompra(Connection conexion, String Id_Compra) throws SQLException {
		Compra compra = null;
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT Id_Compra, fecha, hora, Id_Usuario, Id_Producto, Cantidad_pedida" + " FROM "
							+ this.tablaCompra + " WHERE Id_Compra = ?");
			consulta.setString(1, Id_Compra);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				compra = new Compra(Id_Compra, resultado.getDate("Fecha"),
						resultado.getTime("Hora"), resultado.getString("Id_Usuario"), resultado.getString("Id_Producto"), resultado.getInt("Cantidad_pedida"));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return compra;
	}

	public void removeCompra(Connection conexion, Compra compra) throws SQLException {
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("DELETE FROM " + this.tablaCompra + " WHERE Id_Compra = ?");
			consulta.setString(1, compra.getId_Compra());
			consulta.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
	}

	public List<Compra> getAllCompra(Connection conexion) throws SQLException {
		List<Compra> ListaCompras = new ArrayList<>();
		try {
			PreparedStatement consulta = conexion
					.prepareStatement("SELECT Id_Compra, fecha, hora, Id_UsuarioC, Id_Producto, Cantidad_pedida"
							+ " FROM " + this.tablaCompra);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				ListaCompras.add(new Compra(resultado.getString("Id_Compra"), resultado.getDate("Fecha"), resultado.getTime("Hora")
						,resultado.getString("Id_UsuarioC"), resultado.getString("Id_Producto"), resultado.getInt("Cantidad_pedida")));
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		}
		return ListaCompras;
	}
	
	
}