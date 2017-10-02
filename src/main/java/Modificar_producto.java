import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Modificar_producto extends JPanel{
	
	
	private String unidades[]={"","mililitros","gramos","unidades","centimetros"},
					labels[]={"Clave Producto","Nombre","Unidad de Medida","Cantidad","Bodega","Proveedor","Precio"},
					Botones[]={"Modificar","Guardar","Cancelar"};
	private JTextField Texto[]=new JTextField[7];
	private JComboBox unidad=new JComboBox(unidades);
	private JLabel label[]=new JLabel[labels.length];
	private JButton Boton[]=new JButton[3];
	
	public Modificar_producto(){
		setLayout(new BorderLayout());
		JPanel Centro=new JPanel();
		Centro.setLayout(new GridLayout(7,2,5,5));
		JPanel Sur=new JPanel();
		Sur.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel Norte=new JPanel();
		Norte.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		for(int n=0;n<Texto.length;n++){
			Texto[n]=new JTextField(20);
			label[n]=new JLabel(labels[n]);
		}
		unidad.setEnabled(false);
		for(int n=1;n<Texto.length;n++){
			Centro.add(label[n]);
			if(n==2)
				Centro.add(unidad);
			else
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
		unidad.setEnabled(act);
		Boton[0].setEnabled(!act);
		Boton[1].setEnabled(act);
		Boton[2].setEnabled(act);
	}
	
	class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){

//****** Eventos del Boton MODIFICAR
			if(e.getSource()==Boton[0]){
				ODBC cn=new ODBC();
				if(!cn.Consulta("select nombre,unidad_medida,cantidad_producto,bodega,rfc,precio from producto where clave_producto='"+Texto[0].getText()+"'"))
					System.out.println(cn.Salida);
				if(cn.Tx==0)
					JOptionPane.showMessageDialog(null,"No se encontro el producto");
				else{
					for(int n=0;n<cn.res[0].length;n++){
						System.out.println(n+" "+cn.res[0][n]);
						Texto[n+1].setText(cn.res[0][n]);
					}
					activa(true);
				}
				
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
					String SQL="Update producto set nombre='"+Texto[1].getText()+"', unidad_medida='"+Texto[2].getText()+"', cantidad_producto="+Texto[3].getText()+", Bodega='"+Texto[4].getText()+"', RFC='"+ Texto[5].getText()+"', Precio="+Texto[6].getText()+" where clave_producto='"+Texto[0].getText()+"'";
					System.out.println(SQL);
					ODBC cn=new ODBC();
					if(cn.Inserta(SQL))
						activa(false);
					else
						JOptionPane.showMessageDialog(null,cn.Salida);
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