package views;

import java.sql.SQLException;

import services.ConexionBDSql;
import services.ObjectService;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectService os = new ObjectService();
        try {
			System.out.println(os.getAllProducts(ConexionBDSql.obtener()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
