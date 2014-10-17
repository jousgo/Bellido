import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Conect {
	static Statement sentencia;
	static Connection conexion;
	static int resulconsul;
	static String consulta1;
	static String consulta2;
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/damagricola", "root", "");
			sentencia = conexion.createStatement();
			insertAgricol();
   
		}
		catch (ClassNotFoundException cn) {cn.printStackTrace();}
		catch (SQLException e) {e.printStackTrace();}
  
	}
	
	public static void insertAgricol() throws SQLException{
		
		String procAgri = "load data infile '/Users/dam2/Desktop/clientes.csv' into table clientes fields terminated by ';' lines terminated by '\n';";
		CallableStatement llamada= conexion.prepareCall(procAgri);
		int error=llamada.executeUpdate();
		if(error==0){
		System.out.println("La insercion se realizó correctamente");
		}else{
			System.out.println("Error en la inserción");
			
		}
		
		
			
		}
  
}