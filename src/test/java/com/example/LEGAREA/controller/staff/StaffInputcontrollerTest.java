package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.service.staff.StaffInputService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StaffInputcontroller.class)
public class StaffInputcontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffInputService staffInputService;

    /**
     * 入力画面初期表示のテスト。
     *
     * <p>確認したいこと:
     * GET /input で入力画面が開き、
     * model に staffInput が用意されること。
     */
    @Test
    void showInputFormAddsEmptyInputEntity() throws Exception {
        mockMvc.perform(get("/input"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/input"))
                .andExpect(model().attributeExists("staffInput"));
    }

    /**
     * 入力画面の正常登録テスト。
     *
     * <p>確認したいこと:
     * バリデーション成功時に /list へリダイレクトし、
     * flash にメッセージが入ること。
     */
    @Test
    void inputStaffRedirectsToListWhenValidationSucceeds() throws Exception {
        when(staffInputService.create(any())).thenReturn("created");

        mockMvc.perform(post("/input")
                        .param("staffId", "S001")
                        .param("name", "Alice")
                        .param("division", "Sales")
                        .param("firstName", "A")
                        .param("lastName", "Iice")
                        .param("position", "Leader")
                        .param("age", "30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/list"))
                .andExpect(flash().attribute("message", "created"));
    }

    /**
     * 入力画面のバリデーションエラーテスト。
     *
     * <p>確認したいこと:
     * 必須項目が未入力だと入力画面へ戻り、
     * エラー対象フィールドが model に記録されること。
     */
    @Test
    void inputStaffReturnsFormWhenValidationFails() throws Exception {
        mockMvc.perform(post("/input")
                        .param("staffId", "")
                        .param("name", "Alice")
                        .param("division", "Sales")
                        .param("firstName", "A")
                        .param("lastName", "Iice")
                        .param("age", "30"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/input"))
                .andExpect(model().attributeHasFieldErrors("staffInput", "staffId"));
    }
}
