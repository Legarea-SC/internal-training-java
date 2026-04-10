package com.example.LEGAREA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * SpringBootTest を使った総合テスト。
 *
 * <p>このテストでは次の部品を本物で動かしている。
 * Controller
 * Service
 * Repository
 * PostgreSQL
 *
 * <p>そのため、Web の入口から DB まで実際につながるかを確認できる。
 * 一方で既存データは壊したくないので、各テストはトランザクション内で実行し、
 * 終了時にロールバックする。
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@SqlMergeMode(MergeMode.MERGE)
public class StaffFlowSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 一覧画面の総合テスト。
     *
     * <p>確認したいこと:
     * GET /list へのアクセスが Controller に届くこと。
     * Controller から Service、Repository を通って DB へ到達すること。
     * 最終的に staff/list 画面が返り、model に staffList が入ること。
     */
    @Test
    @DisplayName("SpringBootTest: 一覧画面が Controller から DB までつながって表示される")
    @Sql(
            statements = {
                    "DELETE FROM staffdetail "
                            + "WHERE staffid IN ('TEST_JUNIT_E2E_001', 'TEST_JUNIT_E2E_002')",
                    "DELETE FROM staffinfo "
                            + "WHERE staffid IN ('TEST_JUNIT_E2E_001', 'TEST_JUNIT_E2E_002')",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_E2E_001', 'Alice', 'Sales')",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_E2E_002', 'Bob', 'HR')"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM staffdetail "
                            + "WHERE staffid IN ('TEST_JUNIT_E2E_001', 'TEST_JUNIT_E2E_002')",
                    "DELETE FROM staffinfo "
                            + "WHERE staffid IN ('TEST_JUNIT_E2E_001', 'TEST_JUNIT_E2E_002')"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void listPageWorksEndToEnd() throws Exception {
        // act & assert:
        // URL にアクセスし、画面名と model 属性を確認する。
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/list"))
                .andExpect(model().attributeExists("staffList"));
    }

    /**
     * 詳細画面の総合テスト。
     *
     * <p>確認したいこと:
     * URL パスの staffId が Controller に渡ること。
     * Service と Repository を通じて DB の詳細データを取得できること。
     * 最終的に staff/detail 画面が返り、model に staffDetail が入ること。
     */
    @Test
    @DisplayName("SpringBootTest: 詳細画面が Controller から DB までつながって表示される")
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_E2E_101'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_E2E_101'",
                    "INSERT INTO staffinfo (staffid, name, division) "
                            + "VALUES ('TEST_JUNIT_E2E_101', 'Alice', 'Sales')",
                    "INSERT INTO staffdetail (staffid, firstname, lastname, age, position) "
                            + "VALUES ('TEST_JUNIT_E2E_101', 'A', 'Iice', 30, 'Leader')"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            statements = {
                    "DELETE FROM staffdetail WHERE staffid = 'TEST_JUNIT_E2E_101'",
                    "DELETE FROM staffinfo WHERE staffid = 'TEST_JUNIT_E2E_101'"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void detailPageWorksEndToEnd() throws Exception {
        // act & assert:
        // 詳細画面にアクセスし、画面名と model 属性を確認する。
        mockMvc.perform(get("/list/detail/TEST_JUNIT_E2E_101"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/detail"))
                .andExpect(model().attributeExists("staffDetail"));
    }
}
