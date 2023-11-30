package PDFDataExtract;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import ExtractData.dataExtractionClass;
import GenericLibrary.GenericMethods;

public class ReadingLeaseAggrements {

	
	public static String text="";
	public static String firstPageText="";
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
	
	
	public static String leaseRenewalFee= "";
	public static String increasedRent_amount = "";
	public static final String HVACAirFilterFee = "";
	
	public static File file;
	
	public static boolean petRentFlag=false;
	public static boolean monthlyTaxAmountFlag=false;
	public static boolean rbpFlag=false;
	public static boolean rubsFlag=false;
	
	
	public static boolean getDataFromLeaseAgreements() throws Exception {
		
		
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
			    
			    PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(1);
				stripper.setEndPage(1);
				firstPageText = stripper.getText(document);
				firstPageText = firstPageText.replaceAll(System.lineSeparator(), " ");
				firstPageText = firstPageText.replaceAll(" +", " ");
			    //System.out.println(text);
			    System.out.println("------------------------------------------------------------------");
			    
			    String pattern = "\\d{1,2}/\\d{1,2}/\\d{4}"; 
			    Pattern datePattern = Pattern.compile(pattern);

			    Matcher matcher = datePattern.matcher(firstPageText);
			    
			    while (matcher.find()) {
			    	renewalExecutionDate = matcher.group();
			    }
			    if(renewalExecutionDate.isEmpty())
			    {
			    	 matcher = datePattern.matcher(text);
			    	 while (matcher.find()) {
			    		 renewalExecutionDate = matcher.group();
			 	    	
			 	    }
			    }
			    String[] SplitDate = renewalExecutionDate.split("/");

		       	 for (int i = 0; i < 2; i++) {
		       	     if (SplitDate[i].length() == 1) {
		       	         // Add a leading zero for single-digit values in the first two components
		       	         SplitDate[i] = "0" + SplitDate[i];
		       	     }
		       	 }
		       	 
		       	 renewalExecutionDate= SplitDate[0]+"/"+ SplitDate[1]+"/"+SplitDate[2];

		    	 System.out.println("Last date mentioned on the page: " + renewalExecutionDate);
			    
			    
			    
			    // Monthly Rent
			    commencementDate = dataExtractionClass.getDates(text, dataExtractionClass.getDataOf("commencementDateFromPDF"));
			    
				//commencementDate = LeaseAgreementDownloadandGetData.getDates(getDataOf("commencementDateFromPDF"));
			    System.out.println("Start date = "+ commencementDate);
			    expirationDate = dataExtractionClass.getDates(text,dataExtractionClass.getDataOf("expirationDateFromPDF"));
			    System.out.println("End date = "+ expirationDate);
			    monthlyRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("monthlyRentFromPDF"));
			    System.out.println("Monthly Rent Amount = "+ monthlyRent);
			    monthlyTaxAmountFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("monthlyRentTaxAmountAvailablityCheck"));
			    if(monthlyTaxAmountFlag == true) {
			    	monthlyTaxRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("monthlyRentTaxAmountFromPDF"));
			    	System.out.println("Monthly Tax Amount = "+ monthlyTaxRent);
			    	if(!monthlyRent.equals("Error") || !monthlyTaxRent.equals("Error") || !monthlyTaxRent.equals("0.00")) {
			    		Float totalMonthlyRentWithTaxRaw = Float.parseFloat(monthlyRent)+Float.parseFloat(monthlyTaxRent);
			    		totalMonthlyRentWithTax = String.format("%.2f",totalMonthlyRentWithTaxRaw);
			    		System.out.println("Total Monthly rent with Tax Amount = "+ totalMonthlyRentWithTax);
			    	}
			    	
			    }
			    proratedRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("proratedRentFromPDF"));
			    System.out.println("Prorated Amount = "+ proratedRent);
			    petRentFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("petRentAvailablityCheck"));
			    if(petRentFlag == true) {
			    	petRent = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("petRentFromPDF"));
			    	System.out.println("Pet Rent Amount = "+ petRent);
			    }
			    rbpFlag = dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("rbpAvailabilityCheck"));
			    if(rbpFlag == true) {
			    	rbpAmount = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("rbpFromPDF"));
			    	System.out.println("RBP Amount = "+ rbpAmount);
			    }
			    rubsFlag =dataExtractionClass.getFlags(text,dataExtractionClass.getDataOf("rubsAvailabilityCheck"));
			    if(rubsFlag == true) {
			    	rubsAmount = dataExtractionClass.getValuesWithStartandEndText(text,dataExtractionClass.getDataOf("rubsFromPDF"));
			    	System.out.println("RUBS Amount = "+ rubsAmount);
			    }
			    leaseRenewalFee = dataExtractionClass.getValues(text,dataExtractionClass.getDataOf("leaseRenewalFromPDF"));
			    System.out.println("Lease Renewal Admin Fee = "+ leaseRenewalFee);
			    
			    
			    
			    
		 	}
		}
		catch(Exception e2) {
			e2.printStackTrace();
			return false;
		}

	
		return true;
		
	}
	
	
	
	
	
}
