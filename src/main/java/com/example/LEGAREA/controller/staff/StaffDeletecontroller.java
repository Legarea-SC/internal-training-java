package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.LEGAREA.entity.StaffEntity;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaffDeletecontroller {

    private final StaffService staffService;

    // 社員情報更新画面表示
    @GetMapping("/delete")
    public String showDeleteeForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {

        StaffDetailEntity staff = new StaffDetailEntity();
        List<StaffEntity> staffList = staffService.find();

        if (staffId != null && !staffId.isEmpty()) {
            staff = staffService.findDatail(staffId);
        }

        model.addAttribute("staffDelete", staff);
        model.addAttribute("staffList", staffList);
        return "staff/delete";
    }

    @PostMapping("/delete")
    public String deletestaff(@ModelAttribute("staffDelete") StaffDetailEntity staffDelete,
                                  Model model) {

        StaffDetailEntity staff = new StaffDetailEntity();

        String message = staffService.deleteStaff(staffDelete.getStaffId());

        List<StaffEntity> staffList = staffService.find();

        model.addAttribute("staffDelete", staff);
        model.addAttribute("staffList", staffList);
        model.addAttribute("message", message);
        return "staff/delete";
    }


}
