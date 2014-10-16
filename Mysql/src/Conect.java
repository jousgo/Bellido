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
   conexion = DriverManager.getConnection("jdbc:mysql://localhost/empresa", "root", "");
   sentencia = conexion.createStatement();
   decision();
   //procedimientos();
  }
   catch (ClassNotFoundException cn) {cn.printStackTrace();}
   catch (SQLException e) {e.printStackTrace();}
  
}
  
 public static void decision() throws SQLException{
 Scanner entrada=new Scanner(System.in);
 System.out.println("¿que quiere hacer? \n 1)Consultar \n 2)Modificar \n 3)Procedimientos");
 switch(entrada.nextInt()){
 	case 1: preguntar();break;
 	case 2: modificar();break;
 	case 3: procedimientos();break;
 }
 }
  
public static void preguntar() throws SQLException{
	
	System.out.println("Elige la consulta que quiere realizar: \n 1) Listado de empleados de Catarroja que trabajan en el departamento 2. \n "
			+ "2) Empleados que cobran menos de 850€ y no son de Silla \n "
			+ "3) Salario medio por localidad. \n "
			+ "4) Empleados que trabajan en Madrid o Barcelona. \n "
			+ "5) Empleados que trabajan en Sevilla y no son de Catarroja. \n "
			+ "6) Empleados que traban en cada departamento y su salario medio. \n"
			+ "7) Empleados contratados antes de 2011 que no sean de Catarroja y que trabajen en el departamento de contabilidad. \n"
			+ "8) Empleados que trabajan en el mismo departamento que Pepe. \n"
			+ "9) Empleados contradados el mismo año que Pedro y Pepe. \n"
			+ "10) Empleados que cobran mas que Juan. \n"
			+ "11) Empleados que no trabajan en Sevilla y combran mas que Sara. \n");
	
	Scanner entrada=new Scanner(System.in);
	resulconsul=entrada.nextInt();
	switch(resulconsul){
	case 1: consulta1 = ("SELECT * FROM empleados WHERE DEPTO = 20"); 
	break;
	case 2: consulta1 = ("SELECT * FROM empleados WHERE salario < 850 AND localidad != 'Silla'"); 
	break;
	case 3: consulta1 = ("SELECT localidad, AVG(salario) as media FROM empleados GROUP BY localidad"); 
	break;
	case 4: consulta1 = ("SELECT e.nombre FROM empleados as e INNER JOIN departamentos as d ON d.DEPT_NO=e.DEPTO WHERE d.loc='MADRID' OR d.loc='BARCELONA'");
 	break;
	case 5: consulta1 = ("SELECT nombre FROM empleados WHERE (depto=10) AND localidad IS NOT 'Catarroja'");
	break;
	case 6: consulta1 = ("SELECT depto, COUNT(emp_no), AVG(salario) as salMedio FROM empleados GROUP BY depto");
	break;
	case 7: consulta1 = ("SELECT nombre FROM empleados as e join departamentos as d on e.depto=d.dept_no where d.dnombre='Contabilidad' AND localidad != 'Catarroja' AND e.fecha_alt<'2011-1-1'"); 
	break;
	case 8: consulta1 = ("SELECT nombre FROM empleados where depto IN(select depto from empleados where nombre='Pepe')"); 
	break;
	case 9: consulta1 = ("SELECT nombre FROM empleados WHERE year(fecha_alt) IN (SELECT YEAR(fecha_alt) FROM empleados WHERE (nombre='Pepe') OR (nombre= 'Pedro'))"); 
	break;
	case 10: consulta1 = ("SELECT nombre FROM empleados WHERE salario < (Select salario from empleados where nombre = 'Pedro')");
 	break;
	case 11: consulta1 = ("SELECT nombre FROM empleados as e join departamentos as d ON d.dept_no=e.depto WHERE salario > (Select salario from empleados where nombre = 'Ana' ) AND d.loc != 'Sevilla'");
	break; 
	
	
	}
	consultar();
}
 


