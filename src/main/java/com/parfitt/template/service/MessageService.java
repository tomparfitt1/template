package com.parfitt.template.service;

import com.parfitt.template.channel.Channel;
import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.Template;
import com.samskivert.mustache.Mustache;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private TemplateService templateService;
    private ChannelService channelService;

    @Autowired
    public MessageService(TemplateService templateService, ChannelService channelService) {
        this.templateService = templateService;
        this.channelService = channelService;
    }

    public void send(long templateId, ChannelType channelType, Map<String, Object> content) {

        Template template = templateService.getTemplate(templateId, channelType);
        Set<Channel> applicableChannels = channelService.getApplicableChannels(channelType);

        String message = Mustache.compiler()
                .compile(template.getContent())
                .execute(content);

        applicableChannels.forEach(
                c -> c.send(message)
        );
    }

}
