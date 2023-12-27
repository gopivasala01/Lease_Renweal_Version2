package PDFDataExtract;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.TimeoutException;

import ExtractData.dataExtractionClass;
import GenericLibrary.GenericMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import mainPackage.RunnerClass;

public class ReadingLeaseAggrements {

	
	public static String text="";
	public static String executionPageText="";
	public static String modifiedtext="";
	public static String commencementDate ="";
	public static String expirationDate ="";
	public static String monthlyRent = "";
	public static String monthlyTaxRent = "";
	public static String totalMonthlyRentWithTax = "";
	public static String proratedRent = "";
	public static String petRent = "";
	public static String rbpAmount = "";
	public static String rubsAmount = "";
	public static String renewalExecutionDate="";
	public static int executionPageNumber;
	
	public static String leaseRenewalFee= "";
	public static String increasedRent_amount = "";
	public static  String HVACAirFilterFee = "";
	
	public static File file;
	
	public static boolean petRentFlag=false;
	public static boolean monthlyTaxAmountFlag=false;
	public static boolean rbpFlag=false;
	public static boolean rubsFlag=false;
	
	
	public static boolean getDataFromLeaseAgreements() throws Exception {
		 text="";
		 executionPageText="";
		 modifiedtext="";
		 commencementDate ="";
		 expirationDate ="";
		 monthlyRent = "";
		 monthlyTaxRent = "";
		 totalMonthlyRentWithTax = "";
		 proratedRent = "";
		 petRent = "";
		 rbpAmount = "";
		 rubsAmount = "";
		 renewalExecutionDate="";
		 leaseRenewalFee= "";
		 increasedRent_amount = "";
		 HVACAirFilterFee = "";
		 executionPageNumber=0;
		 petRentFlag=false;
		 monthlyTaxAmountFlag=false;
		 rbpFlag=false;
		 rubsFlag=false;
		
		try {
			Thread.sleep(10000);
			if(GenericMethods.getLastModified() !=null) {
				while (true) {
			 	    file = GenericMethods.getLastModified();
			 	    if (file.getName().endsWith(".crdownload")) {
			 	        try {
			 	            Thread.sleep(5000);
			 	        } catch (InterruptedException e1) {
			 	            
			 	        	e1.printStackTrace();
			 	            // Handle the InterruptedException if needed
			 	        }
			 	    } else {
			 	        // Break the loop if the file name does not end with ".crdownload"
			 	        break;
			 	    }
			 	}
			}
			file = GenericMethods.getLastModified();
		}
		   catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
			}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	 	
	
