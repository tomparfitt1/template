package com.parfitt.template.channel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static com.parfitt.template.entity.ChannelType.EMAIL;
import static com.parfitt.template.entity.ChannelType.PUSH;
import static org.assertj.core.api.Assertions.assertThat;

public class PushChannelTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    private Channel channel;

    @Before
    public void setUp() {
        channel = new PushChannel();
    }

    @Test
    public void getType_returnsCorrectType() {
        assertThat(channel.getType()).isEqualTo(PUSH);
    }

    @Test
    public void send_withContent_LogsOutputCorrectly() {
        // Given
        String content = "send me";

        // When
        channel.send(content);

        // Then
        assertThat(capture.toString()).contains("Sending to PUSH with content:\nsend me");
    }
}