public class CheckingAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 500000.0;

    public CheckingAccount(String accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (balance - amount) >= -OVERDRAFT_LIMIT) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }

    @Override
    public String getAccountType() {
        return "Checking Account";
    }
}