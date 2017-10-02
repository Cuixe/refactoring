import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;


public class Alta_producto extends JPanel{
	
	private JTextField Texto[]=new JTextField[7];
	private String unidades[]={"","mililitros","gramos","unidades","centimetros"},
					labels[]={"Clave Producto","Nombre","Unidad de Medida","Cantidad","Bodega","Clave Proveedor","Precio"},
					Botones[]={"Nuevo","Guardar","Cancelar"};
	private JComboBox unidad=new JComboBox(unidades);
	private JLabel label[]=new JLabel[7];
	private JButton Boton[]=new JButton[3];
	
	public Alta_producto(){
		setLayout(new BorderLayout());
		JPanel Centro=new JPanel();
		Centro.setLayout(new GridLayout(7,2,5,5));
		JPanel Sur=new JPanel();
		Sur.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		for(int n=0;n<Texto.length;n++){
			Texto[n]=new JTextField(20);
			label[n]=new JLabel(labels[n]);
		}
		unidad.setEnabled(false);
		for(int n=0;n<Texto.length;n++){
			Centro.add(label[n]);
			if(n==2)
				Centro.add(unidad);
			else
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
		unidad.setEnabled(act);
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
				Texto[2].setText(unidades[unidad.getSelectedIndex()]);
				for(int n=0;n<Texto.length;n++){
					if((Texto[n].getText()).length()==0){
						JOptionPane.showMessageDialog(null,"El campo "+labels[n]+" esta vacio");
						Texto[n].requestFocus();
						listo=false;
						break;
					}
				}
				if(listo){
					DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
					String fecha=df.format(new Date());
					String SQL="insert into producto values('"+Texto[0].getText()+"','"+Texto[1].getText()+"','"+Texto[2].getText()+"',"+Texto[3].getText()+",'"+Texto[4].getText()+"',0,'"+fecha+"','"+Texto[5].getText()+"',"+Texto[6].getText()+")";
					System.out.println(SQL);
					ODBC cn=new ODBC();
					if(!cn.Inserta(SQL))
						JOptionPane.showMessageDialog(null,"Error de actualizacion\n ya existe la clave del producto\n "+cn.Salida);
					else{
						JOptionPane.showMessageDialog(null,"Actualizacion correcta");
						for(int n=0;n<Texto.length;n++){
							Texto[0].setText("");
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