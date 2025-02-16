package system_design.LoadBalancer.Implementations;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import system_design.LoadBalancer.LoadBalancer;
import system_design.Server.Server;

public class GeoLocationLoadBalancer extends LoadBalancer {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Server> geoServerMap = new HashMap<>();

    public GeoLocationLoadBalancer(List<Server> servers) {
        super(servers);
        for (Server server : servers) {
            geoServerMap.put(server.getGeoLocation(), server);
        }
    }

    @Override
    public Server selectServer() {
        return servers.get(0); // Default return if no geo match (fallback)
    }

    public Server selectServer(String clientGeoLocation) {
        return geoServerMap.getOrDefault(clientGeoLocation, servers.get(0)); // Return closest server or fallback
    }

    @Override
    public void handleRequest(Map<String, Map<String, String>> request) {
        String clientGeoLocation = request.get("headers").get("Geo Location");
        Server selectedServer = selectServer(clientGeoLocation);
        selectedServer.processRequest(request);
    }

    // public void handleRequest(Map<String, Map<String, String>> request) {
    //     if (servers.isEmpty()) {
    //         System.out.println("No available servers!");
    //     } else {
    //         Server selectedServer = selectServer();
    //         selectedServer.processRequest(request);
    //     }
    // }

}

