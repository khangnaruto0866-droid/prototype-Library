package model;

public class RegularMember extends Member{

    public RegularMember(String memId, String name, String phone, String email) {
        super(memId, name, phone, email);
    }

    @Override
    public int getBorrowLimit(){
        return 3;
    }

    @Override
    public int getReturnDayLimit(){
        return 7;
    }

    @Override
    public double getFineFee(int dayOverdue){
        return 5.0*dayOverdue;
    }
}
