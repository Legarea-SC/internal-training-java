package com.example.LEGAREA.controller.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.example.LEGAREA.service.staff.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffEntity;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaffUpdateController {

    private final StaffService staffService;

    @GetMapping("/update")
    public String showUpdate(
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
        return "staff/update";
    }
    @PostMapping("/update")
    public String postUpdate(
            @RequestParam String staffId,
            @RequestParam String name,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String division,
            @RequestParam String position,
            @RequestParam String age,
            RedirectAttributes redirectAttributes
    ) {

        if (staffId.isBlank() || name.isBlank() || firstName.isBlank()
                || lastName.isBlank() || division.isBlank() || age.isBlank()) {

            redirectAttributes.addFlashAttribute("errorMessage", "必須項目を入力してください");
            return "redirect:/update?staffId=" + staffId;
        }

        int ageValue;

        try {
            ageValue = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "年齢は数字で入力してください");
            return "redirect:/update?staffId=" + staffId;
        }

        List<StaffDetailEntity> currentList = staffService.findDatail(staffId);

        if (currentList.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "対象の社員情報が見つかりません");
            return "redirect:/update";
        }

        StaffDetailEntity current = currentList.get(0);

        String positionValue = position == null ? "" : position;
        String currentPosition = current.getPosition() == null ? "" : current.getPosition();

        if (current.getName().equals(name)
                && current.getFirstName().equals(firstName)
                && current.getLastName().equals(lastName)
                && current.getDivision().equals(division)
                && currentPosition.equals(positionValue)
                && current.getAge() == ageValue) {

            redirectAttributes.addFlashAttribute("errorMessage", "変更がありません");
            return "redirect:/update?staffId=" + staffId;
        }

        StaffEntity staffEntity = new StaffEntity(
                staffId,
                name,
                division
        );

        StaffDetailEntity detailEntity = new StaffDetailEntity(
                staffId,
                name,
                division,
                firstName,
                lastName,
                positionValue,
                ageValue
        );

        int result = staffService.update(staffEntity, detailEntity);

        if (result == 2) {
            redirectAttributes.addFlashAttribute("successMessage", "社員情報を更新しました");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "社員情報の更新に失敗しました");
        }

        return "redirect:/update?staffId=" + staffId;
    }
}