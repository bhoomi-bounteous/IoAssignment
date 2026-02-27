import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QUES2 {
    public static void main(String[] args) {
        Path rootDir= Paths.get("C://Users//BhoomikaManral//Downloads/");
        int totalFile=0;
        int totalDir=0;
        int totalSize=0;
        List<Path> allFiles=new ArrayList<>();
        try(Stream<Path> paths =Files.walk(rootDir)){
            List<Path> pathList=paths.collect(Collectors.toList());
            for(Path path : pathList){
                if (Files.isDirectory(path)) {
                    totalDir++;
                } else {
                    totalFile++;
                    long size = Files.size(path);
                    totalSize += size;
                    allFiles.add(path);
                }
            }
            List<Path> top5Largest =allFiles.stream()
                    .sorted(Comparator.comparingLong((Path p) -> {
                        try {
                            return Files.size(p);
                        } catch (IOException e) {
                            return 0L;
                        }
                    }).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            System.out.println("Total Files: " + totalFile);
            System.out.println("Total Directories: " + totalDir);
            System.out.println("Total File Size (bytes): " + totalSize);
            for (Path p : top5Largest) {
                System.out.println(p + " -> " + Files.size(p) + " bytes");
            }
          Path reportPath=Paths.get("report.txt");
            List<String> content= Arrays.asList(
                    "Total Files: " + totalFile,
                    "Total Directories: " + totalDir,
                    "Total File Size (bytes): " + totalSize
            );
            Files.write(reportPath,content, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        }catch (IOException e){
            System.out.println("task cannot be done");
        }
    }
}
