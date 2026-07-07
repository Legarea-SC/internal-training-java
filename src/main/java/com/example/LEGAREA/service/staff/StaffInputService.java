package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.entity.StaffInputEntity;
import com.example.LEGAREA.repository.staff.StaffInputRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffInputService {

    public final StaffInputRepository staffInput;

    @Transactional
    public String create(StaffInputEntity input) {
        try {
            // staffinfo用のデータを作成
            StaffEntity staff = new StaffEntity(
                    input.getStaffId(),
                    input.getName(),
                    input.getDivision()
            );

            // staffdetail用のデータを作成
            StaffDetailEntity staffDetail = new StaffDetailEntity(
                    input.getStaffId(),
                    input.getName(),
                    input.getDivision(),
                    input.getFirstName(),
                    input.getLastName(),
                    input.getPosition(),
                    input.getAge()
            );
            // staffinfo に保存
            staffInput.staffcreate(staff);

            // staffdetail に保存
            staffInput.staffDetailcreate(staffDetail);

            return "登録に成功しました";
        }
        catch (Exception ex) {
            return "登録に失敗しました。";
        }

    }
}
