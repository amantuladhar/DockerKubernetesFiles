package com.example.cloudnative;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ExampleRestController {

    // To throw Internal Server Error after 5 calls
    private static Integer COUNT = 0;

    private final Environment env;
    private final String appVersion;

    public ExampleRestController(Environment env, @Value("${app.version}") String appVersion) {
        this.env = env;
        this.appVersion = appVersion;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> helloWorld() {

        final HashMap<String, String> map = new HashMap<>();

        final String greeting = env.getProperty("GREETING", "Hello");
        final String name = env.getProperty("NAME", "Fellas!!");
        final String message = greeting + " " + name;

        map.put("message", message);
        map.put("host.address", getHostAddress());
        map.put("app.version", appVersion);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/status-5xx")
    public ResponseEntity<Map<String, String>> status5xx() {
        COUNT++;
        if (COUNT > 5) {
            throw new RuntimeException("Count " + COUNT);
        }
        final HashMap<String, String> map = new HashMap<>();

        map.put("message", "Status 5xx");
        map.put("count", String.valueOf(COUNT));
        map.put("host.address", getHostAddress());
        map.put("app.version", appVersion);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/exit-1")
    public ResponseEntity<Map<String, String>> exit1() {
        boolean flag = true;
        if (flag) {
            System.exit(1);
        }
        return ResponseEntity.ok(new HashMap<>());
    }

    @GetMapping("/exit-0")
    public ResponseEntity<Map<String, String>> exit0() {
        boolean flag = true;
        if (flag) {
            System.exit(0);
        }
        return ResponseEntity.ok(new HashMap<>());
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            throw new RuntimeException();
        }
    }
}
