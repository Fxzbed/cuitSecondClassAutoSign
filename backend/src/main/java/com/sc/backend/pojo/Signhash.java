package com.sc.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signhash {
    private String activityid;
    private long begintime;
    private long endtime;
}
