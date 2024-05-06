package com.sakura.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.sakura.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * YAML项目配置信息实体类
 *
 * @author liuzhi
 * @date 2022年12月5日 17:09:35
 */
@Data
@NoArgsConstructor
@ApiModel(value = "Yaml配置对象模型")
public class SysYamlConfig {

    @ApiModelProperty(value = "项目配置", required = true, example = "{\"projects\":[{\"index\":1,\"id\":1,\"name\":\"数据脱敏系统\",\"description\":\"AAS_DM\",\"environments\":[{\"index\":1,\"id\":1,\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":1,\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"V3.0B08D005\",\"description\":\"上一个版本\",\"status\":false}],\"domains\":[{\"index\":1,\"id\":1,\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"域名2\",\"url\":\"https://172.19.1.202:8543/login\",\"status\":false}],\"account\":{\"securitys\":[{\"index\":1,\"id\":1,\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"secadmin2\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"systems\":[{\"index\":1,\"id\":1,\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"sysadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"audits\":[{\"index\":1,\"id\":1,\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"auditadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}]},\"servers\":[{\"index\":1,\"id\":1,\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true},{\"index\":2,\"id\":2,\"host\":\"172.19.1.202\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"202环境\",\"status\":false}],\"databases\":[{\"index\":1,\"id\":1,\"name\":\"Oracle\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true},{\"index\":2,\"id\":2,\"name\":\"MySql\",\"driver\":\"com.mysql.jdbc.Driver\",\"url\":\"jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":false}]}]}]}")
    private Project project;

    @ApiModelProperty(value = "项目配置", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"数据脱敏系统\",\"description\":\"AAS_DM\",\"environments\":[{\"index\":1,\"id\":\"1\",\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":\"1\",\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true}],\"domains\":[{\"index\":1,\"id\":\"1\",\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true}],\"account\":{\"securitys\":[{\"index\":1,\"id\":\"1\",\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true}],\"systems\":[{\"index\":1,\"id\":\"1\",\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true}],\"audits\":[{\"index\":1,\"id\":\"1\",\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true}]},\"servers\":[{\"index\":1,\"id\":\"1\",\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true}],\"dataBases\":[{\"index\":1,\"id\":\"1\",\"name\":\"172.19.5.50环境数据库\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true}]}]}]")
    private List<Project> projects;

    @ApiModelProperty(value = "自动化配置", required = true, example = "{\"projects\":[{\"index\":1,\"id\":1,\"name\":\"数据脱敏系统\",\"description\":\"AAS_DM\",\"environments\":[{\"index\":1,\"id\":1,\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":1,\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"V3.0B08D005\",\"description\":\"上一个版本\",\"status\":false}],\"domains\":[{\"index\":1,\"id\":1,\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"域名2\",\"url\":\"https://172.19.1.202:8543/login\",\"status\":false}],\"account\":{\"securitys\":[{\"index\":1,\"id\":1,\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"secadmin2\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"systems\":[{\"index\":1,\"id\":1,\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"sysadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"audits\":[{\"index\":1,\"id\":1,\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"auditadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}]},\"servers\":[{\"index\":1,\"id\":1,\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true},{\"index\":2,\"id\":2,\"host\":\"172.19.1.202\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"202环境\",\"status\":false}],\"databases\":[{\"index\":1,\"id\":1,\"name\":\"Oracle\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true},{\"index\":2,\"id\":2,\"name\":\"MySql\",\"driver\":\"com.mysql.jdbc.Driver\",\"url\":\"jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":false}]}]}]}")
    private Automation automation;

    @Data
