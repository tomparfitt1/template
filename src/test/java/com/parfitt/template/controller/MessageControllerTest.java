package com.parfitt.template.controller;

import static com.parfitt.template.entity.ChannelType.SMS;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.MessageRequest;
import com.parfitt.template.service.MessageService;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void postMessage_withValidBody_returns204() throws Exception {
        // Given
        long templateId = 1L;
        ChannelType channelType = SMS;
        Map<String, Object> content = singletonMap("name", "Bob");
        MessageRequest request = new MessageRequest(templateId, channelType, content);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(request);
        willDoNothing().given(messageService).send(anyLong(), any(ChannelType.class), anyMap());

        // When
        ResultActions perform = this.mockMvc.perform(post("/message").contentType(APPLICATION_JSON).content(json));

        // Then
        perform.andExpect(status().isNoContent());
        then(messageService).should().send(templateId, channelType, content);
    }

    @Test
    public void postMessage_withMissingChannelType_returns400() throws Exception {
        // Given
        String json = "{\"template\":1,\"content\":{\"name\":\"Bob\"}}";

        // When
        ResultActions perform = this.mockMvc.perform(post("/message").contentType(APPLICATION_JSON).content(json));

        // Then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    public void postMessage_withMissingTemplate_returns400() throws Exception {
        // Given
        String json = "{\"channel\":\"SMS\",\"content\":{\"name\":\"Bob\"}}";

        // When
        ResultActions perform = this.mockMvc.perform(post("/message").contentType(APPLICATION_JSON).content(json));

        // Then
        perform.andExpect(status().isBadRequest());
    }

}