import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class Entrada_producto extends JPanel{
	
	private JTextField Firma[]=new JTextField[2];
	private String columnas[]={"Clave Producto","Cantidad","Fecha Elaboraci�n","Fecha Caducidad","precio"};
	private String filas[][]=new String[0][5];
	public DefaultTableModel modelo= new DefaultTableModel(filas,columnas);
	private JTable tabla=new JTable(modelo);
	private String botones[]={"Nuevo","Agregar","Aceptar","Cancelar","Imprimir"};
	public JButton boton[]=new JButton[botones.length];
	public int No=0;
	
		
	public Entrada_producto(){

        ODBC cn=new ODBC();
		if(cn.Consulta("select max(no_entrada) from entrada"))
			No=Integer.parseInt(cn.res[0][0]);
		Firma[0]=new JTextField(10);
		Firma[1]=new JTextField(10);
		Firma[0].setEnabled(false);
		Firma[1].setEnabled(false);
		setLayout(new BorderLayout());
		JPanel arriba=new JPanel();
		JPanel centro=new JPanel();
		JPanel east=new JPanel();
		JPanel west=new JPanel();
		JPanel sur=new JPanel();
		sur.setLayout(new FlowLayout(FlowLayout.CENTER));
		east.setLayout(new GridLayout(2,1));
		west.setLayout(new GridLayout(2,1));
		arriba.setLayout(new BorderLayout());
		centro.setLayout(new BorderLayout());
		west.add(new JLabel("Clave Proveedor"));
		west.add(Firma[0]);
		east.add(new JLabel("Autorizo"));
		east.add(Firma[1]);
		arriba.add(west,BorderLayout.WEST);
		arriba.add(east,BorderLayout.EAST);
		tabla.setShowHorizontalLines(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setColumnSelectionAllowed(false);
		tabla.setPreferredScrollableViewportSize(new Dimension(500, 100));
		JScrollPane scroll=new JScrollPane(tabla);
		centro.add(new JLabel("          "),BorderLayout.NORTH);
		centro.add(new JLabel("          "),BorderLayout.SOUTH);
		centro.add(new JLabel("          "),BorderLayout.EAST);
		centro.add(new JLabel("          "),BorderLayout.WEST);
		centro.add(scroll,BorderLayout.CENTER);
		add(arriba,BorderLayout.NORTH);
		add(centro,BorderLayout.CENTER);
		for(int n=0;n<boton.length;n++){
			boton[n]=new JButton(botones[n]);
			boton[n].setPreferredSize(new Dimension(100, 25));
			boton[n].addActionListener(new Eventos());
			if(n!=0)
				boton[n].setEnabled(false);
			sur.add(boton[n]);}
		add(sur,BorderLayout.SOUTH);
	}
	
	private void cambia(boolean act){
		for(int n=1;n<botones.length;n++)
			boton[n].setEnabled(act);
		boton[0].setEnabled(!act);
		boton[2].setEnabled(!act);
		Firma[0].setEnabled(act);
		Firma[1].setEnabled(act);
		Firma[0].setText("");
		Firma[1].setText("");
	}
	
	class Eventos implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
//**********   Eventos del Boton NUEVO			
			if(e.getActionCommand()=="Nuevo"){
				cambia(true);
				Firma[0].requestFocus();
				No++;
			}

//**********   Eventos del Boton AGREGAR			
			if(e.getActionCommand()=="Agregar"){
				
				ODBC cn=new ODBC();
				DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
				String fecha=df.format(new Date());
				String SQL="Insert entrada values("+No+",'"+fecha+"','"+ Firma[1].getText()+"','"+Firma[0].getText()+"')";
//				System.out.println(SQL);
				if(!cn.Inserta(SQL))
					JOptionPane.showMessageDialog(null,"La operacion genero el siguiente error\n"+cn.Salida);
				else{
					final Agregar_producto nuevo=new Agregar_producto();
					JDialog Agregar=new JDialog(Almacen.form,"Agregar producto");
					Agregar.addWindowListener( 
					new WindowAdapter(){
						public void windowClosing(WindowEvent e){
							for(int n=0;n<nuevo.Valores.length;n++)
								modelo.addRow(nuevo.Valores[n]);
							boton[1].setEnabled(false);
							boton[2].setEnabled(true);
						}});
					Agregar.getContentPane().setLayout(new BorderLayout());
					Agregar.getContentPane().add(new Titulo("Entrada de Producto","      Comercial Mexicana","Entrada: "+No),BorderLayout.NORTH);
					Agregar.getContentPane().add(nuevo,BorderLayout.CENTER);
					Agregar.setVisible(true);
					Agregar.pack();
					Agregar.setModal(true);
					Agregar.setLocation(200,300);
					Agregar.setResizable(false);
				}
			}
			
//**********   Eventos del Boton CANCELAR						
			if(e.getActionCommand()=="Cancelar"){
				ODBC cn=new ODBC();
				if(!cn.Inserta("delete from entrada where no_entrada="+No))
					JOptionPane.showMessageDialog(null,"La operacion genero el siguiente error\n"+cn.Salida);
				cambia(false);
			}
			
//**********   Eventos del Boton ACEPTAR
			if(e.getActionCommand()=="Aceptar"){
				cambia(false);
				int fil =modelo.getRowCount();
				for(int n=0;n<fil-1;n++)
					modelo.removeRow(n);
				String vacio[]={"","","",""};
				modelo.addRow(vacio);
				modelo.removeRow(0);
			}
		}
	}
}