//    @JSONType(orders = {"index","id","name","description","environments"})
    public static class Project {
        @ApiModelProperty(value = "项目序号", required = true, example = "1")
        @JSONField(ordinal = 1)
        private Integer index;
        @ApiModelProperty(value = "项目ID", required = true, example = "1")
        @JSONField(ordinal = 2)
        private String id;
        @ApiModelProperty(value = "项目名称", required = true, example = "数据脱敏系统")
        @JSONField(ordinal = 3)
        private String name;
        @ApiModelProperty(value = "项目简称", required = true, example = "AAS_DM")
        @JSONField(ordinal = 4)
        private String abbreviate;
        @ApiModelProperty(value = "项目描述", required = true, example = "数据脱敏系统")
        @JSONField(ordinal = 5)
        private String description;

        @ApiModelProperty(value = "环境配置", required = true, example = "{\"index\":1,\"id\":1,\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":1,\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"V3.0B08D005\",\"description\":\"上一个版本\",\"status\":false}],\"domains\":[{\"index\":1,\"id\":1,\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"域名2\",\"url\":\"https://172.19.1.202:8543/login\",\"status\":false}],\"account\":{\"securitys\":[{\"index\":1,\"id\":1,\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"secadmin2\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"systems\":[{\"index\":1,\"id\":1,\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"sysadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"audits\":[{\"index\":1,\"id\":1,\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"auditadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}]},\"servers\":[{\"index\":1,\"id\":1,\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true},{\"index\":2,\"id\":2,\"host\":\"172.19.1.202\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"202环境\",\"status\":false}],\"databases\":[{\"index\":1,\"id\":1,\"name\":\"Oracle\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true},{\"index\":2,\"id\":2,\"name\":\"MySql\",\"driver\":\"com.mysql.jdbc.Driver\",\"url\":\"jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":false}]}")
        @JSONField(ordinal = 6)
        private Environment environment;

        @ApiModelProperty(value = "环境配置列表", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":\"1\",\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true}],\"domains\":[{\"index\":1,\"id\":\"1\",\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true}],\"account\":{\"securitys\":[{\"index\":1,\"id\":\"1\",\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true}],\"systems\":[{\"index\":1,\"id\":\"1\",\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true}],\"audits\":[{\"index\":1,\"id\":\"1\",\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true}]},\"servers\":[{\"index\":1,\"id\":\"1\",\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true}],\"dataBases\":[{\"index\":1,\"id\":\"1\",\"name\":\"172.19.5.50环境数据库\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true}]}]")
        @JSONField(ordinal = 7)
        private List<Environment> environments;

        public Project() {
        }

        public Project(Integer index, String id, String name, String description, List<Environment> environments) {
            this.index = index;
            this.id = id;
            this.name = name;
            this.description = description;
            this.environments = environments;
        }

        @Data
        public static class Environment {
            @ApiModelProperty(value = "环境序号", required = true, example = "1")
            @JSONField(ordinal = 1)
            private Integer index;
            @ApiModelProperty(value = "环境ID", required = true, example = "1")
            @JSONField(ordinal = 2)
            private String id;
            @ApiModelProperty(value = "环境名称", required = true, example = "测试环境")
            @JSONField(ordinal = 3)
            private String name;
            @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
            @JSONField(ordinal = 4)
            private Integer status;
            @ApiModelProperty(value = "版本配置", required = true, example = "1[{\"index\":1,\"id\":\"1\",\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true}]")
            @JSONField(ordinal = 5)
            private List<Version> versions;
            @ApiModelProperty(value = "域名配置", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true}]")
            @JSONField(ordinal = 6)
            private List<Domain> domains;
            @ApiModelProperty(value = "帐号配置", required = true, example = "{\"securitys\":[{\"index\":1,\"id\":\"1\",\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true}],\"systems\":[{\"index\":1,\"id\":\"1\",\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true}],\"audits\":[{\"index\":1,\"id\":\"1\",\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true}]}")
            @JSONField(ordinal = 7)
//            private Account account;
            private List<Account> accounts;
            @ApiModelProperty(value = "服务器配置", required = true, example = "[{\"index\":1,\"id\":\"1\",\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true}]")
            @JSONField(ordinal = 8)
            private List<Server> servers;
            @ApiModelProperty(value = "数据库配置", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"172.19.5.50环境数据库\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true}]")
            @JSONField(ordinal = 9)
