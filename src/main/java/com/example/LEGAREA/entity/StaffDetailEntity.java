package com.example.LEGAREA.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 社員詳細情報のデータを格納するクラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDetailEntity {
    // 社員コード
    private String staffId;
    // 名前
    private String name;
    // 部署
    private String division;
    // セイ
    private String firstName;
    // メイ
    private String lastName;
    // 役職
    private String position;
    // 年齢
    private int age;
}