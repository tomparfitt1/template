package com.parfitt.template.service;

import com.parfitt.template.channel.Channel;
import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.exception.ChannelNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    private Set<Channel> channels;

    @Autowired
    public ChannelService(Set<Channel> channels) {
        this.channels = channels;
    }

    public Set<Channel> getApplicableChannels(ChannelType channelType) {
        Set<Channel> applicableChannels = channels.stream()
                .filter(c -> c.getType() == channelType)
                .collect(Collectors.toSet());

        // check if there are channels compatible with channel type
        if (applicableChannels.isEmpty()) {
            throw new ChannelNotFoundException();
        } else {
            return applicableChannels;
        }
    }

}
