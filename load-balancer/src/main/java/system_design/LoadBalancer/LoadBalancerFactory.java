package system_design.LoadBalancer;

import java.util.List;

import system_design.LoadBalancer.Implementations.GeoLocationLoadBalancer;
import system_design.LoadBalancer.Implementations.LeastConnectionsLoadBalancer;
import system_design.LoadBalancer.Implementations.RandomLoadBalancer;
import system_design.LoadBalancer.Implementations.RoundRobinLoadBalancer;
import system_design.Server.Server;

public class LoadBalancerFactory {
    public enum Strategy {
        ROUND_ROBIN,
        LEAST_CONNECTIONS,
        RANDOM,
        GEO_LOCATION
    }

    public static LoadBalancer createLoadBalancer(Strategy strategy, List<Server> servers) {
        switch (strategy) {
            case ROUND_ROBIN -> {
                return new RoundRobinLoadBalancer(servers);
            }
            case LEAST_CONNECTIONS -> {
                return new LeastConnectionsLoadBalancer(servers);
            }
            case RANDOM -> {
                return new RandomLoadBalancer(servers);
            }
            case GEO_LOCATION -> {
                return new GeoLocationLoadBalancer(servers);
            }
            default -> throw new IllegalArgumentException("Unknown load balancing strategy");
        }
    }
}
