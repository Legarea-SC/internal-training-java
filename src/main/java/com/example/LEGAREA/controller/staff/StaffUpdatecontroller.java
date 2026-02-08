package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffService;
import com.example.LEGAREA.service.staff.StaffUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * 社員情報更新のコントローラクラス
 * 社員情報更新画面からの命令を受け付ける役割
 */
@Controller
@RequiredArgsConstructor
public class StaffUpdatecontroller {

    // 社員検索サービスクラスのインスタンス
    @Autowired
    private final StaffService staffService;
    // 社員更新サービスクラスのインスタンス
    @Autowired
    private final StaffUpdateService staffUpdateService;

    /***
     * 社員情報更新画面の対象社員検索処理
     * @param staffId　対象社員の社員コード
     * @param model　画面の情報を格納する
     * @return　staff/update.htmlのパス
     */
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {

        // staffIdがnullの場合は、空の情報を作成。指定がある場合は対象の詳細情報を検索
        StaffDetailEntity staff = (staffId == null)
                ? new StaffDetailEntity()
                : staffService.findDatail(staffId);

        // 対象の社員が見つからなかった場合
        if (staff == null) {
            // 空の情報を作成
            staff = new StaffDetailEntity();
            // 検索結果０件のメッセージを設定
            model.addAttribute("message", "該当する社員が見つかりませんでした。");
        }

        // 作成した社員情報を画面に格納
        model.addAttribute("staffUpdate", staff); //
        // 社員情報更新画面に遷移
        return "staff/update";
    }

    /***
     * 社員情報更新処理
     * @param staffDetail　対象社員の詳細データクラス
     * @param bindingResult　社員情報詳細クラスに入力エラーがある場合にエラー情報が入る
     * @param model　画面に表示する情報を格納する
     * @return　画面再表示のため再度更新画面の表示処理を呼び出す
     */
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("staffUpdate") StaffDetailEntity staffDetail,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "staff/update";
        }
        String message=staffUpdateService.updateDatail(staffDetail);

        // 更新処理の結果メッセージをモデルに渡す
        model.addAttribute("message", message);

        // 画面再表示のため再度更新画面の表示処理を呼び出す
        return showUpdateForm(staffDetail.getStaffId(), model);
    }
}
