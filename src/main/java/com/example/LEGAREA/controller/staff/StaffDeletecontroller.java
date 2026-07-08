package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffDeleteService;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaffDeletecontroller {

    private final StaffService staffService;
    private final StaffDeleteService staffDeleteService;

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {

        // 全ケースでドロップダウン用リストを取得
        List<StaffEntity> staffList = staffService.find();
        model.addAttribute("staffList", staffList);

        // 初回表示
        if (staffId == null) {
            model.addAttribute("staffDelete", new StaffDetailEntity());
            return "staff/delete";
        }

        // 空白で検索
        if (staffId.isEmpty()) {
            model.addAttribute("staffDelete", new StaffDetailEntity());
            model.addAttribute("message", "入力データが不正です");
            return "staff/delete";
        }

        // DBから検索
        StaffDetailEntity staff = staffService.findDetail(staffId);

        // 該当なし
        if (staff == null || staff.getStaffId() == null) {
            model.addAttribute("staffDelete", new StaffDetailEntity());
            model.addAttribute("message", "該当する社員が見つかりませんでした");
            return "staff/delete";
        }

        model.addAttribute("staffDelete", staff);
        return "staff/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("staffDelete") StaffDetailEntity staffDetail,
                         RedirectAttributes redirectAttributes) {
        String message = staffDeleteService.deleteStaff(staffDetail);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/list";
    }
}