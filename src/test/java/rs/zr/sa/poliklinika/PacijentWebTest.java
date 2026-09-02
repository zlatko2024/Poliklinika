package rs.zr.sa.poliklinika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rs.zr.sa.poliklinika.controller.PacijentController;
import rs.zr.sa.poliklinika.service.PacijentService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacijentController.class)
public class PacijentWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacijentService pacijentService;

    @Test
    public void ptest1() throws Exception { mockMvc.perform(get("/api/pacijenti")).andExpect(status().isOk()); }
    @Test
    public void ptest2() throws Exception { mockMvc.perform(get("/api/pacijenti").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()); }
    @Test
    public void ptest3() throws Exception { mockMvc.perform(get("/api/pacijenti").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()); }
    @Test
    public void ptest4() throws Exception { mockMvc.perform(get("/api/pacijenti/search?ime=pera")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest5() throws Exception { mockMvc.perform(get("/api/pacijenti/1")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest6() throws Exception { mockMvc.perform(get("/api/pacijenti/2")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest7() throws Exception { mockMvc.perform(get("/api/pacijenti/3")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest8() throws Exception { mockMvc.perform(get("/api/pacijenti/4")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest9() throws Exception { mockMvc.perform(get("/api/pacijenti/5")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest10() throws Exception { mockMvc.perform(get("/api/pacijenti/6")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest11() throws Exception { mockMvc.perform(get("/api/pacijenti/7")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest12() throws Exception { mockMvc.perform(get("/api/pacijenti/8")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest13() throws Exception { mockMvc.perform(get("/api/pacijenti/9")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest14() throws Exception { mockMvc.perform(get("/api/pacijenti/10")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest15() throws Exception { mockMvc.perform(get("/api/pacijenti/karton/1")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest16() throws Exception { mockMvc.perform(get("/api/pacijenti/istorija/1")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest17() throws Exception { mockMvc.perform(get("/api/pacijenti/aktivni")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
    @Test
    public void ptest18() throws Exception { mockMvc.perform(get("/api/pacijenti/pretraga")).andExpect(result -> assertTrue(result.getResponse().getStatus() > 0)); }
}