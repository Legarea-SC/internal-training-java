package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.LEGAREA.entity.StaffInputEntity;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

@Mapper
public interface StaffRepository {

    //以下のメソッドが呼び出されると全件取得のSQLが走りリストとして返す
    @Select("SELECT staffid, name, division FROM staffInfo ORDER BY staffid;")
    List<StaffEntity> select();

    /*以下のメソッドが呼び出されると指定されたidの情報を検索して返す
    Optionalは指定IDの情報が存在しないかもしれない場合を考慮して使う
     */
    @Select("SELECT si.staffid,si.name,si.division,sd.firstName,sd.lastName,sd.position, sd.age " +
            "FROM staffinfo si " +
            "INNER JOIN staffdetail sd " +
            "ON si.staffid=sd.staffid " +
            "WHERE si.staffid = #{staffid} ")
    StaffDetailEntity selectByID(@Param("staffid") String staffid);

    @Insert("INSERT INTO staffinfo (staffid, name, division) " +
            "VALUES (#{staffId}, #{name}, #{division})")
    int insertStaffInfo(StaffInputEntity staffInput);

    @Insert("INSERT INTO staffdetail (staffid, firstName, lastName, position, age) " +
            "VALUES (#{staffId}, #{firstName}, #{lastName}, #{position}, #{age})")
    int insertStaffDetail(StaffInputEntity staffInput);

}
