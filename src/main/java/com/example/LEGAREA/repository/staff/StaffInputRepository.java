package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/***
 * 社員情報追加SQLを実行するクラス
 */
@Mapper
public interface StaffInputRepository {

    //社員情報を作成public.
    @Insert("INSERT INTO staffinfo (staffid, name, division) " +
            "VALUES (#{staffId}, #{name}, #{division})")
    void staffcreate(StaffEntity staff);


    //社員詳細を作成
    @Insert("INSERT INTO public.staffdetail(" +
            "staffid, firstname, lastname, age, position)" +
            "VALUES (#{staffId}, #{firstName}, #{lastName}, #{age}, #{position});")
    void staffDetailcreate(StaffDetailEntity staffDetail);

}
