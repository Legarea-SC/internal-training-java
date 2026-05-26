package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffInputEntity;
import com.example.LEGAREA.service.staff.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class StaffInputcontroller {

    private final StaffService staffService;

    // 社員追加画面表示
    @GetMapping("/input")
    public String showInputForm(Model model) {
        model.addAttribute("staffInput", new StaffInputEntity());
        return "staff/input";
    }

    // 社員追加処理
    @PostMapping("/input")
    public String insertStaff(
            @Valid @ModelAttribute("staffInput") StaffInputEntity staffInput,
            BindingResult bindingResult,
            Model model) {

        // 入力チェックエラーがある場合、登録せずに入力画面へ戻す
        if (bindingResult.hasErrors()) {
            return "staff/input";
        }

        String message = staffService.insertStaff(staffInput);
        model.addAttribute("message", message);

        // まだDB登録はしない。まずはPOSTで値を受け取れるか確認する
        return "staff/input";
    }
}