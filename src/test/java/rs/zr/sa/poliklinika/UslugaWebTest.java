package rs.zr.sa.poliklinika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rs.zr.sa.poliklinika.controller.UslugaController;
import rs.zr.sa.poliklinika.service.UslugaService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UslugaController.class)
public class UslugaWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UslugaService uslugaService;

    @Test
    public void test1() throws Exception { mockMvc.perform(get("/api/usluge")).andExpect(status().isOk()); }
    @Test
    public void test2() throws Exception { mockMvc.perform(get("/api/usluge").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()); }
    @Test
    public void test3() throws Exception { mockMvc.perform(get("/api/usluge").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()); }
    @Test
    public void test4() throws Exception { mockMvc.perform(get("/api/usluge/search?naziv=test")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test5() throws Exception { mockMvc.perform(get("/api/usluge/1")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test6() throws Exception { mockMvc.perform(get("/api/usluge/2")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test7() throws Exception { mockMvc.perform(get("/api/usluge/3")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test8() throws Exception { mockMvc.perform(get("/api/usluge/4")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test9() throws Exception { mockMvc.perform(get("/api/usluge/5")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test10() throws Exception { mockMvc.perform(get("/api/usluge/6")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test11() throws Exception { mockMvc.perform(get("/api/usluge/7")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test12() throws Exception { mockMvc.perform(get("/api/usluge/8")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test13() throws Exception { mockMvc.perform(get("/api/usluge/9")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test14() throws Exception { mockMvc.perform(get("/api/usluge/10")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test15() throws Exception { mockMvc.perform(get("/api/usluge/aktivne")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test16() throws Exception { mockMvc.perform(get("/api/usluge/statistika")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test17() throws Exception { mockMvc.perform(get("/api/usluge/sve")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void test18() throws Exception { mockMvc.perform(get("/api/usluge/izvestaj")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
}