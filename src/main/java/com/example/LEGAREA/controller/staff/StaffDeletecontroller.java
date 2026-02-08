package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffDeleteService;
import com.example.LEGAREA.service.staff.StaffService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/***
 * スタッフ情報削除のコントローラクラス
 * 社員一覧画面からの命令を受け付ける役割
 */
@Controller
@RequiredArgsConstructor
public class StaffDeletecontroller {

    // 社員検索サービスクラスのインスタンス
    @Autowired
    private final StaffService staffService;
    // 社員削除サービスクラスのインスタンス
    @Autowired
    private final StaffDeleteService staffDeleteService;

    /***
     * 社員情報削除画面の対象社員検索処理
     * @param staffId　削除対象社員の社員コード
     * @param model　画面のデータを格納するための変数
     * @return　staff/delete.htmlのパス
     */
    @GetMapping("/delete")
    public String showDeleteeForm(@RequestParam(value = "staffId", required = false) String staffId,
                                 Model model) {

        // 社員詳細情報を格納するクラスを用意
        StaffDetailEntity staff;

        // コンボボックス内容のために社員情報一覧を取得
        List<StaffEntity> staffList=staffService.find();
        // 取得情報を画面に渡す
        model.addAttribute("staffList", staffList);

        // 初回表示時は、staffIdがnullで来るため、詳細情報は取得せずコンボボックスの内容表示のため社員一覧を取得する
        if (staffId==null) {
            // 詳細情報は初回表示のため空の情報を作成して返却
            staff=new StaffDetailEntity();
        }
        // IDが指定されていれば社員詳細情報取得
        else {
            // staffIdを使用して社員詳細情報取得
            staff = staffService.findDatail(staffId);
        }

        // 指定Idの社員が検索できなかった場合
        if (staff == null) {
            // 空の情報を作成
            staff = new StaffDetailEntity();
            // 画面に表示するメッセージを設定
            model.addAttribute("message", "該当する社員が見つかりませんでした。");
        }

        // 作成した社員情報を画面に格納
        model.addAttribute("staffDelete", staff);
        return "staff/delete";
    }

    /***
     * /***
     * 社員情報削除画面の削除処理
     * @param staffDetail　社員詳細情報クラス
     * @param bindingResult　社員情報クラスに入力エラーがある場合にエラー情報が入る
     * @param redirectAttributes　リダイレクト時のメッセージ保存用
     * @param model　画面に表示する情報を格納する
     * @return　staff/delete.htmlのパス
     */
    @PostMapping("/delete")
    public String delete(@Valid @ModelAttribute("staffDelete") StaffDetailEntity staffDetail,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes,
                         Model model) {

        // 受信パラメーターにエラーがある場合
        if (bindingResult.hasErrors()) {

            // 処理を行わずにそのまま返す
            return "staff/delete";
        }

        // 社員情報の削除処理を実施
        String message=staffDeleteService.Deletestaff(staffDetail);

        // 削除処理の結果メッセージを保存
        redirectAttributes.addFlashAttribute("message", message);

        // リダイレクトにより画面を再表示
        return "redirect:/delete";
    }
}
