/*package com.twitter.app.twitter;

import java.io.IOException;

import com.algolia.search.ApacheAPIClientBuilder;

import com.algolia.search.APIClient;
import com.algolia.search.Index;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Query;
 
  
public class Algoliasearch {
	
	public static void main(String[] args) throws IOException {
	  //  ApacheAPIClientBuilder client = new AsyncHttpAPIClientBuilder("YourApplicationID", "YourAPIKey").build();
		APIClient client = new ApacheAPIClientBuilder("ODGJXD8XE6", "c3a128d32ef5f9726f97e5666387815d").build();
		
	}
}
*/

package com.twitter.app.twitter;

/*
import java.sql.*;
import java.util.Properties;

  
public class Algoliasearch {
	
	static final String dbURL = "jdbc:redshift://ec2-34-205-219-185.compute-1.amazonaws.com:5432/d7o59j2fmsuj8v"; 
    static final String MasterUsername = "CloudHiti";
    static final String MasterUserPassword = "pd51abf28a9bdd2036c807f5f5ab3e7077ad72bce5c41fb1fc1c0cb83c7b9f383";


	
	public static void main(String[] args)  {
		
		Connection conn = null;
        Statement stmt = null;
        try{
           //Dynamically load driver at runtime.
           //Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc41.Driver
           //Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
        	Class.forName("com.amazon.redshift.jdbc.Driver");

           //Open a connection and define properties.
           System.out.println("Connecting to database...");
           Properties props = new Properties();

           //Uncomment the following line if using a keystore.
           props.setProperty("ssl", "true");  
           props.setProperty("user", MasterUsername);
           props.setProperty("password", MasterUserPassword);
           conn = DriverManager.getConnection(dbURL, props);
        
           //Try a simple query.
           System.out.println("Listing system tables...");
           stmt = conn.createStatement();
           String sql;
           sql = "select * from information_schema.tables;";
           ResultSet rs = stmt.executeQuery(sql);
           
           //Get the data from the result set.
           while(rs.next()){
              //Retrieve two columns.
              String catalog = rs.getString("table_catalog");
              String name = rs.getString("table_name");

              //Display values.
              System.out.print("Catalog: " + catalog);
              System.out.println(", Name: " + name);
           }
           rs.close();
           stmt.close();
           conn.close();
        }catch(Exception ex){
           //For convenience, handle all errors here.
           ex.printStackTrace();
        }finally{
           //Finally block to close resources.
           try{
              if(stmt!=null)
                 stmt.close();
           }catch(Exception ex){
           }// nothing we can do
           try{
              if(conn!=null)
                 conn.close();
           }catch(Exception ex){
              ex.printStackTrace();
           }
        }
        System.out.println("Finished connectivity test.");

	}
}
*/


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Algoliasearch {

	public static void main(String[] argv) {

		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		 
		try {

			  /*Properties props = new Properties();

	           //Uncomment the following line if using a keystore.
	          // props.setProperty("ssl", "true");  
	           props.setProperty("user", "CloudHiti");
	           props.setProperty("password", "pd51abf28a9bdd2036c807f5f5ab3e7077ad72bce5c41fb1fc1c0cb83c7b9f383");
	           connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-205-219-185.compute-1.amazonaws.com:5432/d7o59j2fmsuj8v", props);
	           */
	           connection = DriverManager.getConnection(
					"jdbc:mysql://cloudhiti.c0ttn3nzqakk.us-west-2.rds.amazonaws.com:3306/firepie", "cloudhiti",
					"$DhitiSanskrit$");
	           
	           System.out.println("Listing system tables...");
	           Statement stmt = null;
	           stmt = connection.createStatement();
	           String sql;
	           sql = "show tables;";
	           ResultSet rs = stmt.executeQuery(sql);
	           
	           //Get the data from the result set.
	           while(rs.next()){
	              //Retrieve two columns.
	             /* String catalog = rs.getString("table_catalog");
	              String name = rs.getString("table_name");

	              //Display values.
	              System.out.print("Catalog: " + catalog);
	              System.out.println(", Name: " + name);*/
	           }
	           rs.close();
	           stmt.close();
	           connection.close();
	           
 
		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

}