import java.sql.*;
import java.util.*;


class ODBC {
	private Connection cn=null;
	private Statement st=null;
	public String Salida=null;
	public String[][] res;
	public int Tx=0,Ty=0;
	
	
	public boolean Consulta(String SQL){
		String ar=SQL;
		boolean si=true;
		int x=0,y=0;
		Tx=0;Ty=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
//            Connection cn = DriverManager.getConnection("jdbc:mysql://servidor/Almacen?user=hugo");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/Almacen?user=hugo");
            Statement st = cn.createStatement();
            ResultSet datos = st.executeQuery(ar);
            Ty=(datos.getMetaData()).getColumnCount();
            while(datos.next())
            	Tx++;
            res=new String[Tx][Ty];
            datos = st.executeQuery(SQL);
            while(datos.next()){
            	for(y=0;y<Ty;y++)
            		res[x][y]=datos.getString(y+1);
            	x++;
            }
            cn.close();
		}
		catch(Exception e){
			Salida=e.getLocalizedMessage();
			si=false;			
		}
		return si;
	}
	


	public boolean Inserta(String SQL){
		boolean si=true;
		try{
			Class.forName("com.mysql.jdbc.Driver");
//            Connection cn = DriverManager.getConnection("jdbc:mysql://servidor/Almacen?user=hugo");
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/Almacen?user=hugo");
            Statement st = cn.createStatement();
			st=cn.createStatement();
			st.execute(SQL);
		}
		catch(Exception e){
			Salida=e.getLocalizedMessage();
			si=false;
		}
		return si;
	}
}