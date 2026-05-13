package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffInputEntity;
import com.example.LEGAREA.service.staff.StaffInputService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class StaffInputcontroller {

    private final StaffInputService staffInputService;

    // 社員追加画面表示
    @GetMapping("/input")
    public String showInputForm(Model model) {
        model.addAttribute("staffInput", new StaffInputEntity());
        return "staff/input"; // add.html を返す
    }


    @PostMapping("/input")
    public String inputStaff(@Valid @ModelAttribute("staffInput") StaffInputEntity staffInput,
                           BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        // 入力エラーの場合はエラーメッセージ表示
        if (bindingResult.hasErrors()) {
            return "staff/input";
        }

        // DB保存処理
        String message=staffInputService.create(staffInput);

        // 処理結果を次の画面で表示するための一時保存処理
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/list";
    }



}
