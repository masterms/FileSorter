package gifsorter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;
/** Program to sort file and put them into directories based on date
 * 
 * 
 * @author masterms
 * @version 1.0
 * @
 */

public class GifSorter {
    /**Changes name of a file with increasable number
     * 
     * 
     * 
     * @param path
     * @param s 
     * @param number 
     * @param data 
     */
    public static void nameChange(String path, String s, int number, String data){
        Path source = Paths.get(path+s);
        Path destination = Paths.get(path+data+"\\"+number+"."+s.split("\\.")[1]);
        try{
            Files.move(source, destination, REPLACE_EXISTING);
        }catch(IOException e){
            System.out.println("Program can't open a file");            
        }
    }
    
    
    /**Main method responsible for moving files
     * implements treeSet with own comparator
     * 
     * 
     * 
     * @param path
     * @throws IOException 
     */
    public static void moveGifs(String path) throws IOException{
        Comparator<File> comparator = (File a, File b) -> {
            Date dateA = new Date(a.lastModified());            
            Date dateB = new Date(b.lastModified());            
            return dateA.compareTo(dateB);
        };
        
        File folder = new File(path);
        TreeSet<File> tree = new TreeSet<>(comparator);
        for(int i=0; i<folder.listFiles().length;i++){
            if(folder.list()[i].contains(".")==true){
            File file = new File(path+folder.list()[i]);
            tree.add(file);
            }   
        }
        
        
        
        
        for(int i=0; i<tree.size();i++){
            File first= tree.first();
            Date date = new Date(first.lastModified());
            SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yy");
            String dateText = df2.format(date);
            Path folderName = Paths.get(path+dateText);
            Files.createDirectories(folderName);            
            int number = 1;
            if(folderName.toFile().list()!=null){
                 number = folderName.toFile().list().length+1;
            }
            nameChange(path,tree.first().getName(),number,dateText);
            tree.remove(first);
            i--;


        }
 
    }
    public static void main(String[] args) throws IOException {  
        String absolutePath="";
        moveGifs(absolutePath);
    }   
}