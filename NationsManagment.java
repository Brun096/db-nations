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
			
			
			String sql="select country_id ,c.name as countries  , r.name as regions, c2.name as continents \r\n"
					+ "from countries c \r\n"
					+ "inner join regions r \r\n"
					+ "on c.region_id = r.region_id \r\n"
					+ "inner join continents c2 \r\n"
					+ "on r.continent_id =c2.continent_id \r\n"
					+ "where c.name like ? \r\n"
					+ "order by countries asc;";
			
			
			String sql2="select l.`language`\r\n"
					+ "from country_languages cl \r\n"
					+ "inner join languages l \r\n"
					+ "on cl.language_id =l.language_id\r\n"
					+ "where country_id = ? ;";
			
			String sql3="select *\r\n"
					+ "from country_stats cs \r\n"
					+ "where country_id = ? \r\n"
					+ "order by cs.`year` desc \r\n"
					+ "limit 1;";
			
			try(PreparedStatement ps= con.prepareStatement(sql)){
				//metto le variabili
				ps.setString(1, find);
				
				
				//eseguo la query che restituisce un result set
				try(ResultSet rs=ps.executeQuery()){
					
					
					//itero cul result set in colonne
					while(rs.next()) {
						System.out.print("ID: "+rs.getString(1));
						System.out.print("---");
						System.out.print("COUNTRY: "+rs.getString(2));
						System.out.print("---");
						System.out.print("REGION: "+rs.getString(3));
						System.out.print("---");
						System.out.println("CONTINENT: "+rs.getString(4));
					}
					
				}
			}
			
			System.out.print("Inserisci l'Id del paese: ");
			int countryId=scan.nextInt();
			try(PreparedStatement ps= con.prepareStatement(sql2)){
				//metto le variabili
				ps.setInt(1, countryId);
				
				
				//eseguo la query che restituisce un result set
				try(ResultSet rs=ps.executeQuery()){
					
					
					//itero cul result set in colonne
					while(rs.next()) {
						System.out.print("Languages: ");
						System.out.print(rs.getString(1)+"  ");
					}
					
				}
			}
			System.out.println("");
			try(PreparedStatement ps= con.prepareStatement(sql3)){
				//metto le variabili
				ps.setInt(1, countryId);
				
				
				//eseguo la query che restituisce un result set
				try(ResultSet rs=ps.executeQuery()){
					
					
					//itero cul result set in colonne
					while(rs.next()) {
						System.out.println("Most recent stats: "+rs.getString(1));
						System.out.println("Year: "+rs.getString(2));
						System.out.println("Population: "+rs.getString(3));
						System.out.println("GDP: "+rs.getString(4));
					}
					
				}
			}
			
		}catch(SQLException e) {
			System.out.println("oops si è verificato l'errore "+e);
		}

		
		scan.close();
		
	}

}
