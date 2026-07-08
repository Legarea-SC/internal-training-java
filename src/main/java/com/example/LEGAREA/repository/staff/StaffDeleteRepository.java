package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StaffDeleteRepository {

    // staffdetail を先に削除
    @Delete("DELETE FROM staffdetail " +
            "WHERE staffid = #{staffId}")
    void deleteStaffDetail(StaffDetailEntity entity);

    // staffinfo を後に削除
    @Delete("DELETE FROM staffinfo " +
            "WHERE staffid = #{staffId}")
    void deleteStaffInfo(StaffDetailEntity entity);
}

