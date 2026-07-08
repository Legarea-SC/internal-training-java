package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffDeleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffDeleteService {

    private final StaffDeleteRepository staffDeleteRepository;

    @Transactional
    public String deleteStaff(StaffDetailEntity entity) {
        try {
            if (entity == null || entity.getStaffId() == null || entity.getStaffId().isEmpty()) {
                return "入力データが不正です";
            }

            // staffdetail を先に削除
            staffDeleteRepository.deleteStaffDetail(entity);

            // staffinfo を後に削除
            staffDeleteRepository.deleteStaffInfo(entity);

            return "削除が正常に終了しました";
        }
        catch (Exception ex) {
            return "例外が発生しました";
        }
    }
}