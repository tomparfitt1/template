package com.parfitt.template.entity;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private long template;
    private ChannelType channel;
    private Map<String, Object> content;

}
