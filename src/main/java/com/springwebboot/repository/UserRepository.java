package com.springwebboot.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.springwebboot.bean.UserBean;
@Deprecated
public interface UserRepository extends MongoRepository<UserBean, String>{

}
