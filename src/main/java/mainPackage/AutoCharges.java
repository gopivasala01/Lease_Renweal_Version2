package mainPackage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class AutoCharges {

	public static String lastDayOfTheStartDate3 = "";
	public static String previousMonthlyRent = "";
	public static List<WebElement> existingAutoCharges;
	public static List<WebElement> existingAutoChargeAmounts;
	public static List<WebElement> startDateList;
	public static List<WebElement> endDates;
	public static List<WebElement> discription_List;
	public static List<WebElement> editButtons;
	public static List<WebElement> delButtons;

	public static boolean clearExistingAutoCharges() throws Exception {

		try {
			RunnerClass.driver.navigate().refresh();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			RunnerClass.js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
			RunnerClass.driver.findElement(Locators.summaryTab).click();
			Thread.sleep(2000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);

			RunnerClass.driver.findElement(Locators.summaryEditButton).click();
			
			boolean monthlyRentChargeClosed = false;
			
			existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
			
			
			for (int k = 0; k < existingAutoCharges.size(); k++) {
				existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
				existingAutoChargeAmounts = RunnerClass.driver.findElements(Locators.autoCharge_List_Amounts);
				endDates = RunnerClass.driver.findElements(Locators.autoCharge_List_EndDates);
				discription_List = RunnerClass.driver.findElements(Locators.discription_List);
				startDateList = RunnerClass.driver.findElements(Locators.startdateList);
				editButtons = RunnerClass.driver.findElements(Locators.autoCharge_MonthlyRentEditButton);
				delButtons = RunnerClass.driver.findElements(Locators.autoCharge_MonthlyRentdeleteButton);
				
				String autoChargeCode = existingAutoCharges.get(k).getText();
				String autoChargeAmount = existingAutoChargeAmounts.get(k).getText();
				String endDateAutoCharge = endDates.get(k).getText();
				String startDatelist = startDateList.get(k).getText();
				System.out.println(autoChargeCode + "  ||  " + autoChargeAmount + "  ||  " + endDateAutoCharge);

				if (endDateAutoCharge.trim().isEmpty()) {
					if ((AppConfig.getMonthOnMonthRentChargeCode(RunnerClass.company)).contains(autoChargeCode.replaceAll("[.]", ""))) 
					{
						delButtons.get(k).click();
						Thread.sleep(2000);
						try {
							org.openqa.selenium.Alert alert = RunnerClass.driver.switchTo().alert();
							alert.accept();
						} catch (org.openqa.selenium.NoAlertPresentException e) {
							// Alert not present, continue with the rest of the code
						}
						continue;
					}
				}

				if (autoChargeCode.equals(AppConfig.getMonthlyRentChargeCode(RunnerClass.company))
						&& startDatelist.equals(UpdateValues.firstFullMonth)
						&& !autoChargeAmount.replaceAll("[^0-9]", "").equals(ReadingLeaseAggrements.monthlyRent.replaceAll("[^0-9]", ""))) 
				{
					editButtons.get(k).click();
					lastDayOfTheStartDate3 = GenericMethods.lastDateOfTheMonth(GenericMethods.firstDayOfMonth(UpdateValues.secondFullMonth, -1));
					WebElement endDateField = RunnerClass.driver.findElement(Locators.autoCharge_EndDate);
					endDateField.clear();
					endDateField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

					endDateField.sendKeys(lastDayOfTheStartDate3);
					Thread.sleep(2000);

					if (!AppConfig.saveButtonOnAndOff) {
						RunnerClass.driver.findElement(Locators.autoCharge_CancelButton).click();
					} else {
						RunnerClass.driver.findElement(Locators.autoCharge_SaveButton).click();
						Thread.sleep(3000);
						GenericMethods.handleAlerts();
						try {
							WebElement errorMessage = GenericMethods.findElementWithWait(By.xpath("//*[@id=\"errorMessages\"]/ul/li"));
							if (errorMessage != null && errorMessage.isDisplayed()) {
								RunnerClass.driver.findElement(By.xpath("//*[@id=\"editAutoChargeForm\"]/div[3]/input[2]")).click();
							}

							GenericMethods.handleAlerts();
						} catch (Exception e) {

						}
					}
					continue;
				}

				if (autoChargeCode.equals(AppConfig.getMonthlyRentChargeCode(RunnerClass.company))
						&& endDateAutoCharge.trim().isEmpty() == false && !autoChargeAmount.replaceAll("[^0-9]", "").equals(ReadingLeaseAggrements.monthlyRent.replaceAll("[^0-9]", ""))) 
				{
					previousMonthlyRent = autoChargeAmount;
					editButtons.get(k).click();
					editingExistingAutoCharge();
					monthlyRentChargeClosed = true;
					saveAnAutoCharge();
					continue;
				}
				if (AppConfig.getHVACAirFilterFeeChargeCode(RunnerClass.company).contains(autoChargeCode.replaceAll("[.]", ""))) 
				{
					editButtons.get(k).click();
					editingExistingAutoCharge();
					saveAnAutoCharge();
					continue;
				}
				if ((AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company).contains(autoChargeCode.replaceAll("[.]", "")) && ReadingLeaseAggrements.rbpFlag
						&& !autoChargeAmount.replaceAll("[^0-9]", "").equals(ReadingLeaseAggrements.rbpAmount.replaceAll("[^0-9]", "")))) 
				{
					editButtons.get(k).click();
					editingExistingAutoCharge();
					saveAnAutoCharge();
					continue;
				}

				if (AppConfig.getPetRentChargeCode(RunnerClass.company).contains(autoChargeCode.replaceAll("[.]", ""))
						&& (!autoChargeAmount.replaceAll("[^0-9]", "").equals(ReadingLeaseAggrements.petRent.replaceAll("[^0-9]", ""))
								|| ReadingLeaseAggrements.petRent != "")) 
				{
					editButtons.get(k).click();
					editingExistingAutoCharge();
					saveAnAutoCharge();
					continue;
					
				}
				if (AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company).contains(autoChargeCode.replaceAll("[.]", ""))
						&& (!autoChargeAmount.replaceAll("[^0-9]", "").equals(ReadingLeaseAggrements.rubsAmount.replaceAll("[^0-9]", ""))
								|| ReadingLeaseAggrements.rubsAmount != "")) 
				{
					editButtons.get(k).click();
					editingExistingAutoCharge();
					saveAnAutoCharge();
					continue;
				}

			}

			return true;
		} catch (Exception e) {
			RunnerClass.statusID = 1;
			e.printStackTrace();
			RunnerClass.failedReason = RunnerClass.failedReason + "," + "Something went wrong in clearing previous auto charges";
			System.out.println("Something went wrong in clearing previous auto charges");
			RunnerClass.driver.navigate().refresh();
			RunnerClass.js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
			return false;
		}
	}
	
	
	   public static void editingExistingAutoCharge() throws Exception {
	    	
	    	if (MoveInCharges.dateCheckInLedgerForMonthlyRentStartDate || 
	    		    !(UpdateValues.startDate.split("/")[1].equals("1") || UpdateValues.startDate.split("/")[1].equals("01"))) {
	    		String lastDayOfTheStartDate2 = GenericMethods.lastDateOfTheMonth(GenericMethods.firstDayOfMonth(UpdateValues.firstFullMonth, -1));
	    		WebElement endDateField = RunnerClass.driver.findElement(Locators.autoCharge_EndDate);
	    		endDateField.clear();
	    		endDateField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	        
	    		endDateField.sendKeys(lastDayOfTheStartDate2);
	    		Thread.sleep(2000);

	    	}
	    	else {
	    		WebElement endDateField = RunnerClass.driver.findElement(Locators.autoCharge_EndDate);
	            endDateField.clear();
	            endDateField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	            
	            endDateField.sendKeys(UpdateValues.lastDayOfPreviousMonthUsingStartDate);
	            Thread.sleep(2000);
	    	}
	        if (!AppConfig.saveButtonOnAndOff) 
	        {
	            RunnerClass.driver.findElement(Locators.autoCharge_CancelButton).click();
	        } else 
	        {
	            RunnerClass.driver.findElement(Locators.autoCharge_SaveButton).click();
	            Thread.sleep(3000);

	            GenericMethods.handleAlerts();

	            try {
	            WebElement errorMessage = GenericMethods.findElementWithWait(By.xpath("//*[@id=\"errorMessages\"]/ul/li"));
	            if (errorMessage != null && errorMessage.isDisplayed()) {
	                RunnerClass.driver.findElement(By.xpath("//*[@id=\"editAutoChargeForm\"]/div[3]/input[2]")).click();
	            }

	            GenericMethods.handleAlerts();
	        }
	            catch (Exception e){
	            	
	            }

	        }  Thread.sleep(2000);
	    }
	
		public static void saveAnAutoCharge() throws Exception
		{
			  RunnerClass.js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			  if(AppConfig.saveButtonOnAndOff==true) {
				  RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease)).click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
			  }
				
			  else {
				  RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease)).click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
			  }
	          Thread.sleep(2000);
	          DatabaseClass.intermittentPopUp(RunnerClass.driver);
	          RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	          Thread.sleep(2000);
	          
	          RunnerClass.driver.findElement(Locators.summaryEditButton).click();
	          DatabaseClass.intermittentPopUp(RunnerClass.driver);
				
	          RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.newAutoCharge)).build().perform();
	          
		}
	   
	   
}
