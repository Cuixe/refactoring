/**
 * AWT Sample application
 *
 * @author 
 * @version 1.00 06/05/16
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
import almacen.*;

public class Almacen extends JFrame{

	private JButton Altas=new JButton("Altas Productos");
	private JButton Altas1=new JButton("Altas Proveedores");
	private JPanel panel[]=new JPanel[3];
	private JLabel fecha=new JLabel("mi fecha");
	public static JDialog form=null;
	private Agregar_producto nuevo=null;
	private JLabel Nombre=new JLabel("Nombre de usuario");
	private JLabel Tipo=new JLabel("Tipo de usuario");
	public static Almacen vista=null;
	private static Usuario User=new Usuario();
	boolean abierto=true;

//****** inicio del constructor
	private Almacen() {
		super("MENU PRINCIPAL");
		Date fech=new Date();//fecha actual
		DateFormat df=DateFormat.getInstance();//Formato b�sico
		fecha.setText(df.format(fech));//mandamos al label fecha
		getContentPane().setBackground(Color.DARK_GRAY);//fondo
		menu();//llamamos al metodo para poner menu
		panel[0]=new JPanel();//creamos el panel centro
		panel[0].setLayout(new GridLayout(1,4));//lo recargamos centramos
		panel[0].add(Nombre);
		panel[0].add(Tipo);
		panel[1]=new JPanel();//creamos el panel sur
		panel[1].setLayout(new FlowLayout(FlowLayout.RIGHT));//lo recargamos a la derecha
		panel[1].add(fecha);//ponemos la fecha actual no altulizable
		getContentPane().add(panel[0],BorderLayout.NORTH);
		getContentPane().add(panel[1],BorderLayout.SOUTH);
		pack();
		setExtendedState(MAXIMIZED_BOTH);//maximizado
		setVisible(true);//visible
		//entradas(1," Entrada de Productos");//llamamos metodo altas 
		Contrase�a();
		
	}//fin del constructor
	
	public void Contrase�a(){
		form=new JDialog(Almacen.this,"BIENVENIDO",true);
		form.addWindowListener(
			new WindowAdapter(){
				public void windowClosed(WindowEvent e) {
					Nombre.setText("Nombre de usuario:   "+User.Nombre);
					if(User.Tipo==1)
						Tipo.setText("Tipo de usuario:    Administrador");
					else
						Tipo.setText("Tipo de usuario:    Normal");
				}
				public void windowClosing(WindowEvent e) {
					Contrase�a();
				}
			}
		);
		form.setLayout(new BorderLayout());
		form.setResizable(false);
		form.getContentPane().add(User,BorderLayout.CENTER);
		form.pack();
		form.setLocation(350,300);
		form.setVisible(true);
	}
	

//*******    Metodo para Salida de productos
	private void Salidas(int i,String Title){
		form=new JDialog(Almacen.this,Title,true);//crear JDIalog
		form.setResizable(false);
		if (i==0){
			Salida_producto objeto=new Salida_producto();//llamar componentes
			form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("Salida de Producto","      Comercial Mexicana","Salida: "+(objeto.No+1)),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
		}
		if(i==1){
        	Modificar_salida objeto=new Modificar_salida();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("modificacion de Salida","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        }
        if(i==2){
        	Consultas_salidas objeto=new Consultas_salidas();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("Busqueda de Salida","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        }
		form.pack();
       	form.setLocation(250,200);//localizar JDialog
        form.setVisible(true);
	}


//*******    Metodo para Entrada de proveedores
	private void Proveedor(int i,String Title){
		form=new JDialog(Almacen.this,Title,true);//crear JDIalog
		form.setResizable(false);
		if (i==0){
			Alta_proveedor objeto=new Alta_proveedor();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Altas de Proveedor","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        if (i==1){
			Modificar_proveedor objeto=new Modificar_proveedor();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Modificar Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        if (i==2){
			Buscar_proveedor objeto=new Buscar_proveedor();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Modificar Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        form.pack();
       	form.setLocation(250,200);//localizar JDialog
        form.setVisible(true);
	}	


//*******    Metodo para productos
	private void Producto(int i,String Title){
		form=new JDialog(Almacen.this,Title,true);//crear JDIalog
		form.setResizable(false);
		if (i==0){
			Alta_producto objeto=new Alta_producto();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Altas de Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        if (i==1){
			Modificar_producto objeto=new Modificar_producto();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Modificar Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
         if (i==2){
			Buscar_producto objeto=new Buscar_producto();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("   Modificar Producto","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        form.pack();
       	form.setLocation(250,200);//localizar JDialog
        form.setVisible(true);
	}	

//*******    Metodo para Entrada de productos
	private void entradas(int i,String Title){
		form=new JDialog(Almacen.this,Title,true);//crear JDIalog
		form.setResizable(false);
		if (i==0){
			Entrada_producto objeto=new Entrada_producto();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("Entrada de Producto","      Comercial Mexicana","Entrada: "+(objeto.No+1)),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        	}
        if(i==1){
        	Modificar_entrada objeto=new Modificar_entrada();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("Modificarion de Entrada","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        }
        if(i==2){
        	Consultas_entradas objeto=new Consultas_entradas();//llamar componentes
        	form.getContentPane().setLayout(new BorderLayout());
        	form.getContentPane().add(new Titulo("Busqueda de Entrada","      Comercial Mexicana",""),BorderLayout.NORTH);
        	form.getContentPane().add(objeto,BorderLayout.CENTER);//agregar componetes a Dialog
        }
		form.pack();
       	form.setLocation(250,200);//localizar JDialog
        form.setVisible(true);
		}//fin de metodo altas
	
	//Lo pongo aqui porque es parte del menu y debe ser global para usarlo en los eventos

	private String nombre[][]=new String[5][4];
		String nombre0[]={"Cambiar Sesi�n","Salir"};
		String nombre1[]={"Nueva","Modificar","Consultar"};
		String nombre2[]={"Nueva","Modificar","Consultar"};
		String nombre3[]={"Nuevo","Modificar","Consultar"};
		String nombre4[]={"Nuevo","Modificar","Consultar"};
	private JMenuItem sub0[]=new JMenuItem[nombre0.length];
	private JMenuItem sub1[]=new JMenuItem[nombre1.length];
	private JMenuItem sub2[]=new JMenuItem[nombre2.length];
	private JMenuItem sub3[]=new JMenuItem[nombre3.length];
	private JMenuItem sub4[]=new JMenuItem[nombre4.length];
	private String nom_menus[]={"Archivo","Entradas de Producto","Salidas Producto","Productos","Proveedores"};//Nombres de los menus
	private JMenu menus[]=new JMenu[nom_menus.length];

	//Creamos el menu
	private void menu(){
		
		Eventos event=new Eventos();//constructor del objeto de eventos
		
		JMenuBar menu=new JMenuBar();//cramos la barra del menu
		setJMenuBar(menu);//seteamos la barra del menu
		
		//agregamos titulo a menus y estos los metemos a la barra
		for(int n=0;n<nom_menus.length;n++){
				menus[n]=new JMenu(nom_menus[n]);
				menu.add(menus[n]);}
		
		for(int n=0;n<sub0.length;n++){
			sub0[n]=new JMenuItem(nombre0[n]);
			menus[0].add(sub0[n]);
			sub0[n].addActionListener(event);}
			
		//construimos el contenido del menu Entrada de producto
		for(int n=0;n<sub1.length;n++){
			sub1[n]=new JMenuItem(nombre1[n]);
			menus[1].add(sub1[n]);
			sub1[n].addActionListener(event);}
		
		//construimos el contenido del menu Salidas de Producto
		for(int n=0;n<sub2.length;n++){
			sub2[n]=new JMenuItem(nombre2[n]);
			menus[2].add(sub2[n]);
			sub2[n].addActionListener(event);}
		
		//construimos el contenido del menu Productos
		for(int n=0;n<sub3.length;n++){
			sub3[n]=new JMenuItem(nombre3[n]);
			menus[3].add(sub3[n]);
			sub3[n].addActionListener(event);}
			
		for(int n=0;n<sub4.length;n++){
			sub4[n]=new JMenuItem(nombre4[n]);
			menus[4].add(sub4[n]);
			sub4[n].addActionListener(event);}
		}//fin metodo menu

	
//*******    puerta de entrada al Sistema
	public static void main(String args[]) {
		vista=new Almacen();
		vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//fin main
	
//*******    clase para manejo de eventos de botones y menus
	private class Eventos implements ActionListener{
		public void actionPerformed(ActionEvent evento){
			
//*******eventos del menu entradas de productos
			for(int n=0;n<sub1.length;n++){
				if(evento.getSource()==sub1[n]){
					int algo=(Nombre.getText()).length();
					if((n==0||n==1) && (Nombre.getText()).length()<41)
						JOptionPane.showMessageDialog(null,"No tienes derecho de realizar esta operacion");
					else
						entradas(n,evento.getActionCommand()+" Entrada de Productos");//llamamos metodo altas 
					break;
				}
			}
			
//*******eventos del menu salidas de productos
			for(int n=0;n<sub1.length;n++){
				if(evento.getSource()==sub2[n]){
					if((n==0||n==1) && (Nombre.getText()).length()<41)
						JOptionPane.showMessageDialog(null,"No tienes derecho de realizar esta operacion");
					else
						Salidas(n,evento.getActionCommand()+" Salida de Productos");//llamamos metodo altas 
					break;
				}
			}
			
//*******eventos del menu Productos
			for(int n=0;n<sub1.length;n++){
				if(evento.getSource()==sub3[n]){
					if((n==0||n==1) && (Nombre.getText()).length()<41)
						JOptionPane.showMessageDialog(null,"No tienes derecho de realizar esta operacion");
					else
						Producto(n,evento.getActionCommand()+" Producto");//llamamos metodo altas 
					break;
				}
			}
			
//*******eventos del menu Proveedor
			for(int n=0;n<sub1.length;n++){
				if(evento.getSource()==sub4[n]){
					if((n==0||n==1) && (Nombre.getText()).length()<41)
						JOptionPane.showMessageDialog(null,"No tienes derecho de realizar esta operacion");
					else
						Proveedor(n,evento.getActionCommand()+" Proveedor");//llamamos metodo altas 
					break;
				}
			}


//*******eventos del menu archivo
			for(int n=0;n<sub0.length;n++){
				if(evento.getSource()==sub0[0]){
					Contrase�a();
				}
				if(evento.getSource()==sub0[1]){//eventos del menu Consultas
					System.exit(0);
					break;
				}
			}
		}
	}//fin clase Eventos
}	