public static void consultar() throws SQLException{
	 ResultSet result = sentencia.executeQuery(consulta1);
	   while (result.next()){
		   if(resulconsul==3){
			   System.out.println(result.getString(1) + "  " +result.getInt(2));
		   }else{
			   if(resulconsul==4 ||resulconsul==5 ||resulconsul==7 ||resulconsul==10 ||resulconsul==11|| resulconsul==8 || resulconsul==9){
				   System.out.println(result.getString(1));
			   }else{
				   if(resulconsul==6){
					   System.out.println(result.getInt(1) + " " + result.getInt(2) + " " + result.getInt(3));
				   }else{
				   System.out.println(result.getInt(1) + " " + result.getString(2) + " " + result.getString(3)+ result.getString(4) + " " + result.getString(5) + " " + result.getString(6) + " " );
			   }} }}
	   	result.close();
	  sentencia.close();
	   			conexion.close();
		}


//------------------------------EJER 2-------------------------------------------------


public static void modificar() throws SQLException{
	System.out.println("Elige la modificación que quiere realizar: \n 1) Insertar empleados\n "
			+ "2) Eliminar empleados \n "
			+ "3) Modificar el salario a los empleados de Albal añadiendo 15 euros \n "
			+ "4) Modificar el salario a los empleados con departamento en Sevilla restando un euro");

Scanner entrada=new Scanner(System.in);
resulconsul=entrada.nextInt();
switch(resulconsul){
case 1: consulta2 = ("INSERT INTO empleados VALUES (10,'Pepito','2014-09-05','Albal', 700, 10),(11,'Anita','2014-02-07','Silla', 850, 20),(12,'Juanito','2014-08-06','Catarroja', 750, 30);"); 
break;
case 2: consulta2 = ("DELETE FROM empleados WHERE emp_no IN (4,5,6);"); 
break;
case 3: consulta2 = ("UPDATE empleados SET salario= salario+15 WHERE localidad = 'Albal';"); 
break;
case 4: consulta2 = ("UPDATE empleados SET salario=salario-1 WHERE depto IN(SELECT dept_no FROM departamentos WHERE loc = 'Sevilla');");
break;
default: System.out.println("Inserta un numero entre 1 y 4");
modificar();
break;

}
modi();
}
public static void modi() throws SQLException{
 sentencia.executeUpdate(consulta2);
 System.out.println("Se ha modificado correctamente");
 decision();
}

//------------------------------ EJER 3----------------------------------------


public static void procedimientos() throws SQLException{
	int resulco;
	System.out.println("Elige la consulta que quiere realizar: \n 1) Mostrar los empleados que trabajan en un departamento. \n "
			+ "2) A partir de un año, empleados contratados ese año \n "
			+ "3) A partir de una localidad y un departamento, Empleados que trabajan en ese departamento que sean de dicha localidad. \n "
			+ "4) A partir de una localidad y un salario, mostrar los empleados que trabaja3n en esa ciudad y cobran al menos ese salario. \n "
			+ "5) A partir de un departamento y un pueso, incrementar el salario de los empleados de ese departamento en el citado porcentaje. \n ");
	
	Scanner entrada=new Scanner(System.in);
	resulco=entrada.nextInt();
	switch(resulco){
	case 1: prod1();
	break;
	case 2: prod2();
	break;
	case 3: prod3();
	break;
	case 4: prod4();
	break;
	case 5: prod5();
	break;
	}
	
	
}

public static void prod1() throws SQLException{
	System.out.println("Introduzca el departamento: ");
	Scanner sc = new Scanner(System.in);
	int seleccion= sc.nextInt();

	String proce1 = "{call proc1(?)}";
	CallableStatement llamada= conexion.prepareCall(proce1);
	llamada.setInt(1, seleccion);
	/*llamada.registerOutParameter(2, java.sql.Types.INTEGER);
	llamada.registerOutParameter(3, java.sql.Types.VARCHAR);
	llamada.registerOutParameter(4, java.sql.Types.DATE);
	llamada.registerOutParameter(5, java.sql.Types.VARCHAR);
	llamada.registerOutParameter(6, java.sql.Types.INTEGER);
	llamada.registerOutParameter(7, java.sql.Types.INTEGER);*/
	
	ResultSet resul = llamada.executeQuery();
	System.out.println("Numero empleado  Nombre     Fecha Contratación   Población   Salario  Departamento     ");
	while(resul.next()){
		System.out.println(resul.getInt(1)+ "                 " + resul.getString(2)+ "        " + resul.getString(3) + "           " + resul.getString(4) + "         " + resul.getInt(5) + "       " + resul.getInt(6));
		
	}
		
	//llamada.executeUpdate();
	//System.out.println("Empleado numero: " + llamada.getInt(2) + " Nombre: " + llamada.getString(3) + " Fecha contratacion: " + llamada.getString(4) + " Poblacion:  " + llamada.getString(5) + " Salario: " + llamada.getInt(6) + " Departamento: " + llamada.getInt(7));
	
	}

