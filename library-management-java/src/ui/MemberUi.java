package ui;

import model.Member;
import model.RegularMember;
import model.VipMember;
import service.MemberService;

import java.util.List;
import java.util.Scanner;

public class MemberUi {
    private Scanner sc;
    private MemberService memberService;

    public MemberUi(Scanner sc, MemberService memberService) {
        this.sc = sc;
        this.memberService = memberService;
    }

    public void start(){
        boolean isRunning=true;
        while(isRunning){
            System.out.println("\n\n\n\n\n\n\n\n\n========== MEMBER MANAGEMENT ==========");
            System.out.println("1. Add New Member");
            System.out.println("2. Update Member");
            System.out.println("3. View All Members");
            System.out.println("4. Remove Member");
            System.out.println("5. Search Members");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            try{
                int choice=Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:{
                        handleAddMem();
                        break;
                    }

                    case 2:{
                        handleFixMem();
                        break;
                    }

                    case 3:{
                        handleDisplayMem();
                        break;
                    }

                    case 4:{
                        handleDeleteMem();
                        break;
                    }

                    case 5:{
                        handleSearchMem();
                        break;
                    }

                    case 0:{
                        isRunning = false;
                        break;
                    }

                    default:
                        System.out.println("must enter number from 0-5");
                        System.out.print("Press ENTER to continue...");
                        sc.nextLine();
                }

            }catch (NumberFormatException e){
                System.out.println("must enter number from 0-5");
            }
        }
    }

    private void handleAddMem(){
        System.out.println("\n\n\n\n\n\n\n\n\n--- ADD NEW MEMBER ---");
        try{
            System.out.print("Member ID: ");
            String memId=sc.nextLine();
            System.out.print("Full Name: ");
            String name=sc.nextLine();
            System.out.print("Phone: ");
            String phone=sc.nextLine();
            System.out.print("Email: ");
            String email=sc.nextLine();
            System.out.println("\n[1] Save as Regular Member\n[2] Save as Premium Member\n[3] Cancel");
            System.out.print("Select: ");
            while(true){
                String confirm=sc.nextLine();
                if(confirm.equals("1")){
                    Member member = new RegularMember(memId,name,phone,email);
                    memberService.addMem(member);
                    System.out.println("✅ Regular Member added successfully.");
                    break;
                }

                else if(confirm.equals("2")){
                    Member member = new VipMember(memId,name,phone,email);
                    memberService.addMem(member);
                    System.out.println("✅ Premium Member added successfully.");
                    break;
                }

                else if (confirm.equals("3")) {
                    System.out.println("🚫 Action cancelled.");
                    break;

                } else {
                    System.out.println("❌ Invalid choice! Please select 1, 2, or 3.");
                }
            }

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }

    private void handleFixMem(){
        System.out.println("\n\n\n\n\n\n\n\n\n--- UPDATE MEMBER ---");
        try{
            System.out.print("Enter Member ID: ");
            String memId= sc.nextLine();
            Member member=memberService.findMemById(memId);
            if(member==null){
                System.out.println("this is wrong id");
            }

            else{
                System.out.println("Current Information:");
                System.out.println("Name: " + member.getName());
                System.out.println("Phone: " + member.getPhone());
                System.out.println("Email: " + member.getEmail());

                System.out.print("\nEnter new Full Name (leave blank to skip): ");
                String name=sc.nextLine();
                String finalName=name.isEmpty() ? member.getName():name;

                System.out.print("Enter new Phone (leave blank to skip): ");
                String phone =sc.nextLine();
                String finalePhone=phone.isEmpty() ? member.getPhone():phone;

                System.out.print("Enter new Email (leave blank to skip): ");
                String email=sc.nextLine();
                String finaleEmail=email.isEmpty() ? member.getEmail():email;

                System.out.println("\n[1] Update [2] Cancel");
                System.out.print("Select: ");
                while(true){
                    String confirm=sc.nextLine();
                    if(confirm.equals("1")){
                        memberService.fixMemInf(memId,name,phone,email);
                        System.out.println("✅ Member updated successfully.");
                        break;
                    }

                    else if(confirm.equals("2")){
                        System.out.println("🚫 Action cancelled.");
                        break;
                    }

                    else{
                        System.out.println("❌ Invalid choice! Please select 1 or 2.");
                    }
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            e.getMessage();
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }


    private void handleDeleteMem(){
        System.out.println("\n\n\n\n\n\n\n\n--- REMOVE MEMBER ---");
        try{
            System.out.print("Enter Member ID to remove: ");
            String memId=sc.nextLine();
            Member member=memberService.findMemById(memId);
            if(member==null){
                System.out.println("wrong id");
            }
            else{
                System.out.println("Member Found:");
                System.out.println("Name: " + member.getName());
                System.out.println("Phone: " + member.getPhone());
                System.out.println("Email: " + member.getEmail());
                System.out.println("\n⚠️ WARNING: Are you sure you want to delete this member?");
                System.out.println("[1] Confirm Remove [2] Cancel");
                System.out.println("Select: ");

                while(true){
                    String confirm=sc.nextLine();
                    if(confirm.equals("1")){
                        memberService.removeMem(memId);
                        System.out.println("✅ Member removed successfully.");
                        break;
                    }

                    else if(confirm.equals("2")){
                        System.out.println("🚫 Action cancelled.");
                        break;
                    }

                    else{
                        System.out.println("❌ Invalid choice! Please select 1 or 2.");
                    }
                }
            }

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }



    private void handleDisplayMem(){
        List<Member> allMem=memberService.displayMem();
        if(allMem.isEmpty()){
            System.out.println("there's no mem on the list");
        }
        else{
            System.out.println("\n\n\n\n\n\n\n\n\n--- MEMBER LIST ---");
            System.out.printf("%-10s | %-25s | %-15s | %-25s%n", "ID", "Full Name", "Phone", "Email");
            System.out.println("--------------------------------------------------------------------------------");
            for(Member member:allMem){
                System.out.printf("%-10s | %-25s | %-15s | %-25s\n\n", member.getMemId(), member.getName(), member.getPhone(), member.getEmail());
            }
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();

    }



    private void handleSearchMem(){
        System.out.println("\n\n\n\n\n\n\n\n--- SEARCH MEMBERS ---");
        try{
            System.out.print("Enter keyword (Name or ID): ");
            String keyword=sc.nextLine();
            List<Member>foundMem=memberService.searchMem(keyword);
            if(foundMem==null||foundMem.isEmpty()){
                System.out.println("cannot found anyone with that keyword");
            }
            else{
                System.out.println("\n\n--- MEMBER LIST ---");
                System.out.printf("%-10s | %-25s | %-15s | %-25s%n", "ID", "Full Name", "Phone", "Email");
                System.out.println("--------------------------------------------------------------------------------");
                for(Member member:foundMem){
                    System.out.printf("%-10s | %-25s | %-15s | %-25s\n\n", member.getMemId(), member.getName(), member.getPhone(), member.getEmail());
                }
            }

        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.print("\nPress ENTER to return...");
        sc.nextLine();
    }
}
