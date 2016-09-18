import java.io.*;
import java.util.ArrayList;

/**
 * Created by kay on 17.09.2016.
 */
public class FReader {
    private String fileName;
    private String charSet;

    public FReader(String fileName, String charSet) {
        this.fileName = fileName;
        this.charSet = charSet;
    }

    public void print_bytes() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)))){
            int bv;
            while ((bv = bis.read()) > -1) {
                System.out.print(bv + " ");
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void print_chars() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)))) {
            int bv;
            while ((bv = bis.read()) > -1) {
                System.out.print((char)bv);
            }
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void println(){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),charSet))){
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<String[]> readToAL(String spliter){
        ArrayList<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)),charSet))){
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line.split(spliter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
