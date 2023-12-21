package mainPackage;

import java.util.List;

import org.openqa.selenium.WebElement;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class ReVerification {

	public static boolean verifyMoveInCharges() {
		RunnerClass.driver.navigate().refresh();
		List<WebElement> existingMoveInCharges_ChargeCodes = RunnerClass.driver.findElements(Locators.moveInCharges_List);
		List<WebElement> existingMoveInCharges_Amount = RunnerClass.driver.findElements(Locators.moveInCharge_List_Amount);
		List<WebElement> existingMoveInCharges_Date = RunnerClass.driver.findElements(Locators.moveInCharge_List_Date);
		
			String chargeCode = RunnerClass.moveInCharges[0][0];
			String amount = RunnerClass.moveInCharges[0][1];
			//String startDate = RunnerClass.moveInCharges[0][2];
			//String endDate = RunnerClass.moveInCharges[0][3];
			String description = RunnerClass.moveInCharges[0][4];

			boolean reVerificationCheck = false;
			for (int k = 0; k < existingMoveInCharges_ChargeCodes.size(); k++) {
				String moveinautoChargeCodes = existingMoveInCharges_ChargeCodes.get(k).getText();
				String moveinautoChargeAmount = existingMoveInCharges_Amount.get(k).getText();
				String moveinautoChargeStartDate = existingMoveInCharges_Date.get(k).getText();

				if (chargeCode.contains(moveinautoChargeCodes) && !moveinautoChargeAmount.isEmpty()
						&& moveinautoChargeAmount.substring(1).replaceAll(",", "").equals(amount)
						&& moveinautoChargeStartDate.equals(ReadingLeaseAggrements.renewalExecutionDate)) {
					reVerificationCheck = true;
					GenericMethods.logger.info(description + " already available in reverification");
					return true;
				}
			}
			if(reVerificationCheck == false) {
				RunnerClass.failedReason = RunnerClass.failedReason + "," + "Move in charges not added - "+ description;
				return false;
			}
			return false;
		}
	public static boolean verifyAutoCharges() {
		try {
			RunnerClass.driver.findElement(Locators.summaryTab).click();

			Thread.sleep(2000);

			DatabaseClass.intermittentPopUp(RunnerClass.driver);

			RunnerClass.driver.findElement(Locators.summaryEditButton).click();

			//GetDataFromSQL.getAutoCharges();
			for (int i = 0; i < RunnerClass.autoCharges.length; i++) {
				boolean availabilityCheck = false;
				String chargeCode = RunnerClass.autoCharges[i][0];
				String amount = RunnerClass.autoCharges[i][1];
				String startDate = RunnerClass.autoCharges[i][2];
				String endDate = RunnerClass.autoCharges[i][3];
				String description = RunnerClass.autoCharges[i][4];

				List<WebElement> existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
				for (int k = 0; k < existingAutoCharges.size(); k++) {
					existingAutoCharges = RunnerClass.driver.findElements(Locators.autoCharge_List);
					List<WebElement> existingAutoChargeAmounts = RunnerClass.driver
							.findElements(Locators.autoCharge_List_Amounts);
					List<WebElement> endDates = RunnerClass.driver.findElements(Locators.autoCharge_List_EndDates);
					List<WebElement> discription_List =RunnerClass.driver.findElements(Locators.discription_List);
					List<WebElement> startDates = RunnerClass.driver.findElements(Locators.startdateList);

					String autoChargeCode = existingAutoCharges.get(k).getText();
					String autoChargeAmount = existingAutoChargeAmounts.get(k).getText();
					String endDateAutoCharge = endDates.get(k).getText();
					String description_text = discription_List.get(k).getText();
					String startDatelist = startDates.get(k).getText();
					if (endDateAutoCharge.trim().isEmpty()) {
						if ((autoChargeCode.equalsIgnoreCase(AppConfig.getMonthlyRentChargeCode(RunnerClass.company))
								&& AutoCharges.monthlyRentEdit == true)
								|| (AppConfig.getHVACAirFilterFeeChargeCode(RunnerClass.company).contains(
										autoChargeCode.replaceAll("[.]", "")) && AutoCharges.HVACEdit == true)
								|| (AppConfig.getResidentBenefitsPackageChargeCode(RunnerClass.company)
										.contains(autoChargeCode.replaceAll("[.]", ""))
										&& AutoCharges.RBPEdit == true)
								|| (AppConfig.getPetRentChargeCode(RunnerClass.company).contains(
										autoChargeCode.replaceAll("[.]", "")) && AutoCharges.petRentEdit == true)
								|| (AppConfig.getResidentUtilityBillChargeCode(RunnerClass.company).contains(
										autoChargeCode.replaceAll("[.]", "")) && AutoCharges.RUBSEdit == true)) {
							if (chargeCode.contains(autoChargeCode.replaceAll(".", "")) && autoChargeAmount.substring(1)
									.replaceAll("[^0-9]", "").equals(amount.replaceAll("[^0-9]", ""))
									&& (startDate.equals(startDatelist))) {
								availabilityCheck = true;
								GenericMethods.logger.info(description + " - already available in Reverification");
								break;
							} else {
								if (k == existingAutoCharges.size() - 1) {
									GenericMethods.logger.error("Issue in adding Auto Charge - " + description_text);
									RunnerClass.failedReason = RunnerClass.failedReason + ","
											+ "Move in charges not added - " + description_text;
									return false;
								}
							}
						}
						else {
							if (i == (RunnerClass.autoCharges.length)-1) {
								GenericMethods.logger.info(description_text + " - is already exists and no changes done");
							}

						}
					}

				}
			}
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease))
						.click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
			Thread.sleep(2000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}
	
	
}
