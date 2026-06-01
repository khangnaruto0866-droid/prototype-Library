package ui;

import model.BorrowingTransaction;
import service.TransactionService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TransactionUi {
    private Scanner sc;
    private TransactionService transactionService;

    public TransactionUi(Scanner sc, TransactionService transactionService) {
        this.sc = sc;
        this.transactionService = transactionService;
    }

    public void start(){
        boolean isRunning=true;
        while(isRunning){
            System.out.println("\n\n\n\n\n\n\n\n========== BORROW/RETURN MANAGEMENT ==========");
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. View All Transactions");
            System.out.println("4. View Member Borrow History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine());
            try{
                switch(choice){
                    case 1:{
                        handleBorrowBook();
                        break;
                    }

                    case 2:{
                        handleReturnBook();
                        break;
                    }

                    case 3:{
                        handleDisplayTransaction();
                        break;
                    }

                    case 4:{
                        handleMemBorrowHistory();
                    }

                    case 0:{
                        isRunning=false;
                        break;
                    }

                    default:
                        System.out.println("only choose from 0-4");
                        System.out.print("Press ENTER to continue...");
                        sc.nextLine();
                }

            }catch (NumberFormatException e){
                System.out.println("only choose from 0-4");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleBorrowBook(){
        System.out.println("\n\n\n\n\n\n\n\n\n--- BORROW A BOOK ---");
        try{
            System.out.print("Enter Member ID: ");
            String memId=sc.nextLine();
            System.out.print("Enter Book ID: ");
            String bookId=sc.nextLine();
            System.out.print("Enter Borrow Day (dd/MM/yyyy): ");

            String inputDate=sc.nextLine();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate borrowDay=LocalDate.parse(inputDate,formatter);

            transactionService.borrowBook(bookId,memId,borrowDay);
            System.out.println("✅ Transaction Successful: Book borrowed!");


        }catch (DateTimeException e){
            System.out.println("❌ error, mismatch date input, date must be (dd/MM/yyyy).");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleReturnBook(){
        System.out.println("\n\n\n\n\n\n\n\n--- RETURN A BOOK ---");
        try{
            System.out.print("Enter Member ID: ");
            String memId=sc.nextLine();
            System.out.print("Enter Book ID: ");
            String bookId=sc.nextLine();
            BorrowingTransaction transaction=transactionService.findTransaction(bookId,memId);
            if(transaction == null){
                System.out.println("❌ cannot find, maybe this transaction have been paid");
            }

            else{
                System.out.print("Enter Return Day: ");
                String inputReturnDay=sc.nextLine();
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate returnDay=LocalDate.parse(inputReturnDay,formatter);
                transactionService.returnBook(bookId,memId,returnDay);
                System.out.println("✅ Transaction Successful: Book returned!");
            }

        }catch (DateTimeException e){
            System.out.println("❌ error, mismatch date input, date must be (dd/MM/yyyy).");
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleDisplayTransaction(){
        List<BorrowingTransaction>transactionsList=transactionService.allBills();
        if(transactionsList.isEmpty()){
            System.out.println("there is no bill");
        }

        else{
            System.out.println("\n\n\n\n\n\n\n\n\n--- TRANSACTION HISTORY ---");
            System.out.printf("%-10s | %-15s | %-15s | %-15s%n",
                    "Book ID", "Member ID", "Borrow Date", "Return Date");
            System.out.println("-----------------------------------------------------------------------------");
            for(BorrowingTransaction transaction:transactionsList){
                System.out.printf("%-10s | %-15s | %-15s | %-15s\n\n",
                        transaction.getBookId(),transaction.getMemberId(),transaction.getBorrowDate(),
                        transaction.getReturnDate()==null ? "Borrowing":transaction.getReturnDate());
            }
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleMemBorrowHistory(){
        System.out.println("\n\n\n\n\n\n\n\n\n--- TRANSACTION HISTORY ---");
        System.out.print("Enter Member ID: ");
        String memId = sc.nextLine();
        try{
            List<BorrowingTransaction> memHistory=transactionService.borrowedMemHistory(memId);
            if(memHistory.isEmpty()){
                System.out.println("this member did not borrow any book");
            }
            else{
                System.out.printf("%-10s | %-15s | %-15s | %-15s%n",
                        "Book ID", "Member ID", "Borrow Date", "Return Date");
                System.out.println("-----------------------------------------------------------------------------");
                for (BorrowingTransaction transaction:memHistory){
                    System.out.printf("%-10s | %-15s | %-15s | %-15s\n\n",
                            transaction.getBookId(),transaction.getMemberId(),transaction.getBorrowDate(),
                            transaction.getReturnDate()==null ? "Borrowing":transaction.getReturnDate());
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }
}
