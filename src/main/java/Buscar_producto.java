import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Buscar_producto extends JPanel{
	
	private String opciones[]={"Buscar existencias","Buscar entradas","Buscar salidas"};
	private JRadioButton boton[]=new JRadioButton[3];
	private ButtonGroup grupo=new ButtonGroup();
	private JButton buscar=new JButton("Buscar");
	private JTextField clave=new JTextField(10);
	private int Tipo=0;
	
	public Buscar_producto (){
		setLayout(new BorderLayout());
		
		JPanel Norte=new JPanel();
		Norte.setLayout(new FlowLayout(FlowLayout.LEFT));
		Norte.add(new JLabel("Producto a buscar"));
		Norte.add(clave);
		Norte.add(buscar);
		buscar.addActionListener(new Evento());
		
		add(Norte,BorderLayout.NORTH);
		JPanel Center=new JPanel();
		Center.setLayout(new GridLayout(4,1));
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
						String Campo[]={"Clave_producto","Nombre","Unidad de medida","Cantidad de producto","Bodega","inventario","fecha de alta","Proveedor","Precio"};
						SQL="select * from producto where clave_producto='"+clave.getText()+"'";
						Campos=Campo;
					}
					else if(Tipo==2){
						String Campo[]={"Numero de Entrada","clave producto","numero productos","fecha elaboracion","fecha caducidad"};
						SQL="select * from entrada_producto where id_producto='"+clave.getText()+"'";
						Campos=Campo;
					}
					else {
						String Campo[]={"Numero de Salida","clave de producto","Cantidad de productos"};
						SQL="select * from salida_producto where id_producto='"+clave.getText()+"'";
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
