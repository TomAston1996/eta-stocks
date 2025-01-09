package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.TestModel;
import com.tomaston.etastocks.exception.NotFoundRequestException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class TestRepository {

    HashMap<Integer, TestModel> testMap = new HashMap<>();
    Integer lastId = 0;

    public TestRepository() {
        addTestModel(new TestModel(lastId + 1, "Broadway", 123, "New York", "Personal Info"));
        addTestModel(new TestModel(lastId + 1, "Broadway", 456, "New York", "Personal Info"));
        addTestModel(new TestModel(lastId + 1, "Wall Street", 789, "New York", "Personal Info"));
    }

    public TestModel findById(Integer id) {
        if (!testMap.containsKey(id)) {
            throw new NotFoundRequestException("TestModel with id {" + id + "} not found...");
        }
        return testMap.get(id);
    }

    public void addTestModel(TestModel testModel) {
        testMap.put(lastId + 1, testModel);
        lastId++;
    }
}
