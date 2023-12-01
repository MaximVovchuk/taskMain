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
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public Department getFullDepartmentInfo() {
        Department department = sendDepartmentRequest(baseUrl);
        System.out.println("Department: " + department);
        return department;
    }

    public String getDepartmentName() {
        String name = sendNameRequest(baseUrl + "name");
        System.out.println("Name: " + name);
        return name;
    }

    private String sendNameRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handleResponseErrors(response);
            return response.body();
        } catch (Exception e) {
            handleRequestException(e);
            return null;
        }
    }

    private Department sendDepartmentRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            handleResponseErrors(response);
            return objectMapper.readValue(response.body(), Department.class);
        } catch (Exception e) {
            handleRequestException(e);
            return null;
        }
    }

    private void handleResponseErrors(HttpResponse<?> response) {
        if (response.statusCode() != 200) {
            System.err.println("Error: " + response.statusCode());
            throw new IllegalArgumentException("Failed to get a successful response from the server.");
        }
    }

    private void handleRequestException(Exception e) {
        e.printStackTrace();
        throw new IllegalArgumentException("Failed to execute the request.", e);
    }
}
