package com.parfitt.template.service;

import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.Template;
import com.parfitt.template.entity.exception.TemplateNotFoundException;
import com.parfitt.template.entity.exception.UncompatibleTemplateException;
import com.parfitt.template.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    private TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Template getTemplate(long templateId, ChannelType channelType) {
        Template template = templateRepository.findById(templateId).orElseThrow(TemplateNotFoundException::new);

        // check if template is compatible with channel type
        if (template.getChannelTypes().contains(channelType)) {
            return template;
        } else {
            throw new UncompatibleTemplateException();
        }
    }
}
