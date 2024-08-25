package com.arpit.multipleDbConnection.mySQLDb.repo;

import com.arpit.multipleDbConnection.mySQLDb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
