package enetity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StaffDatailEntity {
    private String staffid;
    private String name;
    private String division;
    private String firstName;
    private String lastName;
    private String position;
    private Integer age;
}