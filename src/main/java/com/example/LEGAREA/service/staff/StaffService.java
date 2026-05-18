package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.repository.staff.StaffRepository;
import enetity.StaffDatailEntity;
import enetity.StaffEntity;
import enetity.StaffUpdateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public List<StaffEntity> find() {
        return staffRepository.select();
    }

    public List<StaffDatailEntity> findDatail(String staffid) {
        return staffRepository.selectByID(staffid);
    }

    @Transactional
    public void create(StaffEntity newEntity) {
        staffRepository.insert(newEntity);
    }

    public StaffUpdateEntity findForUpdate(String staffid) {
        return staffRepository.selectForUpdate(staffid);
    }

    @Transactional
    public void update(StaffUpdateEntity entity) {
        staffRepository.update(entity);
    }

    @Transactional
    public void delete(String staffid) {
        staffRepository.delete(staffid);
    }
}