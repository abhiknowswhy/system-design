package system_design.LoadBalancer.Implementations;

import java.util.List;
import java.util.Random;

import system_design.LoadBalancer.LoadBalancer;
import system_design.Server.Server;

public class RandomLoadBalancer extends LoadBalancer {
    @SuppressWarnings("FieldMayBeFinal")
    private Random random = new Random();

    public RandomLoadBalancer(List<Server> servers) {
        super(servers);
    }

    @Override
    public Server selectServer() {
        if (servers.isEmpty()) return null;
        return servers.get(random.nextInt(servers.size()));
    }
}
