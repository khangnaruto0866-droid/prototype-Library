package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReportService {
    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;

    public ReportService(BookService bookService, MemberService memberService, TransactionService transactionService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionService = transactionService;
    }

    public List<BorrowingTransaction>borrowedBook(){
        return transactionService.outBills();
    }

    public List<BorrowingTransaction>overDueBook(LocalDate currentDate){
        List<BorrowingTransaction> outBills = transactionService.outBills();
        List<BorrowingTransaction> overdueList=new ArrayList<>();
        for(BorrowingTransaction transaction:outBills){
            Member member =memberService.findMemById(transaction.getMemberId());
            LocalDate dueDay=transaction.getBorrowDate().plusDays(member.getReturnDayLimit());
            if(dueDay.isAfter(currentDate)){
                overdueList.add(transaction);
            }
        }
        return overdueList;
    }

    public List<PopularBookRecord>mostPopularBook() {
        List<BorrowingTransaction> allBills = transactionService.allBills();
        HashMap<String, Integer> popularCount = new HashMap<>();

        for (BorrowingTransaction transaction : allBills) {
            String bookId = transaction.getBookId();
            popularCount.put(bookId, popularCount.getOrDefault(bookId, 0) + 1);
        }

        List<PopularBookRecord>bookRecordList=new ArrayList<>();
        List<Book>allBook=bookService.displayBook();
        for(Book book:allBook){
            int point=popularCount.getOrDefault(book.getId(),0);
            bookRecordList.add(new PopularBookRecord(book,point));
        }

        bookRecordList.sort((b1,b2)->b2.getBookBorrowCount()-b1.getBookBorrowCount());
        return bookRecordList;
    }

    public List<MostBorrowMemRecord>mostBorrowMem(){
        List<BorrowingTransaction>allBills=transactionService.allBills();
        HashMap<String,Integer>mostBorrowCount=new HashMap<>();

        for(BorrowingTransaction transaction:allBills){
            String memId=transaction.getMemberId();
            mostBorrowCount.put(memId,mostBorrowCount.getOrDefault(memId,0)+1);
        }

        List<MostBorrowMemRecord>mostBorrowMemRecordList=new ArrayList<>();
        List<Member>allMem=memberService.displayMem();

        for(Member member:allMem){
            int point=mostBorrowCount.getOrDefault(member.getMemId(),0);
            mostBorrowMemRecordList.add(new MostBorrowMemRecord(member,point));
        }

        mostBorrowMemRecordList.sort((m1,m2)->m2.getMemberBorrowCount()- m1.getMemberBorrowCount());
        return mostBorrowMemRecordList;
    }
}
