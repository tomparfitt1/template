package com.parfitt.template.service;

import static com.parfitt.template.entity.ChannelType.EMAIL;
import static com.parfitt.template.entity.ChannelType.SMS;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.parfitt.template.entity.Template;
import com.parfitt.template.entity.exception.TemplateNotFoundException;
import com.parfitt.template.entity.exception.IncompatibleTemplateException;
import com.parfitt.template.repository.TemplateRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    private TemplateService templateService;

    @Before
    public void setUp() throws Exception {
        templateService = new TemplateService(templateRepository);
    }

    @Test
    public void getTemplate_withApplicableTemplate_returnsCorrectTemplate() {
        // Given
        Template template = mock(Template.class);
        given(template.getChannelTypes()).willReturn(singleton(SMS));
        given(templateRepository.findById(anyLong())).willReturn(Optional.of(template));

        // When
        Template returnedTemplate = templateService.getTemplate(1L, SMS);

        // Then
        then(templateRepository).should().findById(1L);
        then(templateRepository).shouldHaveNoMoreInteractions();

        assertThat(returnedTemplate).isEqualTo(template);
    }

    @Test
    public void getTemplate_withNotFoundTemplate_returnsException() {
        // Given
        given(templateRepository.findById(anyLong())).willReturn(Optional.empty());

        // When
        assertThatExceptionOfType(TemplateNotFoundException.class)
                .isThrownBy(() -> templateService.getTemplate(1L, SMS))
                .withNoCause();

        // Then
        then(templateRepository).should().findById(1L);
        then(templateRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void getTemplate_withUncompilableTemplates_returnsException() {
        // Given
        Template template = mock(Template.class);
        given(template.getChannelTypes()).willReturn(singleton(SMS));
        given(templateRepository.findById(anyLong())).willReturn(Optional.of(template));

        // When
        assertThatExceptionOfType(IncompatibleTemplateException.class)
                .isThrownBy(() -> templateService.getTemplate(1L, EMAIL))
                .withNoCause();

        // Then
        then(templateRepository).should().findById(1L);
        then(templateRepository).shouldHaveNoMoreInteractions();
    }

}