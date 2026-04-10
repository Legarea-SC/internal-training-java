package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffInputEntity;
import com.example.LEGAREA.repository.staff.StaffInputRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StaffInputServiceTest {

    @Mock
    private StaffInputRepository staffInputRepository;

    @InjectMocks
    private StaffInputService staffInputService;

    /**
     * StaffInputService#create の正常系テスト。
     *
     * <p>確認したいこと:
     * 一覧テーブルと詳細テーブルの両方へ登録処理が進むこと。
     * 正常終了メッセージが返ること。
     */
    @Test
    void createStoresStaffAndStaffDetailWhenInputIsValid() {
        StaffInputEntity input = createInput();

        String message = staffInputService.create(input);

        verify(staffInputRepository, times(1)).staffcreate(any());
        verify(staffInputRepository, times(1)).staffDetailcreate(any());
        assertThat(message).isNotBlank();
    }

    /**
     * StaffInputService#create の例外系テスト。
     *
     * <p>確認したいこと:
     * 最初の登録で失敗した場合に例外が外へ漏れないこと。
     * 詳細テーブル側の登録には進まないこと。
     */
    @Test
    void createReturnsMessageWhenRepositoryThrowsException() {
        StaffInputEntity input = createInput();
        doThrow(new RuntimeException("insert failed"))
                .when(staffInputRepository)
                .staffcreate(any());

        String message = staffInputService.create(input);

        verify(staffInputRepository, times(1)).staffcreate(any());
        verify(staffInputRepository, times(0)).staffDetailcreate(any());
        assertThat(message).isNotBlank();
    }

    /**
     * テスト用の入力データ作成メソッド。
     *
     * <p>複数のテストで同じ入力値を使うため、共通化している。
     */
    private StaffInputEntity createInput() {
        StaffInputEntity input = new StaffInputEntity();
        input.setStaffId("S001");
        input.setName("Alice");
        input.setDivision("Sales");
        input.setFirstName("A");
        input.setLastName("Iice");
        input.setPosition("Leader");
        input.setAge(30);
        return input;
    }
}
