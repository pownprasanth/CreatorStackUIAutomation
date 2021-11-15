package pages;

import com.relevantcodes.extentreports.LogStatus;
import main.BaseTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.HashMap;
import java.util.List;

public class TransactionsListPage extends BaseTest {
    WebDriver driver;
    Logger log = Logger.getLogger(TransactionsListPage.class);

    @FindBy(how = How.XPATH, using = "//table/tbody//child::tr/td[2]")
    List<WebElement> txnAmounts;

    public TransactionsListPage(HashMap testData,WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
        this.testData=testData;
    }

    public void getTransactionAmountsOfCustomer() {
        double temp = 0;
        for (WebElement txnAmount : txnAmounts) {
            Double dbTxnAmount = Double.valueOf(txnAmount.getText().substring(1));
            temp = temp + dbTxnAmount;
        }
        testData.put("totalTxnAmount", String.format("%.2f", temp));
        test.log(LogStatus.PASS, "Total transaction amounts of the given customer was stored successfully");
        log.info("Total transaction amounts of the given customer was stored successfully");
           }

    public void verifyCustomerCreditAndTxnAmts(){

        try{
            Assert.assertEquals(testData.get("customerCredit"), testData.get("totalTxnAmount"), "Verify whether cusomter credit is equal to transaction amount");
            test.log(LogStatus.PASS, "Verified the given customer credit is equal to the transaction amounts was done successfully");
            log.info("Verified the given customer credit is equal to the transaction amounts was done successfully");
        } catch(AssertionError e) {
            test.log(LogStatus.FAIL, "Verified the given customer credit is equal to the transaction amounts was failed");
            log.info("Verified the given customer credit is equal to the transaction amounts was failed");
        }
    }
}
