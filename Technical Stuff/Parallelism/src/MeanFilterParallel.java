import java.io.BufferedInputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Dylan Tasdhary
 * @version 2022.08.16
 */


public class MeanFilterParallel extends RecursiveAction{
    private int start; // starting x-value
    private int stripWidth; //width of vertical strip
    private BufferedImage input;
    private BufferedImage output;
    private int yBoundary=0; // max y boundary to loop till, will be reassigned later on
    private int surroundPixels; // number of pixels to the left of centre pixel of window all contained within the window
    private int numWPixels; // number of pixels in the window
    private int r, g, b;



    private static int windowWidth;
    private static long runTime;
    static int SEQUENTIAL_CUTOFF=50;

    /**
     * Creating instance of MeanFilterParallel
     * @param input input image
     * @param output output image
     * @param start starting x-value
     * @param stripWidth width of strip
     */
    public MeanFilterParallel(BufferedImage input, BufferedImage output, int start, int stripWidth){
        this.input = input;
        this.start = start;
        this.stripWidth = stripWidth;
        this.output = output;
        surroundPixels = windowWidth/2;
        numWPixels = windowWidth * windowWidth;
        r=g=b=0;
    }  
   
    /**
     * Checks if stripWidth is less than sequential cutoff to determine whether to halve again, or to start applying filter to strips
     */
    protected void compute(){
        if (stripWidth <= SEQUENTIAL_CUTOFF){
            applyFilter();
            return;
        } else{
            int halve = stripWidth/2;
            MeanFilterParallel left = new MeanFilterParallel(input, output, start, halve);
            MeanFilterParallel right = new MeanFilterParallel(input, output, start + halve, stripWidth-halve);
            left.fork();
            right.compute();
            left.join();
        }
    }

    /**
     * Creates first instance of MedianFilterParallel and creates the ForkJoinPool. Timing of algorithm occurs here
     * @param input input image
     * @return output image
     */
    public static BufferedImage render(BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage output = new BufferedImage(width, height, input.getType());
        
        MeanFilterParallel mfp = new MeanFilterParallel(input, output, 0, width);
        ForkJoinPool pool = new ForkJoinPool();

        long beginTime = System.currentTimeMillis();
        pool.invoke(mfp);
        runTime = System.currentTimeMillis() - beginTime;

       // System.out.println("Image render took " + runTime + " milliseconds");
        return output;
    }
    
    /**
     * Applies the Mean Filter in parallel to the input image
     * @return Returns the rendered output image as a BufferedImage object
     */
    public void applyFilter(){
        int iWidth = input.getWidth();
        int iHeight = input.getHeight();

        start = Math.max(start, surroundPixels);
        int remainingPixels = iWidth - surroundPixels - start;
        stripWidth = Math.min(stripWidth, remainingPixels);

        int xBoundary = start + stripWidth;
        yBoundary = iHeight - surroundPixels;
        
        // Looping through the original image to obtain mean values by "sliding" the centre of the window across the pixels
        // Starting at surround and ending at respective boundaries to ensure window fits 
        for (int x = start; x < xBoundary; x++){
            for (int y = surroundPixels; y < yBoundary; y++){

                // Ending position of the block i.e. bottommost-right corner
                int yEnd = y + surroundPixels;
                int xEnd = x + surroundPixels;

                r = g = b = 0;
                // Looping through pixels contained in window
                for (int xBlock = x - surroundPixels; xBlock <= xEnd; xBlock++){
                    for (int yBlock = y - surroundPixels; yBlock <= yEnd; yBlock++){
                        int p = input.getRGB(xBlock, yBlock);

                        r += (p>>16) & 0xff;
                        g += (p>>8) & 0xff;
                        b += p & 0xff;
                    }
                }
                r = r/numWPixels;
                g = g/numWPixels;
                b = b/numWPixels;

                int renderedP = ((r << 16) | (g << 8) | b);
                output.setRGB(x, y, renderedP);
            }
        }
    }

    /**
     * Simulates the experiment to both gather data and find optimal sequential cutoff.
     * @param input
     */
    public static void experiment(BufferedImage input){
        
        long min = Long.MAX_VALUE;
        //int minSeq=0;

        //for (int seq=10; seq < 500; seq+=10){
        for (int width=3; width <= 15; width+=2){
            windowWidth = width;
            for (int i =0; i <5;i++){
                //SEQUENTIAL_CUTOFF = seq;
                BufferedImage rendered = render(input);
                if (runTime < min){
                    min = runTime;
                    //minSeq=seq;
                }
            }
            System.out.print(min + ",");
            min = Long.MAX_VALUE;
        }
        //System.out.println(minSeq);
        
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
        /*

        SEQUENTIAL_CUTOFF = Integer.parseInt(args[2]);
        BufferedImage input = readImage(inputName);
        experiment(input);
        */
 
        if ((windowWidth >= 3) && ((windowWidth % 2)==1)){
            BufferedImage input = readImage(inputName);

            BufferedImage rendered=null;
            rendered = render(input);
            // Writing rendered image to "images" folder
            
            outputImage(rendered, outputName, "Mean");
        } else{
            System.out.println("Please input the correct window numWPixels (>= 3 and an odd number)");
            System.exit(0);
        }
        //*/
    }

     /**
     * Receives an input image and converts it to a BufferedImage to make use of functionality for rendering
     * @param fileName name of the inputted file with extension
     * @return Returns a BufferedImage that was input in the terminal
     */
    public static BufferedImage readImage(String fileName){
        try {    
            BufferedImage input = ImageIO.read(new File("images/input/" + fileName));
            System.out.println("Image read");
            return input;
        } catch (IOException e){
            System.out.println("File could not be found.");
            System.exit(0);
            return null;
        }
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
            ImageIO.write(img, "jpg", new File("images/" + type + "/RenderedParallel" + fileName));
        } catch (IOException e){
            System.out.println("Exception occured: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Image succesfully written");
    }


}