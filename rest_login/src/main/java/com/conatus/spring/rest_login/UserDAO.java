package com.conatus.spring.rest_login;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//import com.conatus.spring.rest_login.UserEntityClass;

@Repository
public interface UserDAO extends CrudRepository<UserEntityClass, Integer> {
	UserDAO findByUsername(String username);
}

