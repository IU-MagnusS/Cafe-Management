package com.magnus.cafe.dao;

import com.magnus.cafe.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

<<<<<<< Updated upstream
=======
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

>>>>>>> Stashed changes
}
