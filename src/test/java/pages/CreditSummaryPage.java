package pages;

import com.relevantcodes.extentreports.LogStatus;
import main.BaseTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import java.util.HashMap;
import java.util.List;

public class CreditSummaryPage extends BaseTest {
    WebDriver driver;
    String dynamicXpath=null;
    Logger log = Logger.getLogger(CreditSummaryPage.class);

    @FindBy(how = How.XPATH, using = "//table/tbody//child::tr/td[1]")
    List<WebElement> customerNames;

    public CreditSummaryPage(HashMap testData,WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
        this.testData=testData;
    }

    public void getCustomerCredit(){
        dynamicXpath = "//table/tbody//child::td[text()='xxx']//following-sibling::td";
        WebElement inpCustomerCredit = prepareWebElementWithDynamicXpath(driver, dynamicXpath, testData.get("customerName"));
        waitExplicit(driver,inpCustomerCredit);
        String customerCredit = inpCustomerCredit.getText().substring(1);
        testData.put("customerCredit", customerCredit);
        test.log(LogStatus.PASS, "Customer Credit was stored successfully");
        log.info("Customer Credit was stored successfully");
    }

    public void openCustomerTransactionList(){
        for (WebElement givenCustomer : customerNames) {
            if (givenCustomer.getText().equals(testData.get("customerName"))) {
                givenCustomer.click();
                break;
            }
        }
        test.log(LogStatus.PASS, "Customer Transactions List Page was opened successfully");
        log.info("Customer Transactions List Page was opened successfully");
    }
}
