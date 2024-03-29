package ExtractData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import GenericLibrary.GenericMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import mainPackage.RunnerClass;





public class DatabaseClass {
	
	public static WebDriverWait wait;
	public static Actions actions;
	public static JavascriptExecutor js;
	
	public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
	public static String[][] IDFromLeaseDashboard;
	public static String EntityID;
	public static String leaseEntityID;
	public static String buildingEntityID;
	
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
		      System.out.println("Database updated successfully ");
		      stmt.close();
	            conn.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
        
	  }
	
	public static boolean getEntityID(String query)
	{
	try
	{
	        Connection con = null;
	        Statement stmt = null;
	        ResultSet rs = null;
	            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	            con = DriverManager.getConnection(connectionUrl);
	            String SQL = query;
	            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	           // stmt = con.createStatement();
	            stmt.setQueryTimeout(60);
	            rs = stmt.executeQuery(SQL);
	            int rows =0;
	            if (rs.last()) 
	            {
	            	rows = rs.getRow();
	            	// Move to beginning
	            	rs.beforeFirst();
	            }
	            if(rows>1 || rows == 0) {
	            	return false;
	            	
	            }
	            GenericMethods.logger.info("No of Rows = "+rows);
	            IDFromLeaseDashboard = new String[rows][1];
	           int  i=0;
	            while(rs.next())
	            {
	  
	            	String 	ID = rs.getObject(1).toString();
	            	
	              //stateCode
	                try 
	                {
	                	if(ID==null)
	                		IDFromLeaseDashboard[i][0] = "";
	                	else
	                	{
	                		IDFromLeaseDashboard[i][0] = ID;
	                		EntityID = IDFromLeaseDashboard[i][0];
	                	}
	                }
	                catch(Exception e)
	                {
	                	IDFromLeaseDashboard[i][0] = "";
	                }
	            }
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return false;
	}
	return true;
}
	
	public static boolean navigateToLease(String company,String leaseName, String buildingAbbrivation,String completeBuildingName,WebDriver driver) {
		try {
			
			if(navigateUsingLeaseEntityID(company,leaseName,completeBuildingName,driver) == false) {
				if(searchBuilding(company,leaseName ,buildingAbbrivation,completeBuildingName,driver)==true) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
		
	}
	
	
	public static boolean navigateUsingLeaseEntityID(String company,String leaseName, String buildingAbbrivation,WebDriver driver) {
		
		try
		{
			String Query = "Select top 1 LeaseEntityID from LeaseFact_Dashboard where LeaseName='" + leaseName +"' and Company= '"+company+"' and IsActive='Yes'";
			if(getEntityID(Query) == true) {
				 if(EntityID == null || EntityID == "") {
			        	//RunnerClass.failedReason = "Building Not Available";
			        	return false;
			        }
				 else {
					 leaseEntityID = EntityID;
						String URL = "https://app.propertyware.com/pw/leases/lease_detail.do?entityID="+leaseEntityID;
						GenericMethods.logger.info("URL = "+URL);
						try {
							driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
					        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					        driver.navigate().refresh();
					        Thread.sleep(3000);
					        intermittentPopUp(driver);
					        Thread.sleep(2000);
					        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
					        driver.findElement(Locators.marketDropdown).click();
					        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
					        String marketName = "HomeRiver Group - "+company.trim();
					        Select marketDropdownList = new Select(driver.findElement(Locators.marketDropdown));
					        marketDropdownList.selectByVisibleText(marketName);
					        Thread.sleep(2000);
					        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
					        
					        driver.navigate().to(URL);
							
					        intermittentPopUp(driver);
					       
					      
						}
						catch (TimeoutException t) {
							 WebDriverManager.chromedriver().clearDriverCache().setup();
							 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
							return false;
							
						}
						catch(Exception e)
						{
							GenericMethods.logger.error("Building Not Available");
							//RunnerClass.failedReason= "Building Not Available";
							return false;
						}
						return true;
					}
				
					
				}
			else {
				return false;
			}
			
			 }
		   catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
			}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean navigateUsingBuildingEntityID(String buildingAbbreviation,WebDriver driver)
	{
		try
		{
			String Query = "Select top 1 BuildingEntityID from LeaseFact_Dashboard where Building like '%"+buildingAbbreviation+"%'"; 
			if(getEntityID(Query) == true) {
				buildingEntityID = EntityID;
				String URL = "https://app.propertyware.com/pw/leases/lease_detail.do?entityID="+leaseEntityID;
				GenericMethods.logger.info("URL = "+URL);
				driver.navigate().to(URL);
				return true;
			}
			else {
				return false;
			}	
			
		
		}
		catch (TimeoutException t) {
			 WebDriverManager.chromedriver().clearDriverCache().setup();
			 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
			return false;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean searchBuilding(String company,String leaseName ,String building,String completeBuildingName,WebDriver driver)
	{
		try
		{
	    //RunnerClass.driver.findElement(Locators.dashboardsTab).click();
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.findElement(Locators.searchbox).clear();
		driver.findElement(Locators.searchbox).sendKeys(building);
			try
			{
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(Locators.searchingLoader)));
			}
			catch(Exception e)
			{
				try
				{
				driver.manage().timeouts().implicitlyWait(200,TimeUnit.SECONDS);
				driver.navigate().refresh();
				driver.findElement(Locators.dashboardsTab).click();
				driver.findElement(Locators.searchbox).clear();
				driver.findElement(Locators.searchbox).sendKeys(building);
				wait.until(ExpectedConditions.invisibilityOf(driver.findElement(Locators.searchingLoader)));
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch(Exception e2) {}
			}
			try
			{
				driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
			if(driver.findElement(Locators.noItemsFoundMessagewhenLeaseNotFound).isDisplayed())
			{
				long count = building.chars().filter(ch -> ch == '.').count();
				if(building.chars().filter(ch -> ch == '.').count()>=2)
				{
					building = building.substring(building.indexOf(".")+1,building.length());
					driver.manage().timeouts().implicitlyWait(200,TimeUnit.SECONDS);
					driver.navigate().refresh();
					driver.findElement(Locators.dashboardsTab).click();
					driver.findElement(Locators.searchbox).clear();
					driver.findElement(Locators.searchbox).sendKeys(building);
					wait.until(ExpectedConditions.invisibilityOf(driver.findElement(Locators.searchingLoader)));
					try
					{
					driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
					if(driver.findElement(Locators.noItemsFoundMessagewhenLeaseNotFound).isDisplayed())
					{
						GenericMethods.logger.error("Building Not Found");
						RunnerClass.failedReason = RunnerClass.failedReason + "," + "Building Not Found";
						return false;
					}
					
					}
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch(Exception e3) {}
				}
				else
				{
					try
					{
					building = building.split("_")[1];
					driver.manage().timeouts().implicitlyWait(200,TimeUnit.SECONDS);
					driver.navigate().refresh();
					driver.findElement(Locators.dashboardsTab).click();
					driver.findElement(Locators.searchbox).clear();
					driver.findElement(Locators.searchbox).sendKeys(building);
					wait.until(ExpectedConditions.invisibilityOf(driver.findElement(Locators.searchingLoader)));
					try
					{
					driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
					if(driver.findElement(Locators.noItemsFoundMessagewhenLeaseNotFound).isDisplayed())
					{
						GenericMethods.logger.error("Building Not Found");
						RunnerClass.failedReason = RunnerClass.failedReason + "," + "Building Not Found";
						return false;
					}
					}
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch(Exception e3) {}
					}
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch(Exception e)
					{
						GenericMethods.logger.error("Building Not Found");
						RunnerClass.failedReason = RunnerClass.failedReason + "," + "Building Not Found";
				    return false;
					}
				}
			}
			}
			catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
			}
			catch(Exception e2)
			{
			}
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			Thread.sleep(3000);
			GenericMethods.logger.info("Building Name " +building);
		// Select Lease from multiple leases
			List<WebElement> displayedCompanies =null;
			try
			{
				displayedCompanies = driver.findElements(Locators.searchedLeaseCompanyHeadings);
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				
			}
				boolean leaseSelected = false;
				for(int i =0;i<displayedCompanies.size();i++)
				{
					String companyName = displayedCompanies.get(i).getText();
					if(companyName.toLowerCase().contains(company.toLowerCase())&&!companyName.contains("Legacy"))
					{
						
						List<WebElement> leaseList = driver.findElements(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li/a"));
						for(int j=0;j<leaseList.size();j++)
						{
							String lease = leaseList.get(j).getText();
							if(lease.toLowerCase().contains(completeBuildingName.toLowerCase()))
							{
								driver.findElement(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li["+(j+1)+"]/a")).click();
								leaseSelected = true;
								break;
							}
						}
						if(leaseSelected == false)
						{
						for(int j=0;j<leaseList.size();j++)
						{
							String lease = leaseList.get(j).getText();
							if(lease.toLowerCase().contains(building.toLowerCase())&&lease.contains(":"))
							{
								
								
								driver.findElement(By.xpath("(//*[@class='section'])["+(i+1)+"]/ul/li["+(j+1)+"]/a")).click();
								leaseSelected = true;
								break;
							}
						}
						}
						if (leaseSelected == false) {
			                String companyName1 = displayedCompanies.get(i).getText();
			                if (companyName1.toLowerCase().contains(company.toLowerCase()) && !companyName1.contains("Legacy") && !companyName1.contains("Sandbox")) 
			                {
			                    List<WebElement> advancesearch = RunnerClass.driver.findElements(Locators.advancedSearch);
			                    advancesearch.get(i).click();
			                    RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.advancedSearch_buildingsSection)).build().perform();
			                    List<WebElement> buildingAddresses = RunnerClass.driver.findElements(Locators.advancedSearch_buildingAddresses);
			                    for (int k = 0; k < buildingAddresses.size(); k++)
			                    {
			                        String address = buildingAddresses.get(k).getText();
			                        if (address.toLowerCase().endsWith(building.toLowerCase())) 
			                        {
			                            buildingAddresses.get(k).click();
			                            leaseSelected = true;
			                            break;
			                        }
			                    }
			                  //  if (checkBuildingIsClicked == true)
			                      //  break;
			                    }
			            }
						
					}
					if(leaseSelected==true)
					{
						try
						{
						js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
						Thread.sleep(2000);
						if(driver.findElement(Locators.leasesTab).getText().equals("Leases"))
							driver.findElement(Locators.leasesTab).click();
						else 
							driver.findElement(Locators.leasesTab2).click();
						driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
						
							actions.moveToElement(driver.findElement(By.partialLinkText(leaseName.trim()))).build().perform();
							driver.findElement(By.partialLinkText(leaseName.trim())).click();
						}
						catch (TimeoutException t) {
							 WebDriverManager.chromedriver().clearDriverCache().setup();
							 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
							return false;
							
						}
						catch(Exception e)
						{
							e.printStackTrace();
							GenericMethods.logger.error("Unable to Click Lease Owner Name");
						  //  RunnerClass.failedReason =  RunnerClass.failedReason+","+  "Unable to Click Lease Onwer Name";
							return false;
						}
						
					     return true;
					}
				}
				if(leaseSelected==false)
				{
				    
					GenericMethods.logger.error("Building Not Found");
					RunnerClass.failedReason =  RunnerClass.failedReason+","+ "Building Not Found";
					return false;
				}
	         } 
		   	catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
		   	}
			catch(Exception e) 
		     {
	         
	        	 GenericMethods.logger.error("Issue in selecting Building");
	        	 RunnerClass.failedReason = RunnerClass.failedReason+","+  "Issue in selecting Building";
	        	 return false;
		     }
		return true;
	}
	
	
	public static void intermittentPopUp(WebDriver driver)
	{
		//Pop up after clicking lease name
				try
				{
					driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
			        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
			        try {
			        	Thread.sleep(1000);
			        	driver.switchTo().frame(driver.findElement(Locators.scheduleMaintananceIFrame));
			        	if(driver.findElement(Locators.scheduleMaintanancePopUp2).isDisplayed()) {
			        		Thread.sleep(1000);
			        		driver.findElement(Locators.maintananceCloseButton).click();
			        	}
			        	driver.switchTo().defaultContent();
			        }
			        catch(Exception e) {}
			        try
			        {
					if(driver.findElement(Locators.popUpAfterClickingLeaseName).isDisplayed())
					{
						driver.findElement(Locators.popupClose).click();
					}
			        }
			        catch(Exception e) {}
			        try
			        {
					if(driver.findElement(Locators.scheduledMaintanancePopUp).isDisplayed())
					{
						driver.findElement(Locators.scheduledMaintanancePopUpOkButton).click();
					}
					 
			        }
			        catch(Exception e) {}
			        try
			        {
			        if(driver.findElement(Locators.scheduledMaintanancePopUpOkButton).isDisplayed()) {
			        	driver.findElement(Locators.scheduledMaintanancePopUpOkButton).click();
			        }
			     
			        } 
			        catch(Exception e) {}
			       
					driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
			        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
				}
				catch(Exception e) {}
				
	}


}
