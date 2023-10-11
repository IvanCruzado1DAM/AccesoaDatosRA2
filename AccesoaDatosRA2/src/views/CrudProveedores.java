package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;

public class CrudProveedores extends JFrame {

	private JFrame ProductWindow;
	private JLabel ProductLabel;
	private JButton AddProduct, DeleteProduct, ModifyProduct, Exit;

	private static JTable ProductTable;
	private DefaultTableModel ProductCombo;
	private JScrollPane ProductScroll;
	private JPanel ProductPanel;

	ObjectService OS = new ObjectService();

	Manejador mane = new Manejador();

	public CrudProveedores() throws ClassNotFoundException, SQLException {
		createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		// Ventana principal
		ProductWindow = new JFrame("Administrar proveedores");
		ProductWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductWindow.setBounds(100, 100, 691, 546);
		ProductWindow.setLocationRelativeTo(null);
		ProductWindow.getContentPane().setLayout(null);
		// Texto menu admin
		ProductLabel = new JLabel("Menú proveedores");
		ProductLabel.setToolTipText("texto eleccion");
		ProductLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ProductLabel.setBounds(160, -10, 223, 66);
		ProductWindow.getContentPane().add(ProductLabel);
		// Botones
		createButtons();
		// tabla
		createTable();
		ProductWindow.setVisible(true);
	}

	private void createTable() throws SQLException {
		ProductPanel = new JPanel();
		ProductPanel.setLayout(null);
		ProductPanel.setBounds(200, 0, 433, 463);
		// Crear el JTable
		// int id, String nombre, Float precio, String img, int stock,
		// String categoria,String marca,int proveedorid,nombre proveedor para localizarlo mejor
		String[] columnas = new String[] { "ID", "Nombre", "Dirección", "Número"};
		ProductCombo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};
		setTablaProducto(new JTable(ProductCombo));
		getTablaProducto().setPreferredScrollableViewportSize(new Dimension(250, 100));
		getTablaProducto().getTableHeader().setReorderingAllowed(true);
		getTablaProducto().setEnabled(true);
		// imagenes
		// getTablaProfesores().getSelectionModel().addListSelectionListener(mi);

		// Crear el ordenador de filas
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ProductCombo);
		getTablaProducto().setRowSorter(sorter);
		sorter.sort();
		// rellenar Productos
		List<Producto> listP;
		List<Proveedor> listPro;
		String nomPro =null;
		try {
			listPro=OS.getAllProveedor(ConexionBDSql.obtener());
			for (Proveedor p : listPro) {
				Object[] data = { p.getIdproveedor(), p.getNombre(), p.getDireccion(), p.getNumero()};
				ProductCombo.addRow(data);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ProductScroll = new JScrollPane(getTablaProducto());
		ProductScroll.setBounds(10, 45, 500, 430);
		ProductWindow.getContentPane().add(ProductScroll);

	}

	private void createButtons() {
		// añadir
		AddProduct = new JButton("Añadir");
		AddProduct.setBounds(553, 323, 100, 34);
		AddProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(AddProduct);
		// modificar
		ModifyProduct = new JButton("Modificar");
		ModifyProduct.setBounds(553, 363, 100, 34);
		ModifyProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(ModifyProduct);
		// eliminar
		DeleteProduct = new JButton("Borrar");
		DeleteProduct.setBounds(553, 403, 100, 34);
		DeleteProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(DeleteProduct);
		// volver
		Exit = new JButton("Volver");
		Exit.setBounds(566, 12, 77, 34);
		Exit.addActionListener(mane);
		ProductWindow.getContentPane().add(Exit);

	}

	private class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == AddProduct) {
				try {
					new AddModifyProveedor();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				ProductWindow.setVisible(false);
			} else if (obj == DeleteProduct) {

			} else if (obj == ModifyProduct) {
//				AddModifyProduct.productId=
				// modificar profesor
				int selectedRow = getTablaProducto().getSelectedRow();
				if (selectedRow != -1) {
					int id = (int) ProductCombo.getValueAt(selectedRow, 0);
					AddModifyProduct.productId= id;

					ProductWindow.setVisible(false);
					try {
						new AddModifyProduct();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(CrudProveedores.this, "ERROR CLASS NOT FOUND ","Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(CrudProveedores.this, "SQL ERROR ","Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CrudProveedores.this, "Por favor, selecciona una fila para modificar.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (obj == Exit) {
				// volver al menu principal
				JFrameAdmin jf = new JFrameAdmin ();
				jf.setVisible(true);
				// Cerrar ventana crud producto
				ProductWindow.dispose();
			}
		}

	}

	public static JTable getTablaProducto() {
		return ProductTable;
	}

	public void setTablaProducto(JTable ProductTable) {
		CrudProveedores.ProductTable = ProductTable;
	}
}
