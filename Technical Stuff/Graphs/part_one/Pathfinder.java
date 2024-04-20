public class Pathfinder {
    

    // Note: first use Dijkstra's to find the nearest taxi per client
    // Then use Dijkstra to find the nearest store from the taxi
    

    /**
     * Finds the nearest taxi to a client
     * 
     * @param g Graph passed which contains the vertexes needed to run Dijkstra's algorithm
     * @param client The client we want to find the nearest to
     */
    public void findTaxi(Graph g, String client, String[] shopVertices){
        
        // Need to have code to check if both nearestTaxi and nearestShop run first before printing
        String nearestTaxi;
        String nearestShop;
        try{
            nearestTaxi = findNearestTaxi(g, client, shopVertices);
            nearestShop = findNearestShop(g, client, shopVertices);

            nearestTaxi = nearestTaxi.substring(0, nearestTaxi.length() -1);
            nearestShop = nearestShop.substring(0, nearestShop.length() -1);
            
            System.out.println("client " + client);
            System.out.println(nearestTaxi);
            System.out.println(nearestShop);
        } catch(Exception e){
            System.out.println("client " + client + "\ncannot be helped");
        }
    }

    /**
     * This method will return the output of the nearest taxi for a certain client as well as
     * the path taken to reach that client
     * 
     * @param g Graph passed which contains the vertexes needed to run Dijkstra's algorithm
     * @param client The client we want to find the nearest taxi for
     * @param shopVertices The list of shop vertices' names
     */
    public String findNearestTaxi(Graph g, String client, String[] shopVertices){
        double min = Double.MAX_VALUE;
        String minTaxi ="";
        // Looping through shops to find closest taxi 
        for (String shop : shopVertices){
            
            // Running dijkstra on the shop to find closest path to everything
            g.dijkstra(shop);
            
            // Getting the distance from that shop to client
            double d = g.getDistance(client);

            if (min > d){
                min = d;
                minTaxi = shop;
            }
        }
        return checkForMultiple(g, client, shopVertices, false, min, minTaxi);
    }

    public String checkForMultiple(Graph g, String client, String[] shopVertices, boolean isClient, double minCost, String minNode){

        String result = "";

        for (String shop : shopVertices){

            if (isClient){
                g.dijkstra(client);

                double d = g.getDistance(shop);

                if (g.dijkstra(client, minNode, minCost) && minNode.equals(shop)){
                    result = result + "shop " + shop + "\nmultiple solutions cost " +
                        Integer.toString((int)minCost) + "\n";
                }
                else if (d==minCost){
                    result = result + "shop " + shop + "\n" + g.printPath(shop) + "\n";
                }
            }
            else{
                g.dijkstra(shop);

                double d = g.getDistance(client);

                if (g.dijkstra(shop, client, minCost)){
                    result = result + "taxi " + shop + "\nmultiple solutions cost " 
                        + Integer.toString((int)minCost) + "\n";
                }
                else if (d==minCost){
                    g.dijkstra(shop);
                    result = result + "taxi " + shop +  "\n" + g.printPath(client) + "\n";
                } 
            }
        }
        return result;
    }


    public String findNearestShop(Graph g, String client, String[] shopVertices){
        double min = Double.MAX_VALUE;
        String minShop = "";
        // Looping through shops to find nearest 
        for (String shop : shopVertices){
            
            // Running dijkstra on the shop to find closest path to everything
            g.dijkstra(client);
            
            // Getting the distance from that shop to client
            double d = g.getDistance(shop);

            if (min > d){
                min = d;
                minShop = shop;
            }
        }
        
        return checkForMultiple(g, client, shopVertices, true, min, minShop);
    }
}
