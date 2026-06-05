package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.service.staff.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        // プルダウンに表示する社員一覧を毎回渡す
        model.addAttribute("staffList", staffService.find());

        // 初期表示：まだ何も選択していない
        if (staffId == null) {
            return "staff/delete";
        }

        // 「社員を選択してください」のまま送信された場合
        if (staffId.isBlank()) {
            model.addAttribute("errorMessage", "社員を選択してください");
            return "staff/delete";
        }

        // 選択された社員コードで詳細情報を取得する
        List<StaffDetailEntity> staff = staffService.findDatail(staffId);

        if (!staff.isEmpty()) {
            model.addAttribute("staffDetail", staff.get(0));
        }

        return "staff/delete";
    }

    @PostMapping("/delete")
    public String deleteStaff(
            @RequestParam String staffId,
            RedirectAttributes redirectAttributes
    ) {
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