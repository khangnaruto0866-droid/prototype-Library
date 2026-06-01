package app;

import service.*;
import ui.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BookService bookService=new BookService();
        MemberService memberService=new MemberService();
        TransactionService transactionService =new TransactionService(bookService,memberService);
        ReportService reportService=new ReportService(bookService,memberService,transactionService);

        BookUi bookUi= new BookUi(sc,bookService);
        MemberUi memberUi= new MemberUi(sc,memberService);
        TransactionUi transactionUi= new TransactionUi(sc,transactionService);
        ReportUi reportUi= new ReportUi(sc,memberService,reportService);
        MainUi mainUi=new MainUi(sc,bookUi,memberUi,transactionUi,reportUi,bookService,memberService,transactionService);

        transactionService.syncBookList();

        mainUi.start();
    }
}