package system_design.Client;

import java.util.HashMap;
import java.util.Map;

public class Client {
    private final String ipAddress;
    private final String geoLocation;
    private final String name;

    public Client(String ipAddress, String geoLocation, String name) {

        this.ipAddress = ipAddress;
        this.geoLocation = geoLocation;
        this.name = name;
    }

    public Map<String, Map<String, String>> Request() {

        Map<String, Map<String, String>> request = new HashMap<>();

        // Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("IP Address", ipAddress);
        headers.put("Geo Location", geoLocation);

        // Body
        Map<String, String> body = new HashMap<>();
        body.put("name", name);

        request.put("headers", headers);
        request.put("body", body);

        return request;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public String getName() {
        return name;
    }
}

