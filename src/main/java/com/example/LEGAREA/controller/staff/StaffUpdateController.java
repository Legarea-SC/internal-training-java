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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class StaffUpdateController {

    private final StaffService staffService;
    private final StaffUpdateService staffUpdateService;

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model){
        //初期表示
        if (staffId == null) {
            model.addAttribute("staffUpdate", new StaffDetailEntity());
            return "staff/update";
        }
        // 空白で検索
        if (staffId.isEmpty()) {
            model.addAttribute("staffUpdate", new StaffDetailEntity());
            model.addAttribute("message", "該当の社員が見つかりませんでした。");
            return "staff/update";
        }
        // DBから検索
        StaffDetailEntity staff = staffService.findDetail(staffId);

        // 該当なし
        if (staff == null || staff.getStaffId() == null) {
            model.addAttribute("staffUpdate", new StaffDetailEntity());
            model.addAttribute("message", "該当の社員が見つかりませんでした。");
            return "staff/update";
        }

        model.addAttribute("staffUpdate", staff);
        return "staff/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("staffUpdate") StaffDetailEntity staffDetail,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "staff/update";
        }

        String message = staffUpdateService.updateDetail(staffDetail);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/list";
    }
}

