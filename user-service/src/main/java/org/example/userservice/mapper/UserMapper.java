package org.example.userservice.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.example.userservice.pojo.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO `tp-user` (username, email, password, created_at, updated_at) VALUES (#{username}, #{email}, #{password}, #{createdAt}, #{updatedAt})")
    void insertUser(User user);

    List<User> findByUsername(String username);

    List<User> findByEmail(String email);
}
