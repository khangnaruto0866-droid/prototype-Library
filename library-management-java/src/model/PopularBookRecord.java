package model;

public class PopularBookRecord {
    private Book book;
    private int bookBorrowCount;

    public PopularBookRecord(Book book, int bookBorrowCount) {
        this.book = book;
        this.bookBorrowCount = bookBorrowCount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getBookBorrowCount() {
        return bookBorrowCount;
    }

    public void setBookBorrowCount(int bookBorrowCount) {
        this.bookBorrowCount = bookBorrowCount;
    }
}
