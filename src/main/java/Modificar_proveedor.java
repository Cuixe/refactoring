import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Modificar_proveedor extends JPanel{
	
	private JTextField Texto[]=new JTextField[6];
	private String labels[]={"Clave Proveedor","Nombre","Telefono","E-mail","Direccion","Codigo_postal"},
					Botones[]={"Modificar","Guardar","Cancelar"};
	private JLabel label[]=new JLabel[6];
	private JButton Boton[]=new JButton[3];
	
	public Modificar_proveedor(){
		setLayout(new BorderLayout());
		JPanel Centro=new JPanel();
		Centro.setLayout(new GridLayout(5,2,5,5));
		JPanel Sur=new JPanel();
		Sur.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel Norte=new JPanel();
		Norte.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		for(int n=0;n<Texto.length;n++){
			Texto[n]=new JTextField(20);
			label[n]=new JLabel(labels[n]);
		}
		for(int n=1;n<Texto.length;n++){
			Centro.add(label[n]);
			Centro.add(Texto[n]);
		}
		add(Centro,BorderLayout.CENTER);
		
		for(int n=1;n<Boton.length;n++){
			Boton[n]=new JButton(Botones[n]);
			Boton[n].setPreferredSize(new Dimension(100, 25));
			Boton[n].addActionListener(new Evento());
			Sur.add(Boton[n]);
		}
		add(Sur,BorderLayout.SOUTH);
		Boton[0]=new JButton(Botones[0]);
		Boton[0].addActionListener(new Evento());
		Norte.add(label[0]);
		Norte.add(Texto[0]);
		Norte.add(Boton[0]);
		
		add(Norte,BorderLayout.NORTH);
		activa(false);
	}
	
	public void activa(boolean act){
		for(int n=1;n<Texto.length;n++){
			Texto[n].setEnabled(act);
		}
		Texto[0].setEnabled(!act);
		Boton[0].setEnabled(!act);
		Boton[1].setEnabled(act);
		Boton[2].setEnabled(act);
	}
	
	class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){

//****** Eventos del Boton NUEVO
			if(e.getSource()==Boton[0]){
				ODBC cn=new ODBC();
				if(!cn.Consulta("select * from proveedor where RFC='"+Texto[0].getText()+"'"))
					System.out.println(cn.Salida);
				if(cn.Tx==0)
					JOptionPane.showMessageDialog(null,"No se encontro el proveedor");
				else{
					for(int n=1;n<cn.res[0].length;n++)
						Texto[n].setText(cn.res[0][n]);
					activa(true);
				}
				
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
					String SQL="Update proveedor set nombre='"+Texto[1].getText()+
					"', Telefono='"+Texto[2].getText()+"', email="+Texto[3].getText()+
					", Direccion='"+Texto[4].getText()+"', CP='"+Texto[5].getText()+
					"' where RFC='"+Texto[0].getText()+"'";
					ODBC cn=new ODBC();
					cn.Inserta(SQL);
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