package views;

import java.sql.Date;
import java.sql.SQLException;

import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.ObjectService;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectService os = new ObjectService();
        try {
			System.out.println(os.getAllProducts(ConexionBDSql.obtener()));
			System.out.println(os.getAllProveedor(ConexionBDSql.obtener()));
			System.out.println(os.getAllEmpleados(ConexionBDSql.obtener()));
			System.out.println(os.getAllTransacciones(ConexionBDSql.obtener()));
			os.removeProveedor(ConexionBDSql.obtener(), new Proveedor(2,"Juan","calle alcatraz 2", 675543234));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
