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

        if (staffId.isBlank() || name.isBlank() || division.isBlank()
                || firstName.isBlank() || lastName.isBlank() || age.isBlank()) {
            model.addAttribute("message", "登録に失敗しました");
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
            } else {
                model.addAttribute("message", "登録に失敗しました");
            }
        } catch (Exception e) {
            model.addAttribute("message", "登録に失敗しました");
        }
        return "staff/insert";
    }
}