package ExtractData;

import org.openqa.selenium.By;

public class Locators {

	public static By searchbox = By.name("eqsSearchText");
	public static By dashboardsTab = By.linkText("Dashboards");
	public static By searchingLoader = By.xpath("//*[@id='eqsResult']/h1");
	public static By noItemsFoundMessagewhenLeaseNotFound = By.xpath("//*[text()='No Items Found']");
	public static By selectSearchedLease = By.xpath("//*[@class='results']/descendant::li/a");
	public static By getLeaseCDEType = By.xpath("//*[@id='summary']/table[1]/tbody/tr[3]/td");
    public static By leasesTab = By.xpath("//*[@class='tabbedSection']/a[4]");	
    public static By leasesTab2 = By.xpath("(//a[text()='Leases'])[2]");
    public static By searchedLeaseCompanyHeadings = By.xpath("//*[@id='eqsResult']/div/div/h1");
    public static By notesAndDocs = By.id("notesAndDocuments");
    public static By documentsList = By.xpath("//*[@id='documentHolderBody']/tr/td[1]/a"); 
    
    public static By marketDropdown = By.id("switchAccountSelect");
    
    public static By popUpAfterClickingLeaseName = By.xpath("//*[@id='viewStickyNoteForm']");
    public static By popupClose = By.xpath("//*[@id='editStickyBtnDiv']/input[2]");
    public static By scheduledMaintanancePopUp = By.xpath("//*[text()='Scheduled Maintenance Notification']");
    public static By scheduledMaintanancePopUpOkButton = By.id("alertDoNotShow");
    
    public static By scheduleMaintananceIFrame = By.xpath("//iframe[@srcdoc='<meta name=\"referrer\" content=\"origin\" />']");
    public static By scheduleMaintanancePopUp2 = By.xpath("//section[@role='dialog']");
    public static By maintananceCloseButton = By.xpath("//a[@aria-label='Close modal']");
    
    
    public static By advancedSearch = By.linkText("Advanced Search >>");
    public static By advancedSearch_buildingsSection = By.id("searchResultTable_buildings");
    public static By advancedSearch_buildingAddresses = By.xpath("//*[@id='searchResultTable_buildings']/following::table[1]/tbody/tr/td[2]/a");
    
}
