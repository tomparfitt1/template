package com.parfitt.template.channel;

import com.parfitt.template.entity.ChannelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.parfitt.template.entity.ChannelType.PUSH;

@Slf4j
@Component
public class PushChannel implements Channel {

    @Override
    public ChannelType getType() {
        return PUSH;
    }

    @Override
    public void send(String content) {
        log.info("Sending to {} with content:\n{}", getType(), content);
    }

}
