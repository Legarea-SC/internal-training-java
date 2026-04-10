package com.example.LEGAREA.repository.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository を対象にした DB 連携テスト。
 *
 * <p>このテストでは Mockito を使わず、実際の PostgreSQL に接続して SQL を実行する。
 * そのため、SQL の書き方、JOIN の結果、Entity への詰め替えが正しいかをまとめて確認できる。
 *
 * <p>ただし既存データを壊さないことが最優先なので、各テストはトランザクション内で実行し、
 * テスト終了時にロールバックする。さらに安全策として、テスト専用 ID を使い、
 * 前後でその ID だけ削除する SQL も入れている。
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Rollback
public class StaffRepositoryIntegrationTest {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffInputRepository staffInputRepository;

    /**
     * 一覧取得の確認用テスト。
     *
     * <p>確認したいこと:
     * staffinfo テーブルのデータを取得できること。
     * 取得結果が staffId 順に並ぶこと。
     * 取得列が StaffEntity に正しく入ること。
     */
    @Test
    @DisplayName("一覧取得: staffinfo のデータを staffId 順に取得できる")
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid IN ('TEST_JUNIT_S001', 'TEST_JUNIT_S002')",
                    "DELETE FROM staffinfo WHERE staffid IN ('TEST_JUNIT_S001', 'TEST_JUNIT_S002')",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_S001', 'Alice', 'Sales')",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_S002', 'Bob', 'HR')"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid IN ('TEST_JUNIT_S001', 'TEST_JUNIT_S002')",
                    "DELETE FROM staffinfo WHERE staffid IN ('TEST_JUNIT_S001', 'TEST_JUNIT_S002')"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void selectReturnsOrderedStaffList() {
        // act:
        // Repository の一覧取得メソッドを実行する。
        List<StaffEntity> result = staffRepository.select();

        // assert:
        // テスト用に登録した 2 件が staffId 順で含まれていることを確認する。
        assertThat(result)
                .extracting(StaffEntity::getStaffId)
                .containsSubsequence("TEST_JUNIT_S001", "TEST_JUNIT_S002");

        // assert:
        // 1 件目の中身も期待通りに入っていることを確認する。
        assertThat(result)
                .filteredOn(staff -> "TEST_JUNIT_S001".equals(staff.getStaffId()))
                .singleElement()
                .satisfies(staff -> {
                    assertThat(staff.getName()).isEqualTo("Alice");
                    assertThat(staff.getDivision()).isEqualTo("Sales");
                });
    }

    /**
     * 詳細取得の確認用テスト。
     *
     * <p>確認したいこと:
     * staffinfo と staffdetail の JOIN が正しく動くこと。
     * 指定した staffId の詳細を 1 件取得できること。
     * 取得列が StaffDetailEntity に正しくマッピングされること。
     */
    @Test
    @DisplayName("詳細取得: staffinfo と staffdetail を結合して詳細を取得できる")
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_S101'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_S101'",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_S101', 'Alice', 'Sales')",
                    "INSERT INTO staffdetail (staffid, firstname, lastname, age, position) "
                            + "VALUES ('TEST_JUNIT_S101', 'A', 'Iice', 30, 'Leader')"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_S101'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_S101'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void selectByIdReturnsJoinedStaffDetail() {
        // act:
        // 指定 ID の詳細データを取得する。
        StaffDetailEntity result = staffRepository.selectByID("TEST_JUNIT_S101");

        // assert:
        // JOIN した値が Entity にそのまま入っていることを確認する。
        assertThat(result).isNotNull();
        assertThat(result.getStaffId()).isEqualTo("TEST_JUNIT_S101");
        assertThat(result.getName()).isEqualTo("Alice");
        assertThat(result.getDivision()).isEqualTo("Sales");
        assertThat(result.getFirstName()).isEqualTo("A");
        assertThat(result.getLastName()).isEqualTo("Iice");
        assertThat(result.getPosition()).isEqualTo("Leader");
        assertThat(result.getAge()).isEqualTo(30);
    }

    /**
     * 登録系 Repository テストのひな形。
     *
     * <p>確認したいこと:
     * insert 用 Repository で登録したデータを、
     * select 用 Repository で読み直せること。
     *
     * <p>教育用には「書き込みを実行する -> 読み戻して確認する」という
     * 一連の流れを学ぶためのサンプルとして使える。
     */
    @Test
    @DisplayName("登録: insert 後に詳細データを読み直せる")
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_S201'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_S201'"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_S201'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_S201'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void insertThenReadBackInsertedData() {
        // arrange:
        // 一覧テーブル用の登録データを作る。
        StaffEntity staff = new StaffEntity(
                "TEST_JUNIT_S201",
                "Carol",
                "Engineering"
        );

        // arrange:
        // 詳細テーブル用の登録データを作る。
        StaffDetailEntity detail = new StaffDetailEntity(
                "TEST_JUNIT_S201",
                "Carol",
                "Engineering",
                "C",
                "Arol",
                "Developer",
                25
        );

        // act:
        // 2 つのテーブルへ登録する。
        staffInputRepository.staffcreate(staff);
        staffInputRepository.staffDetailcreate(detail);

        // act:
        // 登録結果を確認するため、詳細取得 SQL で読み直す。
        StaffDetailEntity result = staffRepository.selectByID("TEST_JUNIT_S201");

        // assert:
        // 登録した内容がそのまま取得できることを確認する。
        assertThat(result).isNotNull();
        assertThat(result.getStaffId()).isEqualTo("TEST_JUNIT_S201");
        assertThat(result.getName()).isEqualTo("Carol");
        assertThat(result.getDivision()).isEqualTo("Engineering");
        assertThat(result.getFirstName()).isEqualTo("C");
        assertThat(result.getLastName()).isEqualTo("Arol");
        assertThat(result.getPosition()).isEqualTo("Developer");
        assertThat(result.getAge()).isEqualTo(25);
    }
}
