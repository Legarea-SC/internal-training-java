package com.example.LEGAREA.repository.staff;

import enetity.StaffDatailEntity;
import enetity.StaffEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffRepository {

    @Select("SELECT staffid, name, division FROM staffInfo ORDER BY staffid;")
    List<StaffEntity> select();

    @Select("SELECT si.staffid, si.name, si.division, sd.firstName, sd.lastName, sd.position, sd.age " +
            "FROM staffinfo si " +
            "LEFT JOIN staffdetail sd ON si.staffid = sd.staffid " +
            "WHERE si.staffid = #{staffid}")
    List<StaffDatailEntity> selectByID(@Param("staffid") String staffid);

    @Insert("""
               INSERT INTO tasks (summary, description, status)
               VALUES (#{task.summary},#{task.description},#{task.Status})
            """)
    void insert(@Param("task") StaffEntity newEntity);

    @Delete("DELETE FROM staffinfo WHERE staffid = #{staffid}")
    void delete(@Param("staffid") String staffid);
}