package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffUpdateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StaffUpdateServiceTest {

    @Mock
    private StaffUpdateRepository staffUpdateRepository;

    @InjectMocks
    private StaffUpdateService staffUpdateService;

    /**
     * StaffUpdateService#updateDatail の入力不正テスト。
     *
     * <p>確認したいこと:
     * null が渡されたときに DB 更新処理へ進まないこと。
     */
    @Test
    void updateDatailDoesNotCallRepositoryWhenEntityIsNull() {
        String message = staffUpdateService.updateDatail(null);

        verify(staffUpdateRepository, never()).updateStaff(any());
        assertThat(message).isNotBlank();
    }

    /**
     * StaffUpdateService#updateDatail の正常系テスト。
     *
     * <p>確認したいこと:
     * staffId を持つ Entity が渡されたとき、
     * Repository の更新処理が呼ばれること。
     */
    @Test
    void updateDatailCallsRepositoryWhenEntityHasStaffId() {
        StaffDetailEntity entity = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );

        String message = staffUpdateService.updateDatail(entity);

        verify(staffUpdateRepository).updateStaff(entity);
        assertThat(message).isNotBlank();
    }

    /**
     * StaffUpdateService#updateDatail の例外系テスト。
     *
     * <p>確認したいこと:
     * DB 更新で例外が起きても Service が落ちず、
     * 失敗メッセージを返すこと。
     */
    @Test
    void updateDatailReturnsMessageWhenRepositoryThrowsException() {
        StaffDetailEntity entity = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        doThrow(new RuntimeException("update failed"))
                .when(staffUpdateRepository)
                .updateStaff(entity);

        String message = staffUpdateService.updateDatail(entity);

        verify(staffUpdateRepository).updateStaff(entity);
        assertThat(message).isNotBlank();
    }
}
