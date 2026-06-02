package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.service.staff.StaffDetailEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.LEGAREA.service.staff.StaffService;
import com.example.LEGAREA.service.staff.StaffEntity;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaffInsertController {

    private final StaffService staffService;

    @GetMapping("/insert")
    public String showInsert() {
        return "staff/insert";
    }

    @PostMapping("/insert")
    public String postInsert(
            @RequestParam("staffId") String staffId,
            @RequestParam("name") String name,
            @RequestParam("division") String division,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("position") String position,
            @RequestParam("age") String age,
            Model model) {

        boolean hasError = false;

        if (staffId.isBlank()) {
            model.addAttribute("staffIdError", "社員コードは必須です");
            hasError = true;
        }

        if (name.isBlank()) {
            model.addAttribute("nameError", "氏名は必須です");
            hasError = true;
        }

        if (division.isBlank()) {
            model.addAttribute("divisionError", "部署は必須です");
            hasError = true;
        }

        if (firstName.isBlank()) {
            model.addAttribute("firstNameError", "セイは必須です");
            hasError = true;
        }

        if (lastName.isBlank()) {
            model.addAttribute("lastNameError", "メイは必須です");
            hasError = true;
        }

        if (age.isBlank()) {
            model.addAttribute("ageError", "年齢は必須です");
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("message", "登録に失敗しました");
            model.addAttribute("messageClass", "text-danger fw-bold fs-5 mb-2");
            return "staff/insert";
        }

        try {
            int ageInt = Integer.parseInt(age);

            StaffEntity staffEntity = new StaffEntity(staffId, name, division);

            StaffDetailEntity detailEntity = new StaffDetailEntity(
                    staffId,
                    name,
                    division,
                    firstName,
                    lastName,
                    position,
                    ageInt
        );

            int result = staffService.create(staffEntity, detailEntity);

            if (result == 2) {
                model.addAttribute("message", "登録が完了しました");
                model.addAttribute("messageClass", "text-success fw-bold fs-5 mb-2");
            } else {
                model.addAttribute("message", "登録に失敗しました");
                model.addAttribute("messageClass", "text-danger fw-bold fs-5 mb-2");
            }
        } catch (Exception e) {
            model.addAttribute("message", "登録に失敗しました");
            model.addAttribute("messageClass", "text-danger fw-bold fs-5 mb-2");
        }
        return "staff/insert";
    }
}