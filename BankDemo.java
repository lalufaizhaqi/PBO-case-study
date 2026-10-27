import java.util.Scanner;

public class BankDemo {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        bank.addCustomer("John", "Doe");
        bank.getCustomer(0).setAccount(new SavingsAccount("ACC-001", "John Doe", 500000));

        int activeCustomerIndex = 0;
        boolean running = true;

        while (running) {
            Customer activeCustomer = bank.getCustomer(activeCustomerIndex);

            System.out.println("\n==========================================");
            System.out.println("          Welcome to Bank MEGAMONEY       ");
            System.out.println("------------------------------------------");
            System.out.printf("Nasabah Aktif: %s %s\n", activeCustomer.getFirstName(), activeCustomer.getLastName());
            if (activeCustomer.getAccount() != null) {
                System.out.printf("Tipe Akun    : %s\n", activeCustomer.getAccount().getAccountType());
            }
            System.out.println("------------------------------------------");
            System.out.println("1. Cek Saldo");
            System.out.println("2. Setor Tunai (Deposit)");
            System.out.println("3. Tarik Tunai (Withdraw)");
            System.out.println("4. Terapkan Bunga Bulanan");
            System.out.println("5. Tambah Nasabah & Buka Rekening");
            System.out.println("6. Pilih / Ganti Nasabah Aktif");
            System.out.println("7. Lihat Daftar Seluruh Nasabah");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu (0-7): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid! Silakan masukkan angka.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    BankAccount acc = activeCustomer.getAccount();
                    if (acc != null) {
                        System.out.printf("Saldo saat ini (%s %s): Rp %,.2f\n",
                                activeCustomer.getFirstName(), activeCustomer.getLastName(), acc.getBalance());
                    } else {
                        System.out.println("Nasabah belum memiliki akun.");
                    }
                    break;

                case 2:
                    if (activeCustomer.getAccount() != null) {
                        System.out.print("Masukkan jumlah setor tunai: Rp ");
                        double depAmount = scanner.nextDouble();
                        activeCustomer.getAccount().deposit(depAmount);
                    } else {
                        System.out.println("Nasabah belum memiliki akun.");
                    }
                    break;

                case 3:
                    if (activeCustomer.getAccount() != null) {
                        System.out.print("Masukkan jumlah tarik tunai: Rp ");
                        double wdAmount = scanner.nextDouble();
                        boolean success = activeCustomer.getAccount().withdraw(wdAmount);
                        if (success) {
                            System.out.printf("Berhasil tarik tunai! Sisa saldo: Rp %,.2f\n",
                                    activeCustomer.getAccount().getBalance());
                        } else {
                            System.out.println("Tarik tunai gagal! Melebihi batas penarikan atau saldo tidak mencukupi.");
                        }
                    } else {
                        System.out.println("Nasabah belum memiliki akun.");
                    }
                    break;

                case 4:
                    if (activeCustomer.getAccount() != null) {
                        activeCustomer.getAccount().applyMonthlyInterest();
                    } else {
                        System.out.println("Nasabah belum memiliki akun.");
                    }
                    break;

                case 5:
                    System.out.print("Masukkan Nama Depan: ");
                    String firstName = scanner.next();
                    System.out.print("Masukkan Nama Belakang: ");
                    String lastName = scanner.next();
                    bank.addCustomer(firstName, lastName);

                    int newIdx = bank.getNumOfCustomers() - 1;
                    Customer newCust = bank.getCustomer(newIdx);

                    System.out.println("Pilih jenis akun:");
                    System.out.println("1. Savings Account (Min. Saldo: Rp 50.000)");
                    System.out.println("2. Checking Account (Overdraft Limit: Rp 500.000)");
                    System.out.print("Pilihan (1/2): ");
                    int typeChoice = scanner.nextInt();

                    System.out.print("Masukkan Saldo Awal: Rp ");
                    double initBal = scanner.nextDouble();
                    String accNum = "ACC-00" + (newIdx + 1);
                    String fullName = firstName + " " + lastName;

                    if (typeChoice == 1) {
                        newCust.setAccount(new SavingsAccount(accNum, fullName, initBal));
                    } else {
                        newCust.setAccount(new CheckingAccount(accNum, fullName, initBal));
                    }

                    System.out.println("Nasabah dan rekening berhasil ditambahkan!");
                    System.out.print("Apakah ingin langsung beralih ke akun ini? (y/n): ");
                    if (scanner.next().equalsIgnoreCase("y")) {
                        activeCustomerIndex = newIdx;
                    }
                    break;

                case 6:
                    System.out.println("\n--- DAFTAR NASABAH ---");
                    for (int i = 0; i < bank.getNumOfCustomers(); i++) {
                        Customer c = bank.getCustomer(i);
                        System.out.printf("[%d] %s %s\n", (i + 1), c.getFirstName(), c.getLastName());
                    }
                    System.out.print("Pilih nomor nasabah: ");
                    int selectIdx = scanner.nextInt() - 1;
                    if (selectIdx >= 0 && selectIdx < bank.getNumOfCustomers()) {
                        activeCustomerIndex = selectIdx;
                        System.out.println("Berhasil beralih ke nasabah tersebut.");
                    } else {
                        System.out.println("Nomor nasabah tidak valid!");
                    }
                    break;

                case 7:
                    System.out.println("\n--- DAFTAR SELURUH NASABAH & SALDO ---");
                    for (int i = 0; i < bank.getNumOfCustomers(); i++) {
                        Customer c = bank.getCustomer(i);
                        double bal = (c.getAccount() != null) ? c.getAccount().getBalance() : 0.0;
                        String type = (c.getAccount() != null) ? c.getAccount().getAccountType() : "Tidak Ada";
                        System.out.printf("[%d] %s %s - Tipe: %s - Saldo: Rp %,.2f\n",
                                (i + 1), c.getFirstName(), c.getLastName(), type, bal);
                    }
                    break;

                case 0:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan layanan bank.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }
}