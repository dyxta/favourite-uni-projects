import java.io.BufferedInputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.io.IOException;
import java.util.Arrays;


public class MedianFilterParallel extends RecursiveAction{
    // start
    private int start; // Starting x for image processing
    private int stripWidth;
    private BufferedImage input;
    private BufferedImage output;
    private int mid; // middle of array 
    private int yBoundary=0; // max y-value to process till
    private int surroundPixels; // number of pixels to the left of the centre pixel contained in the window
    private int numWPixels;
    private int[] buffR;
    private int[] buffG;
    private int[] buffB;

    private static int windowWidth;
    private static long runTime;
    static int SEQUENTIAL_CUTOFF=50;

    /**
     * Constructing MeanFilterParallel instance
     * @param input input image
     * @param output output image
     * @param start starting x value for processing
     * @param stripWidth width of vertical strip
     */
    public MedianFilterParallel(BufferedImage input, BufferedImage output, int start, int stripWidth){
        this.input = input;
        this.start = start;
        this.stripWidth = stripWidth;
        this.output = output;
        mid = windowWidth/2;
        surroundPixels = mid;
        numWPixels = windowWidth * windowWidth;
        buffR = new int[numWPixels];
        buffG = new int[numWPixels];
        buffB = new int[numWPixels];
    } 
    /**
     * Accessor for output image
     * @return output image
     */
    public BufferedImage getOutput(){return output;}
    
    /**
     * Checks if stripWidth is less than sequential cutoff to determine whether to halve again, or to start applying filter to strips
     */
    protected void compute(){
        if (stripWidth <= SEQUENTIAL_CUTOFF){
            applyFilter();
            return;
        } else{
            int halve = stripWidth/2;

            // Left side given to a thread, dealing with the original start, and passing the next stripWidth which is half of what is was
            MedianFilterParallel left = new MedianFilterParallel(input, output, start, halve);

            // Right side dealt with in main thread. Same as left, except x starts at the halfway point and the stripWidth is what it was - halve
            // stripWidth-halve ensures that all pixels are processed
            MedianFilterParallel right = new MedianFilterParallel(input, output, start + halve, stripWidth-halve);
            left.fork(); // given to a thread
            right.compute(); // dealt with in "main" thread or current thread operating the class instance
            left.join(); // waiting for left side to finish
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
        
        MedianFilterParallel mfp = new MedianFilterParallel(input, output, 0, width);
        ForkJoinPool pool = new ForkJoinPool();

        // Timing of algorithm begins here
        long beginTime = System.currentTimeMillis();
        pool.invoke(mfp);
        runTime = System.currentTimeMillis() - beginTime; // Timing ends

        return output;
    }
    
    /**
     * Applies the Median Filter in parallel to the input image
     * @return Returns the rendered output image as a BufferedImage object
     */
    public void applyFilter(){

        int iWidth = input.getWidth();
        int iHeight = input.getHeight();

        // this just ensures that there's enough space for the window. If start < surroundPixels, then x value will be out of bounds
        start = Math.max(start, surroundPixels);
        int remainingPixels = iWidth - surroundPixels - start;

        // ensuring that the number of x values to be processed matches with the number of remaining pixels
        stripWidth = Math.min(stripWidth, remainingPixels);

        int xBoundary = start + stripWidth;
        yBoundary = iHeight - surroundPixels;
        
        // Looping through the original image to obtain median values by "sliding" the centre of the window across the pixels
        // Starting at surround and ending at respective boundaries to ensure window fits 
        for (int x = start; x < xBoundary; x++){
            for (int y = surroundPixels; y < yBoundary; y++){

                // Ending position of the block i.e. bottommost-right corner
                int yEnd = y + surroundPixels;
                int xEnd = x + surroundPixels;

                int index =0;

                // Looping through pixels contained in window
                for (int xBlock = x - surroundPixels; xBlock <= xEnd; xBlock++){
                    for (int yBlock = y - surroundPixels; yBlock <= yEnd; yBlock++){
                        int p = input.getRGB(xBlock, yBlock);

                        buffR[index] = (p>>16) & 0xff;
                        buffG[index] = (p>>8) & 0xff;
                        buffB[index] = p & 0xff;

                        index++;
                    }
                }
                Arrays.sort(buffR);
                Arrays.sort(buffG);
                Arrays.sort(buffB);

                int r = buffR[numWPixels/2];
                int g = buffG[numWPixels/2];
                int b = buffB[numWPixels/2];

                int renderedP = ((r << 16) | (g << 8) | b);
                output.setRGB(x, y, renderedP);
            }
        }
    }
    /**
     * PLEASE IGNORE - USED FOR FINDING OPT SEQ CUTOFF AND DATA COLLECTION
     * @param input input image
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
        /*/
 
        if ((windowWidth >= 3) && ((windowWidth % 2)==1)){
            BufferedImage input = readImage(inputName);

            BufferedImage rendered=null;
            rendered = render(input);
            // Writing rendered image to "images" folder
            
            outputImage(rendered, outputName, "Median");
        } else{
            System.out.println("Please input the correct window numWPixels (>= 3 and an odd number)");
            System.exit(0);
        }
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