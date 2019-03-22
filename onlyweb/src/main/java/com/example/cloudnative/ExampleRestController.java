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

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
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
        return ResponseEntity.ok(getHelloWorldMessage());
    }

    @GetMapping("/status-5xx")
    public ResponseEntity<Map<String, String>> status5xx() {
        COUNT++;
        if (COUNT > 5) {
            throw new RuntimeException("Count " + COUNT);
        }

        final Map<String, String> map = getHelloWorldMessage();
        map.put("count", String.valueOf(COUNT));

        return ResponseEntity.ok(map);
    }

    private Map<String, String> getHelloWorldMessage() {
        final HashMap<String, String> map = new HashMap<>();

        map.put("app.version", appVersion);
        map.put("host.address", getHostAddress());
        map.put("message", env.getProperty("MESSAGE", "Hello Fellas!!!"));

        return map;
    }

    @GetMapping("/exit-1")
    public ResponseEntity<Map<String, String>> exit1() {
        final boolean flag = true;
        if (flag) {
            System.exit(1);
        }
        return ResponseEntity.ok(new HashMap<>());
    }

    @GetMapping("/exit-0")
    public ResponseEntity<Map<String, String>> exit0() {
        final boolean flag = true;
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
