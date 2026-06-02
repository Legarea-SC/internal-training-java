package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.service.staff.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffEntity;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class Staffcontroller {

    private final StaffService staffService;

    @GetMapping
    public String showEmployeeList(Model model) {
        List<StaffEntity> staffList = staffService.find();
        model.addAttribute("staffList", staffList);
        return "staff/list";
    }

    @GetMapping("/detail/{staffid}")
    public String showDetail(@PathVariable String staffid, Model model) {
        List<StaffDetailEntity> staff = staffService.findDatail(staffid); // DBから1件取得
        model.addAttribute("staffDetailList", staff);
        return "staff/detail"; // 詳細画面テンプレート
    }




}
