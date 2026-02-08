package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
/**
 * 社員情報削除SQLを実行するクラス
 */
public interface StaffDeleteRepository {

    // 社員詳細の更新処理
    @Delete("DELETE FROM staffdetail " +
            "WHERE staffid = #{staffId}; "+

            "DELETE FROM staffinfo " +
            "WHERE staffid = #{staffId}; "
            )
    void deleteStaff(StaffDetailEntity staffDetailEntity);
}
