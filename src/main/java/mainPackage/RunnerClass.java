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
	public static String failedReason = "";
	public static ArrayList<String> successBuildings = new ArrayList<String>();
	public static ArrayList<String> failedBuildings = new ArrayList<String>();
	public static String[][] autoCharges;
	public static String[][] moveInCharges;
	public static String[] statusList;
	public static HashMap<String, String> failedReaonsList = new HashMap<String, String>();
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

		GenericMethods.generateLogs();
		GetDataFromSQL.getBuildingsList(AppConfig.pendingRenewalLeases);
		int j = 0;
		while (j < 3) {
			for (int i = 0; i < pendingRenewalLeases.length; i++) 
			{
				if (AppConfig.saveButtonOnAndOff == false) 
	            {
	                String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Failed', StatusID=3,NotAutomatedFields='Save Functionality is Off',LeaseCompletionDate= getDate() where BuildingName like '%" + pendingRenewalLeases[i][1] + "%'";
	                GetDataFromSQL.updateTable(updateSuccessStatus);
	                break;

	            } else 
	            {
	                try {
	                    FileUtils.cleanDirectory(new File(AppConfig.downloadFilePath));
	                } catch (Exception e) {
	                }
	            }

				GenericMethods.logger.info(
 						"-------------------------------------------------------------------------------------------");
				GenericMethods.logger.info(" Record -- " + (i + 1));
				company = pendingRenewalLeases[i][0];
				buildingAbbreviation = pendingRenewalLeases[i][1];
				leaseOwnerName = pendingRenewalLeases[i][2];
				statusID = 0;
				failedReason = "";

				try {
					FileUtils.cleanDirectory(new File(AppConfig.downloadFilePath));
				} catch (Exception e) {
				}
				if (company.equals("California PFW")) {
					company = "California pfw";
				}

				completeBuildingAbbreviation = buildingAbbreviation; 
				try {
					String a = buildingAbbreviation;
					a = a.replace(" ", "");
					int b = a.length() - 1;
					char c = a.charAt(a.indexOf('-') + 1);
					if (a.indexOf('-') >= 1 && a.indexOf('-') == (b - 1))
						buildingAbbreviation = buildingAbbreviation;
					else if (a.indexOf('-') >= 1 && a.charAt(a.indexOf('-') + 1) == '(')
						buildingAbbreviation = buildingAbbreviation.split("-")[0].trim();
					else
						buildingAbbreviation = buildingAbbreviation;
				} catch (Exception e) {
				}
				DatabaseClass.insertData(buildingAbbreviation,"Started",6);
				
				try {
					if (GenericMethods.login() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Failed', StatusID=3,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						GenericMethods.closeDriver();
						continue;

					}
					if (DatabaseClass.navigateToLease(company, leaseOwnerName, buildingAbbreviation,
							completeBuildingAbbreviation, driver) == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Failed', StatusID=3,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						GenericMethods.closeDriver();
						continue;
					}
					if (GenericMethods.downloadLeaseAgreement() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Failed', StatusID=3,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						GenericMethods.closeDriver();
						continue;
					}
					if (ReadingLeaseAggrements.getDataFromLeaseAgreements() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Failed', StatusID=3,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						GenericMethods.closeDriver();
						continue;
					}
					if (MoveInCharges.verifyLedgerForMonhtlyRentStartDate() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Review', StatusID=5,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						
					}
					if (AutoCharges.clearExistingAutoCharges() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Review', StatusID=5,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						
					}
					if (AutoCharges.addingNewAutoCharges() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Review', StatusID=5,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						
					}
					if (OtherInformation.addingOtherInformation() == false) {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Review', StatusID=5,NotAutomatedFields='"
								+ failedReason + "',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						continue;
					} else {
						String updateSuccessStatus = "Update [Automation].leaseRenewalAutomation Set Status ='Completed', StatusID=4,NotAutomatedFields='',LeaseCompletionDate= getDate() where BuildingName like '%"
								+ buildingAbbreviation + "%'";
						GetDataFromSQL.updateTable(updateSuccessStatus);
						GenericMethods.closeDriver();
						continue;
					}
				}

				catch (Exception e) {
					e.printStackTrace();
				}

			}
			GetDataFromSQL.getBuildingsList(AppConfig.failedLeasesQuery);

			
			if (pendingRenewalLeases.length > 0) {
				System.out.println((j + 1) + "- Time Looping");
				j++;
			}	
			else
				break;

		}
	}

}
