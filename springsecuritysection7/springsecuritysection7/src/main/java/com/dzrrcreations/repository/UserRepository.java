package com.dzrrcreations.repository;

import org.springframework.data.repository.CrudRepository;

import com.dzrrcreations.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
