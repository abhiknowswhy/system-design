package system_design.Server;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final String serverIP;
    private final String serverGeoLocation;
    private final Map<String, Integer> activeConnections = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private Random random = new Random();

    public Server(String serverIP, String serverGeoLocation) {
        this.serverIP = serverIP;
        this.serverGeoLocation = serverGeoLocation;
    }

    public void processRequest(Map<String, Map<String, String>> request) {
        CompletableFuture.runAsync(() -> {
            Map<String, String> headers = request.get("headers");
            Map<String, String> body = request.get("body");

            String clientIP = headers.get("IP Address");
            String geoLocation = headers.get("Geo Location");
            String name = body.get("name");

            // Handle request based on active connections
            String response;
            if (activeConnections.size() < 2) {
                activeConnections.put(clientIP, activeConnections.getOrDefault(clientIP, 0) + 1);
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", you are connected to server at " + serverIP + " located in " + serverGeoLocation;
    
                // Remove client from active connections after processing
                activeConnections.remove(clientIP);
            } else {
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", we are experiencing heavy traffic at the moment";
            }

            // Simulate delay
            try {
                if (this.serverGeoLocation.equals(geoLocation)){
                    Thread.sleep(200+random.nextInt(50)); // 0.2-second delay
                }
                else {
                    Thread.sleep(2000+random.nextInt(500)); // 2-second delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println(response);

        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public String getGeoLocation() {
        return this.serverGeoLocation;
    }

    public String getServerIP() {
        return this.serverIP;
    }
}

