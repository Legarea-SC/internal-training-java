package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.repository.staff.StaffDeleteRepository;
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
public class StaffDeleteServiceTest {

    @Mock
    private StaffDeleteRepository staffDeleteRepository;

    @InjectMocks
    private StaffDeleteService staffDeleteService;

    /**
     * StaffDeleteService#Deletestaff の入力不正テスト。
     *
     * <p>確認したいこと:
     * staffId が空文字のときに削除処理へ進まないこと。
     */
    @Test
    void deleteStaffDoesNotCallRepositoryWhenStaffIdIsBlank() {
        StaffDetailEntity entity = new StaffDetailEntity(
                "",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );

        String message = staffDeleteService.Deletestaff(entity);

        verify(staffDeleteRepository, never()).deleteStaff(any());
        assertThat(message).isNotBlank();
    }

    /**
     * StaffDeleteService#Deletestaff の正常系テスト。
     *
     * <p>確認したいこと:
     * staffId があるときは Repository の削除処理が呼ばれること。
     */
    @Test
    void deleteStaffCallsRepositoryWhenStaffIdExists() {
        StaffDetailEntity entity = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );

        String message = staffDeleteService.Deletestaff(entity);

        verify(staffDeleteRepository).deleteStaff(entity);
        assertThat(message).isNotBlank();
    }

    /**
     * StaffDeleteService#Deletestaff の例外系テスト。
     *
     * <p>確認したいこと:
     * DB 側で削除失敗が起きても Service が落ちないこと。
     */
    @Test
    void deleteStaffReturnsMessageWhenRepositoryThrowsException() {
        StaffDetailEntity entity = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        doThrow(new RuntimeException("delete failed"))
                .when(staffDeleteRepository)
                .deleteStaff(entity);

        String message = staffDeleteService.Deletestaff(entity);

        verify(staffDeleteRepository).deleteStaff(entity);
        assertThat(message).isNotBlank();
    }
}
