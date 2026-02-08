package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * 社員情報更新のサービスクラス
 */
@Service
@RequiredArgsConstructor
public class StaffUpdateService {

    @Autowired
    private  final StaffUpdateRepository staffUpdateRepository;

    /***
     * 社員情報の更新処理
     * @param Entity　更新予定の社員詳細情報データクラス
     * @return
     */
    @Transactional
    public String updateDatail(StaffDetailEntity Entity) {

        // 返却メッセージ変数を宣言
        String message=null;

        try
        {
            // 入力データが不正な場合
            if(Entity == null || Entity.getStaffId()==null)
            {
                // 処理は行わずエラーメッセージを返す
                return "入力データが不正です";
            }

            // 更新処理を実行
            staffUpdateRepository.updateStaff(Entity);
            // 成功メッセージを返す
            return "更新が正常に終了しました";
        }
        catch (Exception ex)
        {
            // 更新失敗時はメッセージを返す
            message="例外が発生しました";
            return message;
        }
    }
}
