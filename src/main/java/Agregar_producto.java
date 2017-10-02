import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;

public class Agregar_producto extends JPanel{

	public int Nuevo=0;
	public String Valores[][]=new String[1][5], Temps[][]=new String[1][5];
	private JPanel panel[]=new JPanel[7];
	private JLabel label[]=new JLabel[8];
	private JTextField Texto[]=new JTextField[11];
	private String labels[]={"Id_producto","Numero de productos","Fecha Elaboracion","Fecha caducidad","precio"},botones[]={"Nuevo","Guardar","Cancelar"};
	public JButton boton[]=new JButton[botones.length];
	private boolean opcion=true;
	private String[] Temp2;
	
	
	public Agregar_producto(){
		Eventos event=new Eventos();
		
		setLayout(new BorderLayout());//crea contenedor principal
		for(int n=0;n<panel.length;n++)//creamos los paneles
			panel[n]=new JPanel();
			

		//*****Panel Central
		
		//panel para la fecha de elaboracion
		panel[5].setLayout(new GridLayout(1,5));
		Texto[5]=new JTextField(5);
		Texto[5].setEditable(false);
		panel[5].add(Texto[5]);
		label[5]=new JLabel("     /");
		label[5].setFont(new Font("Times New Roman",Font.BOLD,16));
		panel[5].add(label[5]);
		Texto[6]=new JTextField(5);
		Texto[6].setEditable(false);
		panel[5].add(Texto[6]);
		label[6]=new JLabel("     /");
		label[6].setFont(new Font("Times New Roman",Font.BOLD,16));
		panel[5].add(label[6]);
		Texto[7]=new JTextField(5);
		Texto[7].setEditable(false);
		panel[5].add(Texto[7]);
		
		
		//panel para la fecha de caducidad
		panel[6].setLayout(new GridLayout(1,5));
		Texto[8]=new JTextField(5);
		Texto[8].setEditable(false);
		panel[6].add(Texto[8]);
		label[6]=new JLabel("     /");
		label[6].setFont(new Font("Times New Roman",Font.BOLD,16));
		panel[6].add(label[6]);
		Texto[9]=new JTextField(5);
		Texto[9].setEditable(false);
		panel[6].add(Texto[9]);
		label[7]=new JLabel("     /");
		label[7].setFont(new Font("Times New Roman",Font.BOLD,16));
		panel[6].add(label[7]);
		Texto[10]=new JTextField(5);
		Texto[10].setEditable(false);
		panel[6].add(Texto[10]);
		
		panel[1].setLayout(new GridLayout(6,2,0,5));
		panel[1].add(new JLabel(" "));//estos son para no pegar el titulo con el centro
		panel[1].add(new JLabel(" "));//y como es de dos columnas llenamos la segunda
		for(int n=0;n<labels.length;n++){
			label[n]=new JLabel(labels[n]);
			panel[1].add(label[n]);
			Texto[n]=new JTextField(20);
			Texto[n].setEditable(false);
			if (n==2)//si es el turno de la posicion de la fecha 
				panel[1].add(panel[5]);//metemos el panel de la fecha
			else if(n==3)
				panel[1].add(panel[6]);//metemos el panel de la fecha
			else//en los demas casos
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
	
	//metodo que se llama cuando se quiere ejecutar una modificacion
	public void seteo(String arg[]){
		Texto[0].setText(arg[1]);
		Texto[1].setText(arg[2]);
		Texto[4].setText(arg[5]);		
		Texto[5].setText(arg[3].substring(0,4));
		Texto[6].setText(arg[3].substring(5,7));
		Texto[7].setText(arg[3].substring(8,10));
		Texto[8].setText(arg[4].substring(0,4));
		Texto[9].setText(arg[4].substring(5,7));
		Texto[10].setText(arg[4].substring(8,10));
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
				Texto[2].setText(Texto[5].getText()+"/"+Texto[6].getText()+"/"+Texto[7].getText());
				if((Texto[8].getText()).length()==0)
					Texto[8].setText(Texto[5].getText());
				if((Texto[9].getText()).length()==0)
					Texto[9].setText(Texto[6].getText());
				if((Texto[10].getText()).length()==0)
					Texto[10].setText(Texto[7].getText());
					Texto[3].setText(Texto[8].getText()+"/"+Texto[9].getText()+"/"+Texto[10].getText());
				for(int n=0;n<Texto.length;n++){
					cad=Texto[n].getText();
					if(cad.length()==0){
						listo=false;
						if(n>=4 && n<7)
							JOptionPane.showMessageDialog(null,"Fecha incompleta de elaboracion");
						else
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
							Temps=new String[Nuevo][5];
							Temps=Valores;
							Nuevo++;
							Valores=new String[Nuevo][5];
							for(int n=0;n<Temps.length;n++)
								Valores[n]=Temps[n];
						}
						else
							Nuevo++;
						for(int n=0;n<5;n++)
							Valores[Nuevo-1][n]=Texto[n].getText();
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
		int No=0,Tot,inv;
		String SQL;
		ODBC cn=new ODBC();
		if(cn.Consulta("select max(no_entrada) from entrada"))
			No=Integer.parseInt(cn.res[0][0]);						
		Tot=Integer.parseInt(Texto[1].getText());
		if(opcion)
			SQL="Insert entrada_producto values("+No+",'"+Texto[0].getText()+"',"+ Tot +",'"+Texto[2].getText()+"','"+Texto[3].getText()+"',"+Texto[4].getText()+")";
		else
			SQL="Update entrada_producto set no_entrada="+Temp2[0]+",id_producto='"+Texto[0].getText()+"',no_productos="+ Tot +",fecha_elaboracion='"+Texto[2].getText()+"',fecha_caducidad='"+Texto[3].getText()+"', precio="+Texto[4].getText()
			+" where no_entrada="+Temp2[0]+" and id_producto='"+Temp2[1]+"'";
		System.out.println(SQL);
		if(!cn.Inserta(SQL)){
			ok=false;
			JOptionPane.showMessageDialog(null,"Informacion erronea");
			}
		if(opcion)
			Actualiza();
		return ok;
	}
	
	public void Actualiza(){
		ODBC cn=new ODBC();
		try{
			int inv=0;
			if(!cn.Consulta("select sum(inventario) from producto where clave_producto='"+Texto[0].getText()+"'")){}
			inv=Integer.parseInt(cn.res[0][0]);
			inv+=Integer.parseInt(Texto[1].getText());
			cn.Inserta("Update producto set Inventario="+inv+" where clave_producto='"+Texto[0].getText()+"'");
		}
		catch(Exception er){
			JOptionPane.showMessageDialog(null,"Se creo el siguiente error\n"+er.getMessage());
			}
	}
}