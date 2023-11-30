package mainPackage;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ExtractData.DatabaseClass;
import PDFDataExtract.ReadingLeaseAggrements;


public class MoveInCharges {
	
	
	
	
	
	
	public static boolean dateCheckInLedgerForMonthlyRentStartDate = false;
	
	
	
	public static boolean verifyLedgerForMonhtlyRentStartDate(){
		
		try
		{
		UpdateValues.checkValuesBeforeInsertion();
		
			
		RunnerClass.driver.navigate().refresh();
		DatabaseClass.intermittentPopUp(RunnerClass.driver);
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		RunnerClass.driver.findElement(Locators.ledgerTab).click();
		
		List<WebElement> existingMoveInCharges_ChargeCode = RunnerClass.driver.findElements(Locators.moveInCharges_List);
		List<WebElement> existingMoveInCharges_Date = RunnerClass.driver.findElements(Locators.moveInCharge_List_Date);
		for(int i=0;i<existingMoveInCharges_ChargeCode.size();i++)
		{
			String chargeCode = existingMoveInCharges_ChargeCode.get(i).getText().trim();
			String date = existingMoveInCharges_Date.get(i).getText().trim();
			if (date.trim().equals(UpdateValues.startDate.trim()) && chargeCode.trim().equals(AppConfig.getMonthlyRentChargeCode(RunnerClass.company)))
			{
				dateCheckInLedgerForMonthlyRentStartDate = true;
				System.out.println("Data already exists in Ledger");
				break;
			}
		}
		
		DatabaseClass.intermittentPopUp(RunnerClass.driver);
		//Getting Rent Codes From Arizona
		UpdateValues.updateDates();
		UpdateValues.addingValuesToTable();
		MoveInCharges.checkAvailabilityandaddMoveInCharges();
	
		return true;
		}
		catch(Exception e)
		{
			return false;
		}
		
		
		
	}
	
	
	
	
	
	public static boolean checkAvailabilityandaddMoveInCharges() throws Exception
	{
		try
		{
		 //Get All Auto Charges from Table
		GetDataFromSQL.getMoveInCharges();
		Thread.sleep(2000);
		List<WebElement> existingMoveInCharges_ChargeCodes = RunnerClass.driver.findElements(Locators.moveInCharges_List);
		List<WebElement> existingMoveInCharges_Amount = RunnerClass.driver.findElements(Locators.moveInCharge_List_Amount);
		List<WebElement> existingMoveInCharges_Date = RunnerClass.driver.findElements(Locators.moveInCharge_List_Date);
		//RunnerClass.renewalExecutionDate = RunnerClass.convertDate(PDFReader.renewalExecutionDate);
		for(int i=0;i<RunnerClass.moveInCharges.length;i++)
		{
			
				boolean availabilityCheck = false;
				String chargeCode = RunnerClass.moveInCharges[i][0];
				String amount = RunnerClass.moveInCharges[i][1];
				String startDate = RunnerClass.moveInCharges[i][2];
				String endDate = RunnerClass.moveInCharges[i][3];
				String description = RunnerClass.moveInCharges[i][4];
				
				for(int k=0;k<existingMoveInCharges_ChargeCodes.size();k++)
				{
					String moveinautoChargeCodes = existingMoveInCharges_ChargeCodes.get(k).getText();
					String moveinautoChargeAmount = existingMoveInCharges_Amount.get(k).getText();
					String moveinautoChargeStartDate = existingMoveInCharges_Date.get(k).getText();
					
					    if (chargeCode.contains(moveinautoChargeCodes)
					            && !moveinautoChargeAmount.isEmpty()
					            && moveinautoChargeAmount.substring(1).equals(amount)
					            && moveinautoChargeStartDate.equals(ReadingLeaseAggrements.renewalExecutionDate))  
					    {
					        availabilityCheck = true;
					        System.out.println(description + " already available");
						    break;
						    
					}
				}
				//Add new Charge if it is not there
				if(availabilityCheck==false)
				{
					if(amount=="Error"||amount=="0.00")
					{
						System.out.println("Issue in Adding Move in charge - "+description);
						RunnerClass.failedReason =  RunnerClass.failedReason+","+"Issue in Adding Move in charge - "+description;
						System.out.println(description+ " is not updated");
						RunnerClass.statusID=1;
					}
					
					else
						addingMoveInCharge(chargeCode, amount, startDate, endDate, description);
				
				}
		}
		return true;
		}
		catch(Exception e)
		{
			RunnerClass.statusID=1;
			e.printStackTrace();
			System.out.println("Issue in Adding Move in charges");
			RunnerClass.failedReason =  RunnerClass.failedReason+","+"Issue in Adding Move in charges";
			RunnerClass.driver.navigate().refresh();
			RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			RunnerClass.driver.findElement(Locators.summaryTab).click();
			
			return false;
		}
	}
	
	
	public static boolean addingMoveInCharge(String accountCode, String amount, String startDate,String endDate,String description) throws Exception
	{
		
		try
		{
			RunnerClass.driver.navigate().refresh();
			RunnerClass.driver.findElement(Locators.newCharge).click();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			Thread.sleep(2000);
			//Account code
			Select AutoChargesDropdown = new Select(RunnerClass.driver.findElement(Locators.accountDropdown));
			AutoChargesDropdown.selectByVisibleText(accountCode);
			//Reference
			Thread.sleep(2000);
			RunnerClass.driver.findElement(Locators.referenceName).sendKeys(description);
			Thread.sleep(2000);
			//Amount
			RunnerClass.driver.findElement(Locators.moveInChargeAmount).click();
			RunnerClass.actions.sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).build().perform();
			Thread.sleep(2000);
			RunnerClass.driver.findElement(Locators.moveInChargeAmount).sendKeys(amount); 
			Thread.sleep(2000);
			//Start Date
			RunnerClass.driver.findElement(Locators.moveInChargeDate).clear();
			Thread.sleep(2000);
			RunnerClass.driver.findElement(Locators.moveInChargeDate).sendKeys(ReadingLeaseAggrements.renewalExecutionDate);
			//Save or Cancel button
			Thread.sleep(2000);
			if(AppConfig.saveButtonOnAndOff==false)
				RunnerClass.driver.findElement(Locators.moveInChargeCancel).click();
			else 
				RunnerClass.driver.findElement(Locators.moveInChargeSaveButton).click();
			Thread.sleep(2000);
			RunnerClass.driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(2));
		try
		{
			if(RunnerClass.driver.findElement(Locators.somethingWrongInSavingCharge).isDisplayed())
			{
				RunnerClass.driver.findElement(Locators.moveInChargeCancel).click();
			}
			
		}
		catch(Exception e)
		{}
		 RunnerClass.driver.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS);
	        RunnerClass.wait = new WebDriverWait(RunnerClass.driver, Duration.ofSeconds(100));
		return true;
		}
		catch(Exception e)
		{
			RunnerClass.statusID=1;
			RunnerClass.driver.navigate().refresh();
			RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			
			RunnerClass.driver.findElement(Locators.summaryTab).click();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			e.printStackTrace();
			System.out.println("Issue in adding Move in Charge"+description);
			RunnerClass.failedReason =  RunnerClass.failedReason+","+"Issue in adding Move in Charge - "+description;
			return false;	
		}
	}
	
	
	
}
