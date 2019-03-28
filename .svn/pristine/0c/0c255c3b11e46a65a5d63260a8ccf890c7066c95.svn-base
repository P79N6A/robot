package com.bossbutler.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bossbutler.pojo.User;

public interface UserMapper {

	User queryUser(@Param("name") String name, @Param("pwd") String pwd);

	int saveUser(User user);
	
	List<User> queryUserList();
}