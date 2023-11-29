package com.example.infinispantask;

import com.example.infinispantask.entities.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class Dao {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public Department checkInfo() {
        String url = "http://localhost:8081/";
        Department department = sendGetRequestAndParse(url);
        if (department != null) {
            System.out.println("Department: " + department);
            return department;
        } else {
            System.out.println("Failed to get a response from the server.");
            throw new IllegalArgumentException("Failed to get a response from the server.");
        }
    }

    private static Department sendGetRequestAndParse(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Department.class);
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
