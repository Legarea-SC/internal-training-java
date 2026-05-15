package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffService;
import com.example.LEGAREA.service.staff.StaffUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StaffUpdatecontroller {

    private final StaffService staffService;
    private final StaffUpdateService staffUpdateService;

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {
        StaffDetailEntity staff;

        if (staffId == null || staffId.isEmpty()) {
            staff = new StaffDetailEntity();
            if (staffId != null) {
                model.addAttribute("message", "該当の社員が見つかりませんでした");
            }
        } else {
            staff = staffService.findDatail(staffId);
            if (staff == null) {
                staff = new StaffDetailEntity();
                model.addAttribute("message", "該当の社員が見つかりませんでした");
            }
        }

        model.addAttribute("staffUpdate", staff);
        return "staff/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("staffUpdate") StaffDetailEntity staffDetail,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "staff/update";
        }
        String message = staffUpdateService.updateDatail(staffDetail);
        model.addAttribute("message", message);
        return showUpdateForm(staffDetail.getStaffId(), model);
    }
}