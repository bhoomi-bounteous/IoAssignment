/*
3) In a directory, multiple duplicate files may exist.
      1. Detect duplicate files based on :
             Same file size
             Same content(Byte Comparison)

*/
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QUES3 {
    public static boolean isEqual(Path f1,Path f2){
        try(InputStream is1=Files.newInputStream(f1);InputStream is2=Files.newInputStream(f2)){
            byte[] buffer1=new byte[8192];
            byte[] buffer2=new byte[8192];
            int byte1,byte2;
            while((byte1=is1.read(buffer1))!=-1){
                byte2=is2.read(buffer2);
                if(byte1!=byte2) return false;
                for(int i=0;i<byte1;i++){
                    if(buffer1[i]!=buffer2[i]) return false;
                }
            }
           return is2.read()==-1;
        }catch(IOException e){
            throw new RuntimeException("Error in comparing file");
        }
    }
    public static void main(String[] args) {
        Path pathDir= Paths.get("C://Users//BhoomikaManral//Downloads/");
        List<List<Path>> duplicates = new ArrayList<>();
        try(Stream<Path> paths= Files.walk(pathDir)){
            List<Path> files=paths.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            HashMap<Long,List<Path>> map=new HashMap<>();
            for(Path file : files){
                Long size=Files.size(file);
                map.computeIfAbsent(size,(k)->new ArrayList<>()).add(file);
            }
            for (List<Path> group : map.values()){
                if(group.size()>1){
                    boolean[] visited=new boolean[group.size()];
                     for(int i=0;i<group.size();i++){
                         if(visited[i]) continue;
                         List<Path> dup=new ArrayList<>();
                         dup.add(group.get(i));
                         Path f1=group.get(i);
                         for(int j=i+1;j<group.size();j++){
                             if(visited[j]) continue;
                             Path f2=group.get(j);
                             if(isEqual(f1,f2)){
                                 dup.add(f2);
                                 visited[j]=true;
                             }
                         }
                         if(group.size()>1) duplicates.add(dup);
                         visited[i]=true;
                     }
                }
            }
            for (List<Path> group : duplicates) {
                System.out.println("Duplicate Group:");
                group.forEach(f -> System.out.println("   " + f.toString()));
            }
        }catch (IOException e){
            System.out.println("Cannot be processed");
        }
    }
}
