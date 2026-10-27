Program Review: Banking Account Management System
1. Abstract Class
An abstract class is declared using the abstract keyword. It serves as a base blueprint for related subclasses and cannot be instantiated directly. An abstract class can hold common attributes, concrete methods, and abstract methods that must be implemented by its subclasses.

In this banking system, BankAccount is an abstract class that defines common state (accountNumber, ownerName, balance) and concrete behaviors like deposit(), while leaving account-specific calculations to abstract methods.

```java
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
}
```

2. Arrays
An array is a fixed-size data structure storing elements of the same type. In Java, arrays can hold primitive data types or object references.

In the Bank class, a fixed-size array stores customer records up to a predefined limit.

```java
public class Bank {
    private Customer[] customers;
    private int numberOfCustomers;

    public Bank() {
        customers = new Customer[10];
        numberOfCustomers = 0;
    }

    public void addCustomer(String firstName, String lastName) {
        if (numberOfCustomers < customers.length) {
            customers[numberOfCustomers++] = new Customer(firstName, lastName);
        }
    }
}

```

3. Association & Composition
In object-oriented design, classes interact through structural relationships:

Bank to Customer: One-to-many relationship where a bank maintains multiple customer records.

Customer to BankAccount: Association where a customer references a single bank account polymorphic instance.

```java
public class Customer {
    private String firstName;
    private String lastName;
    private BankAccount account;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }
}

```

4. Scanner
The Scanner utility class parses console input streams (System.in) into primitives and strings.

In BankDemo, Scanner processes user commands, account numbers, names, and transaction amounts.

```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);

System.out.print("Masukkan jumlah tarik tunai: Rp ");
double wdAmount = scanner.nextDouble();

System.out.print("Masukkan Nama Depan: ");
String firstName = scanner.next();

```

5. Inheritance
Inheritance allows derived classes to inherit accessible members of a parent class using the extends keyword, avoiding duplicate field and method declarations.

Both SavingsAccount and CheckingAccount inherit properties from BankAccount:

```java
public class SavingsAccount extends BankAccount {
    private static final double MIN_BALANCE = 50000.0;
    private static final double INTEREST_RATE = 0.005;

    public SavingsAccount(String accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }
}

```
```java
public class CheckingAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = 500000.0;

    public CheckingAccount(String accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }
}

```
6. Polymorphism
Polymorphism allows objects of different concrete subclasses to be referenced by their abstract parent type and provide distinct runtime behaviors via method overriding (@Override).

Withdrawal Policies
SavingsAccount enforces a minimum balance rule:
```java
@Override
public boolean withdraw(double amount) {
    if (amount > 0 && (balance - amount) >= MIN_BALANCE) {
        balance -= amount;
        return true;
    }
    return false;
}

```
CheckingAccount allows transactions to exceed current funds up to an overdraft limit:

```java
@Override
public boolean withdraw(double amount) {
    if (amount > 0 && (balance - amount) >= -OVERDRAFT_LIMIT) {
        balance -= amount;
        return true;
    }
    return false;
}

```

Interest Calculation
SavingsAccount earns interest (e.g., 0.5%):
```java
@Override
public double calculateInterest() {
    return balance * INTEREST_RATE;
}

```
CheckingAccount earns no interest:
```java
@Override
public double calculateInterest() {
    return 0.0;
}

```
Conclusion
The Banking Account Management System illustrates core Object-Oriented Programming (OOP) concepts in Java. The BankAccount abstract class enforces a consistent contract, concrete subclasses use inheritance to specialize behaviors (SavingsAccount with minimum balance constraints and CheckingAccount with overdraft protection), arrays manage indexed customer collections, Scanner handles user terminal input, and runtime polymorphism dynamically executes specialized account rules.

class diagram
```
classDiagram
    class BankAccount {
        <<abstract>>
        #accountNumber : String
        #ownerName : String
        #balance : double
        +BankAccount(accountNumber: String, ownerName: String, balance: double)
        +deposit(amount: double) : void
        +withdraw(amount: double)* : boolean
        +calculateInterest()* : double
        +getAccountType()* : String
        +applyMonthlyInterest() : void
        +getBalance() : double
        +getAccountNumber() : String
        +getOwnerName() : String
    }

    class SavingsAccount {
        -MIN_BALANCE : double = 50000
        -INTEREST_RATE : double = 0.005
        +SavingsAccount(accountNumber: String, ownerName: String, balance: double)
        +withdraw(amount: double) : boolean
        +calculateInterest() : double
        +getAccountType() : String
    }

    class CheckingAccount {
        -OVERDRAFT_LIMIT : double = 500000
        +CheckingAccount(accountNumber: String, ownerName: String, balance: double)
        +withdraw(amount: double) : boolean
        +calculateInterest() : double
        +getAccountType() : String
    }

    BankAccount <|-- SavingsAccount : Generalization
    BankAccount <|-- CheckingAccount : Generalization
```
![Class Diagram](study%20case.PNG)
