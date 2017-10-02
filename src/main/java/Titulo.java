import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;


public class Titulo extends JPanel{

	//public JPanel caja=new JPanel();
	
	public String titulo[]={"","","",""};
	private JPanel panel=new JPanel();
	private JPanel extra[]=new JPanel[5];
	private JLabel titulos[]=new JLabel[4];

	public Titulo(String title,String cia,String cosa){
		
		titulo[0]=title;
		titulo[1]=cia;

		titulo[2]=cosa;

		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");//Formato b�sico
		titulo[3]="Fecha: "+df.format(new Date());//vemos la fecha

		//creamos los paneles centrales y extras
		for(int n=0;n<extra.length;n++)
			extra[n]=new JPanel();
			
		Font fuente=new Font("Courier New",Font.BOLD,14);
		for(int n=0;n<titulo.length;n++){
			titulos[n]=new JLabel(titulo[n]);
			titulos[n].setFont(fuente);
			}
		//Dise�amos los layouts de los paneles	
		setLayout(new BorderLayout());//contendor principal
		panel.setLayout(new BorderLayout());//este es le panel norte
		extra[0].setLayout(new BorderLayout());//contenedor general para titulos
		
		//Comiensa a colocar los paneles para todo el titulo
		extra[1].setLayout(new FlowLayout(FlowLayout.CENTER));//contenedor de titulo 0 del array
		extra[2].setLayout(new FlowLayout(FlowLayout.CENTER));//contenedor de titulo 1 del array
		extra[1].add(titulos[0],BorderLayout.NORTH);//agregamos el titulo 0 al panel 1
		extra[2].add(titulos[1],BorderLayout.NORTH);//agregamos el titulo 1 al panel 2
		extra[3].setLayout(new GridLayout(2,1));
		extra[3].add(new JLabel("   "));//para que aparesca abajdo de la cia
		extra[3].add(titulos[3]);
		extra[4].setLayout(new GridLayout(2,1));
		extra[4].add(new JLabel("   "));//para que aparesca abajdo de la cia
		extra[4].add(titulos[2]);
		
		//Se llena el extra[0]
		extra[0].add(extra[1],BorderLayout.NORTH);
		extra[0].add(extra[2],BorderLayout.CENTER);
		extra[0].add(extra[3],BorderLayout.EAST);
		extra[0].add(extra[4],BorderLayout.WEST);
		
		//agragamos el panel general para titulo al panel norte del Content
		panel.add(extra[0],BorderLayout.NORTH);
					
		//metemos a la caja principal
		add(panel);
	}
}