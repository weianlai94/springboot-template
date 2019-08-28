package com.example.demo.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
public class LoggerInfos implements Serializable {
    //编号d
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //客户端请求ip
    @Column(name = "client_ip")
    private String clientIp;
    //客户端请求路径
    private String uri;
    //终端请求方式,普通请求,ajax请求
    private String type;
    //请求方式method,post,get等
    private String method;
    //请求参数内容,json
    @Column(name = "param_data")
    private String paramData;
    //请求接口唯一session标识
    @Column(name = "session_id")
    private String sessionId;
    //请求时间
    @Column(name = "time", insertable = false)
    private Timestamp time;
    //接口返回时间
    @Column(name = "returm_time")
    private String returnTime;
    //接口返回数据json
    @Column(name = "return_data")
    private String returnData;
    //请求时httpStatusCode代码，如：200,400,404等
    @Column(name = "http_status_code")
    private String httpStatusCode;
    //请求耗时秒单位
    @Column(name = "time_consuming")
    private int timeConsuming;

}
