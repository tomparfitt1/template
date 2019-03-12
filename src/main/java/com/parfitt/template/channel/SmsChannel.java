package com.parfitt.template.channel;

import com.parfitt.template.entity.ChannelType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.parfitt.template.entity.ChannelType.SMS;

@Slf4j
@Component
public class SmsChannel implements Channel {

    @Override
    public ChannelType getType() {
        return SMS;
    }

    @Override
    public void send(String content) {
        log.info("Sending to {} with content:\n{}", getType(), content);
    }

}
