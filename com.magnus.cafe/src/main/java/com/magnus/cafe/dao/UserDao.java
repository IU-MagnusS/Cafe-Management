package com.magnus.cafe.dao;

import com.magnus.cafe.POJO.User;
import com.magnus.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    List<String> getAllAdmin();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    @Transactional
    @Modifying
    int updateEmail(@Param("email") String email, @Param("id") Integer id);

    @Transactional
    @Modifying
    int updateContactNumber(@Param("contactNumber") String contactNumber, @Param("id") Integer id);

    @Transactional
    @Modifying
    int updateName(@Param("name") String name, @Param("id") Integer id);

    User findByEmail(String email);

    @Transactional
    @Modifying

    int deleteUsers(@Param("userIds") List<Integer> userIds);

}


