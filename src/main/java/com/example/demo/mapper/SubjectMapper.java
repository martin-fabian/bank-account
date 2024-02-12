package com.example.demo.mapper;

import com.example.demo.api.CreateSubjectRequest;
import com.example.demo.api.SubjectResponse;
import com.example.demo.domain.Subject;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {

    Subject map(CreateSubjectRequest request);

    SubjectResponse map(Subject subject);
}
