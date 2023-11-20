package mainPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class RunnerClass {
	
	public static String[][] pendingRenewalLeases; 
    public static String company;
    public static String buildingAbbreviation;
    public static String leaseOwnerName;
    public static WebDriver driver;
    public static Alert alert;
    
	public static ChromeOptions options;
	public static Actions actions;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	public static String[][] pendingBuildingList;
	public static int updateStatus;
	public static String failedReason ="";
	public static ArrayList<String> successBuildings = new ArrayList<String>();
	public static ArrayList<String> failedBuildings = new ArrayList<String>();
	public static String[][] autoCharges;
	public static String[][] moveInCharges;
	public static String [] statusList;
	public static String currentDate = "";
	public static HashMap<String,String> failedReaonsList= new HashMap<String,String>();
	public static String leaseStatuses[][];
	public static String UWStatuses[][];
	public static String downloadFilePath;
	public static String monthlyRent;
	public static String startDate;
	public static String monthlyRentInPW;
	public static String startDateInPW;
	public static String portfolioType;
	public static boolean published;
	public static boolean listingAgent;
	public static String currentTime;
	public static int statusID;
	public static String renewalExecutionDate = "";
	public static boolean checkifMoveInDateIsLessThan5DaysToEOM = false;
	public static boolean arizonaCodeAvailable = false;
	public static String arizonaCityFromBuildingAddress = "";
	public static String arizonaRentCode = "";
	public static String completeBuildingAbbreviation;
	public static String portfolioName;
	
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		GetDataFromSQL.getBuildingsList();
		for(int i=0;i<pendingRenewalLeases.length;i++) 
		{
		  System.out.println(" Record -- "+(i+1));
		  company = pendingRenewalLeases[i][0];
		  buildingAbbreviation =pendingRenewalLeases[i][1];
		  leaseOwnerName = pendingRenewalLeases[i][2];
		  statusID =0;
		  failedReason = "";
		  
		  
		  try
			{
			FileUtils.cleanDirectory(new File(AppConfig.downloadFilePath));
			}
			catch(Exception e) {}
		
		  
		  completeBuildingAbbreviation = buildingAbbreviation;  //This will be used when Building not found in first attempt
          try
          {
           String a = buildingAbbreviation;
           a = a.replace(" " , "");
           int b = a.length()-1;
          char c =  a.charAt(a.indexOf('-')+1);
           if(a.indexOf('-')>=1&&a.indexOf('-')==(b-1))
				buildingAbbreviation = buildingAbbreviation;
			else
				if(a.indexOf('-')>=1&&a.charAt(a.indexOf('-')+1)=='(')
           buildingAbbreviation = buildingAbbreviation.split("-")[0].trim();
				else buildingAbbreviation = buildingAbbreviation;
          }
          catch(Exception e) {}
          
          
          try {
        	  if(GenericMethods.login() == true) {
        		  try {
        			  if(DatabaseClass.navigateToLease(company,leaseOwnerName,buildingAbbreviation,completeBuildingAbbreviation,driver) == true) {
            			  try {
            				  if(GenericMethods.downloadLeaseAgreement() == true) {
            					  try {
            						  if(ReadingLeaseAggrements.getDataFromLeaseAgreements()==true) {
            							  //write code for data modification and adding
            						  }
            						  else {
            							  String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation2 Set Status ='Failed', StatusID=3,NotAutomatedFields='"+failedReason+"',LeaseCompletionDate= getDate() where BuildingName like '%"+buildingAbbreviation+"%'";
            			            	  GetDataFromSQL.updateTable(updateSuccessStatus);
            						  }
            					  }
            					  catch(Exception e4) {
                        			  e4.printStackTrace();
                        		  }

            				  }
            				  else {
            					  String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation2 Set Status ='Failed', StatusID=3,NotAutomatedFields='"+failedReason+"',LeaseCompletionDate= getDate() where BuildingName like '%"+buildingAbbreviation+"%'";
            	            	  GetDataFromSQL.updateTable(updateSuccessStatus);
            				  }
            			  }
            			  catch(Exception e3) {
                			  e3.printStackTrace();
                		  }
            		  }
        			  else {
        				  String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation2 Set Status ='Failed', StatusID=3,NotAutomatedFields='"+failedReason+"',LeaseCompletionDate= getDate() where BuildingName like '%"+buildingAbbreviation+"%'";
                    	  GetDataFromSQL.updateTable(updateSuccessStatus);
        			  }
        		  }
        		  catch(Exception e2) {
        			  e2.printStackTrace();
        		  }
            	  
              }
              else {
            	  String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation2 Set Status ='Failed', StatusID=3,NotAutomatedFields='"+failedReason+"',LeaseCompletionDate= getDate() where BuildingName like '%"+buildingAbbreviation+"%'";
            	  GetDataFromSQL.updateTable(updateSuccessStatus);
              }
              try
    		  {
    			  driver.quit();
    		  }
    		  catch(Exception e1) {
    			  e1.printStackTrace();
    		  }
          }
          catch(Exception e) {
			  e.printStackTrace();
		  }
          
          
          
		}
	}
	
	
	

}
