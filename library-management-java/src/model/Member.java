package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Member {
    private String memId;
    private String name;
    private String phone;
    private String email;
    private List<Book>borrowed;

    public Member(String memId, String name, String phone, String email) {
        this.memId = memId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        borrowed=new ArrayList<>();
    }

    public String getMemId() {
        return memId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null||name.trim().isEmpty()){
            throw new IllegalArgumentException("member name cannot be blank");
        }
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone==null||phone.trim().isEmpty()){
            throw new IllegalArgumentException("phone cannot be blank");
        }

        String cleanPhone=phone.trim();
        if(!cleanPhone.matches("^0[0-9]{9}$")){
            throw new IllegalArgumentException("invalid phone (Start with 0 then 9 more nums)");
        }
        this.phone = cleanPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email==null||email.trim().isEmpty()||!email.contains("@")){
            throw new IllegalArgumentException("email cannot be blank and must have '@'");
        }
        this.email = email;
    }

    public void borrow(Book book){
        borrowed.add(book);
    }

    public void returnBook(Book book){
        borrowed.remove(book);
    }

    public int totalBorrow(){
        return borrowed.size();
    }

    public List<Book> showBorrowedBook(){
        return this.borrowed;
    }

    public abstract int getBorrowLimit();
    public abstract int getReturnDayLimit();
    public abstract double getFineFee(int dayOverdue);
}
