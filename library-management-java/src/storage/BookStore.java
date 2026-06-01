package storage;

import model.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookStore {
    private static final String FILE_PATH="data/books.txt";

    private String bookToLine(Book book){
        return book.getId() + "|"
                + book.getTitle() + "|"
                + book.getAuthor() + "|"
                + book.getGenre() + "|"
                + book.getPublicationYear() + "|"
                + book.getQuantity();
    }

    public void saveBook(List<Book> bookList){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Book book:bookList){
                writer.write(bookToLine(book));
                writer.newLine();
            }
            System.out.println("💾 Saving data to files successfully");

        }catch (IOException e){
            System.out.println("❌ error cannot write file"+e.getMessage());
        }
    }

    private Book lineToBook(String line){
        String[] part=line.split("\\|");
        String id=part[0];
        String title=part[1];
        String author=part[2];
        String genre=part[3];
        int publicationYear=Integer.parseInt(part[4]);
        int quantity=Integer.parseInt(part[5]);
        return new Book(id,title,author,genre,publicationYear,quantity);
    }

    public List<Book>loadBook(){
        List<Book>bookList=new ArrayList<>();
        File file = new File(FILE_PATH);

        if(!file.exists()){
            return bookList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line= reader.readLine())!=null){
                if(line.trim().isEmpty()){
                    continue;
                }

                Book book = lineToBook(line);
                bookList.add(book);
            }

        }catch (Exception e){
            System.out.println("❌ Error while reading "+e.getMessage());
        }

        return bookList;
    }
}
