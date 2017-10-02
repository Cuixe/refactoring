import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Modificar_salida extends JPanel{
	
	private JList lista;
	private String Objetos[]={"  "};
	private JLabel label=new JLabel("   Numero de Salida");
	private JButton Busca=new JButton(" Buscar ");
	private JButton Mod=new JButton(" Modificar ");
	private JTextField No =new JTextField(2);
	private ODBC cn=null;

	
	public Modificar_salida(){
		JPanel Arriba=new JPanel();
		JPanel Centro=new JPanel();
		JPanel Este=new JPanel();
		Arriba.setLayout(new FlowLayout(FlowLayout.LEFT));
		Este.setLayout(new FlowLayout(FlowLayout.LEFT));
		Centro.setLayout(new BorderLayout());
		Arriba.add(label);
		Arriba.add(No);
		Arriba.add(Busca);
		Busca.addActionListener(new Eventos());
		Mod.addActionListener(new Eventos());
		setLayout(new BorderLayout());
		lista=new JList(Objetos);
		lista.setVisibleRowCount(5);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setFixedCellWidth(400);
		lista.setFixedCellHeight(20);
		add(Arriba,BorderLayout.NORTH);
		Centro.add(new JLabel("   Salida     Producto        Total     Precio promedio"),BorderLayout.NORTH);
		Centro.add(new JScrollPane(lista),BorderLayout.CENTER);
		Este.add(Mod);
		add(Centro,BorderLayout.CENTER);
		add(Este,BorderLayout.EAST);
	}
	
	private class Eventos implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String valores[];

//*******Eventos del boton BUSCA
			if(e.getSource()==Busca){
				if((No.getText()).equals(""))
					JOptionPane.showMessageDialog(null,"Debes ingresar el numero de entrada a buscar");
				else{
					cn=new ODBC();
					cn.Consulta("select * from salida_producto where id_salida="+No.getText());
					if(cn.Tx>0){
						valores=new String[cn.Tx];
						for(int x=0;x<cn.res.length;x++){
							valores[x]="";
							for(int y=0;y<cn.res[0].length;y++)
								valores[x]+="          "+cn.res[x][y];
						}
						lista.setListData(valores);
					}
					else
						JOptionPane.showMessageDialog(null,"No se encontraron registros de la entrada");
				}
			}
//********Eventos del boton MODIFICAR
			if(e.getSource()==Mod){
				int index=lista.getSelectedIndex();
				if(index>=0){
					final Agregar_salida nuevo=new Agregar_salida();
					JDialog Agregar=new JDialog(Almacen.form,"Agregar producto");
					Agregar.getContentPane().setLayout(new BorderLayout());
					Agregar.getContentPane().add(new Titulo("Salida de Producto","      Comercial Mexicana","Entrada: "+No.getText()),BorderLayout.NORTH);
					Agregar.getContentPane().add(nuevo,BorderLayout.CENTER);
					Agregar.setVisible(true);
					Agregar.pack();
					Agregar.setModal(true);
					Agregar.setLocation(200,300);
					Agregar.setResizable(false);
					nuevo.seteo(cn.res[index]);
				}
				else
					JOptionPane.showMessageDialog(null,"Seleccione un elemento de la lista");			
			}
		}
	}
	
	
}