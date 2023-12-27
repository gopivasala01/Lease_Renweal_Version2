package mainPackage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;

import ExtractData.DatabaseClass;
import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;
import io.github.bonigarcia.wdm.WebDriverManager;

public class OtherInformation {
	public static String enrolledInRBPForPMUse = "";
	public static String enrolledInRBPForPMUseNo = "";
	public static String RBPenrollmentCompleteForSNUseOnly = "";
	public static String RBPenrollmentCompleteForSNUseOnlyNo = "";
	public static String renewalStatus = "";
	public static String reneWalFollowupNotes = "";
	public static String renewalExecutionDate = "";
	public static String currentMonthlyRent = "";
	public static String priorMonthlyRent = "";
	public static String renewalCoordinatorName = "";
	public static String petRentAmount = "";

	// Related Activities - Lease Renewal
	public static String newStartDate = "";
	public static String newEndDate = "";
	public static String renewalOnDate = "";

	public static boolean addingOtherInformation() throws Exception {
		try {

			String leaseStatus = RunnerClass.driver.findElement(Locators.status).getText();

			RunnerClass.driver.navigate().refresh();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			RunnerClass.driver.findElement(Locators.summaryEditButton).click();
			Thread.sleep(2000);
			RunnerClass.js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

			try {
				// Clear all existing values in variables
				clearExistingVariableValues();
				// Adding values to all variables

				enrolledInRBPForPMUse = AppConfig.getEnrolledINRBPForPMUse(RunnerClass.company);
				enrolledInRBPForPMUseNo = AppConfig.getEnrolledINRBPForPMUseNo(RunnerClass.company);
				RBPenrollmentCompleteForSNUseOnly = AppConfig.getRBPenrollmentCompleteForSNUseOnly(RunnerClass.company);
				RBPenrollmentCompleteForSNUseOnlyNo = AppConfig.getRBPenrollmentCompleteForSNUseOnlyNo(RunnerClass.company);
				renewalStatus = "RW-4a%3A+CHARGE+RENEWAL+FEE+-+ANNUAL";
				reneWalFollowupNotes = "Full Lease Executed From " + ReadingLeaseAggrements.commencementDate + " - " + ReadingLeaseAggrements.expirationDate
						+ " - HRG Automation - "; // Need to calculate Month's difference between StartDate and EndDate
				renewalExecutionDate = ReadingLeaseAggrements.renewalExecutionDate;
				currentMonthlyRent = ReadingLeaseAggrements.monthlyRent;
				priorMonthlyRent = AutoCharges.previousMonthlyRent;
				renewalCoordinatorName = "HRG - Automation";
				newStartDate = UpdateValues.startDate;
				newEndDate = UpdateValues.endDate;
				renewalOnDate = GenericMethods.getCurrentDate();
				petRentAmount = ReadingLeaseAggrements.petRent;

				try {
					if (!leaseStatus.equals("Active")) {

						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.activeStatus)).build()
								.perform();
						new Select(RunnerClass.driver.findElement(Locators.activeStatus)).selectByVisibleText("Active");
						RunnerClass.js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

					}
				} 
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
				}

				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.baseRent)).click().build()
							.perform();
					RunnerClass.driver.findElement(Locators.baseRent).click();
					RunnerClass.driver.findElement(Locators.baseRent)
							.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
					RunnerClass.driver.findElement(Locators.baseRent).sendKeys(currentMonthlyRent);
					Thread.sleep(3000);
				} 
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Current Monthly Rent";
					GenericMethods.logger.error("Issue - Other information - Base Rent");
				}

				if (ReadingLeaseAggrements.rbpFlag == true) { // If
																					// residentBenefitsPackageAvailabilityCheck
																					// is true,
																					// Select the option for "enrolled
																					// in RBP for PM use"
					try {
						RunnerClass.actions
								.moveToElement(RunnerClass.driver.findElement(Locators.enrolledInRBPForPMUse)).build()
								.perform();
						Select renewalStatusDropdown = new Select(
								RunnerClass.driver.findElement(Locators.enrolledInRBPForPMUse));
						renewalStatusDropdown.selectByValue(enrolledInRBPForPMUse);
					} 
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch (Exception e) {
						RunnerClass.statusID = 1;
						e.printStackTrace();
						RunnerClass.failedReason = RunnerClass.failedReason + ","
								+ "Other information - Enrolled In RBP For PM Use";
						GenericMethods.logger.error("Issue - Other information - Enrolled In RBP For PM Use");
					}
				}

				else { // If residentBenefitsPackageAvailabilityCheck is false,
						// Select the option for "not enrolled in RBP for PM use"
					try {
						RunnerClass.actions
								.moveToElement(RunnerClass.driver.findElement(Locators.enrolledInRBPForPMUseNo)).build()
								.perform();
						Select renewalStatusDropdown = new Select(
								RunnerClass.driver.findElement(Locators.enrolledInRBPForPMUseNo));
						renewalStatusDropdown.selectByValue(enrolledInRBPForPMUseNo);
					}
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch (Exception e) {
						RunnerClass.statusID = 1;
						e.printStackTrace();
						RunnerClass.failedReason = RunnerClass.failedReason + ","
								+ "Other information - Enrolled In RBP For PM Use (No)";
						GenericMethods.logger.error("Issue - Other information - Enrolled In RBP For PM Use (No)");
					}
					Thread.sleep(2000);
				}

				if (ReadingLeaseAggrements.rbpFlag == true) { 
					try {
						RunnerClass.actions
								.moveToElement(
										RunnerClass.driver.findElement(Locators.RBPEnrollmentCompleteForSNUseOnly))
								.build().perform();
						Select renewalStatusDropdown = new Select(
								RunnerClass.driver.findElement(Locators.RBPEnrollmentCompleteForSNUseOnly));
						renewalStatusDropdown.selectByValue(RBPenrollmentCompleteForSNUseOnly);
					} 
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch (Exception e) {
						RunnerClass.statusID = 1;
						e.printStackTrace();
						RunnerClass.failedReason = RunnerClass.failedReason + ","
								+ "Other information - RBP Enrollment Complete For SN Use Only";
						GenericMethods.logger.error("Issue - Other information - RBP Enrollment Complete For SN Use Only");
					}
				} else { // If residentBenefitsPackageAvailabilityCheck is false,
							// Select the option for "RBP enrollment not complete for SN use only"
					try {
						RunnerClass.actions
								.moveToElement(
										RunnerClass.driver.findElement(Locators.RBPEnrollmentCompleteForSNUseOnlyNo))
								.build().perform();
						Select renewalStatusDropdown = new Select(
								RunnerClass.driver.findElement(Locators.RBPEnrollmentCompleteForSNUseOnlyNo));
						renewalStatusDropdown.selectByValue(RBPenrollmentCompleteForSNUseOnlyNo);
					} 
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
					}
					catch (Exception e) {
						RunnerClass.statusID = 1;
						e.printStackTrace();
						RunnerClass.failedReason = RunnerClass.failedReason + ","
								+ "Other information - RBP Enrollment Complete For SN Use Only (No)";
						GenericMethods.logger.error("Issue - Other information - RBP Enrollment Complete For SN Use Only (No)");
					}
				}
				Thread.sleep(2000);

				// Renewal Status
				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.renewalStatusDropdown))
							.build().perform();
					Select renewalStatusDropdown = new Select(
							RunnerClass.driver.findElement(Locators.renewalStatusDropdown));
					renewalStatusDropdown.selectByValue(renewalStatus);
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + "," + "Other information - Renewal Status";
					GenericMethods.logger.error("Issue - Other information - Renewal Status");
				}

				// Renewal Follow - up Notes
				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.renewalFollowUpNotes))
							.build().perform();
					RunnerClass.driver.findElement(Locators.renewalFollowUpNotes).click();
					RunnerClass.driver.findElement(Locators.renewalFollowUpNotes).sendKeys(Keys.chord(Keys.END));
					RunnerClass.driver.findElement(Locators.renewalFollowUpNotes).sendKeys(reneWalFollowupNotes);
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Renewal Follow up Notes";
					GenericMethods.logger.error("Issue - Other information - Renewal Follow up Notes");
				}

				// Renewal Execution Date
				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.renewalExecutionDate))
							.build().perform();
					RunnerClass.driver.findElement(Locators.renewalExecutionDate).click();
					RunnerClass.driver.findElement(Locators.renewalExecutionDate)
							.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
					RunnerClass.driver.findElement(Locators.renewalExecutionDate).sendKeys(renewalExecutionDate);
					Thread.sleep(2000);
				} 
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Renewal Execution Date";
					GenericMethods.logger.error("Issue - Other information - Renewal Renewal Execution Date");
				}

				// Current Monthly Rent
				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.currentMonthlyRent))
							.click().build().perform();
					RunnerClass.driver.findElement(Locators.currentMonthlyRent).click();
					RunnerClass.driver.findElement(Locators.currentMonthlyRent)
							.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
					RunnerClass.driver.findElement(Locators.currentMonthlyRent).sendKeys(currentMonthlyRent);
					Thread.sleep(3000);
				} 
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
			}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Current Monthly Rent";
					GenericMethods.logger.error("Issue - Other information - Current Monthly Rent");
				}

				// Prior Monthly Rent
				try {
					if(!AutoCharges.previousMonthlyRent.isEmpty() || !AutoCharges.previousMonthlyRent.equalsIgnoreCase("") ) {
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.priorMonthlyRent)).build()
								.perform();
						RunnerClass.driver.findElement(Locators.priorMonthlyRent).click();
						RunnerClass.driver.findElement(Locators.priorMonthlyRent)
								.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
						RunnerClass.driver.findElement(Locators.priorMonthlyRent).sendKeys(priorMonthlyRent);
						Thread.sleep(3000);
					} 
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
				}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Prior Monthly Rent";
					GenericMethods.logger.error("Issue - Other information - Prior Monthly Rent");
				}

				// Renewal Coordinator Name
				try {
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.renewalCoordinatorName))
							.build().perform();
					RunnerClass.driver.findElement(Locators.renewalCoordinatorName).click();
					RunnerClass.driver.findElement(Locators.renewalCoordinatorName)
							.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
					RunnerClass.driver.findElement(Locators.renewalCoordinatorName).sendKeys(renewalCoordinatorName);
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
			}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
					RunnerClass.failedReason = RunnerClass.failedReason + ","
							+ "Other information - Renewal Coordinator Name";
					GenericMethods.logger.error("Issue - Other information - Renewal Coordinator Name");
				}

				// Pet Rent Amount
				if (ReadingLeaseAggrements.petRentFlag == true) {
					try {
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.petRentAmount))
								.build().perform();
						RunnerClass.driver.findElement(Locators.petRentAmount).click();
						RunnerClass.driver.findElement(Locators.petRentAmount)
								.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
						RunnerClass.driver.findElement(Locators.petRentAmount).sendKeys(petRentAmount);
					}
					catch (TimeoutException t) {
						 WebDriverManager.chromedriver().clearDriverCache().setup();
						 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
						return false;
						
				}
					catch (Exception e) {
						RunnerClass.statusID = 1;
						e.printStackTrace();
						RunnerClass.failedReason = RunnerClass.failedReason + ","
								+ "Other information - Pet Rent Amount";
						GenericMethods.logger.error("Issue - Other information - Pet Rent Amount");
					}

				}

				RunnerClass.js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
				Thread.sleep(3000);
				try {
					if (AppConfig.saveButtonOnAndOff == true)
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease))
								.click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
					else
						RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease))
								.click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
					Thread.sleep(3000);
					DatabaseClass.intermittentPopUp(RunnerClass.driver);
				}
				catch (TimeoutException t) {
					 WebDriverManager.chromedriver().clearDriverCache().setup();
					 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
					return false;
					
			}
				catch (Exception e) {
					RunnerClass.statusID = 1;
					e.printStackTrace();
				}
			}
			catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
			}
			catch (Exception e) {
				RunnerClass.js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
				if (AppConfig.saveButtonOnAndOff == true)
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.saveLease))
							.click(RunnerClass.driver.findElement(Locators.saveLease)).build().perform();
				else
					RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.cancelLease))
							.click(RunnerClass.driver.findElement(Locators.cancelLease)).build().perform();
				return false;
			}
			DatabaseClass.intermittentPopUp(RunnerClass.driver);

			// Related Activities
			RelatedActivities();
		}
		catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return true;
	}

	public static boolean RelatedActivities() throws Exception {
		try {
			RunnerClass.driver.navigate().refresh();
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			RunnerClass.driver.findElement(Locators.relatedActivities_LeaseRenewal).click();

			Thread.sleep(2000);

			try {
				RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.removeLeasingFee)).build()
						.perform();
				if (RunnerClass.driver.findElement(Locators.removeLeasingFee).isDisplayed()) {
					RunnerClass.driver.findElement(Locators.removeLeasingFee).click();
				}
			} catch (Exception e1) {
				// Ignore any exception that might occur during lease fee removal
			}

			// Related Activities - New Start Date
			RunnerClass.driver.findElement(Locators.relatedActivities_newStartDate).click();
			RunnerClass.driver.findElement(Locators.relatedActivities_newStartDate)
					.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			RunnerClass.driver.findElement(Locators.relatedActivities_newStartDate).sendKeys(newStartDate);

			// Click this to remove Calendar pop-up if present
			// RunnerClass.driver.findElement(Locators.relatedActivities_newLeaseRenewalPopUpHeading).click();

			// Related Activities - New End Date
			RunnerClass.driver.findElement(Locators.relatedActivities_newEndDate).click();
			RunnerClass.driver.findElement(Locators.relatedActivities_newEndDate)
					.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			RunnerClass.driver.findElement(Locators.relatedActivities_newEndDate).sendKeys(newEndDate);

			// Related Activities - Renewal On Date
			RunnerClass.driver.findElement(Locators.relatedActivities_renewalOnDate).click();
			RunnerClass.driver.findElement(Locators.relatedActivities_renewalOnDate)
					.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			RunnerClass.driver.findElement(Locators.relatedActivities_renewalOnDate).sendKeys(renewalOnDate);
			Thread.sleep(3000);
			RunnerClass.driver.findElement(Locators.relatedActivities_newLeaseRenewalPopUpHeading).click();

			// Base Rent
			RunnerClass.actions.moveToElement(RunnerClass.driver.findElement(Locators.baseRentActivities)).click()
					.build().perform();
			RunnerClass.driver.findElement(Locators.baseRentActivities).click();
			RunnerClass.driver.findElement(Locators.baseRentActivities)
					.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
			RunnerClass.driver.findElement(Locators.baseRentActivities).sendKeys(currentMonthlyRent);
			Thread.sleep(3000);

			if (AppConfig.saveButtonOnAndOff) {
				RunnerClass.driver.findElement(Locators.relatedActivities_save).click();
			} else {
				RunnerClass.driver.findElement(Locators.relatedActivities_cancel).click();
			}
			Thread.sleep(3000);
			DatabaseClass.intermittentPopUp(RunnerClass.driver);
			// Check if an error message is displayed

		} catch (TimeoutException t) {
			 WebDriverManager.chromedriver().clearDriverCache().setup();
			 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
			return false;
			
		}
		catch (Exception e) {
			// Handle any other exceptions
			RunnerClass.statusID = 1;
			RunnerClass.failedReason = RunnerClass.failedReason + "," + "Issue in adding Related Activities";
			GenericMethods.logger.error("Issue in adding Related Activities");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void clearExistingVariableValues() {
		renewalStatus = "";
		reneWalFollowupNotes = "";
		renewalExecutionDate = "";
		currentMonthlyRent = "";
		priorMonthlyRent = "";
		renewalCoordinatorName = "";

		// Related Activities - Lease Renewal
		newStartDate = "";
		newEndDate = "";
		renewalOnDate = "";

	}

}
