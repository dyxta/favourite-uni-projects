import java.util.Scanner;

public class SimulatorOne{

    Scanner input = new Scanner(System.in);
    String trash;
    Graph g = new Graph();

    /**
     * Moving the cursor
     *      
     */
    public void trash()
    {
        trash = input.nextLine();
    }

    public Graph getGraph(){
        return g;
    }

    /**
     * Receives the number of clients and sets respective nodes to client = true
     * 
     * @param clientNum Number of clients
     */
    public String[] initiateClients(int clientNum){
        
        String result = "";

        // Looping through the shop nodes and assigning as shop
        for (int i = 0; i < clientNum; i++){
            
            String num = input.next();
            
            Vertex v = g.getVertex(num);
            v.client = true;

            result = result + num + " ";
        }
        trash();

        String[] clients = result.split(" ");
        return clients;
    }

    /**
     * Receives the number of shops and sets respective nodes to client = false
     * 
     * @param shopNum Number of shops
     */
    public String[] initiateShops(int shopNum){
        // Looping through the shop nodes and assigning as shop
        String result = "";

        for (int i = 0; i < shopNum; i++){
            String num = input.next();
            
            Vertex v = g.getVertex(num);
            v.client = false;

            result = result + num + " ";   
        }
        trash();

        String[] shops = result.split(" ");
        return shops;
    }

    /**
     * Receives inputs of the nodes and distances between source node on a line and the 
     * subsequent destination nodes
     * 
     * @param nodes Number of nodes
     */
    public void initiateNodes(int nodes){
        
        // Looping through the next n lines to get the source node and distances to destination nodes
        for (int i = 0; i<nodes; i++){
                
            // Inputting the line
            String line = input.nextLine();
            
            // Splitting the line into parts for easier manipulation
            String[] lineArr = line.split(" ");
            int size = lineArr.length;

            String sourceNode = lineArr[0];

            // Looping through the line 
            for (int index = 2; index < size; index+=2){
        
                String destNode = lineArr[index-1];
                double cost = Double.parseDouble(lineArr[index]);
                
                // Adding edge to map 
                g.addEdge(sourceNode, destNode, cost);
            }
        }
    }

    public static void main(String[] args){
        SimulatorOne s = new SimulatorOne();

        // Getting number of nodes, n,  (first input)
        int nodes = s.input.nextInt();
        s.trash();
        s.initiateNodes(nodes);

        // Getting number of shops
        int shopNum = s.input.nextInt();
        s.trash();
        String[] shopVertices = s.initiateShops(shopNum);


        // Getting number of clients
        int clientNum = s.input.nextInt();
        s.trash();
        String[] clientVertices = s.initiateClients(clientNum);

        //Pathfinder p = new Pathfinder();
        //p.findTaxi(s.getGraph(), clientVertices[1], shopVertices);
        
        // Finding the paths for the clients using the pathfinder class
        for (String client : clientVertices){
            Pathfinder p = new Pathfinder();
            p.findTaxi(s.getGraph(), client, shopVertices);
        }

    }
}