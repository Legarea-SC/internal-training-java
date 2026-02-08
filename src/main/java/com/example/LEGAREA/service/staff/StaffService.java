package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.repository.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 社員情報検索のサービスクラス
 */
@Service
@RequiredArgsConstructor
public class StaffService {

    @Autowired
    private  final StaffRepository staffRepository;

    /***
     * 社員情報一覧を取得する
     * @return　社員情報一覧リスト
     */
    public List<StaffEntity> find(){
        return  staffRepository.select();
    }

    /***
     * 社員詳細情報を取得する
     * @param Staffid　社員コード
     * @return　社員詳細情報
     */
    public StaffDetailEntity findDatail(String Staffid) {

        try{
            // 社員コードをもとに詳細情報を検索
            return staffRepository.selectByID(Staffid);
        }
        catch (Exception ex)
        {
            // 取得失敗時は空データを返す
            return new StaffDetailEntity();
        }




    }
}
