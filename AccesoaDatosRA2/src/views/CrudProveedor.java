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
import services.Test;

public class CrudProveedor extends JFrame {

	private JFrame ProveedorWindow;
	private JLabel ProveedorLabel;
	private JButton AddSupplier, DeleteSupplier, ModifySupplier, Exit;

	private static JTable ProveedorTable;
	private DefaultTableModel ProveedorCombo;
	private JScrollPane ProveedorScroll;
	private JPanel ProveedorPanel;

	ObjectService OS = new ObjectService();

	Manejador mane = new Manejador();

	public CrudProveedor() throws ClassNotFoundException, SQLException {
		createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		// Ventana principal
		ProveedorWindow = new JFrame("Administrar proveedores");
		ProveedorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ProveedorWindow.setBounds(100, 100, 739, 346);
		ProveedorWindow.setLocationRelativeTo(null);
		ProveedorWindow.getContentPane().setLayout(null);
		// Texto menu admin
		ProveedorLabel = new JLabel("Menú proveedores");
		ProveedorLabel.setToolTipText("texto eleccion");
		ProveedorLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ProveedorLabel.setBounds(160, -10, 223, 66);
		ProveedorWindow.getContentPane().add(ProveedorLabel);
		// Botones
		createButtons();
		// tabla
		createTable();
		ProveedorWindow.setVisible(true);
	}

	private void createTable() throws SQLException {
		ProveedorPanel = new JPanel();
		ProveedorPanel.setLayout(null);
		ProveedorPanel.setBounds(200, 0, 433, 463);
		// Crear el JTable
		// int id, String nombre, Float precio, String img, int stock,
		// String categoria,String marca,int proveedorid,nombre proveedor para localizarlo mejor
		String[] columnas = new String[] { "ID", "Nombre", "Dirección", "Número"};
		ProveedorCombo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};
		setTablaProveedor(new JTable(ProveedorCombo));
		getTablaProveedor().setPreferredScrollableViewportSize(new Dimension(250, 100));
		getTablaProveedor().getTableHeader().setReorderingAllowed(true);
		getTablaProveedor().setEnabled(true);
		// imagenes
		// getTablaProfesores().getSelectionModel().addListSelectionListener(mi);

		// Crear el ordenador de filas
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(ProveedorCombo);
		getTablaProveedor().setRowSorter(sorter);
		sorter.sort();
		// rellenar Proveedor
		List<Producto> listP;
		List<Proveedor> listPro;
		String nomPro =null;
		try {
			listPro=OS.getAllProveedor(ConexionBDSql.obtener());
			for (Proveedor p : listPro) {
				Object[] data = { p.getIdproveedor(), p.getNombre(), p.getDireccion(), p.getNumero()};
				ProveedorCombo.addRow(data);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		ProveedorScroll = new JScrollPane(getTablaProveedor());
		ProveedorScroll.setBounds(10, 45, 500, 231);
		ProveedorWindow.getContentPane().add(ProveedorScroll);

	}

	private void createButtons() {
		// añadir
		AddSupplier = new JButton("Insertar");
		AddSupplier.setBounds(539, 103, 162, 34);
		AddSupplier.addActionListener(mane);
		ProveedorWindow.getContentPane().add(AddSupplier);
		// modificar
		ModifySupplier = new JButton("Modificar");
		ImageIcon iconoUpdateOriginal = new ImageIcon("./icons/IconUpdate.png");
		Image imagenUpdateOriginal = iconoUpdateOriginal.getImage();
		Image nuevaImagenUpdate =imagenUpdateOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoUpdateRedimensionado = new ImageIcon(nuevaImagenUpdate);
		ModifySupplier.setIcon(iconoUpdateRedimensionado);
		ModifySupplier.setBounds(539, 143, 162, 34);
		ModifySupplier.addActionListener(mane);
		ProveedorWindow.getContentPane().add(ModifySupplier);
		// eliminar
		DeleteSupplier = new JButton("Borrar");
		DeleteSupplier.setBounds(539, 190, 162, 34);
		DeleteSupplier.addActionListener(mane);
		ProveedorWindow.getContentPane().add(DeleteSupplier);
		// volver
		Exit = new JButton("Volver");
		Exit.setBounds(539, 12, 162, 34);
		Exit.addActionListener(mane);
		ProveedorWindow.getContentPane().add(Exit);

	}

	private class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == AddSupplier) {
				try {
					new AddModifyProveedor();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				ProveedorWindow.setVisible(false);
			} else if (obj == DeleteSupplier) {
				int id = (int) ProveedorCombo.getValueAt(getTablaProveedor().getSelectedRow(), 0);
				Proveedor p;
				try {
					p = Test.os.getProveedor(ConexionBDSql.obtener(),id);
					Test.os.removeProveedor(ConexionBDSql.obtener(), p);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(CrudProveedor.this, "Registro eliminado correctamente.",
						"Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
				try {
					new CrudProveedor();
				} catch (ClassNotFoundException | SQLException e2) {
					JOptionPane.showMessageDialog(CrudProveedor.this, "Error: vuelve a intentarlo","Error", JOptionPane.ERROR_MESSAGE);
				}
				ProveedorWindow.dispose();
				
			} else if (obj == ModifySupplier) {
//				AddModifyProduct.productId=
				// modificar profesor
				int selectedRow = getTablaProveedor().getSelectedRow();
				if (selectedRow != -1) {
					int id = (int) ProveedorCombo.getValueAt(selectedRow, 0);
					AddModifyProveedor.productId= id;

					ProveedorWindow.setVisible(false);
					try {
						new AddModifyProduct();
					} catch (ClassNotFoundException e1) {
						JOptionPane.showMessageDialog(CrudProveedor.this, "ERROR CLASS NOT FOUND ","Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(CrudProveedor.this, "SQL ERROR ","Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(CrudProveedor.this, "Por favor, selecciona una fila para modificar.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (obj == Exit) {
				// volver al menu principal
				JFrameAdmin jf = new JFrameAdmin ();
				jf.setVisible(true);
				// Cerrar ventana crud producto
				ProveedorWindow.dispose();
			}
		}

	}

	public static JTable getTablaProveedor() {
		return ProveedorTable;
	}

	public void setTablaProveedor(JTable ProveedorTable) {
		CrudProveedor.ProveedorTable = ProveedorTable;
	}
	

}
