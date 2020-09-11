package com.convenient.excel.beans.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table
public class ExcelGetway implements Serializable {

    @Id
    private long getwayId;
    private long type;
    private long userId;
    private LocalDateTime createTime;
    private long updateUserId;
    private LocalDateTime updateTime;


}
