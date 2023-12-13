package mainPackage;

import java.util.List;

import org.openqa.selenium.WebElement;

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
				RunnerClass.failedReason = RunnerClass.failedReason + "," + "Move in charges not added "+ description;
				return false;
			}
			return false;
		}
}
