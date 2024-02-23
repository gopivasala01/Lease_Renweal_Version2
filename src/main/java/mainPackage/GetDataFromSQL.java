package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import GenericLibrary.GenericMethods;

public class GetDataFromSQL {


	 public static void insertData(String buildingName, String status, int statusID) throws Exception
	  {

		  String currentTime = GenericMethods.getCurrentDateTime();
		  String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
		  String sql;
		  if(statusID==1)
		   sql = "Update Automation.LeaseRenewalAutomation Set Status ='"+status+"', StatusID="+statusID+",NotAutomatedFields=NULL,StartTime= "+currentTime+" where BuildingName like '%"+buildingName+"%'";
		  else 
			sql = "Update Automation.LeaseRenewalAutomation Set Status ='"+status+"', StatusID="+statusID+",StartTime= '"+currentTime+"' where BuildingName like '%"+buildingName+"%'";
          //String sql = "Update [Automation].[LeaseInfo] Set Status = 'Completed', StatusID =4 where OwnerName='Duff, V.'";
		  
		    try (Connection conn = DriverManager.getConnection(connectionUrl);
		        Statement stmt = conn.createStatement();) 
		    {
		      stmt.executeUpdate(sql);
		      GenericMethods.logger.info("Database updated successfully ");
		      stmt.close();
	            conn.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
          
	  }
	
	public static boolean getBuildingsList(String query)
	{
		try
		{
		        Connection con = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            con = DriverManager.getConnection(AppConfig.connectionUrl);
		            String SQL =query; 
		            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		           // stmt = con.createStatement();
		            rs = stmt.executeQuery(SQL);
		            int rows =0;
		            if (rs.last()) 
		            {
		            	rows = rs.getRow();
		            	// Move to beginning
		            	rs.beforeFirst();
		            }
		            GenericMethods.logger.info("No of Rows = "+rows);
		            RunnerClass.pendingRenewalLeases = new String[rows][3];
		           int  i=0;
		            while(rs.next())
		            {
		            	
		            	String 	company =  (String) rs.getObject(1);
		                String  buildingAbbreviation = (String) rs.getObject(2);
		                String  ownerName = (String) rs.getObject(3);
		                GenericMethods.logger.info(company +" |  "+buildingAbbreviation+" | "+ownerName);
		    				//Company
		    				RunnerClass.pendingRenewalLeases[i][0] = company;
		    				//Building Abbreviation
		    				RunnerClass.pendingRenewalLeases[i][1] = buildingAbbreviation;
		    				//Owner Name
		    				RunnerClass.pendingRenewalLeases[i][2] = ownerName;
		    				i++;
		            }	
		            GenericMethods.logger.info("Total Pending Buildings  = " +RunnerClass.pendingRenewalLeases.length);
		            //for(int j=0;j<RunnerClass.pendingBuildingList.length;j++)
		            //{
		            //	System.out.println(RunnerClass.pendingBuildingList[j][j]);
		           // }
		            rs.close();
		            stmt.close();
		            con.close();
		 return true;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		 return false;
		}
	}
	
	public static void updateTable(String query)
	 {
		    try (Connection conn = DriverManager.getConnection(AppConfig.connectionUrl);
		        Statement stmt = conn.createStatement();) 
		    {
		      stmt.executeUpdate(query);
		      GenericMethods.logger.info("Record Updated");
		      stmt.close();
	            conn.close();
		    } catch (SQLException e) 
		    {
		      e.printStackTrace();
		    }
	 }
	
	public static boolean getAutoCharges()
	{
		try
		{
		        Connection con = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            con = DriverManager.getConnection(AppConfig.connectionUrl);
		            String SQL = AppConfig.getAutoCharges;
		            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		           // stmt = con.createStatement();
		            rs = stmt.executeQuery(SQL);
		            int rows =0;
		            if (rs.last()) {
		            	rows = rs.getRow();
		            	// Move to beginning
		            	rs.beforeFirst();
		            }
		            GenericMethods.logger.info("No of buildings with status = "+rows);
		            RunnerClass.autoCharges = new String[rows][5];
		           int  i=0;
		            while(rs.next())
		            {
		            	
		            	String 	chargeCode =  (String) rs.getObject(1);
		                String  amount = (String) rs.getObject(2);
		                String  startDate = (String) rs.getObject(3);
		                String  endDate = (String) rs.getObject(4);
		                String  description = (String) rs.getObject(5);
		                
		                GenericMethods.logger.info(chargeCode +" | "+amount+" | "+startDate+" | "+endDate+" | "+description);
		    				//Company
		    				RunnerClass.autoCharges[i][0] = chargeCode;
		    				//Building Abbreviation
		    				RunnerClass.autoCharges[i][1] = amount;
		    				//Monthly Rent From Lease Agreement
		    				RunnerClass.autoCharges[i][2] = startDate;
		    				//Monthly Rent In PW
		    				RunnerClass.autoCharges[i][3] = endDate;
		    				//Start Date From Lease Agreement
		    				RunnerClass.autoCharges[i][4] = description;
		    				i++;
		            }	
		           // System.out.println("Total Pending Buildings  = " +RunnerClass.pendingBuildingList.length);
		            //for(int j=0;j<RunnerClass.pendingBuildingList.length;j++)
		            //{
		            //	System.out.println(RunnerClass.pendingBuildingList[j][j]);
		           // }
		            rs.close();
		            stmt.close();
		            con.close();
		 return true;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		 return false;
		}
	}
	
	public static boolean getMoveInCharges()
	{
		try
		{
		        Connection con = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            con = DriverManager.getConnection(AppConfig.connectionUrl);
		            String SQL = AppConfig.getMoveInCharges;
		            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		           // stmt = con.createStatement();
		            rs = stmt.executeQuery(SQL);
		            int rows =0;
		            if (rs.last()) {
		            	rows = rs.getRow();
		            	// Move to beginning
		            	rs.beforeFirst();
		            }
		            GenericMethods.logger.info("No of buildings with status = "+rows);
		            RunnerClass.moveInCharges = new String[rows][5];
		           int  i=0;
		            while(rs.next())
		            {
		            	
		            	String 	chargeCode =  (String) rs.getObject(1);
		                String  amount = (String) rs.getObject(2);
		                String  startDate = (String) rs.getObject(3);
		                String  endDate = (String) rs.getObject(4);
		                String  description = (String) rs.getObject(5);
		                
		                GenericMethods.logger.info(chargeCode +" |  "+amount+" | "+startDate+" | "+endDate+" | "+description);
		    				//Company
		    				RunnerClass.moveInCharges[i][0] = chargeCode;
		    				//Building Abbreviation
		    				RunnerClass.moveInCharges[i][1] = amount;
		    				//Monthly Rent From Lease Agreement
		    				RunnerClass.moveInCharges[i][2] = startDate;
		    				//Monthly Rent In PW
		    				RunnerClass.moveInCharges[i][3] = endDate;
		    				//Start Date From Lease Agreement
		    				RunnerClass.moveInCharges[i][4] = description;
		    				i++;
		            }	
		            rs.close();
		            stmt.close();
		            con.close();
		 return true;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		 return false;
		}
	}
	public static boolean getpriorRentCharges()
	{
		try
		{
		        Connection con = null;
		        Statement stmt = null;
		        ResultSet rs = null;
		            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            con = DriverManager.getConnection(AppConfig.connectionUrl);
		            String SQL = "Select Amount from automation.LeaseReneWalsPriorRentConfiguration Where Abbreviation = '"+RunnerClass.buildingAbbreviation+"' and Flag ='1'";
		            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		           // stmt = con.createStatement();
		            rs = stmt.executeQuery(SQL);
		            int rows =0;
		            if (rs.last()) {
		            	rows = rs.getRow();
		            	// Move to beginning
		            	rs.beforeFirst();
		            }
		            GenericMethods.logger.info("prior rent with status = "+rows);
		           int  i=0;
		            while(rs.next())
		            {
		                String  amount = (String) rs.getObject(1);
		                GenericMethods.logger.info( "Prior Rent = "+ amount );
		    				
		    				RunnerClass.priorRentCharges = amount;
		    				//Monthly Rent From Lease Agreement
		    				i++;
		            }	
		            rs.close();
		            stmt.close();
		            con.close();
		 return true;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		 return false;
		}
	}


}
