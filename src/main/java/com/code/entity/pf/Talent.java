package com.code.entity.pf;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Data
@Table(name = "`pf_talent`")
@ApiModel(value="Talent对象", description="")
public class Talent {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long talentId;

    @ApiModelProperty(value = "人才名称")
    @Column(name = "`talent_name`")
    private String talentName;

    @ApiModelProperty(value = "密码")
    @Column(name = "password")
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "手机号")
    @Column(name = "mobile")
    @ExcelProperty("手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @Column(name = "email")
    @ExcelProperty("邮箱")
    private String email;

    @ApiModelProperty(value = "人才状态")
    @Column(name = "`status`")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @Column(name = "creator")
    private Long creator;

    @ApiModelProperty(value = "最后更新时间")
    @Column(name = "lmt")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lmt;

    @ApiModelProperty(value = "最后修改人")
    @Column(name = "modifier")
    private Long modifier;

    @ApiModelProperty(value = "状态名称")
    @ExcelProperty("状态名称")
    @Transient
    private String statusName;

    @ApiModelProperty(value = "报名时间")
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    @ApiModelProperty(value = "报名状态")
    @Transient
    private String applyStatusName;

    @ApiModelProperty(value = "项目列表")
    @Transient
    private ArrayList<Project> projectList;

}
