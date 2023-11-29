package com.example.infinispantask.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {
    private Long id;
    private String departmentName;
    private Lector headOfDepartment;
    private List<Lector> lectors;
}
