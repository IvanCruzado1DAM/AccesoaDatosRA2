package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;

public class JFrameAdmin extends JFrame {
	
	private JButton Products, Employee, Supplier, Transaction, SignOff;

	public JFrameAdmin () {
		super("Empleado " + JFrameLogin.EmActivo.getUsername());
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(643,429);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    
	    Products = new JButton ("Products");
	    Products.setFont(new Font("Arial", Font.PLAIN, 16));
	    Products.setBounds(76, 52, 160, 100);
	    Products.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
	    });
	    
	    Employee = new JButton ("Employee");
	    Employee.setFont(new Font("Arial", Font.PLAIN, 16));
	    Employee.setBounds(347, 52, 160, 100);
	    Employee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
	    });
	    
	    Supplier = new JButton ("Supplier");
	    Supplier.setFont(new Font("Arial", Font.PLAIN, 16));
	    Supplier.setBounds(76, 169, 160, 100);
	    Supplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
	    });
	    
	    Transaction = new JButton ("Transaction");
	    Transaction.setFont(new Font("Arial", Font.PLAIN, 16));
	    Transaction.setBounds(347, 169, 160, 100);
	    Transaction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
	    });
	    
	    SignOff = new JButton ("SignOff");
	    SignOff.setFont(new Font("Arial", Font.PLAIN, 16));
	    SignOff.setBounds(118, 288, 341, 77);
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
	    
	    getContentPane().add(Employee);
	    getContentPane().add(Products);
	    getContentPane().add(Supplier);
	    getContentPane().add(Transaction);
	    getContentPane().add(SignOff);
	}
}
