package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.service.staff.StaffDetailEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaffDeleteController {

    private final StaffService staffService;

    @GetMapping("/delete")
    public String showDelete(
            @RequestParam(value = "staffId", required = false) String staffId,
            Model model) {

        if (staffId != null && !staffId.isBlank()) {
            List<StaffDetailEntity> staff = staffService.findDatail(staffId);
            if (staff.isEmpty()) {
                model.addAttribute("errorMessage", "該当する社員情報がありません");
            } else {
                model.addAttribute("staffDetailList", staff);
            }
        }
        return "staff/delete";
    }

    @PostMapping("/delete")
    public String deleteStaff(
            @RequestParam String staffId,
            RedirectAttributes redirectAttributes
    ){
        if (staffId == null || staffId.isBlank()) {
            redirectAttributes.addFlashAttribute("errorMessage", "削除対象の社員コードがありません");
            return "redirect:/delete";
        }

        int result = staffService.delete(staffId);
         if (result == 2) {
             redirectAttributes.addFlashAttribute("successMessage", "社員情報を削除しました");
        } else {
             redirectAttributes.addFlashAttribute("errorMessage", "社員情報の削除に失敗しました");
        }
        return "redirect:/delete";
    }
}
