package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowingTransaction {
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingTransaction(String bookId, String memberId, LocalDate borrowDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int calculateOverdueDay(LocalDate dueDay){
        if(returnDate==null||returnDate.isBefore(dueDay)){
            return 0;
        }

        long dayBetween = ChronoUnit.DAYS.between(dueDay,returnDate);
        return (int)dayBetween;
    }
}
