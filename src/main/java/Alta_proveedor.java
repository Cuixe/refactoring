import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;


public class Alta_proveedor extends JPanel{
	
	private JTextField Texto[]=new JTextField[6];
	private String labels[]={"Clave Proveedor","Nombre","Telefono","E-mail","Direccion","Codigo_postal"},
					Botones[]={"Nuevo","Guardar","Cancelar"};
	private JLabel label[]=new JLabel[6];
	private JButton Boton[]=new JButton[3];
	
	public Alta_proveedor(){
		setLayout(new BorderLayout());
		JPanel Centro=new JPanel();
		Centro.setLayout(new GridLayout(6,2,5,5));
		JPanel Sur=new JPanel();
		Sur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		for(int n=0;n<Texto.length;n++){
			Texto[n]=new JTextField(20);
			label[n]=new JLabel(labels[n]);
		}
		for(int n=0;n<Texto.length;n++){
			Centro.add(label[n]);
			Centro.add(Texto[n]);
		}
		
		add(Centro,BorderLayout.CENTER);
		
		for(int n=0;n<Boton.length;n++){
			Boton[n]=new JButton(Botones[n]);
			Boton[n].setPreferredSize(new Dimension(100, 25));
			Boton[n].addActionListener(new Evento());
			Sur.add(Boton[n]);
		}
		add(Sur,BorderLayout.SOUTH);
		activa(false);
	}
	
	public void activa(boolean act){
		for(int n=0;n<Texto.length;n++){
			Texto[n].setEnabled(act);
		}
		Boton[0].setEnabled(!act);
		Boton[1].setEnabled(act);
		Boton[2].setEnabled(act);
	}
	
	class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){

//****** Eventos del Boton NUEVO
			if(e.getSource()==Boton[0]){
				activa(true);
			}
//****** Eventos del Boton GUARDAR
			if(e.getSource()==Boton[1]){
				boolean listo=true;
				for(int n=0;n<Texto.length;n++){
					if((Texto[n].getText()).length()==0){
						JOptionPane.showMessageDialog(null,"El campo "+labels[n]+" esta vacio");
						Texto[n].requestFocus();
						listo=false;
						break;
					}
				}
				if(listo){
					DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
					String fecha=df.format(new Date());
					String SQL="insert into proveedor values('"+Texto[0].getText()+"','"+Texto[1].getText()+"','"+Texto[2].getText()+"','"+Texto[3].getText()+"','"+Texto[4].getText()+"',"+Texto[5].getText()+")";
					ODBC cn=new ODBC();
					System.out.println(SQL);
					if(!cn.Inserta(SQL))
						JOptionPane.showMessageDialog(null,"Error de actualizacion\n ya existe la clave del proveedor ");
					else{
						JOptionPane.showMessageDialog(null,"Actualizacion correcta");
						for(int n=0;n<Texto.length;n++){
							Texto[n].setText("");
						}
					}
					activa(false);
				}
			}

//****** Eventos del Boton CANCELAR
			if(e.getSource()==Boton[2]){
				activa(false);
				for(int n=0;n<Texto.length;n++)
					Texto[n].setText("");
				
			}
		}
	}
}