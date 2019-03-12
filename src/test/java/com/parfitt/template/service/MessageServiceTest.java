package com.parfitt.template.service;

import static com.parfitt.template.entity.ChannelType.SMS;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

import com.parfitt.template.channel.Channel;
import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.Template;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private ChannelService channelService;

    @Mock
    private TemplateService templateService;

    private MessageService messageService;

    @Before
    public void setUp() {
        messageService = new MessageService(templateService, channelService);
    }

    @Test
    public void send_withValidArgs_sendsCompiledMessageToChannel() {
        // Given
        Template template = mock(Template.class);
        given(template.getContent()).willReturn("Hi {{name}}");
        given(templateService.getTemplate(anyLong(), any(ChannelType.class))).willReturn(template);

        Channel channel1 = mock(Channel.class);
        Channel channel2 = mock(Channel.class);
        Set<Channel> channels = new HashSet(asList(channel1, channel2));
        channels.forEach(c -> willDoNothing().given(c).send(anyString()));
        given(channelService.getApplicableChannels(any(ChannelType.class))).willReturn(channels);

        // When
        messageService.send(1L, SMS, singletonMap("name", "Bob"));

        // Then
        then(channel1).should().send("Hi Bob");
        then(channel2).should().send("Hi Bob");
    }
}