package model;

public class VipMember extends Member{
    public VipMember(String memId, String name, String phone, String email) {
        super(memId, name, phone, email);
    }

    @Override
    public int getBorrowLimit(){
        return 5;
    }

    @Override
    public int getReturnDayLimit(){
        return 14;
    }

    @Override
    public double getFineFee(int dayOverdue){
        return 3.0*dayOverdue;
    }
}
