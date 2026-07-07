package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StaffInputRepository {
    // staffinfo テーブルに追加
    @Insert("INSERT INTO staffinfo (staffid, name, division) " +
            "VALUES (#{staffId}, #{name}, #{division})")
    void staffcreate(StaffEntity staff);

    // staffdetail テーブルに追加
    @Insert("INSERT INTO staffdetail (staffid, firstname, lastname, age, position) " +
            "VALUES (#{staffId}, #{firstName}, #{lastName}, #{age}, #{position})")
    void staffDetailcreate(StaffDetailEntity staffDetail);
}
