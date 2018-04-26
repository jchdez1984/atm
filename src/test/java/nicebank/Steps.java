package nicebank;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import transforms.MoneyConverter;

public class Steps {

    KnowsMyAccount helper;

    public Steps(){
        helper = new KnowsMyAccount();
    }

    @Given("^I have deposited \\$(\\d+\\.\\d+) in my account$")
    public void iHaveDeposited$InMyAccount(@Transform(MoneyConverter.class) Money amount) throws Throwable {
        helper.getMyAccount().deposit(amount);
        Assert.assertEquals("incorrect account balance - ", amount, helper.getMyAccount().getBalance());
    }

    @When("^I withdraw \\$(\\d+)$")
    public void iWithdraw(int amount) throws Throwable{
        Teller teller = new Teller(helper.getCashSlot());
        teller.withdrawFrom(helper.getMyAccount(), amount);
    }

    @Then("^\\$(\\d+) should be dispensed$")
    public void $ShouldBeDispensed(int dollars) throws Throwable {
        Assert.assertEquals("incorrect amount dispensed -", dollars, helper.getCashSlot().contents());
    }


    class Account {
        private Money balance = new Money();

        public void deposit(Money amount) {
            balance = balance.add(amount);
        }

        public Money getBalance() {
            return balance;
        }
    }

    class Teller{
        private CashSlot cashSlot;

        public Teller(CashSlot cashSlot){
            this.cashSlot = cashSlot;
        }

        public void withdrawFrom(Account account, int dollars){
            cashSlot.dispense(dollars);
        }
    }

    class CashSlot{
        private int contents;

        public int contents(){
            return contents;
        }

        public void dispense(int dollars) {
            contents = dollars;
        }
    }

    class KnowsMyAccount{
        private Account myAccount;
        private CashSlot cashSlot;
        public Account getMyAccount(){
            if(myAccount == null){
                myAccount = new Account();
            }
            return myAccount;
        }

        public CashSlot getCashSlot(){
            if(cashSlot == null){
                cashSlot = new CashSlot();
            }
            return cashSlot;
        }
    }
}
