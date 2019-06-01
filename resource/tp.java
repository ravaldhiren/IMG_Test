import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

 
          

public class HexParser {  

    public static void main(String[] args) throws FileNotFoundException { 
        File file = new File("/home/syam/Downloads/sample2.txt"); Scanner sc = new Scanner(file); 
        while (sc.hasNext()) { String hex = sc.nextLine(); 
            System.out.println("hex : " + hex + " val : " + hexToBinary(hex)); } sc.close(); }  

    public static String hexToBinary(String hex) { String binaryString = Integer.toBinaryString(Integer.decode(hex)); 
        return padLeft(binaryString, 32 - binaryString.length()); }  

    private static String padLeft(String s, int n) { return String.format("%0" + n + "d", 0) + s; }  

    private static int getPointsScored(String binaryStr) { return Integer.parseInt(binaryStr.substring(0, 2), 2); } 
}