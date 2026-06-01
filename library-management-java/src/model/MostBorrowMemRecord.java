package model;

public class MostBorrowMemRecord {
    private Member member;
    private int memberBorrowCount;

    public MostBorrowMemRecord(Member member, int memberBorrowCount) {
        this.member = member;
        this.memberBorrowCount = memberBorrowCount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getMemberBorrowCount() {
        return memberBorrowCount;
    }

    public void setMemberBorrowCount(int memberBorrowCount) {
        this.memberBorrowCount = memberBorrowCount;
    }
}
