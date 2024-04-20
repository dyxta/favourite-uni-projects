import java.util.ArrayList;
import java.util.Scanner;

class Apart{
    class Coordinate{
        int x, y;

        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }

        int getX(){ return x;}
        int getY(){ return y;}
    }
    
    ArrayList<Coordinate> coordinates = new ArrayList<>();
    ArrayList<Double> distances = new ArrayList<>();

    void getInput(){
        Scanner input = new Scanner(System.in);
        int numPoints = input.nextInt();

        String trash = input.nextLine();

        for (int i=0; i < numPoints; i++){

            int x = input.nextInt();
            int y = input.nextInt();

            coordinates.add(new Coordinate(x, y));
        }
    }

    void getDistance(Coordinate src, Coordinate dest){
        double distance;
        distance = Math.abs(src.getX() - dest.getX()) + Math.abs(src.getY()-dest.getY());
        distances.add(distance);
    }

    void getAllDistances(){
        for (int i = 0; i < coordinates.size()-1; i++){
            for (int j = i + 1; j < coordinates.size(); j++){
                Coordinate src = coordinates.get(i);
                Coordinate dest = coordinates.get(j);
                getDistance(src, dest);
            }
        }
    }

    double getAverage(){
        double sum = 0;
        int count = 0;
        for (double distance : distances){
            sum += distance;
            count++;
        }

        return (sum / count);
    }
    
    public static void main(String[] args) {
        Apart a = new Apart();
        
        a.getInput();
        a.getAllDistances();
        System.out.printf("%.2f\n", a.getAverage());
    }
}