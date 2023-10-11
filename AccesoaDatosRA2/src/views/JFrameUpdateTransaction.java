package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import models.Transaccion;
import services.ConexionBDSql;
import services.Test;

public class JFrameUpdateTransaction extends JFrame {
	
	private JLabel  Idtransaction, empleado, proveedor, marca, producto, cantidad, fecha;
	private JTextField Idtransactiontext, empleadotext, proveedortext, marcatext, productotext, cantidadtext, fechatext;
    private JButton Register, Return;
    private Date d = new Date (0);
	
	public JFrameUpdateTransaction () {
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
		Idtransactiontext.setText(String.valueOf(JFrameTransactions.t.getIdinventario()));
		Idtransactiontext.setEditable(false);
		empleadotext = new JTextField(10);
		empleadotext.setBounds(266, 489, 86, 33);
		try {
			empleadotext.setText(String.valueOf(Test.os.getEmpleado(ConexionBDSql.obtener(), JFrameTransactions.t.getIdempleado())));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empleadotext.setEditable(false);
		proveedortext = new JTextField(10);
		proveedortext.setBounds(175, 95, 300, 30);
		try {
			proveedortext.setText(String.valueOf(Test.os.getProveedor(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproveedor()).getIdproveedor()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		marcatext = new JTextField(10);
		marcatext.setBounds(175, 141, 300, 30);
		try {
			marcatext.setText(Test.os.getProduct(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproducto()).getMarca());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		productotext = new JTextField(10);
		productotext.setBounds(175, 196, 300, 30);
		try {
			productotext.setText(Test.os.getProduct(ConexionBDSql.obtener(), JFrameTransactions.t.getIdproducto()).getNombre());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cantidadtext = new JTextField(10);
		cantidadtext.setBounds(175, 245, 300, 29);
		cantidadtext.setText(String.valueOf(JFrameTransactions.t.getCantidad()));
		fechatext = new JTextField(10);
		fechatext.setBounds(175, 293, 300, 30);
		fechatext.setText(String.valueOf(d));
		
		Register = new JButton ("Registrar");
		Register.setBounds(352, 347, 120, 60);
		Register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				d.toLocalDate();
				Transaccion t = new Transaccion ( d, Integer.valueOf(productotext.getText()), Integer.valueOf(proveedortext.getText()), Integer.valueOf(cantidadtext.getText()), JFrameLogin.EmActivo.getIduser());
			    try {
					Test.os.saveTransaccion(ConexionBDSql.obtener(), t, 1);
					JOptionPane.showMessageDialog(JFrameUpdateTransaction.this, "Transaccion Creada correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
