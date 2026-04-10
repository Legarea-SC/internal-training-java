package com.example.LEGAREA.controller.staff;

import com.example.LEGAREA.entity.StaffDetailEntity;
import com.example.LEGAREA.entity.StaffEntity;
import com.example.LEGAREA.service.staff.StaffService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(Staffcontroller.class)
public class StaffcontrollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffService staffService;

    /**
     * 一覧画面表示のテスト。
     *
     * <p>確認したいこと:
     * Service が返した一覧を Controller が model に詰め、
     * staff/list を返すこと。
     */
    @Test
    void showEmployeeListAddsStaffListToModel() throws Exception {
        List<StaffEntity> staffList = List.of(
                new StaffEntity("S001", "Alice", "Sales")
        );
        when(staffService.find()).thenReturn(staffList);

        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/list"))
                .andExpect(model().attribute("staffList", staffList));
    }

    /**
     * 詳細画面表示の正常系テスト。
     *
     * <p>確認したいこと:
     * Service から受け取った詳細データを model に入れ、
     * staff/detail を返すこと。
     */
    @Test
    void showDetailAddsFetchedStaffDetailToModel() throws Exception {
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

        mockMvc.perform(get("/list/detail/S001"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/detail"))
                .andExpect(model().attribute("staffDetail", detail));
    }

    /**
     * 詳細画面表示の代替データテスト。
     *
     * <p>確認したいこと:
     * Service が null を返しても Controller が落ちず、
     * 画面用の staffDetail を model に入れること。
     */
    @Test
    void showDetailAddsEmptyEntityWhenServiceReturnsNull() throws Exception {
        when(staffService.findDatail("S001")).thenReturn(null);

        mockMvc.perform(get("/list/detail/S001"))
                .andExpect(status().isOk())
                .andExpect(view().name("staff/detail"))
                .andExpect(model().attributeExists("staffDetail"));
    }
}
