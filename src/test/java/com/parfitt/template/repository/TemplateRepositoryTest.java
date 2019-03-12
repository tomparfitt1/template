package com.parfitt.template.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfitt.template.entity.Template;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static com.parfitt.template.entity.Channel.SMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TemplateRepositoryTest {

    @Autowired
    private TemplateRepository repository;

    @Autowired
    private MockMvc mvc;

    private Template template;

    @Before
    public void setup() {
        template = new Template();
        template.setContent("dummy content");
        template.setChannel(SMS);
    }

    @Test
    public void create_withValidEntity_returns201() throws Exception {
        // Given
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(template);

        // When
        ResultActions perform = this.mvc.perform(
                post("/template")
                        .contentType(APPLICATION_JSON)
                        .content(json));

        // Then
        perform.andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void create_withInvalidEntity_returns201() throws Exception {
        // Given
        ObjectMapper mapper = new ObjectMapper();
        template.setContent("");
        String json = mapper.writeValueAsString(template);

        // When
        ResultActions perform = this.mvc.perform(
                post("/template")
                        .contentType(APPLICATION_JSON)
                        .content(json));

        // Then
        perform.andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    public void read_withValidId_returns204() throws Exception {
        // Given
        Template savedTemplate = repository.save(template);

        // When
        ResultActions perform = this.mvc.perform(delete("/template/{templateId}", savedTemplate.getId()));

        // Then
        perform.andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void read_withInvalidId_returns404() throws Exception {
        // Given
        long templateId = 1234L;

        // When
        ResultActions perform = this.mvc.perform(delete("/template/{templateId}", templateId));

        // Then
        perform.andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    public void update_withValidId_returns204() throws Exception {
        // Given
        Template savedTemplate = repository.save(template);
        long templateId = savedTemplate.getId();

        String updatedContent = "updated content";
        savedTemplate.setContent(updatedContent);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(savedTemplate);

        // When
        ResultActions perform = this.mvc.perform(
                put("/template/{templateId}", templateId)
                        .contentType(APPLICATION_JSON)
                        .content(json));

        // Then
        perform.andExpect(status().isNoContent())
                .andExpect(content().string(""));
        Optional<Template> foundTemplate = repository.findById(templateId);
        assertThat(foundTemplate).isPresent();
        assertThat(foundTemplate.get().getContent()).isEqualTo(updatedContent);
    }

    @Test
    public void delete_withValidId_returns204() throws Exception {
        // Given
        Template savedTemplate = repository.save(template);
        long templateId = savedTemplate.getId();

        // When
        ResultActions perform = this.mvc.perform(delete("/template/{templateId}", templateId));

        // Then
        perform.andExpect(status().isNoContent())
                .andExpect(content().string(""));
        Optional<Template> foundTemplate = repository.findById(templateId);
        assertThat(foundTemplate).isNotPresent();
    }

    @Test
    public void delete_withInvalidId_returns404() throws Exception {
        // Given
        long templateId = 12345L;

        // When
        ResultActions perform = this.mvc.perform(delete("/template/{templateId}", templateId));

        // Then
        perform.andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }
}