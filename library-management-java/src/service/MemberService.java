package service;

import model.Member;
import storage.MemberStore;

import java.util.ArrayList;
import java.util.List;

public class MemberService {
    private MemberStore memberStore;
    private List<Member> members;

    public MemberService(){
        this.memberStore= new MemberStore();
        members=validateMemList(memberStore.loadMember());
    }

    public Member findMemById(String memId){
        for(Member member:members){
            if (member.getMemId().equals(memId)){
                return member;
            }
        }
        return null;
    }

    public void addMem(Member member){
        if(member==null){
            throw new IllegalArgumentException("this mem does not exist");
        }

        String memberId=member.getMemId();
        if(findMemById(memberId)!=null){
            throw new IllegalArgumentException("this mem has already on the list");
        }
        members.add(member);
    }

    public List<Member> displayMem(){
        return this.members;
    }

    public List<Member>searchMem(String keyWord){
        if(keyWord.trim().isEmpty()){
            throw new IllegalArgumentException("key word cannot be blank");
        }
        ArrayList<Member>tempList=new ArrayList<>();
        for(Member member:members){
            if(member.getMemId().toLowerCase().contains(keyWord.toLowerCase())||
               member.getName().toLowerCase().contains(keyWord.toLowerCase())){
                tempList.add(member);
            }
        }
        return tempList;
    }

    public void removeMem(String memId){
       Member member =findMemById(memId);
       if(member==null){
           throw new IllegalArgumentException("this member is not exist");
       }

       if(!member.showBorrowedBook().isEmpty()){
           throw new IllegalArgumentException("to remove, member must return all book");
       }

       members.remove(member);
    }

    public void fixMemInf(String memId, String name, String phone, String email){
        Member member=findMemById(memId);
        if(member==null){
            throw new IllegalArgumentException("this id does not exist");
        }

        member.setName(name);
        member.setPhone(phone);
        member.setEmail(email);
    }

    public void save(){
        memberStore.saveMember(this.members);
    }

    private List<Member> validateMemList(List<Member>memberList){
        if(memberList==null){
            memberList=new ArrayList<>();
        }
        return memberList;
    }
}
