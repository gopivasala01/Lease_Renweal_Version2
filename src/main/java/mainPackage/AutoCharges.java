package mainPackage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class AutoCharges {

	public static String lastDayOfTheStartDate3 = "";
	public static String previousMonthlyRent = "";
	public static List<WebElement> existingAutoCharges;
	public static List<WebElement> existingAutoChargeAmounts;
	public static List<WebElement> startDates;
	public static List<WebElement> endDates;
	public static List<WebElement> discription_List;
	public static List<WebElement> editButtons;
	public static List<WebElement> delButtons;
	public static boolean monthlyRentEdit = false;
	public static boolean HVACEdit = false;
	public static boolean RBPEdit = false;
	public static boolean petRentEdit = false;
	public static boolean RUBSEdit = false;

	public static boolean clearExistingAutoCharges() throws Exception {

		try {
			RunnerClass.driver.navigate().refresh();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			RunnerClass.js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
			RunnerClass.driver.findElement(Locators.summaryTab).click();
			Thread.sleep(2000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);

			RunnerClass.driver.findElement(Locators.summaryEditButton).click();

			existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);

			for (int k = 0; k < existingAutoCharges.size(); k++) {
				existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
				existingAutoChargeAmounts = RunnerClass.driver.findElements(Locators.autoCharge_List_Amounts);
				endDates = RunnerClass.driver.findElements(Locators.autoCharge_List_EndDates);
				discription_List = RunnerClass.driver.findElements(Locators.discription_List);
				startDates = RunnerClass.driver.findElements(Locators.startdateList);
				editButtons = RunnerClass.driver.findElements(Locators.autoCharge_MonthlyRentEditButton);
				delButtons = RunnerClass.driver.findElements(Locators.autoCharge_MonthlyRentdeleteButton);

				String autoChargeCode = existingAutoCharges.get(k).getText();
				String autoChargeAmount = existingAutoChargeAmounts.get(k).getText();
				String endDateAutoCharge = endDates.get(k).getText();
				String startDatelist = startDates.get(k).getText();
				GenericMethods.logger.info(autoChargeCode + "  ||  " + autoChargeAmount + "  ||  " + endDateAutoCharge);

				if (endDateAutoCharge.trim().isEmpty()) {
					if ((AppConfig.getMonthOnMonthRentChargeCode(RunnerClass.company))
							.contains(autoChargeCode.replaceAll("[.]", ""))) {
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

					if (autoChargeCode.equals(AppConfig.getMonthlyRentChargeCode(RunnerClass.company))
							&& startDatelist.equals(UpdateValues.firstFullMonth)
							&& !autoChargeAmount.replaceAll("[^0-9]", "")
									.equals(ReadingLeaseAggrements.monthlyRent.replaceAll("[^0-9]", ""))) {
						editButtons.get(k).click();
						lastDayOfTheStartDate3 = GenericMethods
								.lastDateOfTheMonth(GenericMethods.firstDayOfMonth(UpdateValues.secondFullMonth, -1));
						GenericMethods.logger.info("Last Day of First Full Month= "+lastDayOfTheStartDate3);
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
								WebElement errorMessage = GenericMethods
										.findElementWithWait(By.xpath("//*[@id=\"errorMessages\"]/ul/li"));
								if (errorMessage != null && errorMessage.isDisplayed()) {
									RunnerClass.driver
											.findElement(By.xpath("//*[@id=\"editAutoChargeForm\"]/div[3]/input[2]"))
											.click();
								}

								GenericMethods.handleAlerts();
							} catch (Exception e) {

							}
						}
						continue;
					}

					if (autoChargeCode.equals(AppConfig.getMonthlyRentChargeCode(RunnerClass.company))
							&& !autoChargeAmount.replaceAll("[^0-9]", "")
									.equals(ReadingLeaseAggrements.monthlyRent.replaceAll("[^0-9]", ""))) {
						monthlyRentEdit = true;
						previousMonthlyRent = autoChargeAmount;
						GenericMethods.logger.info("Previous Month rent= " +previousMonthlyRent);
						editButtons.get(k).click();
						editingExistingAutoCharge();
						saveAnAutoCharge();
						continue;
					}
					if (AppConfig.getHVACAirFilterFeeChargeCode(RunnerClass.company)
							.contains(autoChargeCode.replaceAll("[.]", ""))) {
						HVACEdit = true;
						editButtons.get(k).click();
						editingExistingAutoCharge();
						saveAnAutoCharge();
						continue;
					}
					if ((AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company)
							.contains(autoChargeCode.replaceAll("[.]", "")) && ReadingLeaseAggrements.rbpFlag == true
							&& !autoChargeAmount.replaceAll("[^0-9]", "")
									.equals(ReadingLeaseAggrements.rbpAmount.replaceAll("[^0-9]", "")))) {
						RBPEdit = true;
						editButtons.get(k).click();
						editingExistingAutoCharge();
						saveAnAutoCharge();
						continue;
					}

					if (AppConfig.getPetRentChargeCode(RunnerClass.company)
							.contains(autoChargeCode.replaceAll("[.]", ""))
							&& (!autoChargeAmount.replaceAll("[^0-9]", "")
									.equals(ReadingLeaseAggrements.petRent.replaceAll("[^0-9]", "")))) {
						petRentEdit = true;
						editButtons.get(k).click();
						editingExistingAutoCharge();
						saveAnAutoCharge();
						continue;

					}
					if (AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company)
							.contains(autoChargeCode.replaceAll("[.]", ""))
							&& (!autoChargeAmount.replaceAll("[^0-9]", "")
									.equals(ReadingLeaseAggrements.rubsAmount.replaceAll("[^0-9]", "")))) {
						RUBSEdit = true;
						editButtons.get(k).click();
						editingExistingAutoCharge();
						saveAnAutoCharge();
						continue;
					}
				}

			}

			return true;
		} catch (Exception e) {
			RunnerClass.statusID = 1;
			e.printStackTrace();
			RunnerClass.failedReason = RunnerClass.failedReason + ","
					+ "Something went wrong in clearing previous auto charges";
			GenericMethods.logger.error("Something went wrong in clearing previous auto charges");
			RunnerClass.driver.navigate().refresh();
			RunnerClass.js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
			return false;
		}
	}

	public static void editingExistingAutoCharge() throws Exception {

		if (MoveInCharges.dateCheckInLedgerForMonthlyRentStartDate == true|| !(UpdateValues.startDate.split("/")[1].equals("1")
				|| UpdateValues.startDate.split("/")[1].equals("01"))) {
			String lastDayOfTheStartDate2 = GenericMethods
					.lastDateOfTheMonth(GenericMethods.firstDayOfMonth(UpdateValues.firstFullMonth, -1));
			WebElement endDateField = RunnerClass.driver.findElement(Locators.autoCharge_EndDate);
			endDateField.clear();
			endDateField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

			endDateField.sendKeys(lastDayOfTheStartDate2);
			Thread.sleep(2000);

		} else {
			WebElement endDateField = RunnerClass.driver.findElement(Locators.autoCharge_EndDate);
			endDateField.clear();
			endDateField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

			endDateField.sendKeys(UpdateValues.lastDayOfPreviousMonthUsingStartDate);
			Thread.sleep(2000);
		}
		if (!AppConfig.saveButtonOnAndOff) {
			RunnerClass.driver.findElement(Locators.autoCharge_CancelButton).click();
		} else {
			RunnerClass.driver.findElement(Locators.autoCharge_SaveButton).click();
			Thread.sleep(3000);

			GenericMethods.handleAlerts();

			try {
				WebElement errorMessage = GenericMethods
						.findElementWithWait(By.xpath("//*[@id=\"errorMessages\"]/ul/li"));
				if (errorMessage != null && errorMessage.isDisplayed()) {
					RunnerClass.driver.findElement(By.xpath("//*[@id=\"editAutoChargeForm\"]/div[3]/input[2]")).click();
				}

				GenericMethods.handleAlerts();
			} catch (Exception e) {

			}

		}
		Thread.sleep(2000);
	}

	public static void saveAnAutoCharge() throws Exception {
		RunnerClass.js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		if (AppConfig.saveButtonOnAndOff == true) {
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease))
					.click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
		}

		else {
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease))
					.click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
		}
		Thread.sleep(2000);
		DatabaseClass.intermittentPopUp(RunnerClass.driver);
		RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);

		RunnerClass.driver.findElement(Locators.summaryEditButton).click();
		DatabaseClass.intermittentPopUp(RunnerClass.driver);

		RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.newAutoCharge)).build().perform();

	}

	public static boolean addingNewAutoCharges() throws Exception {
		try {

			Thread.sleep(2000);
			GetDataFromSQL.getAutoCharges();
			existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
			for (int i = 0; i < RunnerClass.autoCharges.length; i++) {
				boolean availabilityCheck = false;
				String chargeCode = RunnerClass.autoCharges[i][0];
				String amount = RunnerClass.autoCharges[i][1];
				String startDate = RunnerClass.autoCharges[i][2];
				String endDate = RunnerClass.autoCharges[i][3];
				String description = RunnerClass.autoCharges[i][4];

				for (int k = 0; k < existingAutoCharges.size(); k++) {
					existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
					existingAutoChargeAmounts = RunnerClass.driver.findElements(Locators.autoCharge_List_Amounts);
					startDates = RunnerClass.driver.findElements(Locators.autoCharge_List_startDates);
					endDates = RunnerClass.driver.findElements(Locators.autoCharge_List_EndDates);

					String autoChargeCodes = existingAutoCharges.get(k).getText();
					String autoChargeAmount = existingAutoChargeAmounts.get(k).getText();
					String autoChargeStartDate = startDates.get(k).getText();
					String autoChargeEndDate = endDates.get(k).getText();
					if (chargeCode.contains(autoChargeCodes.replaceAll(".", ""))
							&& autoChargeAmount.substring(1).replaceAll("[^0-9]", "")
									.equals(amount.replaceAll("[^0-9]", ""))
							&& (startDate.equals(autoChargeStartDate) || autoChargeEndDate.trim().equals(""))) {
						availabilityCheck = true;
						GenericMethods.logger.info(description + " already available");
						break;
					}
					if (autoChargeAmount.equalsIgnoreCase("Error") || autoChargeAmount == "0.00" || autoChargeAmount.equalsIgnoreCase("n/a")) {
						GenericMethods.logger.error(description + " Amount Error");
						RunnerClass.failedReason = RunnerClass.failedReason + "," + " issue in adding Auto Charge - "
								+ description;
						RunnerClass.statusID = 1;
						break;

					}
				} 
				if (availabilityCheck == false) {
					if (amount.equalsIgnoreCase("Error") || amount == "0" || amount == "0.00" || amount.equalsIgnoreCase("n/a")) {
						GenericMethods.logger.error(" issue in adding Auto Charge - " + description);
						RunnerClass.failedReason = RunnerClass.failedReason + "," + " issue in adding Auto Charge - "
								+ description;
						RunnerClass.statusID = 1;
						break;
					} else
						addingAnAutoCharge(chargeCode, amount, startDate, endDate, description);
				}

			}
			if (AppConfig.saveButtonOnAndOff == true)
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease))
						.click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
			else
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease))
						.click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
			Thread.sleep(2000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			if(ReVerification.verifyAutoCharges()==true) {
				return true;
			}
			else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			RunnerClass.failedReason = RunnerClass.failedReason + "," + "Something went wrong in adding auto charges";
			GenericMethods.logger.error("Something went wrong in adding auto charges");
			RunnerClass.driver.navigate().refresh();
			return false;
		}

	}

	public static boolean addingAnAutoCharge(String accountCode, String amount, String startDate, String endDate,
			String description) throws Exception {
		try {
			RunnerClass.driver.findElement(Locators.newAutoCharge).click();

			// Charge Code
			Select autoChargesDropdown = new Select(RunnerClass.driver.findElement(Locators.accountDropdown));
			autoChargesDropdown.selectByVisibleText(accountCode); //

			// Start Date
			RunnerClass.driver.findElement(Locators.autoCharge_StartDate).clear();
			Thread.sleep(500);
			RunnerClass.driver.findElement(Locators.autoCharge_StartDate).sendKeys(startDate);

			// click this to hide calendar UI
			RunnerClass.driver.findElement(Locators.autoCharge_refField).click();
			// Amount
			RunnerClass.driver.findElement(Locators.autoCharge_Amount).click();
			RunnerClass.actions.sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE)
					.sendKeys(Keys.BACK_SPACE).sendKeys(Keys.BACK_SPACE).build().perform();
			RunnerClass.driver.findElement(Locators.autoCharge_Amount).sendKeys(amount);
			Thread.sleep(500);

			// Description
			RunnerClass.driver.findElement(Locators.autoCharge_Description).sendKeys(description);

			// Save or Cancel
			if (AppConfig.saveButtonOnAndOff == false)
				RunnerClass.driver.findElement(Locators.autoCharge_CancelButton).click();
			else
				RunnerClass.driver.findElement(Locators.autoCharge_SaveButton).click();
			Thread.sleep(2000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
		} catch (Exception e) {
			try {
				e.printStackTrace();
				RunnerClass.statusID = 1;
				GenericMethods.logger.error("Issue in adding Move in Charge" + description);
				RunnerClass.failedReason = RunnerClass.failedReason + "," + "Issue in adding Auto Charge - "
						+ description;
				RunnerClass.driver.findElement(Locators.autoCharge_CancelButton).click();
				return false;
			} catch (Exception e2) {
				RunnerClass.driver.navigate().refresh();
			}
		}
		return true;
	}

}
