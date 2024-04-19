package mainPackage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class UpdateValues {
	public static String startDate;
	public static String endDate;
	public static String lastDayOfPreviousMonthUsingStartDate;
	public static String firstFullMonth;
	public static String secondFullMonth;
	public static String oldLeaseStartDate_ProrateRent;
	public static String oldLeaseEndDate_ProrateRent;
	public static String newLeaseEndDate_ProrateRent;
	public static String priorMonthlyRent;
	public static String prorateResidentBenefitPackage;
	public static String prorateMonthlyRent;
	
	public static String updated_monthlyRent_StartDate ;
	public static String updated_ResidentBenefitPackage_StartDate ;
	public static String updated_petRent_StartDate ;
	
	
	public static boolean checkValuesBeforeInsertion() throws Exception {
		
		if(RunnerClass.company.equals("Arizona"))
			getRentCodeForArizona();
		
		//Clear all values Configuration table first
		String query1 = "update automation.LeaseReneWalsAutoChargesConfiguration set Amount = Null, StartDate= Null, EndDate= Null, Flag = Null";
		GetDataFromSQL.updateTable(query1);
		startDate="";
		endDate="";
		lastDayOfPreviousMonthUsingStartDate="";
		firstFullMonth="";
		secondFullMonth="";
		oldLeaseStartDate_ProrateRent = "";
		oldLeaseEndDate_ProrateRent="";
		newLeaseEndDate_ProrateRent="";
		prorateResidentBenefitPackage="";
		prorateMonthlyRent="";
		updated_monthlyRent_StartDate ="";
		updated_ResidentBenefitPackage_StartDate ="";
		updated_petRent_StartDate="" ;
		priorMonthlyRent ="";
		
		try {
			startDate = GenericMethods.convertDate(ReadingLeaseAggrements.commencementDate);
			endDate = GenericMethods.convertDate(ReadingLeaseAggrements.expirationDate);
			lastDayOfPreviousMonthUsingStartDate = GenericMethods.lastDateOfTheMonth(GenericMethods.firstDayOfMonth(startDate, -1));
			GenericMethods.logger.info("Last day of Previous Month = " +lastDayOfPreviousMonthUsingStartDate);
			firstFullMonth = GenericMethods.firstDayOfMonth(startDate, 1);
			GenericMethods.logger.info("First Full Month = " +firstFullMonth);
			secondFullMonth = GenericMethods.firstDayOfMonth(startDate, 2);
			GenericMethods.logger.info("Second Full Month = " +secondFullMonth);
			
			try {
	        	boolean comparisonResult = GenericMethods.compareBeforeDates(startDate,endDate);

	        	if (comparisonResult == false) 
	        	{
	        		GenericMethods.logger.info("End Date is before Start Date");
	        	    // Handle the situation where the End Date is before the Start Date
	        	} 
	        	 else 
	        	{
	        		 GenericMethods.logger.info("End Date is after Start Date");
	        	    // Handle the situation where the End Date is after the Start Date
	        	}
	        } 
	        catch (Exception e)
	        {
	        	RunnerClass.failedReason = RunnerClass.failedReason + "," + "Issue in Comparing dates- Whether End Date is before Start Date";
		        
	        }
			try {
				RunnerClass.driver.navigate().refresh();
				DatabaseClass.intermittentPopUp(RunnerClass.driver);
				RunnerClass.driver.findElement(Locators.summaryEditButton).click();
				Thread.sleep(2000);
				RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.priorMonthlyRent)).build().perform();
				String priorAmount = RunnerClass.driver.findElement(Locators.priorMonthlyRent).getAttribute("value");
				priorMonthlyRent = priorAmount.replace("$", "").replace(",", "");
				System.out.println("Prior Montly Rent = "+priorMonthlyRent);
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease)).click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
			}
			catch(Exception e) {
				priorMonthlyRent = "Error";
				GenericMethods.logger.error("Issue in getting prior monthly rent");
			     RunnerClass.failedReason = RunnerClass.failedReason + "," + "Issue in getting prior monthly rent";
			     return false;
			}
		} catch (Exception e) {
			GenericMethods.logger.error("Issue in getting or Converting dates");
		     RunnerClass.failedReason = RunnerClass.failedReason + "," + "Issue in getting or Converting dates";
		     return false;
		}
		
		
		
		
		return true;
		
	}
	
	
	
	
	
	public static void updateDates() throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date leaseStartdate;
		Date ExecutionDate;
		leaseStartdate = dateFormat.parse(startDate);
		ExecutionDate = dateFormat.parse(ReadingLeaseAggrements.renewalExecutionDate);
		
	    if(startDate.split("/")[1].equals("1")||startDate.split("/")[1].equals("01"))
	    {
	    	//If there is already a monthly rent charge in Ledger,then auto charge start date is first full month
	    	if (MoveInCharges.dateCheckInLedgerForMonthlyRentStartDate == true || leaseStartdate.before(ExecutionDate)) 
	    	{
	    		updated_monthlyRent_StartDate = firstFullMonth;
				updated_ResidentBenefitPackage_StartDate = firstFullMonth;
				updated_petRent_StartDate =firstFullMonth;
	    	}
	    	else
	    	{
	    	updated_monthlyRent_StartDate = startDate;
			updated_ResidentBenefitPackage_StartDate = startDate;
			updated_petRent_StartDate = startDate;
	    	}
	    }
	    else
	    {
		updated_monthlyRent_StartDate = firstFullMonth;
		updated_ResidentBenefitPackage_StartDate = firstFullMonth;
		updated_petRent_StartDate = firstFullMonth;
		try {
			oldLeaseStartDate_ProrateRent = GenericMethods.firstDayOfMonth(UpdateValues.startDate, 0);
			oldLeaseEndDate_ProrateRent = GenericMethods.dateMinusOneDay(UpdateValues.startDate);
	    	newLeaseEndDate_ProrateRent = GenericMethods.lastDateOfTheMonth(UpdateValues.startDate);
	    	
			if(!ReadingLeaseAggrements.rbpAmount.equalsIgnoreCase("Error")) {
				prorateResidentBenefitPackage=ProrateAmountCalculator.prorateAmountOld(ReadingLeaseAggrements.rbpAmount);
			}
			if(!priorMonthlyRent.equalsIgnoreCase("") ||!priorMonthlyRent.isEmpty() ||!priorMonthlyRent.equalsIgnoreCase("Error")) {
				prorateMonthlyRent = ProrateAmountCalculator.prorateAmountOld(priorMonthlyRent);  
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
	    }
	
	    
	    
	}
	
	
	
	public static boolean addingValuesToTable()
	{
		try
		{
		
					
		String query ="";
		for(int i=1;i<=8;i++)
		{
			switch(i)
			{
			case 1:
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.monthlyRent+"',StartDate='"+updated_monthlyRent_StartDate+"',EndDate='',Flag = '' where ID=2";
				break;
			case 2:
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.rbpAmount+"',StartDate='"+updated_ResidentBenefitPackage_StartDate+"',EndDate='',Flag = '' where ID=4";
				break;
			case 3:
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getHVACAirFilterFeeChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.HVACAirFilterFee+"',StartDate='',EndDate='',Flag = '' where ID=5";
				break;
			case 4: 
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getPetRentChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.petRent+"',StartDate='"+updated_petRent_StartDate+"',EndDate='',Flag = '' where ID=6";
				break;
			case 5: 
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.rubsAmount+"',StartDate='"+updated_monthlyRent_StartDate+"',EndDate='',Flag = '' where ID=7";
			    break;
			case 6: 
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company)+"',Amount = '"+prorateResidentBenefitPackage+"',StartDate='"+oldLeaseStartDate_ProrateRent+"',EndDate='"+oldLeaseEndDate_ProrateRent+"',Flag = '' where ID=14";
			    break;
			case 7: 
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getProrateRentChargeCode(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.proratedRent+"',StartDate='"+startDate+"',EndDate='"+newLeaseEndDate_ProrateRent+"',Flag = '' where ID=15";
			    break;
			case 8: 
				query = query+"\n Update automation.LeaseReneWalsAutoChargesConfiguration Set ChargeCode = '"+AppConfig.getMonthlyRentChargeCode(RunnerClass.company)+"',Amount = '"+prorateMonthlyRent+"',StartDate='"+oldLeaseStartDate_ProrateRent+"',EndDate='"+oldLeaseEndDate_ProrateRent+"',Flag = '' where ID=16";
			    break;
			}
		 }
		GetDataFromSQL.updateTable(query);
		
		String query1 ="";
		//if(RunnerClass.portfolioType=="MCH")
		//{
			if(ReadingLeaseAggrements.petRentFlag==false ||(ReadingLeaseAggrements.petRentFlag==true&&(ReadingLeaseAggrements.petRent.equalsIgnoreCase("0.00") ||ReadingLeaseAggrements.petRent.equalsIgnoreCase("Error") || ReadingLeaseAggrements.petRent.equalsIgnoreCase("n/a")  )) )
			{
				if(ReadingLeaseAggrements.rbpFlag==true && !RunnerClass.company.equalsIgnoreCase("Hawaii")&& !RunnerClass.company.equalsIgnoreCase("Chicago PFW"))
				{
						query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (2,4)";
				}
				
			   else
			    {
					query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (2)";
				}
				
		     } 
			else
			{
			   if(ReadingLeaseAggrements.petRentFlag==true)
				{
				   if(ReadingLeaseAggrements.rbpFlag==true && !RunnerClass.company.equalsIgnoreCase("Hawaii")&& !RunnerClass.company.equalsIgnoreCase("Chicago PFW"))
					{
						query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (2,4,6)";
						}
				}
				else
				{
					query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (2,6)";
				}
					
			}
			GetDataFromSQL.updateTable(query1);
			
			if(((RunnerClass.company.equals("Boise")||RunnerClass.company.equals("Idaho Falls")||RunnerClass.company.equals("Utah"))&& ReadingLeaseAggrements.rubsFlag==true&& (!ReadingLeaseAggrements.rubsAmount.equalsIgnoreCase("Error") || !ReadingLeaseAggrements.rubsAmount.equalsIgnoreCase("n/a"))))
			{
				query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (7)";
				GetDataFromSQL.updateTable(query1);
			}
			if(!ReadingLeaseAggrements.proratedRent.equalsIgnoreCase("Error")|| !ReadingLeaseAggrements.rubsAmount.equalsIgnoreCase("n/a")) {
				if(ReadingLeaseAggrements.rbpFlag==true) {
					query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (14,15,16)";
				}
				else {
					query1 = "update automation.LeaseReneWalsAutoChargesConfiguration Set Flag = 1 where ID in (15,16)";
				}
				GetDataFromSQL.updateTable(query1);
			}
			
			try
			{
			String query2 =null;
			for(int i=1;i<=1;i++)
			{
				switch(i)
				{
				case 1:
					query2 = "Update automation.LeaseReneWalsMoveInChargesConfiguration Set ChargeCode = '"+AppConfig.getResidentRenewalAdminFee(RunnerClass.company)+"',Amount = '"+ReadingLeaseAggrements.leaseRenewalFee+"',StartDate='"+updated_monthlyRent_StartDate+"',EndDate='' where ID=1";
					break;
				}
			}
			GetDataFromSQL.updateTable(query2);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				GenericMethods.logger.error("Issue in adding values to MoveIn charges table");
				RunnerClass.failedReason =  RunnerClass.failedReason+","+"Internal Error - consolidating Move In charges";
				return false;
			}
			
			
			
		return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			GenericMethods.logger.error("Issue in adding values to Auto charges table");
			RunnerClass.failedReason =  RunnerClass.failedReason+","+"Internal Error - consolidating auto charges";
			return false;
		}
	}
	
	public static boolean priorMonthlyRent(String PriorRent) {
		try{
			String query =" ";
			
			query = "insert into automation.LeaseReneWalsPriorRentConfiguration (Abbreviation,Amount,Flag) values ('"+RunnerClass.buildingAbbreviation+"','"+PriorRent+"',1)";
			GetDataFromSQL.updateTable(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			GenericMethods.logger.error("Issue in adding values to prior monthly rent");
			RunnerClass.failedReason =  RunnerClass.failedReason+","+"Internal Error - prior monthly rent";
			return false;
		}
		return true;
		
	}
	
	public static void getRentCodeForArizona() throws Exception
	{
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		RunnerClass.driver.findElement(Locators.ledgerTab).click();
		Thread.sleep(2000);
		RunnerClass.actions.sendKeys(Keys.ESCAPE).build().perform();
		RunnerClass.driver.findElement(Locators.newCharge).click();
		Thread.sleep(2000);
		//Account code
		RunnerClass.driver.findElement(Locators.accountDropdown).click();
		List<WebElement> chargeCodes = RunnerClass.driver.findElements(Locators.chargeCodesList);
		for(int i=0;i<chargeCodes.size();i++)
		{
			String code = chargeCodes.get(i).getText();
			if(code.contains(RunnerClass.arizonaCityFromBuildingAddress))
			{
				RunnerClass.arizonaRentCode = code;
				RunnerClass.arizonaCodeAvailable = true;
				break;
				
			}
		}
		RunnerClass.driver.findElement(Locators.moveInChargeCancel).click();
		
	}

}
