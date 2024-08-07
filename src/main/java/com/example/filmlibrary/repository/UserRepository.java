package com.example.filmlibrary.repository;


import com.example.filmlibrary.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository
        extends GenericRepository<User>{
    User findUserByLogin(String login);
    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String uuid);

    @Query(nativeQuery = true,
            value = """
                 select u.*
                 from users u
                 where u.first_name ilike '%' || coalesce(:firstName, '%') || '%'
                 and u.last_name ilike '%' || coalesce(:lastName, '%') || '%'
                 and u.login ilike '%' || coalesce(:login, '%') || '%'
                  """)
    Page<User> searchUsers(String firstName,
                           String lastName,
                           String login,
                           Pageable pageable);

    @Query(nativeQuery = true,
            value = """
            select distinct email
            from users u join orders o on u.id = o.user_id
            where o.rent_date < now()
            and o.purchase = false
            and u.is_deleted = false
            """)
    List<String> getDelayedEmails();
}