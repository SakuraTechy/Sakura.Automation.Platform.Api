package com.sakura.system.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 步骤
 * @author wutun
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Step implements Serializable {

      private String pid;

      private Integer order;

      private String id;

      private String name;

      private String operationType;

      private String operationName;

      private String action;

      private String setting;

      private List<Config> config;

      private String copyId;
      private String copyPid;
//      private String stepType;
//      private String host; //服务器地址
//
//      private String port; //服务器端口
//
//      private String userName; //服务器用户名
//
//      private String passWord;//服务器密码
//
//      private String shell;//服务器执行命令
//
//      private String url; //URL地址
//
//      private String type;//元素定位方
//
//      private String element;//元素坐标
//
//      private String locator;//元素坐标地址
//
//      private String value;//输入内容
//
//      private String expect;//登录失败弹窗提示
//
//      private String message;//提示信息
//
//      private String catalogue;//目录
//
//      private String path;//路径
//
//      private String file;//文件
//
//      private String skip;//是否跳过


}
