package com.example.infinispantask.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lector implements Serializable {
    private Long id;
    private String fullName;
    private Degree degree;
    private BigDecimal salary;
}
