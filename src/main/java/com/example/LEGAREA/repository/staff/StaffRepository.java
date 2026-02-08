package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * 社員情報の件s九SQLを実行するクラス
 */
@Mapper
public interface StaffRepository {

    @Select("SELECT staffid, name, division FROM staffInfo ORDER BY staffid;")
    List<StaffEntity> select();

    @Select("SELECT si.staffid,si.name,si.division,sd.firstName,sd.lastName,sd.position, sd.age " +
            "FROM staffinfo si " +
            "INNER JOIN staffdetail sd " +
            "ON si.staffid=sd.staffid " +
            "WHERE si.staffid = #{staffid} ")
    StaffDetailEntity selectByID(@Param("staffid") String staffid);

}