public static void prod2() throws SQLException{
	System.out.println("Introduzca un año:");
	Scanner sc = new Scanner(System.in);
	int seleccion= sc.nextInt();

	String proce1 = "{call proc2(?)}";
	CallableStatement llamada= conexion.prepareCall(proce1);
	llamada.setInt(1, seleccion);
	ResultSet resul = llamada.executeQuery();
	System.out.println("Numero empleado  Nombre     Fecha Contratación   Población   Salario  Departamento     ");
	while(resul.next()){
		System.out.println(resul.getInt(1)+ "                 " + resul.getString(2)+ "        " + resul.getString(3) + "           " + resul.getString(4) + "         " + resul.getInt(5) + "       " + resul.getInt(6));
		
	}
		
		
	}
public static void prod3() throws SQLException{
	System.out.println("Introduzca localidad");
	Scanner sc = new Scanner(System.in);
	String localidad= sc.next();
	System.out.println("Introduzca numero de departamento");
	Scanner sc2 = new Scanner(System.in);
	int departamento= sc.nextInt();

	String proce1 = "{call proc3(?,?)}";
	CallableStatement llamada= conexion.prepareCall(proce1);
	llamada.setString(1, localidad);
	llamada.setInt(2, departamento);
	ResultSet resul = llamada.executeQuery();
	System.out.println("Numero empleado  Nombre     Fecha Contratación   Población   Salario  Departamento     ");
	while(resul.next()){
		System.out.println(resul.getInt(1)+ "                 " + resul.getString(2)+ "        " + resul.getString(3) + "           " + resul.getString(4) + "         " + resul.getInt(5) + "       " + resul.getInt(6));
		
	}
		
		
	}
public static void prod4() throws SQLException{
	System.out.println("Introduzca ciudad");
	Scanner sc = new Scanner(System.in);
	String localidad= sc.next();
	System.out.println("Introduzca salario");
	Scanner sc2 = new Scanner(System.in);
	int salario= sc.nextInt();

	String proce1 = "{call proc4(?,?)}";
	CallableStatement llamada= conexion.prepareCall(proce1);
	llamada.setString(1, localidad);
	llamada.setInt(2, salario);
	ResultSet resul = llamada.executeQuery();
	System.out.println("Numero empleado  Nombre     Fecha Contratación   Población   Salario  Departamento     ");
	while(resul.next()){
		System.out.println(resul.getInt(1)+ "                 " + resul.getString(2)+ "        " + resul.getString(3) + "           " + resul.getString(4) + "         " + resul.getInt(5) + "       " + resul.getInt(6));
		
	}
		
		
	}
public static void prod5() throws SQLException{
	System.out.println("Introduzca porcentaje a subir de sueldo");
	Scanner sc = new Scanner(System.in);
	int sueldo= sc.nextInt();
	System.out.println("Introduzca numero de departamento");
	Scanner sc2 = new Scanner(System.in);
	int departamento = sc.nextInt();
	System.out.println("Introduzca localidad ");
	Scanner sc3 = new Scanner(System.in);
	String localidad= sc.next();

	String proce1 = "{call proc5(?,?,?)}";
	CallableStatement llamada= conexion.prepareCall(proce1);
	llamada.setInt(1, sueldo);
	llamada.setInt(2, departamento);
	llamada.setString(3, localidad);
	int funciona=llamada.executeUpdate();
	if(funciona==0){
	System.out.println("Ha sido modificado");
	}else{
		System.out.println("Ha habido un error");
	}
		
		
	}
}
