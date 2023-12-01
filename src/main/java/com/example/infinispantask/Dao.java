package com.example.infinispantask;

import com.example.infinispantask.entities.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class Dao {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8081/";

    public Department getFullDepartmentInfo() {
        Department department = sendDepartmentRequest(baseUrl);
        if (department != null) {
            System.out.println("Department: " + department);
            return department;
        } else {
            System.out.println("Failed to get a response from the server.");
            throw new IllegalArgumentException("Failed to get a response from the server.");
        }
    }

    public String getDepartmentName() {
        String name = sendNameRequest(baseUrl + "name");
        if (name != null) {
            System.out.println("Name: " + name);
            return name;
        } else {
            System.out.println("Failed to get a response from the server.");
            throw new IllegalArgumentException("Failed to get a response from the server.");
        }
    }

    private String sendNameRequest(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Department sendDepartmentRequest(String url) {
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
