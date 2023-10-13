package views;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameAdmin extends JFrame {
	
	private JButton Products, Supplier, Transaction, SignOff;
	private final Icon IconProducts = new ImageIcon("icons/IconProducts.png"), IconSuppliers = new ImageIcon("icons/IconSuppliers.png")
			,IconTransaction = new ImageIcon("icons/IconTransactions.png"), IconSignOff = new ImageIcon ("icons/IconSignOff.png");

	public JFrameAdmin () {
		super("Admin | Empleado : " + JFrameLogin.EmActivo.getUsername());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(700,429);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    
	    setContentPane(new JPanel() {
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
	    
	    Products = new JButton ("Productos");
	    Products.setFont(new Font("Arial", Font.PLAIN, 16));
	    Products.setBounds(241, 33, 200, 236);
	    Products.setIcon(IconProducts);
	    Products.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					CrudProducto cp = new CrudProducto();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	    });
	    
	    Supplier = new JButton ("Proveedores");
	    Supplier.setFont(new Font("Arial", Font.PLAIN, 16));
	    Supplier.setBounds(31, 33, 200, 236);
	    Supplier.setIcon(IconSuppliers);
	    Supplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					CrudProveedor cprov=new CrudProveedor();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	    });
	    
	    Transaction = new JButton ("Transacción");
	    Transaction.setFont(new Font("Arial", Font.PLAIN, 16));
	    Transaction.setBounds(451, 33, 200, 236);
	    Transaction.setIcon(IconTransaction);
	    Transaction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameTransactions jf = new JFrameTransactions ();
				jf.setVisible(true);
			}
	    });
	    
	    SignOff = new JButton ("Cerrar Sesión");
	    SignOff.setFont(new Font("Arial", Font.PLAIN, 16));
	    SignOff.setBounds(181, 289, 341, 77);
	    SignOff.setIcon(IconSignOff);
	    SignOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameLogin jf = new JFrameLogin ();
				jf.setVisible(true);
			}
	    });
	    getContentPane().setLayout(null);
	    getContentPane().add(Products);
	    getContentPane().add(Supplier);
	    getContentPane().add(Transaction);
	    getContentPane().add(SignOff);
	}
}
