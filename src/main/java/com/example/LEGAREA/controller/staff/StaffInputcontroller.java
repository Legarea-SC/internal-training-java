package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffInputEntity;
import com.example.LEGAREA.service.staff.StaffInputService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/***
 * 社員情報追加のコントローラクラス
 */
@Controller
@RequiredArgsConstructor
public class StaffInputcontroller {

    @Autowired
    private final StaffInputService staffInputService;

    /***
     * 社員情報追加画面の初期表示処理
     * @param model　画面に表示する情報を格納する
     * @return　staff/input.htmlのパス
     */
    @GetMapping("/input")
    public String showInputForm(Model model) {
        // 画面情報入力用に社員詳細入力データクラスを格納
        model.addAttribute("staffInput", new StaffInputEntity());

        // 表示したい画面のURL階層を指定して返す
        return "staff/input"; // add.html を返す
    }


    /***
     * 社員情報の追加処理
     * @param staffInput　社員入力クラス　
     * @param bindingResult　社員入力クラスに入力エラーがある場合にエラー情報が入る
     * @param redirectAttributes　リダイレクト時のメッセージ保存用
     * @return　社員詳細画面へのリダイレクトパス
     */
    @PostMapping("/input")
    public String inputStaff(@Valid @ModelAttribute("staffInput") StaffInputEntity staffInput,
                           BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        // 入力パラメータエラーの場合はエラーメッセージ表示
        if (bindingResult.hasErrors()) {
            return "staff/input";
        }

        // 入力された社員情報をDBに保存
        String message=staffInputService.create(staffInput);

        // 社員情報追加処理の結果メッセージを画面に格納
        redirectAttributes.addFlashAttribute("message", message);

        // 社員情報追加後は社員一覧画面へ遷移させる
        return "redirect:/list";
    }

}
