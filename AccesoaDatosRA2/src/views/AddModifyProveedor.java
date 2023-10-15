package views;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Producto;
import models.Proveedor;
import services.ConexionBDSql;
import services.ObjectService;
import services.Test;

import javax.swing.SwingConstants;

public class AddModifyProveedor extends JFrame {

	// Supplier window add-modify
	private JFrame SupplierWindowAM;
	private JLabel lblName, lblAddress, lblNumber;
	private JTextField txtName, txtAddress, txtNumber;
	private JButton btnBack, btnUpdate;
	
	ObjectService OS = new ObjectService();
	public static int supplierId;
	private Proveedor supplier;

	public AddModifyProveedor() throws ClassNotFoundException, SQLException {
	    createWindow();
	}

	private void createWindow() throws ClassNotFoundException, SQLException {
		SupplierWindowAM = new JFrame("Menú editar proveedor");
		SupplierWindowAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SupplierWindowAM.setBounds(100, 100, 439, 355);
		SupplierWindowAM.setLocationRelativeTo(null);
		SupplierWindowAM.setContentPane(new JPanel() {
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
		SupplierWindowAM.getContentPane().setLayout(null);
		
	    lblName = new JLabel("Nombre:");
	    lblName.setBounds(98, 123, 68, 18);
	    SupplierWindowAM.getContentPane().add(lblName);

	    lblAddress = new JLabel("Dirección:");
	    lblAddress.setBounds(98, 159, 68, 18);
	    SupplierWindowAM.getContentPane().add(lblAddress);

	    lblNumber = new JLabel("Número:");
	    lblNumber.setBounds(98, 197, 68, 18);
	    SupplierWindowAM.getContentPane().add(lblNumber);

	    txtName = new JTextField();
	    txtName.setBounds(205, 122, 96, 19);
	    SupplierWindowAM.getContentPane().add(txtName);
	    

	    txtAddress = new JTextField();
	    txtAddress.setBounds(205, 158, 96, 19);
	    txtAddress.setColumns(10);
	    SupplierWindowAM.getContentPane().add(txtAddress);

	    txtNumber = new JTextField();
	    txtNumber.setBounds(205, 196, 96, 19);
	    txtNumber.setColumns(10);
	    SupplierWindowAM.getContentPane().add(txtNumber);

	    //editar proveedor
	    JLabel lblNewLabel = new JLabel("Editar Proveedor");
	    lblNewLabel.setBounds(80, 54, 242, 40);
	    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
	    SupplierWindowAM.getContentPane().add(lblNewLabel);
	    try {
	        supplier = OS.getProveedor(ConexionBDSql.obtener(), supplierId);

	        } catch (ClassNotFoundException | SQLException e) {

	            e.printStackTrace();
	        }
	        // Fill with all the data
	        txtName.setText(supplier.getNombre());
	        txtAddress.setText(supplier.getDireccion());
	        txtNumber.setText(String.valueOf(supplier.getNumero()));
	        
	        SupplierWindowAM.setVisible(true);
	        
	     //boton volver
	     btnBack = new JButton("Volver");
	     ImageIcon iconoExitOriginal = new ImageIcon("./icons/IconReturn.png");
		 Image imagenExitOriginal = iconoExitOriginal.getImage();
		 Image nuevaImagenExit =imagenExitOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		 ImageIcon iconoExitRedimensionado = new ImageIcon(nuevaImagenExit);
		 btnBack.setIcon(iconoExitRedimensionado);
	     btnBack.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent e) {
	     		SupplierWindowAM.dispose();
		    		try {
						CrudProveedor cp=new CrudProveedor();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	     	}
	     });
	     btnBack.setBounds(324, 23, 91, 27);
	     //boton actualizar
	     btnUpdate = new JButton("Actualizar");
	     ImageIcon iconoUpdateOriginal = new ImageIcon("./icons/IconUpdate.png");
		 Image imagenUpdateOriginal = iconoUpdateOriginal.getImage();
		 Image nuevaImagenUpdate =imagenUpdateOriginal.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		 ImageIcon iconoUpdateRedimensionado = new ImageIcon(nuevaImagenUpdate);
		 btnUpdate.setIcon(iconoUpdateRedimensionado);
	     btnUpdate.addActionListener(new ActionListener() {
	    	 public void actionPerformed(ActionEvent e) {
	    		if (txtName.getText().equals("") || txtAddress.getText().equals("") || txtNumber.getText().equals("") ) {
						JOptionPane.showMessageDialog(AddModifyProveedor.this, "Error: Los campos no pueden estar vacíos.",
								"Error de Registro", JOptionPane.ERROR_MESSAGE);
				}else if(txtNumber.getText().matches("[0-9]{9}")) {
		    			Proveedor p=new Proveedor(supplier.getIdproveedor(),txtName.getText(),txtAddress.getText(),Integer.parseInt(txtNumber.getText()));
		    			try {
							Test.os.saveProveedor(ConexionBDSql.obtener(), p,2);
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    			SupplierWindowAM.dispose();
			    		try {
							CrudProveedor cp=new CrudProveedor();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}else {
		    			JOptionPane.showMessageDialog(AddModifyProveedor.this,"El campo 'Número' solo admite 9 números","Error",JOptionPane.ERROR_MESSAGE);
		    		}
		    		
		    	}
	     });
	     btnUpdate.setBounds(121, 243, 133, 34);
	     
	     SupplierWindowAM.getContentPane().add(btnBack);
	     SupplierWindowAM.getContentPane().add(btnUpdate);
	}
	
}
