package com.example.LEGAREA.entity;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffInputEntity {

    @NotBlank(message = "社員コードは必須入力です")
    private String staffId;

    @NotBlank(message = "名前は必須入力です")
    private String name;

    @NotBlank(message = "部署は必須入力です")
    private String division;

    @NotBlank(message = "セイは必須入力です")
    private String firstName;

    @NotBlank(message = "メイは必須入力です")
    private String lastName;

    private String position; // 任意

   @NotNull(message = "年齢は必須入力です")
    private Integer age;
}
