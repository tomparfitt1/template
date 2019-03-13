package com.parfitt.template.validator;

import static com.parfitt.template.entity.ChannelType.SMS;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

import com.parfitt.template.entity.ChannelType;
import com.parfitt.template.entity.Template;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

public class TemplateValidatorTest {

    private TemplateValidator templateValidator;

    @Before
    public void setUp() {
        templateValidator = new TemplateValidator();
    }

    @Test
    public void supports_givenTemplate_returnsTrue() {
        // Given
        Class clazz = Template.class;

        // When
        boolean supports = templateValidator.supports(clazz);

        // Then
        assertThat(supports).isTrue();
    }

    @Test
    public void supports_givenNotTemplate_returnsTrue() {
        // Given
        Class clazz = String.class;

        // When
        boolean supports = templateValidator.supports(clazz);

        // Then
        assertThat(supports).isFalse();
    }

    @Test
    public void validate_withValidTemplate_returnsNoError() {
        // Given
        Template template = mock(Template.class);
        given(template.getChannelTypes()).willReturn(singleton(SMS));
        given(template.getContent()).willReturn("content");
        Errors errors = mock(Errors.class);

        // When
        templateValidator.validate(template, errors);

        // Then
        then(errors).shouldHaveZeroInteractions();
    }

    @Test
    public void validate_withNotChannelTypes_returnsError() {
        // Given
        Template template = mock(Template.class);
        given(template.getChannelTypes()).willReturn(emptySet());
        given(template.getContent()).willReturn("content");
        Errors errors = mock(Errors.class);
        willDoNothing().given(errors).rejectValue(anyString(), anyString());

        // When
        templateValidator.validate(template, errors);

        // Then
        then(errors).should().rejectValue("channelTypes", "channelTypes.empty");
        then(errors).shouldHaveNoMoreInteractions();
    }

    @Test
    public void validate_withNoContent_returnsError() {
        // Given
        Template template = mock(Template.class);
        given(template.getChannelTypes()).willReturn(singleton(SMS));
        given(template.getContent()).willReturn("");
        Errors errors = mock(Errors.class);
        willDoNothing().given(errors).rejectValue(anyString(), anyString());

        // When
        templateValidator.validate(template, errors);

        // Then
        then(errors).should().rejectValue("content", "content.empty");
        then(errors).shouldHaveNoMoreInteractions();
    }
}