//            private DataBase dataBase;
            private List<DataBase> dataBases;

            @Data
            public static class Version {
                @ApiModelProperty(value = "版本序号", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Integer index;
                @ApiModelProperty(value = "版本ID", required = true, example = "1")
                @JSONField(ordinal = 2)
                private String id;
                @ApiModelProperty(value = "版本名称", required = true, example = "V3.0B08D007")
                @JSONField(ordinal = 3)
                private String name;
                @ApiModelProperty(value = "版本描述", required = true, example = "主线版本")
                @JSONField(ordinal = 4)
                private String description;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 5)
                private Integer status;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 6)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 7)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 8)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 9)
                private String updateTime;
            }

            @Data
            public static class Domain {
                @ApiModelProperty(value = "域名序号", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Integer index;
                @ApiModelProperty(value = "域名ID", required = true, example = "1")
                @JSONField(ordinal = 2)
                private String id;
                @ApiModelProperty(value = "域名名称", required = true, example = "域名1")
                @JSONField(ordinal = 3)
                private String name;
                @ApiModelProperty(value = "域名地址", required = true, example = "https://172.19.1.201:8543/login")
                @JSONField(ordinal = 4)
                private String url;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 5)
                private Integer status;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 6)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 7)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 8)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 9)
                private String updateTime;
            }

            @Data
            public static class Account {
                @ApiModelProperty(value = "帐号序号", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Integer index;
                @ApiModelProperty(value = "帐号类型", required = true, example = "systems")
                @JSONField(ordinal = 2)
                private String type;
                @ApiModelProperty(value = "帐号ID", required = true, example = "1")
                @JSONField(ordinal = 3)
                private String id;
                @ApiModelProperty(value = "用户名", required = true, example = "sysadmin")
                @JSONField(ordinal = 4)
                private String userName;
                @ApiModelProperty(value = "密码", required = true, example = "3edc$RFV")
                @JSONField(ordinal = 5)
                private String passWord;
                @ApiModelProperty(value = "描述", required = true, example = "管理员帐号")
                @JSONField(ordinal = 6)
                private String description;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 7)
                private Integer status;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 8)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 9)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 10)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 11)
                private String updateTime;
            }

            @Data
            public static class Server {
                @ApiModelProperty(value = "服务器序号", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Integer index;
                @ApiModelProperty(value = "服务器ID", required = true, example = "1")
                @JSONField(ordinal = 2)
                private String id;
                @ApiModelProperty(value = "主机", required = true, example = "172.19.1.202")
                @JSONField(ordinal = 3)
                private String host;
                @ApiModelProperty(value = "端口", required = true, example = "8543")
                @JSONField(ordinal = 4)
                private Integer port;
                @ApiModelProperty(value = "用户名", required = true, example = "root")
                @JSONField(ordinal = 5)
                private String userName;
                @ApiModelProperty(value = "密码", required = true, example = "@1fw#2soc$3vpn")
                @JSONField(ordinal = 6)
                private String passWord;
                @ApiModelProperty(value = "描述", required = true, example = "202环境")
                @JSONField(ordinal = 7)
                private String description;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 8)
                private Integer status;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 9)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 10)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 11)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 12)
                private String updateTime;
            }

            @Data
            public static class DataBase {
                @ApiModelProperty(value = "数据库序号", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Integer index;
                @ApiModelProperty(value = "数据库类型", required = true, example = "MySql")
                @JSONField(ordinal = 2)
                private String type;
                @ApiModelProperty(value = "数据库ID", required = true, example = "1")
                @JSONField(ordinal = 3)
                private String id;
                @ApiModelProperty(value = "数据库名称", required = true, example = "Oracle||MySql")
                @JSONField(ordinal = 4)
                private String name;
                @ApiModelProperty(value = "数据库端口", required = true, example = "1521||3306")
                @JSONField(ordinal = 5)
                private Integer port;
                @ApiModelProperty(value = "数据库驱动", required = true, example = "oracle.jdbc.driver.OracleDriver")
                @JSONField(ordinal = 6)
                private String driver;
                @ApiModelProperty(value = "数据库连接串", required = true, example = "jdbc:oracle:thin:@172.19.5.50:1521:dbtest01||jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true")
                @JSONField(ordinal = 7)
                private String url;
                @ApiModelProperty(value = "数据库用户名", required = true, example = "root")
                @JSONField(ordinal = 8)
                private String userName;
                @ApiModelProperty(value = "数据库密码", required = true, example = "Sakura_mySQL123")
                @JSONField(ordinal = 9)
                private String passWord;
                @ApiModelProperty(value = "最大连接池数量", required = true, example = "20")
                @JSONField(ordinal = 10)
                private Integer maxActive;
                @ApiModelProperty(value = "最大等待时间", required = true, example = "60000")
                @JSONField(ordinal = 11)
                private Integer maxWait;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 12)
                private Integer status;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 13)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 14)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 15)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 16)
                private String updateTime;
            }
        }
    }

    @Data
    public static class Automation {
        @ApiModelProperty(value = "自动化类型", required = true, example = "1")
        @JSONField(ordinal = 1)
        private Web web;

        @Data
        public static class Web {
            @ApiModelProperty(value = "类型ID", required = true, example = "1")
            @JSONField(ordinal = 1)
            private String id;
            @ApiModelProperty(value = "类型名称", required = true, example = "WEB自动化")
            @JSONField(ordinal = 2)
            private String name;
            @ApiModelProperty(value = "类型描述", required = true, example = "WEB自动化测试")
            @JSONField(ordinal = 3)
            private String description;

            @ApiModelProperty(value = "项目配置", required = true, example = "1")
            @JSONField(ordinal = 4)
            private Project project;

            @ApiModelProperty(value = "环境配置", required = true, example = "1")
            @JSONField(ordinal = 5)
            private List<Environment> environments;

            @ApiModelProperty(value = "浏览器配置", required = true, example = "1")
            @JSONField(ordinal = 6)
            private Browser browser;

            @Data
            public static class Project {
                @ApiModelProperty(value = "项目ID", required = true, example = "1")
                @JSONField(ordinal = 1)
                private String id;
                @ApiModelProperty(value = "项目名称", required = true, example = "Sakura.Web.UI.Automation.Test")
                @JSONField(ordinal = 2)
                private String name;
                @ApiModelProperty(value = "项目描述", required = true, example = "Web-UI自动化测试项目")
                @JSONField(ordinal = 3)
                private String description;
                @ApiModelProperty(value = "GitLab地址", required = true, example = "http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git")
                @JSONField(ordinal = 4)
                private String gitLab;
            }

            @Data
            public static class Environment {
                @ApiModelProperty(value = "环境ID", required = true, example = "1")
                @JSONField(ordinal = 1)
                private String id;
                @ApiModelProperty(value = "环境名称", required = true, example = "Local")
                @JSONField(ordinal = 2)
                private String name;
                @ApiModelProperty(value = "环境描述", required = true, example = "本地环境")
                @JSONField(ordinal = 3)
                private String description;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git")
                @JSONField(ordinal = 4)
                private Integer status;
                @ApiModelProperty(value = "自动化项目保存路径", required = true, example = "D:/King/Eclipse/Eclipse/Sakura.Web.UI.Automation.Test")
                @JSONField(ordinal = 5)
                private String savePath;
                @ApiModelProperty(value = "自动化脚本Java保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestCases/")
                @JSONField(ordinal = 6)
                private String testCases;
                @ApiModelProperty(value = "自动化脚本XML保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/")
                @JSONField(ordinal = 7)
                private String testCaseXml;
                @ApiModelProperty(value = "自动化脚本TestReportXml保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestReportXml/TestngReport.xml")
                @JSONField(ordinal = 8)
                private String testReportXml;
                @ApiModelProperty(value = "自动化脚本TestRunXml保存路径", required = true, example = "/src/test/java/TestRunXml/ExtentReport.xml")
                @JSONField(ordinal = 9)
                private String testRunXml;
                @ApiModelProperty(value = "自动化脚本运行日志路径", required = true, example = "/Logs/log.txt")
                @JSONField(ordinal = 10)
                private String testRunLogs;
                @ApiModelProperty(value = "自动化脚本测试报告路径", required = true, example = "/TestOutput/ExtentReport/2022-11-22/AAS_DBSG/V6_1B01_POC/index.html")
                @JSONField(ordinal = 11)
                private String testOutput;
                @ApiModelProperty(value = "Python程序路径(用于自动识别验证码)", required = true, example = "D:/Program/Python/3.9.0/python.exe")
                @JSONField(ordinal = 12)
                private String pythonPath;
                @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                @JSONField(ordinal = 13)
                private String createByName;
                @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                @JSONField(ordinal = 14)
                private String createTime;
                @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                @JSONField(ordinal = 15)
                private String updateByName;
                @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                @JSONField(ordinal = 16)
                private String updateTime;
            }

            @Data
            public static class Browser {
                @ApiModelProperty(value = "谷歌浏览器配置", required = true, example = "/TestOutput/ExtentReport/2022-11-22/AAS_DBSG/V6_1B01_POC/index.html")
                @JSONField(ordinal = 1)
                private List<Chrome> chromes;
                @ApiModelProperty(value = "火狐浏览器配置", required = true, example = "D:/Program/Python/3.9.0/python.exe")
                @JSONField(ordinal = 2)
                private List<Firefox> firefoxs;

                @Data
                public static class Chrome {
                    @ApiModelProperty(value = "浏览器ID", required = true, example = "1")
                    @JSONField(ordinal = 1)
                    private String id;
                    @ApiModelProperty(value = "浏览器名称", required = true, example = "谷歌浏览器")
                    @JSONField(ordinal = 2)
                    private String name;
                    @ApiModelProperty(value = "浏览器路径", required = true, example = "C:/Program Files/Google/Chrome/Application/chrome.exe")
                    @JSONField(ordinal = 3)
                    private String path;
                    @ApiModelProperty(value = "浏览器驱动路径", required = true, example = "C:/Program Files/Google/Chrome/Application/chromedriver.exe")
                    @JSONField(ordinal = 4)
                    private String driver;
                    @ApiModelProperty(value = "浏览器配置文件路径", required = true, example = "C:/Users/user06/AppData/Local/Google/Chrome/User Data/Default")
                    @JSONField(ordinal = 5)
                    private String profile;
                    @ApiModelProperty(value = "浏览器版本", required = true, example = "108.0.5359.71")
                    @JSONField(ordinal = 6)
                    private String version;
                    @ApiModelProperty(value = "浏览器官方下载地址", required = true, example = "https://www.google.cn/intl/zh-CN/chrome/")
                    @JSONField(ordinal = 7)
                    private String officialDownload;
                    @ApiModelProperty(value = "浏览器驱动下载地址", required = true, example = "http://chromedriver.storage.googleapis.com/108.0.5359.71/chromedriver_win32.zip")
                    @JSONField(ordinal = 8)
                    private String driverDownload;
                    @ApiModelProperty(value = "浏览器启用状态(0关闭 1开启)", required = true, example = "1")
                    @JSONField(ordinal = 9)
                    private Integer status;
                    @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                    @JSONField(ordinal = 10)
                    private String createByName;
                    @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                    @JSONField(ordinal = 11)
                    private String createTime;
                    @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                    @JSONField(ordinal = 12)
                    private String updateByName;
                    @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                    @JSONField(ordinal = 13)
                    private String updateTime;
                }

                @Data
                public static class Firefox {
                    @ApiModelProperty(value = "浏览器ID", required = true, example = "1")
                    @JSONField(ordinal = 1)
                    private String id;
                    @ApiModelProperty(value = "浏览器名称", required = true, example = "火狐浏览器")
                    @JSONField(ordinal = 2)
                    private String name;
                    @ApiModelProperty(value = "浏览器路径", required = true, example = "C:/Program Files/Mozilla Firefox/firefox.exe")
                    @JSONField(ordinal = 3)
                    private String path;
                    @ApiModelProperty(value = "浏览器驱动路径", required = true, example = "C:/Program Files/Mozilla Firefox/geckodriver.exe")
                    @JSONField(ordinal = 4)
                    private String driver;
                    @ApiModelProperty(value = "浏览器配置文件路径", required = true, example = "C:/Users/user06/AppData/Roaming/Mozilla/Firefox/Profiles/vi2c8er8.default")
                    @JSONField(ordinal = 5)
                    private String profile;
                    @ApiModelProperty(value = "浏览器版本", required = true, example = "108.0.5359.71")
                    @JSONField(ordinal = 6)
                    private String version;
                    @ApiModelProperty(value = "浏览器官方下载地址", required = true, example = "https://www.firefox.com.cn/download/#product-desktop-release")
                    @JSONField(ordinal = 7)
                    private String officialDownload;
                    @ApiModelProperty(value = "浏览器驱动下载地址", required = true, example = "https://github.com/mozilla/geckodriver/releases/download/v0.31.0/geckodriver-v0.31.0-win64.zip")
                    @JSONField(ordinal = 8)
                    private String driverDownload;
                    @ApiModelProperty(value = "浏览器启用状态(0关闭 1开启)", required = true, example = "1")
                    @JSONField(ordinal = 9)
                    private Integer status;
                    @ApiModelProperty(value = "创建人", required = true, example = "刘智")
                    @JSONField(ordinal = 10)
                    private String createByName;
                    @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
                    @JSONField(ordinal = 11)
                    private String createTime;
                    @ApiModelProperty(value = "更新人", required = true, example = "刘智")
                    @JSONField(ordinal = 12)
                    private String updateByName;
                    @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
                    @JSONField(ordinal = 13)
                    private String updateTime;
                }
            }
        }
    }
}