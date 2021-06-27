package com.loco.photo.locophoto.repository

import com.loco.photo.locophoto.bean.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM User u WHERE u.email = ?1",
            nativeQuery = true)
    User findDistinctEmail(String email);

}
