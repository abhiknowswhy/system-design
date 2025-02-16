package system_design.Testing;

import java.util.Arrays;
import java.util.List;

import system_design.Client.Client;
import system_design.LoadBalancer.LoadBalancer;
import system_design.LoadBalancer.LoadBalancerFactory;
import system_design.Server.Server;

public class Random {
    public static void main(String[] args) {
        // Create Servers
        Server server1 = new Server("192.168.1.1", "New York");
        Server server2 = new Server("192.168.1.2", "California");
        Server server3 = new Server("192.168.1.3", "Texas");

        List<Server> servers = Arrays.asList(server1, server2, server3);

        // Create Load Balancer using Factory
        LoadBalancer loadBalancer = LoadBalancerFactory.createLoadBalancer(
                LoadBalancerFactory.Strategy.RANDOM, servers);

        // Create Clients
        Client client1 = new Client("10.0.0.1", "New York", "Alice");
        Client client2 = new Client("10.0.0.2", "Los Angeles", "Bob");
        Client client3 = new Client("10.0.0.3", "Chicago", "Charlie");

        // Handle Requests
        loadBalancer.handleRequest(client1.Request());
        loadBalancer.handleRequest(client1.Request());
        loadBalancer.handleRequest(client1.Request());
        loadBalancer.handleRequest(client2.Request());
        loadBalancer.handleRequest(client3.Request());
    }
}
