import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.IOException;

/**
 * @author Dylan Tasdhary
 * @version 2022.08.16
 */
public class MeanFilterSerial {

    // begin time of filtering
    private static long begin;

    // end time of filtering
    private static long runTime;

    // width of image
    private static int width;

    // height of image
    private static int height;

    // BufferedImage variable
    private static BufferedImage img = null;

    // width of window
    private static int windowWidth;

    // Arrays storing the Red, Green, and Blue values of the surrounding and middle pixel
    public static int buffR, buffG, buffB;

    /**
     * Receives an input image and converts it to a BufferedImage to make use of functionality for rendering
     * @param fileName name of the inputted file with extension
     * @return Returns a BufferedImage that was input in the terminal
     */
    public static BufferedImage readImage(String fileName){
        
        reset();
        
        try {    
            img = ImageIO.read(new File("images/input/" + fileName));
            System.out.println("Image read");
            return img;
        } catch (IOException e){
            System.out.println("File could not be found.");
            return null;
        }
    }

    /**
     * Resets the img, begin, runtime, width and height variables to process a new image
     */
    public static void reset(){
        begin = runTime = width = height = 0;
        img = null;
    }

    /**
     * Receives a BufferedImage, name of input image, and the type of rendering that has occured and creates the image saved in "images/" directory in the
     * respective folder i.e input/Median or input/Mean
     * @param img rendered BufferedImage object
     * @param fileName input file image name 
     * @param type type of rendering that occured i.e. which filter and algorithm was applied
     */
    public static void outputImage(BufferedImage img, String fileName, String type){
        
        try{
            ImageIO.write(img, "jpg", new File("images/" + type + "/RenderedSerial" + fileName));
        } catch (IOException e){
            System.out.println("Exception occured: " + e.getMessage());
        }
        System.out.println("Image succesfully written");
        System.out.println("Time taken: " + runTime);
    }

    /**
     * Finds the average of the values in a given array
     * @param arr array we wish to find the average value of 
     * @return average/mean value in integer form
     */
    public static int average(int[] arr){

        int length = arr.length;
        int sum = 0;

        for (int i = 0; i < length; i++){
            sum += arr[i];
        }

        return (sum/length);
    }

    /**
     * Applies the Median Filter serially to the input image
     * @return Returns the rendered output image as a BufferedImage object
     */
    public static BufferedImage applyFilter(){
        
        // Number of pixels surrounding the centre of the block
        int surroundPixels = (windowWidth -1)/2;

        // Limits of looping - ensures there's enough pixels to fit block
        int xBoundary = width - surroundPixels;
        int yBoundary = height - surroundPixels;

        BufferedImage outputImg = new BufferedImage(width, height, img.getType());

        begin = System.currentTimeMillis();

        
        // Sliding the centre of the block across the entire image
        for (int x = surroundPixels; x < xBoundary; x++){
            for (int y = surroundPixels; y < yBoundary; y++){

                // Finding the topmost left and botttommost right corner of the window
                int yStart = y - surroundPixels;
                int yEnd = yStart + windowWidth;

                int xStart = x - surroundPixels;
                int xEnd = xStart + windowWidth;

                buffR = buffG = buffB = 0;
                // Looping through pixels contained within window
                for (int xBlock = xStart; xBlock < xEnd; xBlock++){
                    for (int yBlock = yStart; yBlock < yEnd; yBlock++){

                        int p = img.getRGB(xBlock, yBlock);

                        // getting red
                        buffR += (p>>16) & 0xff;
                        
                        // getting green
                        buffG += (p>>8) & 0xff;

                        // getting blue
                        buffB += p & 0xff;
                    }
                }

                int r = buffR/(windowWidth * windowWidth);
                int g = buffG/(windowWidth * windowWidth);
                int b = buffB/(windowWidth * windowWidth);

                int renderedP = (r << 16) | (g << 8) | b;
                outputImg.setRGB(x, y, renderedP);
            }
        }
        runTime = System.currentTimeMillis() - begin;

        return outputImg;
    }
    /**
     * PLEASE IGNORE - USED FOR DATA GATHERING
     * @param input
     */
    public static void experiment(BufferedImage input){
        
        long min = Long.MAX_VALUE;

        for (int width=3; width <= 15; width+=2){
            windowWidth = width;
            for (int i =0; i <5;i++){
                BufferedImage rendered = applyFilter();
                if (runTime < min){
                    min = runTime;
                }
            }
            System.out.print(min + ",");
            min = Long.MAX_VALUE;
        }
        
    }

    public static void main(String[] args){
        
        // Ensuring enough command line arguments
        if (args.length != 3){
            System.out.println("Please ensure correct amount of arguments");
            System.exit(0);
        }
        
        String inputName = args[0];
        String outputName = args[1];
        windowWidth = Integer.parseInt(args[2]);

       /* BufferedImage img = readImage(inputName);
        width = img.getWidth();
        height = img.getHeight();
        experiment(img);
       */ 
        if ((windowWidth >= 3) && ((windowWidth % 2)==1)){

            // Reading in image to BufferedImage type
            img = readImage(inputName);

            // Getting properties of inputted image
            width = img.getWidth();
            height = img.getHeight();

            BufferedImage rendered=null;
            rendered = applyFilter();
          
            // Writing rendered image to "images" folder
            outputImage(rendered, outputName, "Mean");
        } else{
            System.out.println("Please input the correct window size (>= 3 and an odd number)");
            System.exit(0);
        }

    }


}

