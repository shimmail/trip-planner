package org.example.userservice.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.example.userservice.pojo.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO `tp-user`.`tp-user` (username, email, password, created_at, updated_at) VALUES (#{username}, #{email}, #{password}, #{createdAt}, #{updatedAt})")
    void insertUser(User user);

    User findByUsername(String username);

    User findByEmail(String email);
}
