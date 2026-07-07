package com.example.LEGAREA.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffEntity {
    private String staffId;
    private String name;
    private String division;
}
