package com.example.LEGAREA.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDetailEntity {
    private String staffId;
    private String name;
    private String division;
    private String firstName;
    private String lastName;
    private String position;
    private int age;
}