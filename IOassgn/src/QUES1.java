/*
1) you are given a large application log file (application.log) (Files should be generated first for it)
Using path and files API :
     count total number of INFO, WARN, ERROR
     Extract all errors lines into a new file : error.log
     Generate  a summary file summary.txt like below 
              INFO = 120
              WARN = 15
              ERROR = 8

*/

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;
public class QUES1 {
    public static void main(String[] args) {
        long infoCount=0;
        long errorCount=0;
        long warnCount=0;
        Path applicationPath=Paths.get("C://Users//BhoomikaManral//Desktop//IOAssignment//IOassgn//src//application.log/");
        Path errorPath=Paths.get("error.log");
        Path summaryPath=Paths.get("summary.txt");
        ArrayList<String> errorLines=new ArrayList<>();
        try(Stream<String> lines=Files.lines(applicationPath)){
            List<String> allLines=lines.collect(Collectors.toList());
            for(String line : allLines){
                if(line.contains("INFO")) infoCount++;
                else if (line.contains("ERROR")) {
                    errorCount++;
                    errorLines.add(line);
                }
                else if (line.contains("WARN")) {
                    warnCount++;
                }

            }
            Files.write(errorPath, errorLines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
            List<String> summary = Arrays.asList(
                    "INFO = " + infoCount,
                    "WARN = " + warnCount,
                    "ERROR = " + errorCount
            );
            Files.write(summaryPath, summary, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE);
        }catch (IOException e){
            System.out.println("File cannot be read");
        }
    }
}
