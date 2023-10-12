package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class JFrameAdmin extends JFrame {
	
	private JButton Products, Supplier, Transaction, SignOff;

	public JFrameAdmin () {
		super("Empleado " + JFrameLogin.EmActivo.getUsername());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(643,429);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    
	    Products = new JButton ("Productos");
	    Products.setFont(new Font("Arial", Font.PLAIN, 16));
	    Products.setBounds(201, 33, 200, 100);
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
	    Supplier.setBounds(76, 169, 200, 100);
	    Supplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					CrudProveedores cprov=new CrudProveedores();
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
	    Transaction.setBounds(347, 169, 200, 100);
	    Icon IconTransaction = new ImageIcon ("icons/IconTransactions.png");
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
	    SignOff.setBounds(118, 288, 341, 77);
	    Icon IconSignOff = new ImageIcon ("icons/IconSignOff.png");
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
