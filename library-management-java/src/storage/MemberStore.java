package storage;

import model.Member;
import model.RegularMember;
import model.VipMember;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberStore {
    private static final String FILE_PATH = "data/member.txt";

    private String memberToLine(Member member) {
        String type = "";
        if (member instanceof VipMember) {
            type = "VIP";
        } else {
            type = "REGULAR";
        }

        return type + "|"
                + member.getMemId() + "|"
                + member.getName() + "|"
                + member.getPhone() + "|"
                + member.getEmail();
    }

    public void saveMember(List<Member> memberList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Member member : memberList) {
                writer.write(memberToLine(member));
                writer.newLine();
            }
            System.out.println("💾 Saving data to files successfully");

        } catch (IOException e) {
            System.out.println("❌ error cannot write file " + e.getMessage());
        }
    }

    private Member lineTomember(String line) {
        String[] part = line.split("\\|");
        String type = part[0];
        String memId = part[1];
        String name = part[2];
        String phone = part[3];
        String email = part[4];

        if (type.equals("VIP")) {
            return new VipMember(memId, name, phone, email);
        }

        return new RegularMember(memId, name, phone, email);
    }

    public List<Member> loadMember() {
        List<Member> memberList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return memberList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                Member member = lineTomember(line);
                memberList.add(member);
            }

        } catch (Exception e) {
            System.out.println("❌ Error while reading " + e.getMessage());
        }

        return memberList;
    }
}
