package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameCreateTransaction extends JFrame{
	
	private JLabel  Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField Idtransactiontext, empleadotext, proveedortext, marcatext, productotext, cantidadtext, fechatext;
    private JButton Register, Return;
	
	public JFrameCreateTransaction() {
		super("Crear Transacciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(626,470);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		Idtransaction = new JLabel("Idtransacccion");
		Idtransaction.setBounds(69, 53, 112, 25);
		empleado = new JLabel("Empleado");
		empleado.setBounds(277, 449, 58, 37);
		proveedor = new JLabel("Proveedor");
		proveedor.setBounds(69, 89, 112, 42);
		marca = new JLabel("Marca");
		marca.setBounds(69, 142, 112, 29);
		producto = new JLabel("Nombre");
		producto.setBounds(69, 197, 97, 29);
		cantidad = new JLabel("Cantidad");
		cantidad.setBounds(69, 237, 102, 42);
		fecha = new JLabel("Fecha");
		fecha.setBounds(69, 290, 97, 37);
		
		Idtransactiontext = new JTextField(10);
		Idtransactiontext.setBounds(175, 50, 300, 30);
		Idtransactiontext.setEditable(false);
		empleadotext = new JTextField(10);
		empleadotext.setBounds(266, 489, 86, 33);
		empleadotext.setText(JFrameLogin.EmActivo.getUsername());
		empleadotext.setEditable(false);
		proveedortext = new JTextField(10);
		proveedortext.setBounds(175, 95, 300, 30);
		marcatext = new JTextField(10);
		marcatext.setBounds(175, 141, 300, 30);
		productotext = new JTextField(10);
		productotext.setBounds(175, 196, 300, 30);
		cantidadtext = new JTextField(10);
		cantidadtext.setBounds(175, 245, 300, 29);
		fechatext = new JTextField(10);
		fechatext.setBounds(175, 293, 300, 30);
		fechatext.setText(String.valueOf(Date.class));
		
		Register = new JButton ("Registrar");
		Register.setBounds(352, 347, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date d = new Date ();
				d.toInstant();
				Transaccion t = new Transaccion ((java.sql.Date) d, Integer.valueOf(productotext.getText()), Integer.valueOf(proveedortext.getText()), Integer.valueOf(proveedortext.getText()), Integer.valueOf(empleadotext.getText()));
			    try {
					Test.os.saveTransaccion(ConexionBDSql.obtener(), t, 1);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Return = new JButton ("Return");
		Return.setBounds(138, 347, 120, 60);
		Return.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				JFrameTransactions jf = new JFrameTransactions ();
				jf.setVisible(true);
			}
		});
		
		getContentPane().setLayout(null);
		getContentPane().add(Idtransaction);
		getContentPane().add(proveedor);
		getContentPane().add(marca);
		getContentPane().add(producto);
		getContentPane().add(cantidad);
		getContentPane().add(fecha);
		getContentPane().add(Idtransactiontext);
		getContentPane().add(proveedortext);
		getContentPane().add(marcatext);
		getContentPane().add(productotext);
		getContentPane().add(cantidadtext);
		getContentPane().add(fechatext);
		getContentPane().add(Register);
		getContentPane().add(Return);
	}
}
