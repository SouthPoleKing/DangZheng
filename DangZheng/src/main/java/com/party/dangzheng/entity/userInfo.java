package com.party.dangzheng.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//所有用户共用字段
@Getter
@Setter
@Data
public class userInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="account")
    private long account;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private int role;

    @Column(name="token")
    private String token;

    private Byte isDeleted;
    private Byte lockedFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dateCreated;


}
