package com.example.LEGAREA.service.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.repository.staff.StaffRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    /**
     * StaffService#find の確認用テスト。
     *
     * <p>確認したいこと:
     * Service が自分でデータを作るのではなく、
     * Repository から受け取った一覧をそのまま返していること。
     */
    @Test
    void findReturnsStaffListFromRepository() {
        // arrange:
        // Repository が返す一覧データを準備する。
        List<StaffEntity> expected = List.of(
                new StaffEntity("S001", "Alice", "Sales"),
                new StaffEntity("S002", "Bob", "HR")
        );
        when(staffRepository.select()).thenReturn(expected);

        // act:
        // Service 経由で一覧取得を実行する。
        List<StaffEntity> actual = staffService.find();

        // assert:
        // Repository の結果がそのまま返ることを確認する。
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    /**
     * StaffService#findDatail の正常系テスト。
     *
     * <p>確認したいこと:
     * Repository が詳細データを返したとき、
     * Service が余計な加工をせずそのまま返すこと。
     */
    @Test
    void findDatailReturnsStaffDetailWhenRepositorySucceeds() {
        // arrange:
        // Repository が返す詳細データを作る。
        StaffDetailEntity expected = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        when(staffRepository.selectByID("S001")).thenReturn(expected);

        // act:
        // 詳細取得を実行する。
        StaffDetailEntity actual = staffService.findDatail("S001");

        // assert:
        // 同じインスタンスが返ることを確認する。
        assertThat(actual).isSameAs(expected);
    }

    /**
     * StaffService#findDatail の例外系テスト。
     *
     * <p>確認したいこと:
     * Repository で例外が起きても Service が落ちず、
     * 画面側で扱える空の Entity を返すこと。
     */
    @Test
    void findDatailReturnsEmptyEntityWhenRepositoryThrowsException() {
        // arrange:
        // DB エラーを想定して例外を投げるようにする。
        when(staffRepository.selectByID("S001"))
                .thenThrow(new RuntimeException("db error"));

        // act:
        // Service を呼び出す。
        StaffDetailEntity actual = staffService.findDatail("S001");

        // assert:
        // null ではなく空の Entity が返ることを確認する。
        assertThat(actual).isNotNull();
        assertThat(actual.getStaffId()).isNull();
        assertThat(actual.getAge()).isZero();
    }
}
