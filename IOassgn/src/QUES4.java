import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class QUES4 {
    public static void main(String[] args) {
        Path employeePath= Paths.get("C://Users//BhoomikaManral//Desktop//IOAssignment//employee.csv");
        Path processPath=Paths.get("processed.csv");
        long totalSalary=0L;
        long highestSalary=0;
        List<String[]> data = new ArrayList<>();
        try{
            List<String> lines= Files.readAllLines(employeePath);
            for(int i=1;i<lines.size();i++){
                String[] content=lines.get(i).split(",");
                int id=Integer.parseInt(content[0].trim());
                String name=content[1].trim();
                long salary=Long.parseLong(content[2].trim());
                totalSalary+=salary;
                if(salary>highestSalary) highestSalary=salary;
                int newSalary = (int) (salary * 1.1);
                data.add(new String[]{String.valueOf(id),name,String.valueOf(newSalary)});
            }
            List<String> outputLines=new ArrayList<>();
            outputLines.add("id,name,salary");
            for(String[] rows:data){
                outputLines.add(String.join(",",rows));
            }
            Files.write(processPath,outputLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            System.out.println("Highest salary is "+highestSalary);
            System.out.println("Average salary is"+(int)(totalSalary/data.size()));
        }catch (IOException e){
            System.out.println("Files cannot be processed");
        }
    }
}
