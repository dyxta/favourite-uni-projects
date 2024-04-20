import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;

public class Undermine{
    static String[][] outputMap;
    static Map m;
    static int width, height;
    
    static void initialiseMap(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                outputMap[x][y] = ".";
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter the name of the data file:");

        Scanner input = new Scanner(System.in);

        String filename = input.nextLine();
        

        try{
            FileReader f = new FileReader(filename);
            BufferedReader br = new BufferedReader(f);
            // Getting width and height from file

            String line = br.readLine();
            String[] lineNew = line.split(" ");


            width = Integer.parseInt(lineNew[0]);
            height = Integer.parseInt(lineNew[1]);
            
            // Creating a map with mines
            m = new Map(width, height);

            // This is the output map
            outputMap = new String[width][height];
            initialiseMap();

            line = br.readLine();

            // Getting mine number
            int minesNo = Integer.parseInt(line);

            for (int i = 0; i < minesNo; i++){
                line = br.readLine();
                m.addMine(m.processCoord(line));
            }


        }catch (IOException e){
            System.out.println("This file does not exist");
            System.exit(0);
        }
        System.out.println("Width " + width + ", height " + height + " top-left is (0, 0).");
        System.out.println("Enter a location (as two space-separated integers):");
        
        String line = input.nextLine();
        line = line.replace(" ", ", ");
        m.input(line);
    }
}
