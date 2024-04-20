import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Arrays;

public class ImageChecker{

    public static BufferedImage readImage(String fileName, String filter, String type){
        try {    
            BufferedImage img = ImageIO.read(new File("images/"+ filter +"/Rendered" + type + fileName));
            System.out.println("Image read");
            return img;
        } catch (IOException e){
            System.out.println("File could not be found.");
            return null;
        }

    }

    public static boolean compareImages(BufferedImage img1, BufferedImage img2){
        int[] i1 = img1.getRGB(0, 0, img1.getWidth(), img1.getHeight(), null, 0, img1.getWidth());
        int[] i2 = img2.getRGB(0, 0, img2.getWidth(), img2.getHeight(), null, 0, img2.getWidth());

        if (Arrays.compare(i1, i2) == 0){
            return true;
        }
        return false;
    }

    public static void main(String args[]){
        BufferedImage i1 = readImage(args[0], args[1], "Parallel");
        BufferedImage i2 = readImage(args[0], args[1], "Serial");

        if (compareImages(i1, i2)){
            System.out.println("Images are the same");
        }else{
            System.out.println("Images are not the same");
        }


    }
}