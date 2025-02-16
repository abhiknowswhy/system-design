package system_design.LoadBalancer.Implementations;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import system_design.LoadBalancer.LoadBalancer;
import system_design.Server.Server;

public class RoundRobinLoadBalancer extends LoadBalancer {
    @SuppressWarnings("FieldMayBeFinal")
    private AtomicInteger index = new AtomicInteger(0);

    public RoundRobinLoadBalancer(List<Server> servers) {
        super(servers);
    }

    @Override
    public Server selectServer() {
        if (servers.isEmpty()) return null;
        int currentIndex = index.getAndIncrement() % servers.size();
        return servers.get(currentIndex);
    }
}
