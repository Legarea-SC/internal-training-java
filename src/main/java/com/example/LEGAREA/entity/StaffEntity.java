package com.example.LEGAREA.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 社員情報のデータクラス（一覧表示では本クラスをList化して使用する）
 */
@Getter
@AllArgsConstructor
public class StaffEntity {
    // 社員コード
    private String staffId;
    // 名前
    private String name;
    // 部署
    private String division;
}
