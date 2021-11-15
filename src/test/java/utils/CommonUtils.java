package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import main.BaseTest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import pages.CreditSummaryPage;
import pages.TransactionsListPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class CommonUtils {
    public HashMap<String, String> testData = new HashMap<>();
    public Logger log = Logger.getLogger(BaseTest.class);
    public static ExtentTest test;
    public static ExtentReports report;
    public Properties prop = new Properties();
    public WebDriver driver;
    public CreditSummaryPage summaryPageObj;
    public TransactionsListPage txnListObj;
    public Workbook creatorStackWorkbook = null;
    File file =    new File("src/main/resources/testCases.xlsx");

    public  WebElement prepareWebElementWithDynamicXpath(WebDriver driver, String xpathValue, String substitutionValue) {
        WebElement e = driver.findElement(By.xpath(xpathValue.replace("xxx", substitutionValue)));
        test.log(LogStatus.PASS, "Dynamic Xpath method was created successfully");
        log.info("Dynamic Xpath was created successfully");
        return e;
    }

    public void waitExplicit(WebDriver driver, WebElement e) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(e));
        test.log(LogStatus.PASS, "Element was visible after successful wait time");
        log.info("Element was found visible after successful wait time");
        }

     public void initialSetup() throws IOException {
         FileInputStream fis = new FileInputStream("src/main/resources/testData.properties");
         prop.load(fis);

         report = new ExtentReports("./extent-reports/extent-report.html");
         test = report.startTest("Verify whether user was able to check whether credit amount is equal to the transaction amounts of the given customer");

         BasicConfigurator.configure();

         test.log(LogStatus.PASS, "Setup was completed successfully");
         log.info("Setup was completed successfully");

         loadExcel();
     }

     public void openChromeBrowser(){
         WebDriverManager.chromedriver().setup();
         String URL = prop.getProperty("url");
         driver = new ChromeDriver();
         driver.get(URL);
         driver.manage().window().maximize();
         driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS) ;
         summaryPageObj=new CreditSummaryPage(testData,driver);
         txnListObj=new TransactionsListPage(testData,driver);
         test.log(LogStatus.PASS, "Browser window opened successfully with the specified URL");
         log.info("Browser window opened successfully with the specified URL");
     }

     public static void endExtentTest(){
         report.endTest(test);
         report.flush();
     }

     public void closeBrowser(){
         driver.close();
         test.log(LogStatus.PASS, "Browser was closed successfully");
         log.info("Browser was closed successfully");
     }

     public void closeExtentReport(){
         report.close();
     }


    public void loadExcel() throws IOException {

        FileInputStream inputStream = new FileInputStream(file);
        creatorStackWorkbook = new XSSFWorkbook(inputStream);
        test.log(LogStatus.PASS, "Excel sheet was loaded successfully");
        log.info("Excel sheet was loaded successfully");
    }

    public void getCustomerName(String sheetName) throws IOException {
        Sheet customerCreditTcSheet = creatorStackWorkbook.getSheet(sheetName);
        int i=0;

        Row row = customerCreditTcSheet.getRow(i);
        for (int j = 0; j < row.getLastCellNum(); j++) {
            if(row.getCell(j).getStringCellValue().equals("CustomerName")){
                row=customerCreditTcSheet.getRow(i+1);
                String customerNameCellValue=row.getCell(j).getStringCellValue();
                testData.put("customerName",customerNameCellValue);
                System.out.println("Customer Name:"+customerNameCellValue);
                break;
            }
        }
        test.log(LogStatus.PASS, "Customer name was loaded from Excel Sheet successfully");
        log.info("Customer name was loaded from Excel Sheet successfully");
    }
}


