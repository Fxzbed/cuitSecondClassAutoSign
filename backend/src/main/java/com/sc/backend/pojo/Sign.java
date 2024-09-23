package com.sc.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sign {
    private String scpass;
    private String activityid;
    private String signbegin;
    private Integer id;
    private String activitystatus;
    private String signend;
    private String activityname;
}
