package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
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

	private static JTable SupplierTable;
	private DefaultTableModel SupplierCombo;
	private JScrollPane SupplierScroll;

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
		ProveedorWindow.setContentPane(new JPanel() {
			BufferedImage backgroundImage;
			{
				try {
//---------------------------Load your background image--------------------------//
					backgroundImage = ImageIO.read(new File("background/backgroundTransactions.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
//-------------------------------Draw the background image------------------------//
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		});
		ProveedorWindow.getContentPane().setLayout(null);
		ImageIcon IconTransaction = new ImageIcon("icons/IconSuppliers.png");
		ProveedorWindow.setIconImage(IconTransaction.getImage());
		// Texto menu admin
		
		ProveedorLabel = new JLabel("Menú proveedores");
		ProveedorLabel.setToolTipText("texto eleccion");
		ProveedorLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		ProveedorLabel.setBounds(160, 10, 223, 34);
		ProveedorWindow.getContentPane().add(ProveedorLabel);
		// Botones
		createButtons();
		// tabla
		createTable();
		ProveedorWindow.setVisible(true);
	}

	private void createTable() throws SQLException {
		
		// Crear el JTable
		String[] columnas = new String[] { "ID", "Nombre", "Dirección", "Número"};
		SupplierCombo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};
		setTablaProveedor(new JTable(SupplierCombo));
		getTablaProveedor().setPreferredScrollableViewportSize(new Dimension(250, 100));
		getTablaProveedor().getTableHeader().setReorderingAllowed(true);
		getTablaProveedor().setEnabled(true);
		
		// Crear las filas de la tabla
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(SupplierCombo);
		getTablaProveedor().setRowSorter(sorter);
		sorter.sort();
		// rellenar Proveedor
		List<Proveedor> listPro;
		try {
			listPro=OS.getAllProveedor(ConexionBDSql.obtener());
			for (Proveedor p : listPro) {
				Object[] data = { p.getIdproveedor(), p.getNombre(), p.getDireccion(), p.getNumero()};
				SupplierCombo.addRow(data);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		SupplierScroll = new JScrollPane(getTablaProveedor());
		SupplierScroll.setBounds(10, 54, 501, 232);
		ProveedorWindow.getContentPane().add(SupplierScroll);

	}

	private void createButtons() {
		// añadir
		AddSupplier = new JButton("Insertar");
		ImageIcon iconoRegisterOriginal = new ImageIcon("./icons/IconRegister.png");
		Image imagenRegisterOriginal = iconoRegisterOriginal.getImage();
		Image nuevaImagenRegister =imagenRegisterOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoRegisterRedimensionado = new ImageIcon(nuevaImagenRegister);
		AddSupplier.setIcon(iconoRegisterRedimensionado);
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
		DeleteSupplier = new JButton("Eliminar");
		DeleteSupplier.setBounds(539, 190, 162, 34);
		DeleteSupplier.addActionListener(mane);
		ImageIcon iconoDeleteOriginal = new ImageIcon("./icons/IconDelete.png");
		Image imagenDeleteOriginal = iconoDeleteOriginal.getImage();
		Image nuevaImagenDelete =imagenDeleteOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoDeleteRedimensionado = new ImageIcon(nuevaImagenDelete);
		DeleteSupplier.setIcon(iconoDeleteRedimensionado);
		ProveedorWindow.getContentPane().add(DeleteSupplier);
		// volver
		Exit = new JButton("Volver");
		Exit.setBounds(539, 12, 162, 34);
		Exit.addActionListener(mane);
		ImageIcon iconoExitOriginal = new ImageIcon("./icons/IconReturn.png");
		Image imagenExitOriginal = iconoExitOriginal.getImage();
		Image nuevaImagenExit =imagenExitOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon iconoExitRedimensionado = new ImageIcon(nuevaImagenExit);
		Exit.setIcon(iconoExitRedimensionado);
		ProveedorWindow.getContentPane().add(Exit);

	}

	private class Manejador implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == AddSupplier) {
				try {
					new AddProveedor();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				ProveedorWindow.setVisible(false);
			} else if (obj == DeleteSupplier) {
				int selectedRow = getTablaProveedor().getSelectedRow();
				if (selectedRow != -1) {
					int id = (int) SupplierCombo.getValueAt(selectedRow, 0);
					AddModifyProveedor.supplierId= id;
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
				}else{
						JOptionPane.showMessageDialog(CrudProveedor.this, "Por favor, selecciona una fila para eliminar.",
								"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			} else if (obj == ModifySupplier) {
//				modificar proveedor
				int selectedRow = getTablaProveedor().getSelectedRow();
				if (selectedRow != -1) {
					int id = (int) SupplierCombo.getValueAt(selectedRow, 0);
					AddModifyProveedor.supplierId= id;

					ProveedorWindow.setVisible(false);
					try {
						new AddModifyProveedor();
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
				// Cerrar ventana crud proveedor
				ProveedorWindow.dispose();
			}
		}

	}

	public static JTable getTablaProveedor() {
		return SupplierTable;
	}

	public void setTablaProveedor(JTable SupplierTable) {
		CrudProveedor.SupplierTable = SupplierTable;
	}
	

}
