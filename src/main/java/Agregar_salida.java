import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;

public class Agregar_salida extends JPanel{

	public int Nuevo=0;
	public String Valores[][]=new String[1][4], Temps[][]=new String[1][4],precio;
	private JPanel panel[]=new JPanel[5];
	private JLabel label[]=new JLabel[2];
	private JTextField Texto[]=new JTextField[2];
	private String labels[]={"Id_producto","Numero de productos"},botones[]={"Nuevo","Guardar","Cancelar"};
	public JButton boton[]=new JButton[botones.length];
	private boolean opcion=true;
	private String[] Temp2;
	
	
	public Agregar_salida(){
		Eventos event=new Eventos();
		
		setLayout(new BorderLayout());//crea contenedor principal
		for(int n=0;n<panel.length;n++)//creamos los paneles
			panel[n]=new JPanel();
			

		//*****Panel Central
		
		panel[1].setLayout(new GridLayout(6,2,0,5));
		panel[1].add(new JLabel(" "));//estos son para no pegar el titulo con el centro
		for(int n=0;n<labels.length;n++){
			label[n]=new JLabel(labels[n]);
			panel[1].add(label[n]);
			Texto[n]=new JTextField(20);
			Texto[n].setEditable(false);
			panel[1].add(Texto[n]);//metemos el texto al panel
		}
		add(panel[1],BorderLayout.CENTER);

		//*****Paneles Este y Oeste
		panel[2].add(new JLabel("          "));
		add(panel[2],BorderLayout.EAST);
		panel[3].add(new JLabel("          "));
		add(panel[3],BorderLayout.WEST);
		
		//*****Panel Sur
		for(int n=0;n<boton.length;n++){
			boton[n]=new JButton(botones[n]);
			boton[n].setPreferredSize(new Dimension(100, 25));
			if(n!=0)
				boton[n].setEnabled(false);
			boton[n].addActionListener(event);
			panel[4].add(boton[n]);}
			
		add(panel[4],BorderLayout.SOUTH);
	}//fin del contructor
		
	private void activaBotones(boolean act){
		for(int n=1;n<boton.length;n++)
			boton[n].setEnabled(act);
		boton[0].setEnabled(!act);
		for(int n=0;n<Texto.length;n++)
				Texto[n].setEditable(act);
	}//fin del metodo
	
	public void seteo(String arg[]){
		Texto[0].setText(arg[1]);
		Texto[1].setText(arg[2]);
		boton[0].setText("Modificar");
		Temp2=arg;
		opcion=false;
	}
	
	class Eventos implements ActionListener{
		private boolean listo=true;
		private String cad;
		public void actionPerformed(ActionEvent evento){
						
//**********eventos del boton Nuevo
			if(evento.getSource()==boton[0]){
				activaBotones(true);
				Texto[0].requestFocus();
				
			}
				
//**********Eventos del boton Guardar
			if(evento.getSource()==boton[1]){
				listo=true;
				for(int n=0;n<Texto.length;n++){
					cad=Texto[n].getText();
					if(cad.length()==0){
						listo=false;
						JOptionPane.showMessageDialog(null,"El campo ("+ labels[n] +") esta vacio");
						Texto[n].requestFocus();
						break;}
				}
				//si esta listo para guardarse
				if(listo==true){
					
					if(Guardar()){
						activaBotones(false);
						//  Comenzamos a almacenar todos los valores en Valor creando strings mas grandes
						if(Nuevo>0){
							Temps=new String[Nuevo][4];
							Temps=Valores;
							Nuevo++;
							Valores=new String[Nuevo][4];
							for(int n=0;n<Temps.length;n++)
								Valores[n]=Temps[n];
						}
						else
							Nuevo++;
						for(int n=0;n<2;n++)
							Valores[Nuevo-1][n]=Texto[n].getText();
						Valores[Nuevo-1][2]=precio;
					}
				}
			}

//***********Eventos del boton Cancelar
			if(evento.getSource()==boton[2]){
				activaBotones(false);
			}
		}
	}
	
	public boolean Guardar(){
		boolean ok=true;
		int No=0,Tot,inv=0,inv2=0;
		String SQL;
		ODBC cn=new ODBC();
		if(cn.Consulta("select max(id_salida) from salida"))
		No=Integer.parseInt(cn.res[0][0]);						
		Tot=Integer.parseInt(Texto[1].getText());
		if(opcion){
			cn.Consulta("select nombre from producto where clave_producto='"+Texto[0].getText()+"'");
			if(cn.Tx==0)
				JOptionPane.showMessageDialog(null,"Producto no localizado");
			else{
				SQL="select sum(inventario) from producto where clave_producto='"+Texto[0].getText()+"'";
				cn.Consulta(SQL);
				inv=Integer.parseInt(cn.res[0][0]);
				inv2=inv-Tot;
				if(!(inv2<0)){
					cn.Consulta("select avg(precio) from entrada_producto where id_producto='"+Texto[0].getText()+"'");
					precio=cn.res[0][0];
					cn.Inserta("Update producto set Inventario="+inv2+" where clave_producto='"+Texto[0].getText()+"'");
					SQL="Insert salida_producto values("+No+",'"+Texto[0].getText()+"',"+ Tot +","+precio+")";
					cn.Inserta(SQL);
				}
				else{
					JOptionPane.showMessageDialog(null,"No hay productos suficientes para esta salida\n El sistema reporta "+inv+" existencias");
					ok=false;
				}
			}
		}
		else{
				SQL="Update salida_producto set id_salida="+Temp2[0]+",id_producto='"+Texto[0].getText()+"',cantidad="+ Tot
				+" where id_salida="+Temp2[0]+" and id_producto='"+Temp2[1]+"'";
			if(!cn.Inserta(SQL)){
				ok=false;
				JOptionPane.showMessageDialog(null,"Informacion erronea"+cn.Salida);
			}
		}
		return ok;
	}
}
