package bg.softuni.paintShop.web;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser(roles = "ADMIN")
@AutoConfigureMockMvc
class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    void getComments() throws Exception {
        mockMvc.perform(get("/api/{productId}/comments", 1)).
                andExpect(status().isOk());
    }


    @Test
    void getCommentsThrowsCommentNotFoundException() throws Exception {
        mockMvc.perform(get("/api/{productId}/comments", 2)).
                andExpect(status().isNotFound());
    }

}