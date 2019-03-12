package com.parfitt.template.channel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static com.parfitt.template.entity.ChannelType.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailChannelTest {

    @Rule
    public OutputCapture capture = new OutputCapture();

    private Channel channel;

    @Before
    public void setUp() {
        channel = new EmailChannel();
    }

    @Test
    public void getType_returnsCorrectType() {
        assertThat(channel.getType()).isEqualTo(EMAIL);
    }

    @Test
    public void send_withContent_LogsOutputCorrectly() {
        // Given
        String content = "send me";

        // When
        channel.send(content);

        // Then
        assertThat(capture.toString()).contains("Sending to EMAIL with content:\nsend me");
    }
}