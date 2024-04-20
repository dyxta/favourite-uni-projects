import java.util.Arrays;

public class Map {
    private int width;
    private int height;
    public int xLower, yLower = 0;
    public int xUpper, yUpper;

    public String[][] map;

    Map(int width, int height){
        this.width = width;
        this.height = height;
        xUpper = width-1;
        yUpper = height-1;

        map = new String[width][height];
        this.initialiseMap();
    }

    String getMap(Boolean mine, Coordinate coord){
        String s = "";
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                
                if (mine){
                    if ((coord.x == x) && (coord.y == y)){
                        map[x][y] = "+";
                    }
                }
                s = s.concat(map[x][y]);
            }
            s = s.concat("\n");
        }
        s = s.substring(0, s.length()-1);
        s = s.replace("*", ".");
        s = s.replace("+", "*");

        return s;
        
    }

    void initialiseMap(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                map[x][y] = ".";
            }
        }
    }

    // Formatting mine to [x,y]
    Coordinate processCoord(String coord){

        // Returning string array i.e. [x,y]
        return new Coordinate(coord.split(", "));
    }

    // Adding mine to map instance
    void addMine(Coordinate coord){
        
        // Getting x value of mine
        int x = coord.x;

        // Getting y value of mine
        int y = coord.y;

        map[x][y] = "*";
    }

    // Checks if mine is there
    boolean checkMine(Coordinate coord){
        int x = coord.x;
        int y = coord.y;

        if (map[x][y].equals("*")){
            return true;
        }
        return false;
    }

    // Gets number of adjacent mines
    int getAdjacent(Coordinate coord){

        int xL = Math.max(coord.x-1, xLower);
        int xU = Math.min(coord.x+1, xUpper);

        int yL = Math.max(coord.y-1, yLower);
        int yU = Math.min(coord.y+1, yUpper);
        
        int count = 0;
        for (int x = xL; x <= xU; x++){
            for (int y = yL; y <= yU; y++){
                if (map[x][y].equals("*")){
                    count++;
                }
            }
        }
        return count;
    }

    void NoAdj(Coordinate coord){
        int xL = Math.max(coord.x-1, xLower);
        int xU = Math.min(coord.x+1, xUpper);

        int yL = Math.max(coord.y-1, yLower);
        int yU = Math.min(coord.y+1, yUpper);

        for (int x = xL; x <= xU; x++){
            for (int y = yL; y <= yU; y++){
                
                String[] adjCoord = new String[2];
                adjCoord[0] = String.valueOf(x);
                adjCoord[1] = String.valueOf(y);

                map[x][y] = String.valueOf(getAdjacent(new Coordinate(adjCoord)));
            }
        }

    }

    void addToMap(Coordinate coord, int adjacent){
        map[coord.x][coord.y] = String.valueOf(adjacent);
    }


    void input(String coord){

        Coordinate test = processCoord(coord);

        if (test.x < 0 || test.x > width-1 || test.y < 0 || test.y > height-1){
            System.out.println("Invalid input.");
        }else{
            if (!(checkMine(test))){
                int adjacentMines = getAdjacent(test);

                if (adjacentMines == 0){
                    NoAdj(test);
                }else{
                    addToMap(test, getAdjacent(test));
                }
                System.out.println("Final state:");
                System.out.println(getMap(checkMine(test), test));
            }else{
                System.out.println("Boom!");
                System.out.println("Final state:");
                System.out.println(getMap(checkMine(test), test));
            }
        }
    }

    public static void main(String[] args) {
        Map m = new Map(4, 4);

    
        m.addMine(m.processCoord("0, 2"));
        m.addMine(m.processCoord("1, 3"));
        m.addMine(m.processCoord("3, 3"));

        m.input("0, 2");
    }
}
