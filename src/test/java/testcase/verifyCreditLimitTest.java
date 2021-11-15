package testcase;

import main.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class verifyCreditLimitTest extends BaseTest{

    @Test(description = "Verify whether user was able to check whether credit amount is equal to the transaction amounts of the given customer")
    public void verifyCreditLimitTest() throws IOException {

        getCustomerName("CustomerCreditTCs");
        summaryPageObj.getCustomerCredit();
        summaryPageObj.openCustomerTransactionList();
        txnListObj.getTransactionAmountsOfCustomer();
        txnListObj.verifyCustomerCreditAndTxnAmts();

    }

}
