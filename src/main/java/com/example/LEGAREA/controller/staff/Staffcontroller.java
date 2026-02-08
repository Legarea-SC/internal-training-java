package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 * スタッフ情報取得のコントローラクラス
 * 社員一覧画面からの命令を受け付ける役割
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class Staffcontroller {

    // サービスクラスのインスタンス
    @Autowired
    private final StaffService staffService;

    /***
     * 社員情報一覧画面に遷移時の取得処理
     * http://localhost:5000/listのURLに遷移したとき一番最初に動く
     * @param model　画面に渡したい情報を詰めるための変数
     * @return　staff/list.htmlのパス
     */
    @GetMapping
    public String showEmployeeList(Model model) {
        // サービスクラスを使用して社員一覧情報を取得
        List<StaffEntity> staffList = staffService.find();

        // 取得した社員一覧情報(staffList)を画面で表示するためにmodelに格納
        model.addAttribute("staffList", staffList);
        // 表示したい画面のURL階層を指定して返す
        return "staff/list";
    }

    /***
     * 社員情報詳細を取得する際に動作する取得処理
     * @param staffid　画面で選択された社員の社員コード
     * @param model　画面に渡したい情報を詰めるための変数
     * @return　staff/detail.htmlのパス
     */
    @GetMapping("/detail/{staffid}")
    public String showDetail(@PathVariable String staffid, Model model) {

        // 社員コードを引数として渡し、サービスクラスから社員詳細情報を取得
        StaffDetailEntity staff = staffService.findDatail(staffid);

        // データが取得できたか確認
        if(staff == null)
        {
            // データ取得に失敗した場合は、空の社員情報を作成
            // nullのまま返すと画面表示でエラーとなる
            staff =new StaffDetailEntity();
        }

        // 取得した社員一覧情報(staffList)を画面で表示するためにmodelに格納
        model.addAttribute("staffDetail", staff);

        // 表示したい画面のURL階層を指定して返す
        return "staff/detail";
    }




}
