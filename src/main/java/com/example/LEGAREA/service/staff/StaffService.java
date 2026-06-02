package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.repository.staff.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private  final StaffRepository staffRepository;
    public List<StaffEntity> find(){
        return  staffRepository.select();
    }

    public List<StaffDetailEntity> findDatail(String taskid) {

        return staffRepository.selectByID(taskid);
    }

    @Transactional
    public int create(StaffEntity newEntity, StaffDetailEntity detailEntity) {

        int infoResult = staffRepository.insert(newEntity);

        int detailResult = staffRepository.insertDetail(detailEntity);

        return infoResult + detailResult;
    }
}
