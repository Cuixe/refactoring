import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Usuario extends JPanel{
	
	private JTextField Nom;
	private JPasswordField Pws;
	private JButton boton[]=new JButton[2];
	private JLabel label[]=new JLabel[3];
	private JPanel panel[]=new JPanel[4];
	public String Nombre="vacio";
	public int Tipo=0;
	
	public Usuario(){
		
		setLayout(new BorderLayout());
		Nom=new JTextField(20);
		Pws=new JPasswordField(20);
		boton[0]=new JButton("\"Entrar\"");
		boton[1]=new JButton("\"Cancelar\"");
		boton[0].addActionListener(new Evento());
		boton[1].addActionListener(new Evento());
		
		for(int n=0;n<panel.length;n++){
			panel[n]=new JPanel();
			panel[n].setLayout(new FlowLayout(FlowLayout.CENTER));
		}
		panel[1].setLayout(new GridLayout(2,1,5,5));
		panel[2].setLayout(new GridLayout(2,1,5,5));
		panel[0].add(new JLabel(" \"BIENVENIDO\"  "));
		panel[1].add(new JLabel("Nombre de usuario"));
		panel[1].add(new JLabel("Contrase�a"));
		panel[2].add(Nom);
		panel[2].add(Pws);
		panel[3].add(boton[0]);
		panel[3].add(boton[1]);
		add(panel[0],BorderLayout.NORTH);
		add(panel[1],BorderLayout.WEST);
		add(panel[2],BorderLayout.EAST);
		add(panel[3],BorderLayout.SOUTH);
	}
	
	class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){

//******* Eventos del boton ENTRAR
			if(e.getSource()==boton[0]){
				
				if(!(Nom.getText()).equals("") && !(Pws.getText()).equals("")){
					ODBC cn=new ODBC();
					if(cn.Consulta("select nombre,tipo from usuario where usuario='"+Nom.getText()+"' and psw='"+Pws.getText()+"'")){
						if(cn.Tx>0){
							Nombre=cn.res[0][0];
							Tipo=Integer.parseInt(cn.res[0][1]);
							Nom.setText("");
							Pws.setText("");
							Almacen.form.dispose();
						}
						else
							JOptionPane.showMessageDialog(null,"No existe Usuario");
					}
					else
						JOptionPane.showMessageDialog(null,"Error: "+cn.Salida);
				}
				else
					JOptionPane.showMessageDialog(null,"Campos vacios");
			}
			
//****** Evenots del boton CANCELAR
			if(e.getSource()==boton[1]){
				Nom.setText("");
				Pws.setText("");
			}
		}
	}
}