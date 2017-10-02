import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Consultas_salidas extends JPanel{
	
	private JLabel l1=new JLabel("  Numero de Salida ");
	private JTextField No=new JTextField(2);
	private JButton Busca=new JButton("BUSCAR");
	private String columnas[]={"Clave Producto","Clave Proveedor","Cantidad","precio promedio"};
	private String filas[][]=new String[0][5];
	public DefaultTableModel modelo= new DefaultTableModel(filas,columnas);
	private JTable tabla=new JTable(modelo);
	private boolean band=false;
	
	public Consultas_salidas(){
		
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
		tabla.setPreferredScrollableViewportSize(new Dimension(400, 200));
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
				for(int n=1;n<=fil-1;n++)
					modelo.removeRow(n);
				String SQL=null;
				if((No.getText()).equals(""))
					JOptionPane.showMessageDialog(null,"Debes ingresar el numero de entrada a buscar");
				else if(No.getText()!=""){
					int x=Integer.parseInt(No.getText());
					SQL="select * from salida_producto where id_salida="+x;
					ODBC cn=new ODBC();
					cn.Consulta(SQL);
					if(cn.Tx!=0){
						for(int n=0;n<cn.res.length;n++)
							modelo.addRow(cn.res[n]);
						if(band)
							modelo.removeRow(0);
					}
					else
						JOptionPane.showMessageDialog(null,"Numero de entrada no localizada");
				}
				band=true;
			}
		}
	}
}
