public abstract class BankAccount {
    protected String accountNumber;
    protected String ownerName;
    protected double balance;

    public BankAccount(String accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.printf("Setor tunai Rp %,.2f berhasil. Saldo saat ini: Rp %,.2f\n", amount, this.balance);
        } else {
            System.out.println("Jumlah setor tunai harus lebih dari 0!");
        }
    }

    public abstract boolean withdraw(double amount);
    public abstract double calculateInterest();
    public abstract String getAccountType();

    public void applyMonthlyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            this.balance += interest;
            System.out.printf("Bunga bulanan (Rp %,.2f) berhasil ditambahkan ke saldo.\n", interest);
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }
}