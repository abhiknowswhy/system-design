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
    private final Random random = new Random();

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


            int latency;
            if (this.serverGeoLocation.equals(geoLocation)){
                latency=200+random.nextInt(50); // 0.2-second latency
            }
            else {
                latency=2000+random.nextInt(200); // 0.2-second latency
            }

            // Simulate latency
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Handle request based on active connections
            String response;
            if (activeConnections.size() < 2) {
                activeConnections.put(clientIP, activeConnections.getOrDefault(clientIP, 0) + 1);
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", you are connected to server at " + serverIP + " located in " + serverGeoLocation + "\t| request latency = " + latency;
    
                // Remove client from active connections after processing
                activeConnections.remove(clientIP);
            } else {
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", we are experiencing heavy traffic at the moment";
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

