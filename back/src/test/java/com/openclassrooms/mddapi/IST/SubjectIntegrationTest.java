import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateSubject() throws Exception {
        String subjectName = "Java";

        // Envoie d'une requête HTTP POST pour créer un nouveau sujet
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + subjectName + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(subjectName));
    }

    @Test
    public void testGetSubjectById() throws Exception {
        // Créer un sujet dans la base de données pour les besoins du test
        Subject subject = new Subject("Java");
        subjectRepository.save(subject);

        // Envoie d'une requête HTTP GET pour récupérer le sujet par ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subjects/" + subject.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(subject.getName()));
    }

    // Autres tests pour les autres fonctionnalités du SubjectController, SubjectService et SubjectRepository...

}
