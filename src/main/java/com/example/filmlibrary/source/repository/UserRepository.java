package com.example.filmlibrary.source.repository;

import com.example.filmlibrary.source.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends GenericRepository<User>{
//    @Query(nativeQuery = true,
//    value = "select * from users where login = :login")
//    @Query(nativeQuery = false,
//            value = "select User from User where login = :login") // другой синтаксис - hsql
    User findUserByLogin(String login);

    User findUserByEmail(String email);
//    User findUserByPhone(String phone);
}