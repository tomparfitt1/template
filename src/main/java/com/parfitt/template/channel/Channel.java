package com.parfitt.template.channel;

import com.parfitt.template.entity.ChannelType;

public interface Channel {

    ChannelType getType();

    void send(String content);

}
