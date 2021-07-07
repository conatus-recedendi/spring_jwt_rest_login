package com.conatus.spring.rest_login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import com.conatus.spring.rest_login.UserEntityClass;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {
	UserDAO findByUsername(String username);
}

