package com.parfitt.template.repository;

import com.parfitt.template.entity.Template;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "template", path = "template")
public interface TemplateRepository extends PagingAndSortingRepository<Template, Long> {

}
