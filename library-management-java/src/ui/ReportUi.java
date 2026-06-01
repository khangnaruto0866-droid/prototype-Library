package ui;


import model.BorrowingTransaction;
import model.Member;
import model.MostBorrowMemRecord;
import model.PopularBookRecord;
import service.MemberService;
import service.ReportService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class ReportUi {
    private Scanner sc;
    private MemberService memberService;
    private ReportService reportService;

    public ReportUi(Scanner sc, MemberService memberService, ReportService reportService) {
        this.sc = sc;
        this.memberService=memberService;
        this.reportService = reportService;
    }

    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n\n\n\n\n\n\n\n\n========== REPORT & STATISTICS ==========");
            System.out.println("1. View All Borrowed Books (Not returned yet)");
            System.out.println("2. View Overdue Books");
            System.out.println("3. View Most Popular Books");
            System.out.println("4. View Most Borrowing Member");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1: {
                        handleDisplayBorrowingBook();
                        break;
                    }

                    case 2: {
                        handelDisplayOverdueBook();
                    }

                    case 3: {
                        handleDisplayMostPopularBook();
                        break;
                    }

                    case 4: {
                        handleDisplayMostBorrowMem();
                        break;
                    }

                    case 0: {
                        isRunning = false;
                        break;
                    }

                    default:
                        System.out.println("only enter number from 0-4");
                        System.out.print("Press ENTER to continue...");
                        sc.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("only enter number from 0-4");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void handleDisplayBorrowingBook() {
        System.out.println("\n\n\n\n\n\n\n\n--- BORROWED BOOKS REPORT ---");
        List<BorrowingTransaction> borrowList = reportService.borrowedBook();
        if (borrowList.isEmpty()) {
            System.out.println("there's no book on the list");
        } else {
            System.out.printf("%-15s | %-15s | %-15s%n",
                    "Book ID", "Member ID", "Borrow Date");
            System.out.println("------------------------------------------------------------------");
            for (BorrowingTransaction transaction : borrowList) {
                System.out.printf("%-15s | %-15s | %-15s\n\n", transaction.getBookId(), transaction.getMemberId(), transaction.getBorrowDate());
            }
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handelDisplayOverdueBook() {
        System.out.println("\n\n\n\n\n\n\n\n--- OVERDUE BOOKS REPORT ---");
        try {
            System.out.print("Enter Current Day: ");
            String inputDay = sc.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate currentDay = LocalDate.parse(inputDay, formatter);
            List<BorrowingTransaction> overdueBookList = reportService.overDueBook(currentDay);
            if (overdueBookList.isEmpty()) {
                System.out.println("✅ Great! No overdue books at the moment.");
            } else {
                System.out.printf("%-15s | %-15s | %-15s%n",
                        "Book ID", "Member ID", "Overdue Days");
                System.out.println("------------------------------------------------------------------");
                for (BorrowingTransaction transaction : overdueBookList) {
                    Member member = memberService.findMemById(transaction.getMemberId());
                    LocalDate dueDay = transaction.getBorrowDate().plusDays(member.getReturnDayLimit());
                    long overdueDays = ChronoUnit.DAYS.between(dueDay, currentDay);
                    System.out.printf("%-15s | %-15s | %-15d\n\n", transaction.getBookId(), transaction.getMemberId(), overdueDays);
                }
            }

        } catch (DateTimeException e) {
            System.out.println("❌ error, mismatch date input, date must be (dd/MM/yyyy).");
        }
    }

    private void handleDisplayMostPopularBook() {
        List<PopularBookRecord> popularList = reportService.mostPopularBook();
        if (popularList.isEmpty()) {
            System.out.println("No data available yet.");
        } else {
            System.out.printf("%-10s | %-30s | %-15s%n", "Book ID", "Title", "Times Borrowed");
            System.out.println("---------------------------------------------------------------");
            for (PopularBookRecord book : popularList) {
                System.out.printf("%-10s | %-30s | %-15d\n\n", book.getBook().getId(), book.getBook().getTitle(), book.getBookBorrowCount());
            }
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleDisplayMostBorrowMem() {
        List<MostBorrowMemRecord> memList = reportService.mostBorrowMem();
        if (memList.isEmpty()) {
            System.out.println("No data available yet.");
        } else {
            System.out.printf("%-10s | %-30s | %-15s%n", "Member ID", "Name", "Times Borrow");
            System.out.println("---------------------------------------------------------------");
            for (MostBorrowMemRecord member : memList) {
                System.out.printf("%-10s | %-30s | %-15d\n\n", member.getMember().getMemId(), member.getMember().getName(), member.getMemberBorrowCount());
            }
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }
}
