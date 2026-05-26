package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.repository.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.LEGAREA.entity.StaffInputEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private  final StaffRepository staffRepository;
    public List<StaffEntity> find(){
        return  staffRepository.select();
    }

    public StaffDetailEntity findDatail(String Staffid) {

        try{
            return staffRepository.selectByID(Staffid);
        }
        catch (Exception ex)
        {
            // 取得失敗時は空データを返す
            return new StaffDetailEntity();
        }
    }
    public String insertStaff(StaffInputEntity staffInput) {

        int staffInfoResult = staffRepository.insertStaffInfo(staffInput);
        int staffDetailResult = staffRepository.insertStaffDetail(staffInput);

        int result = staffInfoResult + staffDetailResult;

        if (result == 2) {
            return "登録が正常に完了しました";
        } else {
            return "登録に失敗しました";
        }

    }
    public String updateStaff(StaffDetailEntity staffUpdate) {

        int staffInfoResult = staffRepository.updateStaffInfo(staffUpdate);
        int staffDetailResult = staffRepository.updateStaffDetail(staffUpdate);

        int result = staffInfoResult + staffDetailResult;

        if (result == 2) {
            return "更新が正常に完了しました";
        } else {
            return "更新に失敗しました";
        }
    }
}
