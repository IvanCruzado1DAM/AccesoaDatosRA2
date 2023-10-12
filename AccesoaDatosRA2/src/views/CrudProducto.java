package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;

public class CrudProducto extends JFrame {
	private static final long serialVersionUID = 1L;
	// ventana
	private JFrame ProductWindow;
	private JLabel ProductLabel, ProductImg,
			// filtro
			LabelCategoria, LabelNombre, LabelMarca, LabelPrecio, LabelProveedor, LabelStock;
	private JTextField FieldNombre, FieldStock, FieldPrecio;
	// desplegables
	private JComboBox<Proveedor> ComboProveedor;
	private JComboBox<Producto> ComboCategoria, ComboMarca;
	private JComboBox<String> ComboPrecio, ComboStock;
	// ventana
	private JButton AddProduct, DeleteProduct, ModifyProduct, Exit;
	private static JTable ProductTable;
	private DefaultTableModel ProductCombo;
	private JScrollPane ProductScroll;
	private JPanel ProductPanel;
	// manejador
	ManejadorImagen mi = new ManejadorImagen();
	ObjectService OS = new ObjectService();
	Manejador mane = new Manejador();

	public CrudProducto() throws ClassNotFoundException, SQLException {
		createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		// Ventana principal
		ProductWindow = new JFrame("Administrar productos");
		ProductWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProductWindow.setBounds(100, 100, 749, 600);
		ProductWindow.setLocationRelativeTo(null);
		ProductWindow.getContentPane().setLayout(null);
		// Texto menu admin
		ProductLabel = new JLabel("Menu productos");
		ProductLabel.setToolTipText("texto eleccion");
		ProductLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ProductLabel.setBounds(259, -13, 203, 66);
		ProductWindow.getContentPane().add(ProductLabel);
		// Imagen
		ProductImg = new JLabel("");
		ProductImg.setBounds(529, 142, 180, 180);
		ProductWindow.getContentPane().add(ProductImg);
		// Botones
		createButtons();
		// tabla
		createTable();

		// filtros
		createFilters();

		ProductWindow.setVisible(true);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createFilters() {
		// filtro categoria
		LabelCategoria = new JLabel("Categoria: ");
		LabelCategoria.setToolTipText("Filtrar por categoria");
		LabelCategoria.setBounds(23, 31, 64, 48);
		ProductWindow.getContentPane().add(LabelCategoria);
		// desplegable categoria
		ComboCategoria = new JComboBox();
		ComboCategoria.setBounds(10, 78, 100, 21);
		ProductWindow.getContentPane().add(ComboCategoria);

		// filtro nombre
		LabelNombre = new JLabel("Nombre: ");
		LabelNombre.setToolTipText("Filtrar por nombre");
		LabelNombre.setBounds(259, 31, 58, 48);
		ProductWindow.getContentPane().add(LabelNombre);
		// field nombre
		FieldNombre = new JTextField();
		FieldNombre.setBounds(231, 79, 96, 19);
		ProductWindow.getContentPane().add(FieldNombre);
		FieldNombre.setColumns(10);

		// filtro marca
		LabelMarca = new JLabel("Marca: ");
		LabelMarca.setToolTipText("Filtrar por marca");
		LabelMarca.setBounds(150, 22, 58, 66);
		ProductWindow.getContentPane().add(LabelMarca);
		// desplegable marca
		ComboMarca = new JComboBox();
		ComboMarca.setBounds(121, 78, 90, 21);
		ProductWindow.getContentPane().add(ComboMarca);

		// filtro precio
		LabelPrecio = new JLabel("Precio: ");
		LabelPrecio.setToolTipText("Filtrar por marca");
		LabelPrecio.setBounds(493, 22, 64, 66);
		ProductWindow.getContentPane().add(LabelPrecio);
		// desplegable simbolos
		ComboPrecio = new JComboBox();
		ComboPrecio.setBounds(447, 78, 38, 21);
		ProductWindow.getContentPane().add(ComboPrecio);
		// field precio
		FieldPrecio = new JTextField();
		FieldPrecio.setBounds(493, 79, 64, 19);
		ProductWindow.getContentPane().add(FieldPrecio);
		FieldPrecio.setColumns(10);
		// filtro stock
		LabelStock = new JLabel("Stock: ");
		LabelStock.setToolTipText("Filtrar por marca");
		LabelStock.setBounds(605, 22, 58, 66);
		ProductWindow.getContentPane().add(LabelStock);
		// desplegable simbolos
		ComboStock = new JComboBox();
		ComboStock.setBounds(567, 78, 38, 21);
		ProductWindow.getContentPane().add(ComboStock);
		// field stock
		FieldStock = new JTextField();
		FieldStock.setColumns(10);
		FieldStock.setBounds(617, 79, 74, 19);
		ProductWindow.getContentPane().add(FieldStock);
		// filtro proveedor
		LabelProveedor = new JLabel("Proovedor: ");
		LabelProveedor.setToolTipText("Filtrar por marca");
		LabelProveedor.setBounds(360, 22, 70, 66);
		ProductWindow.getContentPane().add(LabelProveedor);
		// desplegable proveedor
		ComboProveedor = new JComboBox();
		ComboProveedor.setBounds(337, 78, 100, 21);
		ProductWindow.getContentPane().add(ComboProveedor);
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
		String[] columnas = new String[] { "ID", "Nombre", "Marca", "Precio", "Img", "Categoria", "Id Proveedor",
				"Nombre proovedor" };
		ProductCombo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};
		setTablaProducto(new JTable(ProductCombo));
		ProductTable.setPreferredScrollableViewportSize(new Dimension(250, 100));
		ProductTable.getTableHeader().setReorderingAllowed(true);
		ProductTable.setEnabled(true);
		// imagenes
		ProductTable.getSelectionModel().addListSelectionListener(mi);
		// Crear el ordenador de filas
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ProductCombo);
		ProductTable.setRowSorter(sorter);
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
					}
				}
				Object[] data = { p.getIdproducto(), p.getNombre(), p.getMarca(), p.getPrecio(), p.getImg(),
						p.getCategoria(), p.getProveedorid(), nomPro };

				ProductCombo.addRow(data);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ProductScroll = new JScrollPane(ProductTable);
		ProductScroll.setBounds(10, 123, 500, 430);
		ProductWindow.getContentPane().add(ProductScroll);

	}

	private void createButtons() {
		// añadir
		AddProduct = new JButton("ADD");
		AddProduct.setBounds(567, 357, 100, 34);
		AddProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(AddProduct);
		// modificar
		ModifyProduct = new JButton("MODIFY");
		ModifyProduct.setBounds(567, 401, 100, 34);
		ModifyProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(ModifyProduct);
		// eliminar
		DeleteProduct = new JButton("DELETE");
		DeleteProduct.setBounds(567, 450, 100, 34);
		DeleteProduct.addActionListener(mane);
		ProductWindow.getContentPane().add(DeleteProduct);
		// volver
		Exit = new JButton("EXIT");
		Exit.setBounds(580, 519, 77, 34);
		Exit.addActionListener(mane);
		ProductWindow.getContentPane().add(Exit);

	}

	private class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			int selectedRow = ProductTable.getSelectedRow();
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
					String nombre = ProductCombo.getValueAt(selectedRow, 1).toString();
					int confirmResult = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que quieres eliminar este registro?\nID: " + id + "\nNombre: " + nombre,
							"Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
					if (confirmResult == JOptionPane.YES_OPTION) {
						try {
							OS.removeProductoID(ConexionBDSql.obtener(), id);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();

//						try {
//							try {
//								//remover con id4
//							} catch (ClassNotFoundException e1) {
//								e1.printStackTrace();
//							} catch (SQLException e1) {
//								e1.printStackTrace();
//							}
//							File imagenes = new File(ruta);
//							Files.deleteIfExists(imagenes.toPath());
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}

							JOptionPane.showMessageDialog(CrudProducto.this, "Registro eliminado correctamente.",
									"Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
							// RELOAD
							try {
								new CrudProducto();
							} catch (ClassNotFoundException e2) {
								e2.printStackTrace();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							ProductWindow.dispose();

						}
					} else {
						JOptionPane.showMessageDialog(CrudProducto.this,
								"Por favor, selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CrudProducto.this, "Por favor, selecciona una fila para modificar.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (obj == ModifyProduct) {
				System.out.println("Modificar");
//				AddModifyProduct.productId=
				// modificar profesor
				if (selectedRow != -1) {
					int id = (int) ProductCombo.getValueAt(selectedRow, 0);
					try {
						AddModifyProduct.productId = id;
						new AddModifyProduct();
						ProductWindow.setVisible(false);

					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(CrudProducto.this, "ERROR CLASS NOT FOUND ", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(CrudProducto.this, "SQL ERROR ", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CrudProducto.this, "Por favor, selecciona una fila para modificar.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (obj == Exit) {
				System.out.println("Salir");
				// volver al menu principal
				JFrameAdmin jf = new JFrameAdmin();
				jf.setVisible(true);
				// Cerrar ventana crud producto
				ProductWindow.dispose();
			}
		}

	}

	public class ManejadorImagen implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			if (ProductTable.getValueAt(ProductTable.getSelectedRow(), 4) != null) {
				Image img = new ImageIcon(ProductTable.getValueAt(ProductTable.getSelectedRow(), 4).toString())
						.getImage();

				Image newimg = img.getScaledInstance(150, 180, java.awt.Image.SCALE_SMOOTH);
				ImageIcon imageIcon = new ImageIcon(newimg);
				ProductImg.setIcon(imageIcon);
			}

		}
	}

	public void setTablaProducto(JTable ProductTable) {
		CrudProducto.ProductTable = ProductTable;
	}
}
