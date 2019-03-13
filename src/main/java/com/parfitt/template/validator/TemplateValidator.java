package com.parfitt.template.validator;

import com.parfitt.template.entity.Template;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TemplateValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Template.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Template template = (Template) o;
        if (template.getChannelTypes() == null || template.getChannelTypes().isEmpty()) {
            errors.rejectValue("channelTypes", "channelTypes.empty");
        }

        if (template.getContent() == null || "".equals(template.getContent().trim())) {
            errors.rejectValue("content", "content.empty");
        }

    }
}