		try {
		 	if(file !=null) {
		 	 	FileInputStream fis = new FileInputStream(file);
				PDDocument document = PDDocument.load(fis);
			    text = new PDFTextStripper().getText(document);
			    text = text.replaceAll(System.lineSeparator(), " ");
			    text = text.trim().replaceAll(" +", " ");
			    text = text.toLowerCase();
			    
			    String pattern = "\\d{1,2}/\\d{1,2}/\\d{4}"; 
			    
			    PDFTextStripper stripper = new PDFTextStripper();
			    for (int page = 1; page <= document.getNumberOfPages(); page++) {
		            // Set the start and end page
		            stripper.setStartPage(page);
		            stripper.setEndPage(page);

		            // Extract text from the specified page
		            String pdfText = stripper.getText(document);

		            
		            Pattern datePattern = Pattern.compile(pattern);
		            
		            // Matcher for the extracted text
		            Matcher matcher = datePattern.matcher(pdfText);

		            String lastOccurrence = null;
		            while (matcher.find()) {
	                    lastOccurrence = matcher.group();
	                }
		            if (lastOccurrence != null) {
		            	renewalExecutionDate = lastOccurrence;
	                    System.out.println("Occurrence of Pattern (Page " + page + "): " + lastOccurrence);
	                    break;
	                }
		        }
 
				//GenericMethods.logger.info(text);
				GenericMethods.logger.info("------------------------------------------------------------------");
			    
			   

			    //Matcher matcher = datePattern.matcher(firstPageText);
			   /* Matcher matcher = datePattern.matcher(executionPageText);
			    int occurrenceCount = 0;

			    while (matcher.find()) {
			        occurrenceCount++;

			        // Check if it's the second occurrence
			        if (occurrenceCount == 2) {
			            renewalExecutionDate = matcher.group();
			            break; // Exit the loop after finding the second occurrence
			        }
			    }
			    
			   // while (matcher.find()) {
			    	//renewalExecutionDate = matcher.group();
			   // }
			    if(renewalExecutionDate.isEmpty())
			    {
			    	 matcher = datePattern.matcher(text);
			    	 while (matcher.find()) {
			    		 renewalExecutionDate = matcher.group();
			 	    	
			 	    }
			    }*/
			    String[] SplitDate = renewalExecutionDate.split("/");

		       	 for (int i = 0; i < 2; i++) {
		       	     if (SplitDate[i].length() == 1) {
		       	         // Add a leading zero for single-digit values in the first two components
		       	         SplitDate[i] = "0" + SplitDate[i];
		       	     }
		       	 }
		       	 
		       	 renewalExecutionDate= SplitDate[0]+"/"+ SplitDate[1]+"/"+SplitDate[2];

		       	GenericMethods.logger.info("Last date mentioned on the page: " + renewalExecutionDate);
			    
			    
			    
			    // Monthly Rent
			    commencementDate = dataExtractionClass.getDates(text, dataExtractionClass.getDataOf("commencementDateFromPDF"));
			    
				//commencementDate = LeaseAgreementDownloadandGetData.getDates(getDataOf("commencementDateFromPDF"));
			    GenericMethods.logger.info("Start date = "+ commencementDate);
			    expirationDate = dataExtractionClass.getDates(text,dataExtractionClass.getDataOf("expirationDateFromPDF"));
			    GenericMethods.logger.info("End date = "+ expirationDate);
			    monthlyRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("monthlyRentFromPDF"));
			    GenericMethods.logger.info("Monthly Rent Amount = "+ monthlyRent);
			    monthlyTaxAmountFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("monthlyRentTaxAmountAvailablityCheck"));
			    if(monthlyTaxAmountFlag == true) {
			    	monthlyTaxRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("monthlyRentTaxAmountFromPDF"));
			    	GenericMethods.logger.info("Monthly Tax Amount = "+ monthlyTaxRent);
			    	if(!monthlyRent.equals("Error") || !monthlyTaxRent.equals("Error") || !monthlyTaxRent.equals("0.00")) {
			    		Float totalMonthlyRentWithTaxRaw = Float.parseFloat(monthlyRent)+Float.parseFloat(monthlyTaxRent);
			    		totalMonthlyRentWithTax = String.format("%.2f",totalMonthlyRentWithTaxRaw);
			    		GenericMethods.logger.info("Total Monthly rent with Tax Amount = "+ totalMonthlyRentWithTax);
			    	}
			    	
			    }
			    proratedRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("proratedRentFromPDF"));
			    GenericMethods.logger.info("Prorated Amount = "+ proratedRent);
			    petRentFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("petRentAvailablityCheck"));
			    if(petRentFlag == true) {
			    	petRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("petRentFromPDF"));
			    	GenericMethods.logger.info("Pet Rent Amount = "+ petRent);
			    }
			    rbpFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("rbpAvailabilityCheck"));
			    if(rbpFlag == true) {
			    	rbpAmount = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("rbpFromPDF"));
			    	GenericMethods.logger.info("RBP Amount = "+ rbpAmount);
			    }
			    rubsFlag =dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("rubsAvailabilityCheck"));
			    if(rubsFlag == true) {
			    	rubsAmount = dataExtractionClass.getValuesWithStartandEndText(text,dataExtractionClass.getDataOf("rubsFromPDF"));
			    	GenericMethods.logger.info("RUBS Amount = "+ rubsAmount);
			    }
			    leaseRenewalFee = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("leaseRenewalFromPDF"));
			    GenericMethods.logger.info("Lease Renewal Admin Fee = "+ leaseRenewalFee);
			    
			    
			    
			    
		 	}
		}
		 catch (TimeoutException t) {
				 WebDriverManager.chromedriver().clearDriverCache().setup();
				 RunnerClass.failedReason = RunnerClass.failedReason + "," + "TimeOut Error";
				return false;
				
			}
		catch(Exception e2) {
			e2.printStackTrace();
			return false;
		}

	
		return true;
		
	}
	
	
	
	
	
}
