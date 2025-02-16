package system_design.LoadBalancer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import system_design.Server.Server;

public abstract class LoadBalancer {
    protected List<Server> servers;
    protected Map<Server, Integer> connectionCount = new ConcurrentHashMap<>();

    public LoadBalancer(List<Server> servers) {
        this.servers = servers;
        for (Server server : servers) {
            connectionCount.put(server, 0);
        }
    }

    public abstract Server selectServer();

    public void addServer(Server server) {
        servers.add(server);
        connectionCount.put(server, 0);
    }

    public void removeServer(Server server) {
        servers.remove(server);
        connectionCount.remove(server);
    }

    public String handleRequest(Map<String, Map<String, String>> request) {
        if (servers.isEmpty()) {
            return "No available servers!";
        }
        Server selectedServer = selectServer();
        return selectedServer.processRequest(request).join();
    }
}
