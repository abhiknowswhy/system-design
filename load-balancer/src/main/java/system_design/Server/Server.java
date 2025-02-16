package system_design.Server;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final String serverIP;
    private final String serverGeoLocation;
    private final Map<String, Integer> activeConnections = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(String serverIP, String serverGeoLocation) {
        this.serverIP = serverIP;
        this.serverGeoLocation = serverGeoLocation;
    }

    public CompletableFuture<String> processRequest(Map<String, Map<String, String>> request) {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, String> headers = request.get("headers");
            Map<String, String> body = request.get("body");

            String clientIP = headers.get("IP Address");
            String geoLocation = headers.get("Geo Location");
            String name = body.get("name");

            // Simulate delay
            activeConnections.put(clientIP, activeConnections.getOrDefault(clientIP, 0) + 1);
            try {
                if (this.serverGeoLocation.equals(geoLocation)){
                    Thread.sleep(200); // 0.2-second delay
                }
                else {
                    Thread.sleep(2000); // 2-second delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Handle request based on active connections
            String response;
            if (activeConnections.size() < 2) {
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", you are connected to server at " + serverIP + " located in " + serverGeoLocation;
            } else {
                response = "Hi " + name + " using " + clientIP + " from " + geoLocation +
                        ", we are experiencing heavy traffic at the moment";
            }

            // Remove client from active connections after processing
            activeConnections.remove(clientIP);

            System.out.println(response);

            return response;
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

