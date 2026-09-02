package rs.zr.sa.poliklinika;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PregledControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPregledi() throws Exception {
        mockMvc.perform(get("/api/pregledi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}