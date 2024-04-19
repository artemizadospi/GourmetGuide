package com.pweb.gourmetguide.repository;

import com.pweb.gourmetguide.exception.UserNotFoundException;
import com.pweb.gourmetguide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username) throws UserNotFoundException;
    User getUserById(int id);
}
