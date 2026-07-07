package com.example.LEGAREA.repository.staff;


import com.example.LEGAREA.entity.StaffDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StaffUpdateRepository {
    //staffinfo テーブルの更新
    @Update("UPDATE staffinfo " +
            "SET name = #{name}, division = #{division} " +
            "WHERE staffid = #{staffId}")
    void updateStaffInfo(StaffDetailEntity entity);

    //staffdetail テーブルの更新
    @Update("UPDATE staffdetail " +
            "SET firstName = #{firstName}, lastName = #{lastName}, " +
            "position = #{position}, age = #{age} " +
            "WHERE staffid = #{staffId}")
    void updateStaffDetail(StaffDetailEntity entity);
}
