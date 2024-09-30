package com.tomaston.etastocks.repository;

import com.tomaston.etastocks.domain.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Integer> {
}
