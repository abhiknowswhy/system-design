package system_design.LoadBalancer.Implementations;

import java.util.List;

import system_design.LoadBalancer.LoadBalancer;
import system_design.Server.Server;

public class LeastConnectionsLoadBalancer extends LoadBalancer {

    public LeastConnectionsLoadBalancer(List<Server> servers) {
        super(servers);
    }

    @Override
    public Server selectServer() {
        return servers.stream()
                .min((s1, s2) -> Integer.compare(connectionCount.get(s1), connectionCount.get(s2)))
                .orElse(null);
    }
}
