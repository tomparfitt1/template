package com.parfitt.template.service;

import static com.parfitt.template.entity.ChannelType.EMAIL;
import static com.parfitt.template.entity.ChannelType.PUSH;
import static com.parfitt.template.entity.ChannelType.SMS;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

import com.parfitt.template.channel.Channel;
import com.parfitt.template.entity.exception.ChannelNotFoundException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ChannelServiceTest {

    private ChannelService channelService;

    @Mock
    private Channel channel1;

    @Mock
    private Channel channel2;

    @Before
    public void setUp() throws Exception {
        Set<Channel> channels = new HashSet(asList(channel1, channel2));
        channelService = new ChannelService(channels);
    }

    @Test
    public void getApplicableChannels_withApplicableChannel_returnsCorrectChannel() {
        // Given
        given(channel1.getType()).willReturn(SMS);
        given(channel2.getType()).willReturn(PUSH);

        // When
        Set<Channel> applicableChannels = channelService.getApplicableChannels(SMS);

        // Then
        assertThat(applicableChannels).containsExactly(channel1);
    }

    @Test
    public void getApplicableChannels_withNoApplicableChannel_returnsException() {
        // Given
        given(channel1.getType()).willReturn(SMS);
        given(channel2.getType()).willReturn(PUSH);

        // When + Then
        assertThatExceptionOfType(ChannelNotFoundException.class)
                .isThrownBy(() -> channelService.getApplicableChannels(EMAIL))
                .withNoCause();
    }
}