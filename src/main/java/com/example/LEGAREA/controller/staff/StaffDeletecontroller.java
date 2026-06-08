package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffDeleteService;
import com.example.LEGAREA.service.staff.StaffService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StaffDeletecontroller {

    private final StaffService staffService;
    private final StaffDeleteService staffDeleteService;

    // 社員情報更新画面表示
    @GetMapping("/delete")
    public String showDeleteeForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {

        StaffDetailEntity staff;

        // 初回はIDが必ずNULLでくる
        // deleteの場合は、登録されている社員IDと名前を初回で取得してコンボボックス表示する
        if (staffId==null) {

            List<StaffEntity> staffList=staffService.find();
            // 取得情報を画面に渡す
            model.addAttribute("staffList", staffList);
            staff=new StaffDetailEntity();
        }

        // IDがNULLで無ければ社員詳細情報取得
        else {
            staff = staffService.findDatail(staffId);
        }

        // 検索社員が見つからない場合
        if (staff == null) {
            staff = new StaffDetailEntity();
            model.addAttribute("message", "該当する社員が見つかりませんでした。");
        }

        model.addAttribute("staffDelete", staff);
        return "staff/delete";
    }

    @PostMapping("/delete")
    public String delete(@Valid @ModelAttribute("staffDelete") StaffDetailEntity staffDetail,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes,
                         Model model) {

        System.out.println("★hasErrors = " + bindingResult.hasErrors());
        System.out.println("★errors = " + bindingResult.getAllErrors());
        System.out.println("★staffId = [" + staffDetail.getStaffId() + "]");

        if (bindingResult.hasErrors()) {
            return "staff/delete";
        }
        String message=staffDeleteService.Deletestaff(staffDetail);

        // メッセージを保存
        redirectAttributes.addFlashAttribute("message", message);

        // リダイレクトにより画面を再表示
        return "redirect:/delete";

    }
}
