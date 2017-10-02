import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Consultas_entradas extends JPanel{
	
	private JLabel l1=new JLabel("  Numero de Entrada ");
	private JTextField No=new JTextField(2);
	private JButton Busca=new JButton("BUSCAR");
	private String columnas[]={"Numero de entrada","Clave Producto","Cantidad","Fecha Elaboraci�n","Fecha Caducidad","precio"};
	private String filas[][]=new String[0][5];
	public DefaultTableModel modelo= new DefaultTableModel(filas,columnas);
	private JTable tabla=new JTable(modelo);
	private boolean band=false;
	
	public Consultas_entradas(){
		
		setLayout(new BorderLayout());
		JPanel Arriba=new JPanel();
		JPanel centro=new JPanel();
		Arriba.setLayout(new FlowLayout(FlowLayout.LEFT));
		Arriba.add(l1);
		Arriba.add(No);
		Arriba.add(Busca);
		add(Arriba,BorderLayout.NORTH);
		tabla.setShowHorizontalLines(true);
		tabla.setRowSelectionAllowed(true);
		tabla.setColumnSelectionAllowed(false);
		tabla.setPreferredScrollableViewportSize(new Dimension(600, 200));
		JScrollPane scroll=new JScrollPane(tabla);
		centro.add(scroll,BorderLayout.CENTER);
		add(centro,BorderLayout.CENTER);
		Busca.addActionListener(new Evento());
	}
	public class Evento implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
//**********Eventos del boton BUSCAR			
			if(e.getSource()==Busca){
				int fil =modelo.getRowCount();
				for(int n=0;n<fil-1;n++)//eliminamos los campos que existan exepto el ultimo				
					modelo.removeRow(n);
				String SQL=null;
				if((No.getText()).equals(""))
					JOptionPane.showMessageDialog(null,"Debes ingresar el numero de entrada a buscar");
				else if(No.getText()!=""){
					int x=Integer.parseInt(No.getText());
					SQL="select * from entrada_producto where no_entrada="+x;
					ODBC cn=new ODBC();
					cn.Consulta(SQL);
					if(cn.Tx!=0){
						for(int n=0;n<cn.res.length;n++)
							modelo.addRow(cn.res[n]);
						if(band)//if ya a sido activado el boton 
							modelo.removeRow(0);//eliminamos el ultimo campo de la consulta anterior
					}
					else
						JOptionPane.showMessageDialog(null,"Numero de entrada no localizada");
				}
			band=true;
			}
		}
	}
}
