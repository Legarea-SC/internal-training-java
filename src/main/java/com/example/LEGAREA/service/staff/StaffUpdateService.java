package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffUpdateService {

    private final StaffUpdateRepository staffUpdateRepository;

    @Transactional
    public  String updateDetail(StaffDetailEntity Entity){
        try {
            if (Entity == null || Entity.getStaffId() == null) {
                return "入力データが不正です";
            }

            staffUpdateRepository.updateStaffInfo(Entity);
            staffUpdateRepository.updateStaffDetail(Entity);
            return "更新が正常に終了しました";
        }
        catch (Exception ex){
            return "例外が発生しました";
        }
    }
}
