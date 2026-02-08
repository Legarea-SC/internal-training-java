package com.example.LEGAREA.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/***
 * 社員情報追加のためのデータクラス（メッセージを指定するため、別クラスとして用意）
 */
@Data
public class StaffInputEntity {
    // 社員コード
    @NotBlank(message = "社員コードは必須入力です")
    private String staffId;
    // 名前
    @NotBlank(message = "名前は必須入力です")
    private String name;
    // 部署
    @NotBlank(message = "部署は必須入力です")
    private String division;
    // セイ
    @NotBlank(message = "セイは必須入力です")
    private String firstName;
    // メイ
    @NotBlank(message = "メイは必須入力です")
    private String lastName;
    // 役職
    private String position;
    @NotNull(message = "年齢は必須入力です")
    // 年齢
    private Integer age;
}
