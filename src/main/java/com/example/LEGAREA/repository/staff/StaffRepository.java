package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.service.staff.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface StaffRepository {

    //以下のメソッドが呼び出されると全件取得のSQLが走りリストとして返す
    @Select("SELECT staffid, name, division FROM staffInfo ORDER BY staffid;")
    List<StaffEntity> select();

    /*以下のメソッドが呼び出されると指定されたidの情報を検索して返す
    Optionalは指定IDの情報が存在しないかもしれない場合を考慮して使う
     */
    @Select("SELECT si.staffid,si.name,si.division,sd.firstName,sd.lastName,sd.position, sd.age\n" +
            "FROM staffinfo si\n" +
            "INNER JOIN staffdetail sd\n" +
            "ON si.staffid=sd.staffid\n" +
            "WHERE si.staffid = #{staffid}")
    List<StaffDetailEntity> selectByID(@Param("staffid") String staffid);

    @Insert("""
        INSERT INTO staffinfo (staffid, name, division)
        VALUES (#{staff.staffid}, #{staff.name}, #{staff.division})
""")
    int insert(@Param("staff") StaffEntity staffEntity);

    @Insert("""
    INSERT INTO staffdetail (staffid, firstName, lastName, position, age)
    VALUES (#{detail.staffid}, #{detail.firstName}, #{detail.lastName}, #{detail.position}, #{detail.age})
""")
    int insertDetail(@Param("detail") StaffDetailEntity detailEntity);

    @Update("""
        UPDATE staffinfo
        SET
            name = #{staff.name},
            division = #{staff.division}
        WHERE staffid = #{staff.staffid}
            """)
    int update(@Param("staff") StaffEntity staff);

    @Update("""
         UPDATE staffdetail
         SET
             firstName = #{staffdetail.firstName},
             lastName = #{staffdetail.lastName},
             position = #{staffdetail.position},
             age = #{staffdetail.age}
         WHERE staffid = #{staffdetail.staffid}
  """)
    int updateDetail(@Param("staffdetail") StaffDetailEntity staffDetailEntity);

    @Delete("""
        DELETE FROM staffdetail
        WHERE staffid = #{staffId}
        """)
    int deleteDetail(@Param("staffId") String staffId);

    @Delete("""
        DELETE FROM staffinfo
        WHERE staffid = #{staffId}
        """)
    int deleteStaff(@Param("staffId") String staffId);
}