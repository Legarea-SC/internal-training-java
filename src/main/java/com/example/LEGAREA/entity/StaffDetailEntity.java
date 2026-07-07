package com.example.LEGAREA.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaffDetailEntity {
    private String staffid;
    private String name;
    private String division;
    private String firstName;
    private String lastName;
    private String position;
    private int age;
}