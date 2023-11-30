package mainPackage;


public class AppConfig {
	
	

	public static boolean saveButtonOnAndOff= false;
	public static String username= "mds0418@gmail.com";
	public static String password="KRm#V39fecMDGg#";
	public static String URL="https://app.propertyware.com/pw/login.jsp";
	public static String[] Buildings= {"SABA2399"};
	public static String[] Names= {"Baxter - Hernandez"};
	
	public static String pdfImage = "C:\\SantoshMurthyP\\Tessaract Images\\";
	
	public static String test ="";
    public static String pendingRenewalLeases = "Select  Company,buildingName,OwnerName from Automation.leaseRenewalAutomation2 order by AsOfDate desc";//where NotAutomatedFields != ',Unable to download Lease Agreement' ";//where NotAutomatedFields in ('Building Not Available',',Building Not Found') ";//where Status = 'In Progress'";
		  
	public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
    public static String downloadFilePath = "C:\\Users\\gopi\\Documents\\BaseRent Update Files\\New folder";
    public static String[] LeaseAgreementFileNames = {"RT Renewal Signed","RT - RENEWAL","RT_Full_Lease","Full Lease -","RENEWAL","renewal_","Renewal","Full_Lease","Full"};
    
    public static String buildingPageURL = "https://app.propertyware.com/pw/leases/lease_detail.do?entityID=";
    

	
	//Mail credentials
    public static String fromEmail = "bireports@beetlerim.com";
	   public static String fromEmailPassword = "Welcome@123";
	   
	   public static String toEmail ="gopi.v@beetlerim.com,Santosh.p@beetlerim.com";
	   public static String CCEmail = "santosh.t@beetlerim.com";
	   
	   public static String mailSubject = "Lease Audit for the Month of   ";
	   
	   public static String excelFileLocation = "E:\\Automation\\Gopi\\Lease Audit Automation";
	   
	   public static String getAutoCharges = "Select ChargeCode, Amount, StartDate,EndDate,Description from automation.LeaseReneWalsAutoChargesConfiguration Where Flag =1";
	   public static String[] monthlyRentFromPDF = {"Monthly Rent:^Monthly Rent due in the amount of $^ ","Monthly Rent:^Tenant will pay Landlord monthly rent in the amount of $^ ","monthly installments,^on or before the 1st day of each month, in the amount of $^ "};
	   public static String getMoveInCharges = "Select ChargeCode, Amount, StartDate,EndDate,Description from automation.LeaseReneWalsMoveInChargesConfiguration Where Flag =1";
	   
