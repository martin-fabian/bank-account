package com.example.demo.service;

import com.example.demo.api.CreateSubjectRequest;
import com.example.demo.api.SubjectResponse;
import com.example.demo.client.PrefixClient;
import com.example.demo.domain.BankAccount;
import com.example.demo.domain.Subject;
import com.example.demo.mapper.SubjectMapper;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final BankAccountRepository bankAccountRepository;
    private final SequenceProvider sequenceProvider;
    private final PrefixClient prefixClient;

    @Override
    @Transactional
    public Long save(CreateSubjectRequest request) {
        Subject subject = subjectRepository.saveAndFlush(subjectMapper.map(request));
        Long subjectId = subject.getId();

        String suffix = sequenceProvider.next();

        PrefixClient.Prefix prefixBody = prefixClient.getPrefix().getBody();
        if (prefixBody == null || prefixBody.getPrefix() == null) {
            throw new IllegalStateException("Prefix response body or prefix value is null");
        }
        String prefix = prefixBody.getPrefix();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setSubject(subject);
        bankAccount.setSuffix(suffix);
        bankAccount.setPrefix(prefix);
        bankAccountRepository.saveAndFlush(bankAccount);

        return subjectId;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectResponse> findById(Long id) {
        return subjectRepository.findById(id)
                .map(db -> {
                    final var mapped = subjectMapper.map(db);
                    mapped.setNumberOfAccounts(bankAccountRepository.numberOfAccounts(db.getId()));

                    return mapped;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> subjectsWithLowBalance() {
        return subjectRepository.getSubjectsWithLowBalance()
                .stream()
                .map(subjectMapper::map)
                .collect(Collectors.toList());
    }
}
