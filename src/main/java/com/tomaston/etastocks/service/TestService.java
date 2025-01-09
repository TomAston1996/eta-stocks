package com.tomaston.etastocks.service;

import com.tomaston.etastocks.domain.TestModel;
import com.tomaston.etastocks.dto.TestDTO;
import com.tomaston.etastocks.repository.TestRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    TestRepository testRepository;

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public TestDTO getTestData(Integer id) {

        TestModel testModel = testRepository.findById(id);
        return new TestDTO.Builder()
            .setId(testModel.getId())
            .setStreetName(testModel.getStreetName())
            .setHouseNumber(testModel.getHouseNumber())
            .setCity(testModel.getCity())
            .build();
    }
}
