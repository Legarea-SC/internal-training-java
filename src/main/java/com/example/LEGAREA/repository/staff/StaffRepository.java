package com.example.LEGAREA.repository.staff;

import enetity.StaffDatailEntity;
import enetity.StaffEntity;
import enetity.StaffUpdateEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StaffRepository {

    @Select("SELECT staffid, name, division FROM staffInfo ORDER BY staffid;")
    List<StaffEntity> select();

    @Select("SELECT si.staffid,si.name,si.division,sd.firstName,sd.lastName,sd.position, sd.age\n" +
            "FROM staffinfo si\n" +
            "INNER JOIN staffdetail sd\n" +
            "ON si.staffid=sd.staffid\n" +
            "WHERE si.staffid = #{staffid}")
    List<StaffDatailEntity> selectByID(@Param("staffid") String staffid);

    @Insert("""
               INSERT INTO tasks (summary, description, status)
               VALUES (#{task.summary},#{task.description},#{task.Status})
            """)
    void insert(@Param("task") StaffEntity newEntity);

    @Select("SELECT si.staffid,si.name,si.division,sd.firstName,sd.lastName,sd.position,sd.age\n" +
            "FROM staffinfo si\n" +
            "INNER JOIN staffdetail sd\n" +
            "ON si.staffid=sd.staffid\n" +
            "WHERE si.staffid = #{staffid}")
    StaffUpdateEntity selectForUpdate(@Param("staffid") String staffid);

    @Update("UPDATE staffinfo si\n" +
            "INNER JOIN staffdetail sd ON si.staffid=sd.staffid\n" +
            "SET si.name=#{entity.name}, si.division=#{entity.division},\n" +
            "sd.firstName=#{entity.sei}, sd.lastName=#{entity.mei},\n" +
            "sd.position=#{entity.position}, sd.age=#{entity.age}\n" +
            "WHERE si.staffid=#{entity.staffid}")
    void update(@Param("entity") StaffUpdateEntity entity);

    @Delete("DELETE FROM staffinfo WHERE staffid = #{staffid}")
    void delete(@Param("staffid") String staffid);
}