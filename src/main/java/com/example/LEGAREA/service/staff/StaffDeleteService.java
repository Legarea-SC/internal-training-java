package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffDeleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * 社員情報削除のサービスクラス
 */
@Service
@RequiredArgsConstructor
public class StaffDeleteService {

    @Autowired
    private  final StaffDeleteRepository staffDeleteRepository;

    /***
     * スタッフ情報の削除処理を実行し、結果によりメッセージを設定する
     * @param Entity 対象社員の詳細情報クラス
     * @return
     */
    @Transactional
    public String Deletestaff(StaffDetailEntity Entity) {

        try
        {
            // 入力データが不正な場合は、エラーメッセージを返す
            if(Entity == null || Entity.getStaffId()==null || Entity.getStaffId().isEmpty())
            {
                return "入力データが不正です";
            }

            // 削除処理を実行
            staffDeleteRepository.deleteStaff(Entity);
            // 成功メッセージを返す
            return "削除が正常に終了しました";
        }
        catch (Exception ex)
        {
            // 削除失敗時はメッセージを返す
            return "例外が発生しました";
        }
    }
}
