package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.repository.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private  final StaffRepository staffRepository;
    public List<StaffEntity> find(){
        return  staffRepository.select();
    }

    public StaffDetailEntity findDetail(String StaffId) {

        try{
            StaffDetailEntity result = staffRepository.selectByID(StaffId);
            return staffRepository.selectByID(StaffId);
        }
        catch (Exception ex)
        {
            // 取得失敗時は空データを返す
            return new StaffDetailEntity();
        }




    }
}