	   public static String[] IAGClientList = {"510","AVE","BTH","CAP","FOR","HRG","HS","MAN","MCH","OFF","PIN","RF","SFR3","TH","HH","Lofty.Ai"};
	
	
	   public static String getMonthlyRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "40010 - Rent Income";
		   case "Alabama":
			  return "40010 - Rent Income";
		   case "North Carolina":
			   return "40010 - Rent Income";
		   case "Dallas/Fort Worth":
			   return "40010 - Rent Income";
		   case "Arkansas":
			   return "40010 - Rent Income";
		   case "Indiana":
			   return "40010 - Rent Income";
		   case "Little Rock":
			   return "40010 - Rent Income";
		   case "Georgia":
			   return "40010 - Rent Income";
		   case "Tennessee":
			   return "40010 - Rent Income";
		   case "California":
			   return "40010 - Rent Income";
		   case "California PFW":
			   return "40010 - Rent Income";
		   case "Houston":
			   return "40010 - Rent Income";
		   case "Austin":
			   return "40010 - Rent Income";
		   case "Chattanooga":
			   return "40010 - Rent Income";
		   case "Chicago":
			   return "40010 - Rent Income";
		   case "Savannah":
			   return "40010 - Rent Income";
		   case "South Carolina":
			   return "40010 - Rent Income";
		   case "Tulsa":
			   return "40010 - Rent Income";
		   case "Ohio":
			   return "40010 - Rent Income";
		   case "Maine":
				  return "40010 - Rent Income";
		   case "OKC":
				  return "40010 - Rent Income";
		   case "San Antonio":
				  return "40010 - Rent Income";
		   case "Pennsylvania":
				  return "40010 - Rent Income";
		   case "Colorado Springs":
				  return "40010 - Rent Income";
		   case "Kansas City":
				  return "40010 - Rent Income";
		   case "Lake Havasu":
				  return "40010 - Rent Income";
		   case "New Mexico":
				  return "40010 - Rent Income";
		   case "Chicago PFW":
				  return "40010 - Rent Income";
		   case "Boise":
				  return "40010 - Rent Income";
		   case "Spokane":
				  return "40010 - Rent Income";
		   case "Utah":
				  return "40010 - Rent Income";
		   case "Hawaii":
				  return "40010 - Rent Income";
		   case "Columbia - St Louis":
				  return "40010 - Rent Income";
		   case "Idaho Falls":
				  return "40010 - Rent Income";
		   case "Arizona":
			   if(RunnerClass.arizonaCodeAvailable==false&&RunnerClass.arizonaCityFromBuildingAddress.contains("Phoenix"))
				   return "40018 - Rent-PHX";
			   else if(RunnerClass.arizonaCodeAvailable==false)
			   return "40010 - Rent Income";
			   else return RunnerClass.arizonaRentCode;
		   case "Maryland":
				  return "40010 - Rent Income";
		   case "Virginia":
				  return "40010 - Rent Income";
		   case "Washington DC":
				  return "40010 - Rent Income";
		   case "New Jersey":
				  return "40010 - Rent Income";
		   case "Montana":
				  return "40010 - Rent Income";
		   }
		   return "";
	   }
	   
	   public static String getPetRentChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "40230 - Pet Rent";
		   case "Alabama":
			   return "40230 - Pet Rent";
		   case "North Carolina":
			   return "40230 - Pet Rent";
		   case "Dallas/Fort Worth":
			   return "40230 - Pet Rent";
		   case "Arkansas":
			   return "40230 - Pet Rent";
		   case "Georgia":
			   return "40230 - Pet Rent";
		   case "Indiana":
			   return "40230 - Pet Rent";
		   case "Little Rock":
			   return "40230 - Pet Rent";
		   case "Tennessee":
			   return "40230 - Pet Rent";
		   case "California":
			   return "40230 - Pet Rent";
		   case "California PFW":
			   return "40230 - Pet Rent";
		   case "Houston":
			   return "40230 - Pet Rent";
		   case "Austin":
			   return "40230 - Pet Rent";
		   case "Chattanooga":
			   return "40230 - Pet Rent";
		   case "Chicago":
			   return "40230 - Pet Rent";
		   case "Savannah":
			   return "40230 - Pet Rent";
		   case "South Carolina":
			   return "40230 - Pet Rent";
		   case "Tulsa":
			   return "40230 - Pet Rent";
		   case "Ohio":
			   return "40230 - Pet Rent";
		   case "Maine":
			   return "40230 - Pet Rent";
		   case "OKC":
			   return "40230 - Pet Rent";
		   case "San Antonio":
			   return "40230 - Pet Rent";
		   case "Pennsylvania":
			   return "40230 - Pet Rent";
		   case "Colorado Springs":
			   return "40230 - Pet Rent";
		   case "Kansas City":
			   return "40230 - Pet Rent";
		   case "Lake Havasu":
			   return "40230 - Pet Rent";
		   case "New Mexico":
			   return "40230 - Pet Rent";
		   case "Chicago PFW":
			   return "40230 - Pet Rent";
		   case "Boise":
			   return "40230 - Pet Rent";
		   case "Spokane":
			   return "40230 - Pet Rent";
		   case "Utah":
			   return "40230 - Pet Rent";
		   case "Hawaii":
			   return "40230 - Pet Rent";
		   case "Columbia - St Louis":
			   return "40230 - Pet Rent";
		   case "Idaho Falls":
			   return "40230 - Pet Rent";
		   case "Arizona":
			   return "40230 - Pet Rent";
		   case "Maryland":
			   return "40230 - Pet Rent";
		   case "Virginia":
			   return "40230 - Pet Rent";
		   case "Washington DC":
			   return "40230 - Pet Rent";
		   case "New Jersey":
			   return "40230 - Pet Rent";
		   case "Montana":
			   return "40230 - Pet Rent";
		   }
		   return "";
	   }
	   
	
	 
	   public static String getHVACAirFilterFeeChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "43060 - Filter Fee";
		   case "Alabama":
			   return "43060 - Filter Fee";
		   case "North Carolina":
			   return "43060 - Filter Fee";
		   case "Dallas/Fort Worth":
			   return "43060 - Filter Fee";
		   case "Arkansas":
			   return "43060 - Filter Fee";
		   case "Georgia":
			   return "43060 - Filter Fee";
		   case "Indiana":
			   return "43060 - Filter Fee";
		   case "Little Rock":
			   return "43060 - Filter Fee";
		   case "Tennessee":
			   return "43060 - Filter Fee";
		   case "California":
			   return "43060 - Filter Fee";
		   case "California PFW":
			   return "43060 - Filter Fee";
		   case "Houston":
			   return "43060 - Filter Fee";
		   case "Austin":
			   return "43060 - Filter Fee";
		   case "Chattanooga":
			   return "43060 - Filter Fee";
		   case "Chicago":
			   return "43060 - Filter Fee";
		   case "Savannah":
			   return "43060 - Filter Fee";
		   case "South Carolina":
			   return "43060 - Filter Fee";
		   case "Tulsa":
			   return "43060 - Filter Fee";
		   case "Ohio":
			   return "43060 - Filter Fee";
		   case "Maine":
			   return "43060 - Filter Fee";
		   case "OKC":
			   return "43060 - Filter Fee";
		   case "San Antonio":
			   return "43060 - Filter Fee";
		   case "Pennsylvania":
			   return "43060 - Filter Fee";
		   case "Colorado Springs":
			   return "43060 - Filter Fee";
		   case "Kansas City":
			   return "43060 - Filter Fee";
		   case "Lake Havasu":
			   return "43060 - Filter Fee";
		   case "New Mexico":
			   return "43060 - Filter Fee";
		   case "Chicago PFW":
				   return "43060 - Filter Fee";
		   case "Boise":
			   return "43060 - Filter Fee";
		   case "Spokane":
			   return "43060 - Filter Fee";
		   case "Utah":
			   return "43060 - Filter Fee";
		   case "Hawaii":
			   return "43060 - Filter Fee";
		   case "Columbia - St Louis":
			   return "43060 - Filter Fee";
		   case "Idaho Falls":
			   return "43060 - Filter Fee";
		   case "Arizona":
			   return "43060 - Filter Fee";
		   case "Maryland":
			   return "43060 - Filter Fee";
		   case "Virginia":
			   return "43060 - Filter Fee";
		   case "Washington DC":
			   return "43060 - Filter Fee";
		   case "New Jersey":
			   return "43060 - Filter Fee";
		   case "Montana":
			   return "43060 - Filter Fee";
			   
		   }
		   return "";
	   }
	   
	   public static String getResidentBenefitsPackageChargeCode(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "43070 - Resident Benefit Package Fee";
		   case "Alabama":
			   return "43070 - Resident Benefit Package Fee";
		   case "North Carolina":
			   return "43070 - Resident Benefit Package Fee";
		   case "Dallas/Fort Worth":
			   return "43070 - Resident Benefit Package Fee";
		   case "Arkansas":
			   return "43070 - Resident Benefits Package";
		   case "Georgia":
			   return "43070 - Resident Benefit Package Fee";
		   case "Indiana":
			   return "43070 - Resident Benefit Package Fee";
		   case "Little Rock":
			   return "43070 - Resident Benefit Package Fee";
		   case "Tennessee":
			   return "43070 - Resident Benefit Package Fee";
		   case "California":
			   return "43070 - Resident Benefit Package Fee";
		   case "California PFW":
			   return "43070 - Resident Benefit Package Fee";
		   case "Houston":
			   return "43070 - Resident Benefit Package Fee";
		   case "Austin":
			   return "43070 - Resident Benefit Package Fee";
		   case "Chattanooga":
			   return "43070 - Resident Benefit Package Fee";
		   case "Chicago":
			   return "43070 - Resident Benefit Package Fee";
		   case "Savannah":
			   return "43070 - Resident Benefit Package Fee";
		   case "South Carolina":
			   return "43070 - Resident Benefit Package Fee";
		   case "Tulsa":
			   return "43070 - Resident Benefit Package Fee";
		   case "Ohio":
			   return "43070 - Resident Benefit Package Fee";
		   case "Maine":
			   return "43070 - Resident Benefit Package Fee";
		   case "OKC":
			   return "43070 - Resident Benefit Package Fee";
		   case "San Antonio":
			   return "43070 - Resident Benefit Package Fee";
		   case "Pennsylvania":
			   return "43070 - Resident Benefit Package Fee";
		   case "Colorado Springs":
			   return "43070 - Resident Benefit Package Fee";
		   case "Kansas City":
			   return "43070 - Resident Benefit Package Fee";
		   case "Lake Havasu":
			   return "43070 - Resident Benefit Package Fee";
		   case "New Mexico":
			   return "43070 - Resident Benefit Package Fee";
		   case "Chicago PFW":
			   return "43070 - Resident Benefit Package Fee";
		   case "Boise":
			   return "43070 - Resident Benefit Package Fee";
		   case "Spokane":
			   return "43070 - Resident Benefit Package Fee";
		   case "Utah":
			   return "43070 - Resident Benefit Package Fee";
		   case "Hawaii":
			   return "43070 - Resident Benefit Package Fee";
		   case "Columbia - St Louis":
			   return "43070 - Resident Benefit Package Fee";
		   case "Idaho Falls":
			   return "43070 - Resident Benefit Package Fee";
		   case "Arizona":
			   return "43070 - Resident Benefit Package Fee";
		   case "Maryland":
			   return "43070 - Resident Benefit Package Fee";
		   case "Virginia":
			   return "43070 - Resident Benefit Package Fee";
		   case "Washington DC":
			   return "43070 - Resident Benefit Package Fee";
		   case "New Jersey":
			   return "43070 - Resident Benefit Package Fee";
		   case "Montana":
			   return "43070 - Resident Benefit Package Fee";
		   }
		   return "";
	   }
	   public static String getResidentRenewalAdminFee(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Alabama":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "North Carolina":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Dallas/Fort Worth":
			   return "43040 - Resident Renewal Admin Fee";  
		   case "Arkansas":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Georgia":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Indiana":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Little Rock":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Tennessee":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "California":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "California PFW":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Houston":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Austin":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Chattanooga":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Chicago":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Savannah":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "South Carolina":
			   return "43040 - Resident Renewal Admin Fee"; 
		   case "Tulsa":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Ohio":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Maine":
			   return "43040 - Resident Renewal Admin Fee";
		   case "OKC":
			   return "43040 - Resident Renewal Admin Fee";
		   case "San Antonio":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Pennsylvania":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Colorado Springs":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Kansas City":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Lake Havasu":
			   return "43040 - Resident Renewal Admin Fee";
		   case "New Mexico":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Chicago PFW":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Boise":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Spokane":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Utah":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Hawaii":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Columbia - St Louis":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Idaho Falls":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Arizona":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Maryland":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Virginia":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Washington DC":
			   return "43040 - Resident Renewal Admin Fee";
		   case "New Jersey":
			   return "43040 - Resident Renewal Admin Fee";
		   case "Montana":
			   return "43040 - Resident Renewal Admin Fee";
		    
		   }
		   return "";
	   }
	   
	   public static String getMonthOnMonthRentChargeCode(String company) {
		    switch (company) {
		        case "Florida":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Alabama":
		            return "43050 - Month-to-Month Admin Fee";
		        case "North Carolina":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Dallas/Fort Worth":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Arkansas":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Indiana":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Little Rock":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Georgia":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Tennessee":
		            return "43050 - Month-to-Month Admin Fee";
		        case "California":
		            return "43050 - Month-to-Month Admin Fee";
		        case "California PFW":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Houston":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Austin":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Chattanooga":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Chicago":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Savannah":
		            return "43050 - Month-to-Month Admin Fee";
		        case "South Carolina":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Tulsa":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Ohio":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Maine":
		            return "43050 - Month-to-Month Admin Fee";
		        case "OKC":
		            return "43050 - Month-to-Month Admin Fee";
		        case "San Antonio":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Pennsylvania":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Colorado Springs":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Kansas City":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Lake Havasu":
		            return "43050 - Month-to-Month Admin Fee";
		        case "New Mexico":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Chicago PFW":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Boise":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Spokane":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Utah":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Hawaii":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Columbia - St Louis":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Idaho Falls":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Arizona":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Maryland":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Virginia":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Washington DC":
		            return "43050 - Month-to-Month Admin Fee";
		        case "New Jersey":
		            return "43050 - Month-to-Month Admin Fee";
		        case "Montana":
		            return "43050 - Month-to-Month Admin Fee";
		        default:
		            return "";
		    }
		}

	   public static String getEnrolledINRBPForPMUse(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "YES";
		   case "Alabama":
			   return "YES";
		   case "North Carolina":
			   return "YES";
		   case "Dallas/Fort Worth":
			   return "YES"; 
		   case "Arkansas":
			   return "YES"; 
		   case "Georgia":
			   return "YES"; 
		   case "Indiana":
			   return "YES"; 
		   case "Little Rock":
			   return "YES"; 
		   case "Tennessee":
			   return "YES"; 
		   case "California":
			   return "YES";
		   case "California PFW":
			   return "YES";
		   case "Houston":
			   return "YES";
		   case "Austin":
			   return "YES";
		   case "Chattanooga":
			   return "YES";
		   case "Chicago":
			   return "YES";
		   case "Savannah":
			   return "YES";
		   case "South Carolina":
			   return "YES";
		   case "Tulsa":
			   return "YES";
		   case "Ohio":
			   return "YES";
		   case "Maine":
			   return "YES";
		   case "OKC":
			   return "YES";
		   case "San Antonio":
			   return "YES";
		   case "Pennsylvania":
			   return "YES";
		   case "Colorado Springs":
			   return "YES";
		   case "Kansas City":
			   return "YES";
		   case "Lake Havasu":
			   return "YES";
		   case "New Mexico":
			   return "YES";
		   case "Chicago PFW":
			   return "YES";
		   case "Boise":
			   return "YES";
		   case "Spokane":
			   return "YES";
		   case "Utah":
			   return "YES";
		   case "Hawaii":
			   return "YES";
		   case "Columbia - St Louis":
			   return "YES";
		   case "Idaho Falls":
			   return "YES";
		   case "Arizona":
			   return "YES";
		   case "Maryland":
			   return "YES";
		   case "Virginia":
			   return "YES";
		   case "Washington DC":
			   return "YES";
		   case "New Jersey":
			   return "YES";
		   case "Montana":
			   return "YES";
		   }
		   return "";
	   }
	   public static String getRBPenrollmentCompleteForSNUseOnly(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "Yes";
		   case "Alabama":
			   return "Yes";
		   case "North Carolina":
			   return "Yes";
		   case "Dallas/Fort Worth":
			   return "Yes"; 
		   case "Arkansas":
			   return "Yes";
		   case "Georgia":
			   return "Yes";
		   case "Indiana":
			   return "Yes";
		   case "Little Rock":
			   return "Yes";
		   case "Tennessee":
			   return "Yes";
		   case "California":
			   return "Yes";
		   case "California PFW":
			   return "Yes";
		   case "Houston":
			   return "Yes";
		   case "Austin":
			   return "Yes";
		   case "Chattanooga":
			   return "Yes";
		   case "Chicago":
			   return "Yes";
		   case "Savannah":
			   return "Yes";
		   case "South Carolina":
			   return "Yes";
		   case "Tulsa":
			   return "Yes";
		   case "Ohio":
			   return "Yes";
		   case "Maine":
			   return "Yes";
		   case "OKC":
			   return "Yes";
		   case "San Antonio":
			   return "Yes";
		   case "Pennsylvania":
			   return "Yes";
		   case "Colorado Springs":
			   return "Yes";
		   case "Kansas City":
			   return "Yes";
		   case "Lake Havasu":
			   return "Yes";
		   case "New Mexico":
			   return "Yes";
		   case "Chicago PFW":
			   return "Yes";
		   case "Boise":
			   return "Yes";
		   case "Spokane":
			   return "Yes";
		   case "Utah":
			   return "Yes";
		   case "Hawaii":
			   return "Yes";
		   case "Columbia - St Louis":
			   return "Yes";
		   case "Idaho Falls":
			   return "Yes";
		   case "Arizona":
			   return "Yes";
		   case "Maryland":
			   return "Yes";
		   case "Virginia":
			   return "Yes";
		   case "Washington DC":
			   return "Yes";
		   case "New Jersey":
			   return "Yes";
		   case "Montana":
			   return "Yes";
		   }
		   return "";    
	   }
	   
	   public static String getEnrolledINRBPForPMUseNo(String company)
	   {
		   switch(company)
		   {
		   case "Florida":
			   return "NO";
		   case "Alabama":
			   return "NO";
		   case "North Carolina":
			   return "NO";
		   case "Dallas/Fort Worth":
			   return "NO"; 
		   case "Arkansas":
			   return "NO"; 
		   case "Georgia":
			   return "NO"; 
		   case "Indiana":
			   return "NO"; 
		   case "Little Rock":
			   return "NO"; 
		   case "Tennessee":
			   return "NO"; 
		   case "California":
			   return "NO";
		   case "California PFW":
			   return "NO";
		   case "Houston":
			   return "NO";
		   case "Austin":
			   return "NO";
		   case "Chattanooga":
			   return "NO";
		   case "Chicago":
			   return "NO";
		   case "Savannah":
			   return "NO";
		   case "South Carolina":
			   return "NO";
		   case "Tulsa":
			   return "NO";
		   case "Ohio":
			   return "NO";
		   case "Maine":
			   return "NO";
		   case "OKC":
			   return "NO";
		   case "San Antonio":
			   return "NO";
		   case "Pennsylvania":
			   return "NO";
		   case "Colorado Springs":
			   return "NO";
		   case "Kansas City":
			   return "NO";
		   case "Lake Havasu":
			   return "NO";
		   case "New Mexico":
			   return "NO";
		   case "Chicago PFW":
			   return "NO";
		   case "Boise":
			   return "NO";
		   case "Spokane":
			   return "NO";
		   case "Utah":
			   return "NO";
		   case "Hawaii":
			   return "NO";
		   case "Columbia - St Louis":
			   return "NO";
		   case "Idaho Falls":
			   return "NO";
		   case "Arizona":
			   return "NO";
		   case "Maryland":
			   return "NO";
		   case "Virginia":
			   return "NO";
		   case "Washington DC":
			   return "NO";
		   case "New Jersey":
			   return "NO";
		   case "Montana":
			   return "NO";
		   }
		   return "";
	   }
		   public static String getRBPenrollmentCompleteForSNUseOnlyNo(String company)
		   {
			   switch(company)
			   {
			   case "Florida":
				   return "No";
			   case "Alabama":
				   return "No";
			   case "North Carolina":
				   return "No";
			   case "Dallas/Fort Worth":
				   return "No"; 
			   case "Arkansas":
				   return "No";
			   case "Georgia":
				   return "No";
			   case "Indiana":
				   return "No";
			   case "Little Rock":
				   return "No";
			   case "Tennessee":
				   return "No";
			   case "California":
				   return "No";
			   case "California PFW":
				   return "No";
			   case "Houston":
				   return "No";
			   case "Austin":
				   return "No";
			   case "Chattanooga":
				   return "No";
			   case "Chicago":
				   return "No";
			   case "Savannah":
				   return "No";
			   case "South Carolina":
				   return "No";
			   case "Tulsa":
				   return "No";
			   case "Ohio":
				   return "No";
			   case "Maine":
				   return "No";
			   case "OKC":
				   return "No";
			   case "San Antonio":
				   return "No";
			   case "Pennsylvania":
				   return "No";
			   case "Colorado Springs":
				   return "No";
			   case "Kansas City":
				   return "No";
			   case "Lake Havasu":
				   return "No";
			   case "New Mexico":
				   return "No";
			   case "Chicago PFW":
				   return "No";
			   case "Boise":
				   return "No";
			   case "Spokane":
				   return "No";
			   case "Utah":
				   return "No";
			   case "Hawaii":
				   return "No";
			   case "Columbia - St Louis":
				   return "No";
			   case "Idaho Falls":
				   return "No";
			   case "Arizona":
				   return "No";
			   case "Maryland":
				   return "No";
			   case "Virginia":
				   return "No";
			   case "Washington DC":
				   return "No";
			   case "New Jersey":
				   return "No";
			   case "Montana":
				   return "No";
			   }
			   return "";    
		   }
	   
		   public static String getResidentUtilityBillChargeCode(String company)
		   {
			   switch(company)
			   {
			       case "Florida":
			           return "42030 - Utility Reimbursement";
			       case "Alabama":
			           return "42030 - Utility Reimbursement";
			       case "North Carolina":
			           return "42030 - Utility Reimbursement";
			       case "Dallas/Fort Worth":
			           return "42030 - Utility Reimbursement"; 
			       case "Arkansas":
			           return "42030 - Utility Reimbursement";
			       case "Georgia":
			           return "42030 - Utility Reimbursement";
			       case "Indiana":
			           return "42030 - Utility Reimbursement";
			       case "Little Rock":
			           return "42030 - Utility Reimbursement";
			       case "Tennessee":
			           return "42030 - Utility Reimbursement";
			       case "California":
			           return "42030 - Utility Reimbursement";
			       case "California PFW":
			           return "42030 - Utility Reimbursement";
			       case "Houston":
			           return "42030 - Utility Reimbursement";
			       case "Austin":
			           return "42030 - Utility Reimbursement";
			       case "Chattanooga":
			           return "42030 - Utility Reimbursement";
			       case "Chicago":
			           return "42030 - Utility Reimbursement";
			       case "Savannah":
			           return "42030 - Utility Reimbursement";
			       case "South Carolina":
			           return "42030 - Utility Reimbursement";
			       case "Tulsa":
			           return "42030 - Utility Reimbursement";
			       case "Ohio":
			           return "42030 - Utility Reimbursement";
			       case "Maine":
			           return "42030 - Utility Reimbursement";
			       case "OKC":
			           return "42030 - Utility Reimbursement";
			       case "San Antonio":
			           return "42030 - Utility Reimbursement";
			       case "Pennsylvania":
			           return "42030 - Utility Reimbursement";
			       case "Colorado Springs":
			           return "42030 - Utility Reimbursement";
			       case "Kansas City":
			           return "42030 - Utility Reimbursement";
			       case "Lake Havasu":
			           return "42030 - Utility Reimbursement";
			       case "New Mexico":
			           return "42030 - Utility Reimbursement";
			       case "Chicago PFW":
			           return "42030 - Utility Reimbursement";
			       case "Boise":
			           return "44040 - Resident Utility Bill Serv Rev";
			       case "Spokane":
			           return "42030 - Utility Reimbursement";
			       case "Utah":
			           return "42030 - Utility Reimbursement";
			       case "Hawaii":
			           return "42030 - Utility Reimbursement";
			       case "Columbia - St Louis":
			           return "42030 - Utility Reimbursement";
			       case "Idaho Falls":
			           return "42030 - Utility Reimbursement";
			       case "Arizona":
			           return "42030 - Utility Reimbursement";
			       case "Maryland":
			           return "42030 - Utility Reimbursement";
			       case "Virginia":
			           return "42030 - Utility Reimbursement";
			       case "Washington DC":
			           return "42030 - Utility Reimbursement";
			       case "New Jersey":
			           return "42030 - Utility Reimbursement";
			       case "Montana":
			           return "42030 - Utility Reimbursement";
			   }

			return "";
		   }
     

    	
}
