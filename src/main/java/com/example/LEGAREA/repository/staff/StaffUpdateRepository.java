package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/***
 * 社員情報更新SQLを実行するクラス
 */
@Mapper
public interface StaffUpdateRepository {

    // 社員詳細の更新処理
    @Update("UPDATE staffinfo si " +
            "SET name = #{name}, division = #{division} " +
            "WHERE si.staffid = #{staffId}; " +

            "UPDATE staffdetail sd " +
            "SET firstName = #{firstName}, lastName = #{lastName}, " +
            "    position = #{position}, age = #{age} " +
            "WHERE sd.staffid = #{staffId}")
    void updateStaff(StaffDetailEntity staffDetailEntity);


}
