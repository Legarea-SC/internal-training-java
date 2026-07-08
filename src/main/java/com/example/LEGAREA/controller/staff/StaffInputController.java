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
public class StaffInputController {

    private final StaffInputService staffInputService;

    //社員追加画面表示
    @GetMapping("/input")
    public String showInputForm(Model model) {
        model.addAttribute("staffInput", new StaffInputEntity());
        return "staff/input";
    }
    //社員追加処理
    @PostMapping("/input")
    public String inputStaff(@Valid @ModelAttribute("staffInput") StaffInputEntity staffInput,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "staff/input";
        }
        String message = staffInputService.create(staffInput);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/list";
    }
}
