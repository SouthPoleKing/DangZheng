package com.party.dangzheng.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class userToken {
    private String token;
    private Long id;
    private Date createTime;
    private Date expireTime;
}
