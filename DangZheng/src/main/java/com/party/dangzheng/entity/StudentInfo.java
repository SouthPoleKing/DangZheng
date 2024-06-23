package com.party.dangzheng.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="studentinfo")
@Getter
@Setter
public class StudentInfo extends userInfo{

    @Column(name="party_branch_id")
    private long partyBranch;

    @Column(name="tutor_id")
    private long tutor;

    @Column(name="counsellor_id")
    private long counsellor;

    @Column(name="isSecretary")
    private boolean isSecretary;

}
