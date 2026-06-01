package ui;

import service.BookService;
import service.MemberService;
import service.TransactionService;

import java.util.Scanner;

public class MainUi {
    private Scanner sc;
    private BookUi bookUi;
    private MemberUi memberUi;
    private TransactionUi transactionUi;
    private ReportUi reportUi;

    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;

    public MainUi(Scanner sc, BookUi bookUi, MemberUi memberUi, TransactionUi transactionUi, ReportUi reportUi,
                  BookService bookService, MemberService memberService, TransactionService transactionService) {
        this.sc = sc;
        this.bookUi = bookUi;
        this.memberUi = memberUi;
        this.transactionUi = transactionUi;
        this.reportUi = reportUi;
        this.bookService=bookService;
        this.memberService=memberService;
        this.transactionService=transactionService;
    }

    public void start(){
        boolean isRunning=true;
        while(isRunning){
            try{
                System.out.println("\n\n\n\n\n\n\n\n");
                System.out.println("===============================================");
                System.out.println("      📚 LIBRARY MANAGEMENT SYSTEM 📚");
                System.out.println("===============================================");
                System.out.println("1.             📕 Manage Books");
                System.out.println("2.             👤 Manage Members");
                System.out.println("3.             🔄 Borrow / Return");
                System.out.println("4.             📊 Reports & Stats");
                System.out.println("0.             🚪 Exit System");
                System.out.println("===============================================");
                System.out.print("Select an option: ");
                int choice=Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:{
                        bookUi.start();
                        break;
                    }

                    case 2:{
                        memberUi.start();
                        break;
                    }

                    case 3:{
                        transactionUi.start();
                        break;
                    }

                    case 4:{
                        reportUi.start();
                    }

                    case 0:{
                        isRunning=false;
                        System.out.println("💾 Saving data to files...");
                        bookService.save();
                        memberService.save();
                        transactionService.save();
                        System.out.println("👋 Goodbye! See you again!");
                        break;
                    }

                    default:
                        System.out.println("❌ Invalid choice! Please select from 0 to 4.");
                        System.out.print("Press ENTER to continue...");
                        sc.nextLine();
                }

            }catch (NumberFormatException e){
                System.out.println("❌ Invalid choice! Please select from 0 to 4.");
            }
        }
    }
}
