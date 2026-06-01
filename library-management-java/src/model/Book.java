package model;

public class Book {
    private final String id;
    private String title;
    private String author;
    private String genre;
    private int publicationYear, quantity, borrowedCount;

    public Book(String id, String title, String author, String genre, int publicationYear, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.borrowedCount=0;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title==null||title.trim().isEmpty()){
            throw new IllegalArgumentException("the title cannot be blank");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author==null||author.trim().isEmpty()){
            throw new IllegalArgumentException("author cannot be blank");
        }
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if(genre==null||genre.trim().isEmpty()){
            throw new IllegalArgumentException("genre cannot be blank");
        }
        this.genre = genre;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if(publicationYear<1440||publicationYear>2026){
            throw new IllegalArgumentException("invalid publication year (1440<x<2026)");
        }
        this.publicationYear = publicationYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity<0){
            throw new IllegalArgumentException("quantity error, can't be negative");
        }
        this.quantity = quantity;
    }

    public boolean isAvailable(){
        return(this.quantity-this.borrowedCount)>0;
    }

    public String getStatus() {
        if(isAvailable()){
            return "Available";
        }
        return "out of stock";
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

}
