package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

public class CrudProducto extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame ProductWindow;
	private JLabel ProductLabel,ProductImg;

	private JButton AddProduct, DeleteProduct, ModifyProduct, Exit;

	private static JTable ProductTable;
	private DefaultTableModel ProductCombo;
	private JScrollPane ProductScroll;
	private JPanel ProductPanel;
//	ManejadorImagen mi = new ManejadorImagen();

	ObjectService OS = new ObjectService();

	Manejador mane = new Manejador();

	public CrudProducto() throws ClassNotFoundException, SQLException {
		createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		// Ventana principal
		ProductWindow = new JFrame("Administrar productos");
		ProductWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductWindow.setBounds(100, 100, 650, 519);
		ProductWindow.setLocationRelativeTo(null);
		ProductWindow.getContentPane().setLayout(null);
		// Texto menu admin
		ProductLabel = new JLabel("Menu productos");
		ProductLabel.setToolTipText("texto eleccion");
		ProductLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ProductLabel.setBounds(145, -10, 223, 66);
		ProductWindow.add(ProductLabel);
		//Imagen
		ProductImg = new JLabel("");
		ProductImg.setBounds(464, 70, 180, 180);
		ProductWindow.getContentPane().add(ProductImg);
		// Botones
		createButtons();
		// tabla
		createTable();
		ProductWindow.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void createTable() throws SQLException {
		ProductPanel = new JPanel();
		ProductPanel.setLayout(null);
		ProductPanel.setBounds(200, 0, 433, 463);
		// Crear el JTable
		// int id, String nombre, Float precio, String img, int stock,
		// String categoria,String marca,int proveedorid,nombre proveedor para
		// localizarlo mejor
		String[] columnas = new String[] { "ID", "Nombre", "Marca", "Precio", "Img", "Categoria",
				"Id Proveedor", "Nombre proovedor" };
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
		String nomPro = null;
		try {
			listP = OS.getAllProducts(ConexionBDSql.obtener());
			listPro = OS.getAllProveedor(ConexionBDSql.obtener());
			for (Producto p : listP) {
				for (Proveedor pro : listPro) {
					if (p.getProveedorid() == pro.getIdproveedor()) {
						nomPro = pro.getNombre();
					}}
					Object[] data = { p.getIdproducto(), p.getNombre(), p.getMarca(), p.getPrecio(), p.getImg(),
							p.getCategoria(), p.getProveedorid(), nomPro };

					ProductCombo.addRow(data);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ProductScroll = new JScrollPane(getTablaProducto());
		ProductScroll.setBounds(10, 45, 500, 430);
		ProductWindow.add(ProductScroll);

	}

	private void createButtons() {
		// añadir
		AddProduct = new JButton("ADD");
		AddProduct.setBounds(525, 300, 100, 34);
		AddProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(AddProduct);
		// modificar
		ModifyProduct = new JButton("MODIFY");
		ModifyProduct.setBounds(525, 340, 100, 34);
		ModifyProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(ModifyProduct);
		// eliminar
		DeleteProduct = new JButton("DELETE");
		DeleteProduct.setBounds(525, 380, 100, 34);
		DeleteProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(DeleteProduct);
		// volver
		Exit = new JButton("EXIT");
		Exit.setBounds(540, 438, 77, 34);
		Exit.addActionListener(mane);
		ProductWindow.getContentPane().add(Exit);

	}

	private class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			int selectedRow = getTablaProducto().getSelectedRow();
			if (obj == AddProduct) {
				try {
					new AddModifyProduct();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				ProductWindow.setVisible(false);
			} else if (obj == DeleteProduct) {
				// eliminar producto
				if (selectedRow != -1) {
					int id = (int) ProductCombo.getValueAt(selectedRow, 0);
					String ruta = ProductCombo.getValueAt(selectedRow, 4).toString();
					String nombre = ProductCombo.getValueAt(selectedRow, 1).toString();
					int confirmResult = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que quieres eliminar este registro?\nID: " + id + "\nNombre: " + nombre,
							"Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
					if (confirmResult == JOptionPane.YES_OPTION) {
						try {
							OS.removeProductoID(ConexionBDSql.obtener(), id);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//						try {
//							try {
//								//remover con id4
//							} catch (ClassNotFoundException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							} catch (SQLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							File imagenes = new File(ruta);
//							Files.deleteIfExists(imagenes.toPath());
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
						
						JOptionPane.showMessageDialog(CrudProducto.this, "Registro eliminado correctamente.",
								"Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
						ProductWindow.setVisible(false);
						//RELOAD
						ProductWindow.setVisible(true);
				}}else{
					JOptionPane.showMessageDialog(CrudProducto.this, "Por favor, selecciona una fila para eliminar.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (obj == ModifyProduct) {
//				AddModifyProduct.productId=
				// modificar profesor
				if (selectedRow != -1) {
					int id = (int) ProductCombo.getValueAt(selectedRow, 0);
					try {
						AddModifyProduct.productId= id;
						new AddModifyProduct();
						ProductWindow.setVisible(false);
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(CrudProducto.this, "ERROR CLASS NOT FOUND ","Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(CrudProducto.this, "SQL ERROR ","Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CrudProducto.this, "Por favor, selecciona una fila para modificar.",
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
		CrudProducto.ProductTable = ProductTable;
	}

}
