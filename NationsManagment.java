package org.generation.italy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class NationsManagment {

	//creare il mio database e lo salvo all'interno delle stringhe
		private final static String URL = "jdbc:mysql://localhost:3306/nations";
		private final static  String USER = "root";
		private final static String PASSWORD = "40RootBru96";
	
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		
		
		
		//Connection è un interfaccia del package sql
		try(Connection con =DriverManager.getConnection(URL, USER, PASSWORD)){
			System.out.print("Cerca il paese: ");
			String findCountries=scan.nextLine();
			String find="%"+findCountries+"%";
			
			
			String sql="select c.name as countries , country_id , r.name as regions, c2.name as continents \r\n"
					+ "from countries c \r\n"
					+ "inner join regions r \r\n"
					+ "on c.region_id = r.region_id \r\n"
					+ "inner join continents c2 \r\n"
					+ "on r.continent_id =c2.continent_id \r\n"
					+ "where c.name like ? \r\n"
					+ "order by countries asc;";
					
			try(PreparedStatement ps= con.prepareStatement(sql)){
				//metto le variabili
				ps.setString(1, find);
				//eseguo la query che restituisce un result set
				try(ResultSet rs=ps.executeQuery()){
					
					
					//itero cul result set in colonne
					while(rs.next()) {
						System.out.print(rs.getString(1));
						System.out.print("---");
						System.out.print(rs.getString(2));
						System.out.print("---");
						System.out.print(rs.getString(3));
						System.out.print("---");
						System.out.println(rs.getString(4));
					}
					
				}
			}
			
			
			
		}catch(SQLException e) {
			System.out.println("oops si è verificato l'errore "+e);
		}

		
		scan.close();
		
	}

}
