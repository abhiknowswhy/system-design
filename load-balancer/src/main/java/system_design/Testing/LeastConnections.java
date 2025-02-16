package system_design.Testing;

import java.util.Arrays;
import java.util.List;

import system_design.Client.Client;
import system_design.LoadBalancer.LoadBalancer;
import system_design.LoadBalancer.LoadBalancerFactory;
import system_design.Server.Server;

public class LeastConnections {
    public static void main(String[] args) {
        // Create Servers
        Server server1 = new Server("192.168.1.1", "New York");
        Server server2 = new Server("192.168.1.2", "Los Angeles");
        Server server3 = new Server("192.168.1.3", "Chicago");

        List<Server> servers = Arrays.asList(server1, server2, server3);

        // Create Load Balancer using Factory
        LoadBalancer loadBalancer = LoadBalancerFactory.createLoadBalancer(
                LoadBalancerFactory.Strategy.LEAST_CONNECTIONS, servers);

        // Create Clients
        Client client1 = new Client("10.0.0.1", "New York", "Alice");
        Client client2 = new Client("10.0.0.2", "New York", "Bob");
        Client client3 = new Client("10.0.0.3", "New York", "Charlie");
        Client client4 = new Client("10.0.0.4", "Chicago", "Dorothy");
        Client client5 = new Client("10.0.0.5", "New York", "Eustass");

        // Handle Requests

        loadBalancer.handleRequest(client1.Request());
        loadBalancer.handleRequest(client2.Request());
        loadBalancer.handleRequest(client3.Request());
        loadBalancer.handleRequest(client4.Request());
        loadBalancer.handleRequest(client5.Request());}
}
