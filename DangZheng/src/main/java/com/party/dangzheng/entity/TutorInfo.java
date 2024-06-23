package com.party.dangzheng.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="tutorinfo")
public class TutorInfo extends userInfo{
    @Column(name="job_title")
    private String jobTitle;
}
