package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.service.staff.StaffService;
import com.example.LEGAREA.service.staff.StaffUpdateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(StaffUpdatecontroller.class)
public class StaffUpdatecontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService;

    @MockBean
    private StaffUpdateService staffUpdateService;

    /**
     * 更新画面初期表示のテスト。
     */
    @Test
    void showUpdateFormAddsEmptyEntityWhenStaffIdIsMissing() throws Exception {
        mockMvc.perform(get("/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/update"))
                .andExpect(model().attributeExists("staffUpdate"));
    }

    /**
     * 更新画面詳細表示のテスト。
     */
    @Test
    void showUpdateFormAddsFetchedStaffWhenStaffIdExists() throws Exception {
        StaffDetailEntity detail = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        when(staffService.findDatail("S001")).thenReturn(detail);

        mockMvc.perform(get("/update").param("staffId", "S001"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/update"))
                .andExpect(model().attribute("staffUpdate", detail));
    }

    /**
     * 更新処理の正常系テスト。
     */
    @Test
    void updateReturnsUpdateViewWhenValidationSucceeds() throws Exception {
        StaffDetailEntity refreshed = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        when(staffUpdateService.updateDatail(any())).thenReturn("updated");
        when(staffService.findDatail("S001")).thenReturn(refreshed);

        mockMvc.perform(post("/update")
                        .param("staffId", "S001")
                        .param("name", "Alice")
                        .param("division", "Sales")
                        .param("firstName", "A")
                        .param("lastName", "Iice")
                        .param("position", "Leader")
                        .param("age", "30"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/update"))
                .andExpect(model().attribute("message", "updated"))
                .andExpect(model().attribute("staffUpdate", refreshed));
    }
}
