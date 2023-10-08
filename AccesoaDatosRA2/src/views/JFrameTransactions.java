package views;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import models.Empleado;
import models.Producto;
import models.Proveedor;
import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameTransactions extends JFrame {

	private JLabel NombreTabla;
	private static JTableBloqueoCeldas model;
	private static JTable table;
	private static JComboBox<String> Filtro_Empleado, Filtro_Proveedor, Filtro_Marca, Filtro_Producto;
	private List<String> Empleado = new ArrayList<>();

	public JFrameTransactions() {
		super("Transactions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(879, 581);
		setResizable(false);
		setLocationRelativeTo(null);

		NombreTabla = new JLabel("Transactions");
		NombreTabla.setBounds(0, 0, 0, 0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 86, 704, 366);

		table = new JTable();
		model = new JTableBloqueoCeldas();
		table.setModel(model);

		scrollPane.setViewportView(table);

		model.addColumn("ID");
		model.addColumn("EMPLEADO");
		model.addColumn("PROVEEDOR");
		model.addColumn("MARCA");
		model.addColumn("PRODUCTO");
		model.addColumn("CANTIDAD");
		model.addColumn("FECHA");
		table.setRowHeight(30);

		EscribirTabla();

		Filtro_Empleado = new JComboBox<String>();
		Filtro_Empleado
				.setFont(new Font("Arial", Filtro_Empleado.getFont().getStyle(), Filtro_Empleado.getFont().getSize()));
		Filtro_Empleado.setBounds(298, 11, 228, 64);
		Empleado = new ArrayList<>();
		Filtro_Empleado.addItem("Ninguno");
		try {
			for (Empleado em : Test.os.getAllEmpleados(ConexionBDSql.obtener())) {
				String m = em.getUsername();
				if (!Empleado.contains(m)) {
					Empleado.add(m);
					Filtro_Empleado.addItem(m);
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().setLayout(null);

		getContentPane().add(scrollPane);
		getContentPane().add(NombreTabla);
		getContentPane().add(Filtro_Empleado);
	}

	public static void EscribirTabla() {
		try {
			for (Transaccion t : Test.os.getAllTransacciones(ConexionBDSql.obtener())) {
				Producto p = Test.os.getProduct(ConexionBDSql.obtener(), t.getIdproducto());
				Proveedor pro = Test.os.getProveedor(ConexionBDSql.obtener(), t.getIdproveedor());
				Empleado em = Test.os.getEmpleado(ConexionBDSql.obtener(), t.getIdempleado());
				Object[] Fila = new Object[model.getColumnCount()];
				Fila[0] = t.getIdinventario();
				Fila[1] = em.getUsername();
				Fila[2] = pro.getNombre();
				Fila[3] = p.getMarca();
				Fila[4] = p.getNombre();
				Fila[5] = t.getCantidad();
				Fila[6] = t.getFecha();
				model.addRow(Fila);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
