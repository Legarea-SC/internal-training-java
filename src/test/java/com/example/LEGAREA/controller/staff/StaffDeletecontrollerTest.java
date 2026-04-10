package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffDeleteService;
import com.example.LEGAREA.service.staff.StaffService;
import java.util.List;
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

@WebMvcTest(StaffDeletecontroller.class)
public class StaffDeletecontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService;

    @MockBean
    private StaffDeleteService staffDeleteService;

    /**
     * 削除画面表示のテスト。
     */
    @Test
    void showDeleteFormAddsStaffListAndTargetStaff() throws Exception {
        StaffDetailEntity detail = new StaffDetailEntity(
                "S001",
                "Alice",
                "Sales",
                "A",
                "Iice",
                "Leader",
                30
        );
        List<StaffEntity> staffList = List.of(
                new StaffEntity("S001", "Alice", "Sales")
        );
        when(staffService.find()).thenReturn(staffList);
        when(staffService.findDatail("S001")).thenReturn(detail);

        mockMvc.perform(get("/delete").param("staffId", "S001"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/delete"))
                .andExpect(model().attribute("staffList", staffList))
                .andExpect(model().attribute("staffDelete", detail));
    }

    /**
     * 削除処理の正常系テスト。
     */
    @Test
    void deleteRedirectsBackToDeletePageWhenValidationSucceeds() throws Exception {
        when(staffDeleteService.Deletestaff(any())).thenReturn("deleted");

        mockMvc.perform(post("/delete")
                        .param("staffId", "S001")
                        .param("name", "Alice")
                        .param("division", "Sales")
                        .param("firstName", "A")
                        .param("lastName", "Iice")
                        .param("position", "Leader")
                        .param("age", "30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/delete"))
                .andExpect(flash().attribute("message", "deleted"));
    }
}
