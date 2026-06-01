package storage;

import model.BorrowingTransaction;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionStore {
    private static final String FILE_PATH="data/bills.txt";

    private String transactionToLine(BorrowingTransaction transaction){
        String borrowDate=(transaction.getBorrowDate().toString());

        return transaction.getBookId() + "|"
                + transaction.getMemberId() + "|"
                + borrowDate;
    }

    public void saveBill(List<BorrowingTransaction> billList){
        try(BufferedWriter writer =new BufferedWriter(new FileWriter(FILE_PATH))){
            for(BorrowingTransaction transaction:billList){
                writer.write(transactionToLine(transaction));
                writer.newLine();
            }
            System.out.println("💾 Saving data to files successfully");

        }catch (IOException e){
            System.out.println("❌ error cannot write file\"+e.getMessage())");
        }
    }

    private BorrowingTransaction lineToBill(String line){
        String[] part=line.split("\\|");
        String bookId=part[0];
        String memId=part[1];
        LocalDate borrowDay=LocalDate.parse(part[2]);
        return new BorrowingTransaction(bookId,memId,borrowDay);
    }

    public List<BorrowingTransaction>loadBill(){
        List<BorrowingTransaction>billList=new ArrayList<>();
        File file=new File(FILE_PATH);

        if(!file.exists()){
            return billList;
        }

        try (BufferedReader reader=new BufferedReader(new FileReader(file))){
            String line;
            while((line=reader.readLine())!=null){
                if(line.trim().isEmpty()){
                    continue;
                }
                BorrowingTransaction bill=lineToBill(line);
                billList.add(bill);
            }

        }catch (Exception e){
            System.out.println("❌ Error while reading "+e.getMessage());
        }

        return billList;
    }
}
