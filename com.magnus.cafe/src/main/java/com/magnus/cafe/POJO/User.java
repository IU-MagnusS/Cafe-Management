package com.magnus.cafe.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
<<<<<<< Updated upstream
=======

@NamedQuery(name = "User.getAllUser", query = "select new com.magnus.cafe.wrapper.UserWrapper(u.id, u.name,u.email,u.contactNumber,u.status) from User u where u.role='user' and u.status <> 'Deleted'" )

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status =:status where u.id=:id")

@NamedQuery(name = "User.updateEmail", query = "update User u set u.email = :email where u.id = :id")

@NamedQuery(name = "User.updateContactNumber", query = "update User u set u.contactNumber = :contactNumber where u.id = :id")

@NamedQuery(name = "User.updateName", query = "update User u set u.name = :name where u.id = :id")

@NamedQuery(name = "User.getAllAdmin", query = "select u.email  from User u where u.role='admin'" )

@NamedQuery(name = "User.deleteUsers", query ="UPDATE User u SET u.status = 'Deleted' WHERE u.id IN :userIds")


>>>>>>> Stashed changes
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
