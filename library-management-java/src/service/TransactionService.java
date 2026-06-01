package service;

import model.Book;
import model.BorrowingTransaction;
import model.Member;
import storage.TransactionStore;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private TransactionStore transactionStore;
    private List<BorrowingTransaction>transactions;
    private BookService bookService;
    private MemberService memberService;

    public TransactionService(BookService bookService, MemberService memberService) {
        this.transactionStore=new TransactionStore();
        this.bookService = bookService;
        this.memberService = memberService;
        transactions=validateBillList(transactionStore.loadBill());
    }

    public void borrowBook(String bookId, String memberId, LocalDate borrowDay){
        Book book =bookService.findBookById(bookId);
        Member member =memberService.findMemById(memberId);
        if(book==null||member==null){
            throw new IllegalArgumentException("wrong book id or name id");
        }

        if(!book.isAvailable()){
            throw new IllegalStateException("run out of stock");
        }

        if(member.totalBorrow()== member.getBorrowLimit()){
            throw new IllegalArgumentException("this member reached borrow limit");
        }

        List<Book> bookOnMem =member.showBorrowedBook();
        for(Book b:bookOnMem){
            if(b.getId().equals(bookId)){
                throw new IllegalArgumentException("cannot borrow the same book");
            }
        }

        member.borrow(book);
        book.setBorrowedCount(book.getBorrowedCount()+1);
        BorrowingTransaction bill=new BorrowingTransaction(bookId,memberId,borrowDay);
        transactions.add(bill);
    }

    public  BorrowingTransaction findTransaction(String bookId,String memId){
        for(BorrowingTransaction transaction:transactions){
            if(transaction.getBookId().equals(bookId)&&
               transaction.getMemberId().equals(memId)&&
               transaction.getReturnDate()==null){
                return transaction;
            }
        }
        return null;
    }

    public void returnBook(String bookId,String memId,LocalDate returnDate){
        BorrowingTransaction transaction=findTransaction(bookId,memId);
        if(transaction==null){
            throw new IllegalArgumentException("can't find, all information must be true to purchase");
        }

        transaction.setReturnDate(returnDate);

        Member member=memberService.findMemById(memId);
        Book book=bookService.findBookById(bookId);

        book.setBorrowedCount(book.getBorrowedCount()-1);
        member.returnBook(book);

        LocalDate dueDate=transaction.getBorrowDate().plusDays(member.getReturnDayLimit());
        int overdueDay=transaction.calculateOverdueDay(dueDate);
        if(overdueDay>0){
            member.getFineFee(overdueDay);
        }
    }

    public List<BorrowingTransaction>outBills(){
        ArrayList<BorrowingTransaction>tempList=new ArrayList<>();
        for(BorrowingTransaction transaction:transactions){
            if(transaction.getReturnDate()==null){
                tempList.add(transaction);
            }
        }
        return tempList;
    }

    public List<BorrowingTransaction>allBills(){
        return transactions;
    }

    public List<BorrowingTransaction>borrowedMemHistory(String memId){
        if(memId.trim().isEmpty()){
            throw new IllegalArgumentException("please enter member id");
        }
        ArrayList<BorrowingTransaction>tempList=new ArrayList<>();
        for(BorrowingTransaction transaction:transactions){
            if(transaction.getMemberId().equals(memId)){
                tempList.add(transaction);
            }
        }
        return tempList;
    }

    public void save(){
        transactionStore.saveBill(this.transactions);
    }

    private List<BorrowingTransaction>validateBillList(List<BorrowingTransaction> bill){
        if(bill==null){
            bill=new ArrayList<>();
        }
        return bill;
    }

    public void syncBookList(){
        for(BorrowingTransaction bill : this.transactions){
            Member client=memberService.findMemById(bill.getMemberId());
            Book borrowedBook=bookService.findBookById(bill.getBookId());
            if(client!=null&&borrowedBook!=null){
                client.borrow(borrowedBook);
            }
        }
    }
}
