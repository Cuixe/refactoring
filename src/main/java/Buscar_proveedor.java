import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Buscar_proveedor extends JPanel{
	
	private String opciones[]={"Buscar Referencias","Buscar productos"};
	private JRadioButton boton[]=new JRadioButton[opciones.length];
	private ButtonGroup grupo=new ButtonGroup();
	private JButton buscar=new JButton("Buscar");
	private JTextField clave=new JTextField(10);
	private int Tipo=0;
	
	public Buscar_proveedor  (){
		setLayout(new BorderLayout());
		
		JPanel Norte=new JPanel();
		Norte.setLayout(new FlowLayout(FlowLayout.LEFT));
		Norte.add(new JLabel("Producto a buscar"));
		Norte.add(clave);
		Norte.add(buscar);
		buscar.addActionListener(new Evento());
		
		add(Norte,BorderLayout.NORTH);
		JPanel Center=new JPanel();
		Center.setLayout(new GridLayout(3,1));
		Center.add(new JLabel(" Tipo de Busqueda"));
		for(int n=0;n<boton.length;n++){
			boton[n]=new JRadioButton(opciones[n],false);
			grupo.add(boton[n]);
			Center.add(boton[n]);
			boton[n].addActionListener(new Evento());
		}
		add(Center,BorderLayout.CENTER);
		add(new JLabel("     "),BorderLayout.WEST);
		add(new JLabel("     "),BorderLayout.SOUTH);
	}
	
	private class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){
//*****Eventos del botones de opcion
			for(int n=0;n<boton.length;n++){
				if(e.getSource()==boton[n]){
					Tipo=n+1;
					break;
				}
			}

//*****Evento del boton Buscar
			if(e.getSource()==buscar){
				if((clave.getText()).length()==0)
					JOptionPane.showMessageDialog(null,"Introdusca clave de producto a buscar");
				else if(Tipo==0)
					JOptionPane.showMessageDialog(null,"Seleccione una opcion de busqueda");
				else{
					String SQL;
					String Salida[][],Campos[]={"",""};
					JTable Tabla;
					if(Tipo==1){
						String Campo[]={"Clave_proveedor","Nombre","Telefono","E-mail","Direccion","Codigo Postal"};
						SQL="select * from proveedor where RFC='"+clave.getText()+"'";
						Campos=Campo;
					}
					else{
						String Campo[]={"Clave producto","Nombre","unidad Medida","cantidad","Bodega","Inventario","fecha de alta"};
						SQL="select * from producto where RFC='"+clave.getText()+"'";
						Campos=Campo;
					}
					ODBC cn=new ODBC();
					if(cn.Consulta(SQL)){}
					if(cn.Tx==0)
						JOptionPane.showMessageDialog(null,"La operacion no arrojo resultados");
					else{
						Salida=cn.res;
						Tabla=new JTable(Salida,Campos);
						JScrollPane scroll=new JScrollPane(Tabla);
						Tabla.setPreferredScrollableViewportSize(new Dimension(700, 200));
						JDialog Agregar=new JDialog(Almacen.form,"Busqueda de producto");
						Agregar.getContentPane().setLayout(new BorderLayout());
						Agregar.getContentPane().add(new Titulo("Busqueda de Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
						Agregar.getContentPane().add(scroll,BorderLayout.CENTER);
						Agregar.setVisible(true);
						Agregar.pack();
						Agregar.setModal(true);
						Agregar.setLocation(150,300);
						Agregar.setResizable(false);
					}
				}
			}
		}
	}
}
