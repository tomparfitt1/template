package com.parfitt.template.entity;

import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    @NotNull
    private Long template;

    @NotNull
    private ChannelType channel;

    private Map<String, Object> content;

}
