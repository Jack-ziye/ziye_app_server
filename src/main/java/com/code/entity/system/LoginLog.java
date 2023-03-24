package com.code.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@Table(name = "`user_login`")
@ApiModel(value="UserLogin对象", description="登录日志对象")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "token")
    @Column(name = "token")
    private String token;

    @ApiModelProperty(value = "登录地址")
    @Column(name = "address")
    private String address;

    @ApiModelProperty(value = "登录地点")
    @Column(name = "place")
    private String place;

    @ApiModelProperty(value = "浏览器")
    @Column(name = "browser")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    @Column(name = "system")
    private String system;

    @ApiModelProperty(value = "登录状态")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "登录时间")
    @Column(name = "login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    @ApiModelProperty(value = "用户名称")
    @Transient
    private String username;

    @ApiModelProperty(value = "状态名称")
    @Transient
    private String statusName;

}
