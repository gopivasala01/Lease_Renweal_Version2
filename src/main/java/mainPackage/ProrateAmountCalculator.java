package mainPackage;

import GenericLibrary.GenericMethods;
import PDFDataExtract.ReadingLeaseAggrements;

public class ProrateAmountCalculator {
	
	
	
	
	
	public static String prorateAmountOld(String Amount) throws Exception {
			String proratedAmount = "";
	    	
	    	
	    	//Prorate RBP
	    	try
			{ 
				int dayInMoveInDate = Integer.parseInt(UpdateValues.startDate.split("/")[1]);
				int daysInMonth = GenericMethods.getDaysInMonth(UpdateValues.startDate);
				double RBPAmount = Double.parseDouble(Amount);
				double RBPPerDay = RBPAmount /daysInMonth;
				double prorateRBP = (dayInMoveInDate-1)*RBPPerDay; 
				proratedAmount = String.format("%.2f", prorateRBP);
				
			}
			catch(Exception e)
			{
				proratedAmount = "Error";
				e.printStackTrace();
			}
			System.out.println("Prorate Amount = "+proratedAmount);
		
		return proratedAmount;
		
	}
}
