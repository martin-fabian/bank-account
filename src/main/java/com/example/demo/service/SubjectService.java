package com.example.demo.service;

import com.example.demo.api.CreateSubjectRequest;
import com.example.demo.api.SubjectResponse;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    Long save(CreateSubjectRequest request);

    Optional<SubjectResponse> findById(Long id);

    List<SubjectResponse> subjectsWithLowBalance();
}
