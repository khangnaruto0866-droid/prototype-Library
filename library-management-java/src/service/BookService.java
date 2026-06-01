package service;

import model.Book;
import storage.BookStore;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private BookStore bookStore;
    private List<Book>books;


    public BookService(){
        this.bookStore = new BookStore();
        books=validateBookList(bookStore.loadBook());
    }

    public Book findBookById(String bookId){
        for(Book book:books){
            if(book.getId().equals(bookId)){
                return book;
            }
        }
        return null;
    }

    public void addBook(Book newBook){
        if(newBook==null){
            throw new IllegalArgumentException("this book is not exist");
        }
        String tempBookId = newBook.getId();
        if(findBookById(tempBookId)!=null){
            throw new IllegalArgumentException("book has already existed on list");
        }
        books.add(newBook);
    }

    public List<Book> displayBook(){
        return this.books;
    }

    public List<Book> searchBook(String keyWord){
        if(keyWord.trim().isEmpty()){
            throw new IllegalArgumentException("key word cannot blank");
        }
        ArrayList<Book> tempList =new ArrayList<>();
        for(Book book:books){
            if(book.getTitle().toLowerCase().contains(keyWord.toLowerCase())||
               book.getAuthor().toLowerCase().contains(keyWord.toLowerCase())||
               book.getGenre().toLowerCase().contains(keyWord.toLowerCase())){
                tempList.add(book);
            }
        }
        return tempList;
    }

    public void removeBook(String bookId){
        Book book = findBookById(bookId);
        if(book ==null){
            throw new IllegalArgumentException("id not exist");
        }

        if(book.getBorrowedCount()>0){
            throw new IllegalArgumentException("to remove, these books must have been returned all");
        }

        books.remove(book);
    }

    public void fixBookInf(String id, String title, String author, String genre, int publicationYear, int quantity){
        Book book = findBookById(id);
        if(book==null){
            throw new IllegalArgumentException("this id does not exist");
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublicationYear(publicationYear);
        book.setQuantity(quantity);
    }

    public void save(){
        bookStore.saveBook(this.books);
    }

    private List<Book> validateBookList(List<Book>bookList){
        if(bookList==null){
            bookList=new ArrayList<>();
        }
        return bookList;
    }

}


