package com.sakura.common.utils.yaml;

import com.alibaba.fastjson.annotation.JSONField;
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
public class YamlConfig {

    @ApiModelProperty(value = "项目配置", required = true, example = "{\"projects\":[{\"index\":1,\"id\":1,\"name\":\"数据脱敏系统\",\"description\":\"AAS_DM\",\"environments\":[{\"index\":1,\"id\":1,\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":1,\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"V3.0B08D005\",\"description\":\"上一个版本\",\"status\":false}],\"domains\":[{\"index\":1,\"id\":1,\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"域名2\",\"url\":\"https://172.19.1.202:8543/login\",\"status\":false}],\"account\":{\"securitys\":[{\"index\":1,\"id\":1,\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"secadmin2\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"systems\":[{\"index\":1,\"id\":1,\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"sysadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"audits\":[{\"index\":1,\"id\":1,\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"auditadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}]},\"servers\":[{\"index\":1,\"id\":1,\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true},{\"index\":2,\"id\":2,\"host\":\"172.19.1.202\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"202环境\",\"status\":false}],\"databases\":[{\"index\":1,\"id\":1,\"name\":\"Oracle\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true},{\"index\":2,\"id\":2,\"name\":\"MySql\",\"driver\":\"com.mysql.jdbc.Driver\",\"url\":\"jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":false}]}]}]}")
    private Project project;

    @ApiModelProperty(value = "项目配置列表", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"数据脱敏系统\",\"description\":\"AAS_DM\",\"environments\":[{\"index\":1,\"id\":\"1\",\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":\"1\",\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true}],\"domains\":[{\"index\":1,\"id\":\"1\",\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true}],\"account\":{\"securitys\":[{\"index\":1,\"id\":\"1\",\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true}],\"systems\":[{\"index\":1,\"id\":\"1\",\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true}],\"audits\":[{\"index\":1,\"id\":\"1\",\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true}]},\"servers\":[{\"index\":1,\"id\":\"1\",\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true}],\"dataBases\":[{\"index\":1,\"id\":\"1\",\"name\":\"172.19.5.50环境数据库\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true}]}]}]")
    private List<Project> projects;

    @ApiModelProperty(value = "自动化配置", required = true, example = "{}")
    private Automation automation;

    @ApiModelProperty(value = "自动化配置列表", required = true, example = "[]")
    private List<Automation> automations;

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
        @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
        @JSONField(ordinal = 6)
        private Integer status;

        @ApiModelProperty(value = "环境配置", required = true, example = "{\"index\":1,\"id\":1,\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":1,\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"V3.0B08D005\",\"description\":\"上一个版本\",\"status\":false}],\"domains\":[{\"index\":1,\"id\":1,\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true},{\"index\":2,\"id\":2,\"name\":\"域名2\",\"url\":\"https://172.19.1.202:8543/login\",\"status\":false}],\"account\":{\"securitys\":[{\"index\":1,\"id\":1,\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"secadmin2\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"systems\":[{\"index\":1,\"id\":1,\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"sysadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}],\"audits\":[{\"index\":1,\"id\":1,\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true},{\"index\":2,\"id\":2,\"userName\":\"auditadmin1\",\"passWord\":\"3edc$RFV\",\"status\":false}]},\"servers\":[{\"index\":1,\"id\":1,\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true},{\"index\":2,\"id\":2,\"host\":\"172.19.1.202\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"202环境\",\"status\":false}],\"databases\":[{\"index\":1,\"id\":1,\"name\":\"Oracle\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true},{\"index\":2,\"id\":2,\"name\":\"MySql\",\"driver\":\"com.mysql.jdbc.Driver\",\"url\":\"jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":false}]}")
        @JSONField(ordinal = 7)
        private Environment environment;

        @ApiModelProperty(value = "环境配置列表", required = true, example = "[{\"index\":1,\"id\":\"1\",\"name\":\"测试环境\",\"status\":true,\"versions\":[{\"index\":1,\"id\":\"1\",\"name\":\"V3.0B08D007\",\"description\":\"主线版本\",\"status\":true}],\"domains\":[{\"index\":1,\"id\":\"1\",\"name\":\"域名1\",\"url\":\"https://172.19.1.201:8543/login\",\"status\":true}],\"account\":{\"securitys\":[{\"index\":1,\"id\":\"1\",\"userName\":\"secadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"安全员帐号\",\"status\":true}],\"systems\":[{\"index\":1,\"id\":\"1\",\"userName\":\"sysadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"管理员帐号\",\"status\":true}],\"audits\":[{\"index\":1,\"id\":\"1\",\"userName\":\"auditadmin\",\"passWord\":\"3edc$RFV\",\"description\":\"审核员帐号\",\"status\":true}]},\"servers\":[{\"index\":1,\"id\":\"1\",\"host\":\"172.19.1.201\",\"port\":8543,\"userName\":\"root\",\"passWord\":\"@1fw#2soc$3vpn\",\"description\":\"201环境\",\"status\":true}],\"dataBases\":[{\"index\":1,\"id\":\"1\",\"name\":\"172.19.5.50环境数据库\",\"driver\":\"oracle.jdbc.driver.OracleDriver\",\"url\":\"jdbc:oracle:thin:@172.19.5.50:1521:dbtest01\",\"userName\":\"root\",\"passWord\":\"Sakura_mySQL123\",\"maxActive\":20,\"maxWait\":60000,\"status\":true}]}]")
        @JSONField(ordinal = 8)
        private List<Environment> environments;

        public Project() {
        }

        public Project(Integer index, String id, String name, String description, String abbreviate, List<Environment> environments) {
            this.index = index;
            this.id = id;
            this.name = name;
            this.abbreviate = abbreviate;
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
                @ApiModelProperty(value = "参数配置", required = true, example = "{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}")
                @JSONField(ordinal = 7)
                private Config config;
                @ApiModelProperty(value = "参数配置列表", required = true, example = "[{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}]")
                @JSONField(ordinal = 8)
                private List<Config> configList;
                @Data
                public static class Config {
                    @ApiModelProperty(value = "参数名", required = true, example = "Version_Path")
                    @JSONField(ordinal = 1)
                    private String paramsName;

                    @ApiModelProperty(value = "参数值", required = true, example = "\\172.23.1.15\\研发内部库\\01_AAS\\01_ANKKI\\06_AAS_V6.5\\AAS_V6.5\\V6.5B03\\V6.5B03内部迭代测试\\V6.5B03D002")
                    @JSONField(ordinal = 2)
                    private String paramsValue;
                }
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
                @ApiModelProperty(value = "删除状态(0存在 1删除)", required = true, example = "true")
                @JSONField(ordinal = 9)
                private Integer delFlag;
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
                @ApiModelProperty(value = "帐号类型", required = true, example = "systems")
                @JSONField(ordinal = 1)
                private String type;
                @ApiModelProperty(value = "帐号序号", required = true, example = "1")
                @JSONField(ordinal = 2)
                private Integer index;
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
                @ApiModelProperty(value = "类型", required = true, example = "Linux")
                @JSONField(ordinal = 7)
                private String type;
                @ApiModelProperty(value = "版本", required = true, example = "CentOS Linux release 7.9.2009")
                @JSONField(ordinal = 7)
                private String version;
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
                @ApiModelProperty(value = "域名", required = true, example = "https://172.19.5.47:443")
                @JSONField(ordinal = 7)
                private String domain;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 8)
                private Integer status;
                @ApiModelProperty(value = "参数配置", required = true, example = "{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}")
                @JSONField(ordinal = 7)
                private Version.Config config;
                @ApiModelProperty(value = "参数配置列表", required = true, example = "[{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}]")
                @JSONField(ordinal = 8)
                private List<Version.Config> configList;
                @Data
                public static class Config {
                    @ApiModelProperty(value = "参数名", required = true, example = "Version_Path")
                    @JSONField(ordinal = 1)
                    private String paramsName;

                    @ApiModelProperty(value = "参数值", required = true, example = "\\172.23.1.15\\研发内部库\\01_AAS\\01_ANKKI\\06_AAS_V6.5\\AAS_V6.5\\V6.5B03\\V6.5B03内部迭代测试\\V6.5B03D002")
                    @JSONField(ordinal = 2)
                    private String paramsValue;
                }
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
                @ApiModelProperty(value = "数据库类型", required = true, example = "MySql")
                @JSONField(ordinal = 1)
                private String type;
                @ApiModelProperty(value = "版本", required = true, example = "5.7.38")
                @JSONField(ordinal = 7)
                private String version;
                @ApiModelProperty(value = "数据库序号", required = true, example = "1")
                @JSONField(ordinal = 2)
                private Integer index;
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
                @ApiModelProperty(value = "数据库描述", required = true, example = "WEB自动化测试")
                @JSONField(ordinal = 5)
                private String description;
                @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "true")
                @JSONField(ordinal = 12)
                private Integer status;
                @ApiModelProperty(value = "参数配置", required = true, example = "{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}")
                @JSONField(ordinal = 7)
                private Config config;
                @ApiModelProperty(value = "参数配置列表", required = true, example = "[{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}]")
                @JSONField(ordinal = 8)
                private List<Config> configList;
                @Data
                public static class Config {
                    @ApiModelProperty(value = "参数名", required = true, example = "Version_Path")
                    @JSONField(ordinal = 1)
                    private String paramsName;

                    @ApiModelProperty(value = "参数值", required = true, example = "\\172.23.1.15\\研发内部库\\01_AAS\\01_ANKKI\\06_AAS_V6.5\\AAS_V6.5\\V6.5B03\\V6.5B03内部迭代测试\\V6.5B03D002")
                    @JSONField(ordinal = 2)
                    private String paramsValue;
                }
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
        @ApiModelProperty(value = "配置类型", required = true, example = "WEB")
        @JSONField(ordinal = 1)
        private String type;
        @ApiModelProperty(value = "配置序号", required = true, example = "1")
        @JSONField(ordinal = 2)
        private Integer index;
        @ApiModelProperty(value = "配置ID", required = true, example = "1")
        @JSONField(ordinal = 3)
        private String id;
        @ApiModelProperty(value = "配置名称", required = true, example = "WEB自动化")
        @JSONField(ordinal = 4)
        private String name;
        @ApiModelProperty(value = "配置描述", required = true, example = "WEB自动化测试")
        @JSONField(ordinal = 5)
        private String description;

        @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "1")
        @JSONField(ordinal = 6)
        private Integer status;

        @ApiModelProperty(value = "项目配置", required = true, example = "1")
        @JSONField(ordinal = 7)
        private Project project;

        @ApiModelProperty(value = "项目配置列表", required = true, example = "1")
        @JSONField(ordinal = 8)
        private List<Project> projectList;

        @ApiModelProperty(value = "Jenkins配置", required = true, example = "1")
        @JSONField(ordinal = 9)
        private Jenkins jenkins;

        @ApiModelProperty(value = "Jenkins配置列表", required = true, example = "1")
        @JSONField(ordinal = 10)
        private List<Jenkins> jenkinsList;

        @ApiModelProperty(value = "环境配置", required = true, example = "1")
        @JSONField(ordinal = 11)
        private Environment environment;

        @ApiModelProperty(value = "环境配置列表", required = true, example = "1")
        @JSONField(ordinal = 12)
        private List<Environment> environmentList;

        @ApiModelProperty(value = "浏览器配置", required = true, example = "1")
        @JSONField(ordinal = 13)
        private Browser browser;

        @ApiModelProperty(value = "浏览器配置列表", required = true, example = "1")
        @JSONField(ordinal = 14)
        private List<Browser> browserList;

        public Automation() {
        }

        public Automation(Integer index, String id, String name, String description, List<Project> projectList, List<Jenkins> jenkinstList, List<Environment> environmentList, List<Browser> browserList) {
            this.index = index;
            this.id = id;
            this.name = name;
            this.description = description;
            this.projectList = projectList;
            this.jenkinsList = jenkinstList;
            this.environmentList = environmentList;
            this.browserList = browserList;
        }

        @Data
        public static class Project {
            @ApiModelProperty(value = "项目序号", required = true, example = "1")
            @JSONField(ordinal = 1)
            private Integer index;
            @ApiModelProperty(value = "项目ID", required = true, example = "1")
            @JSONField(ordinal = 2)
            private String id;
            @ApiModelProperty(value = "项目名称", required = true, example = "Sakura.Web.UI.Automation.Test")
            @JSONField(ordinal = 3)
            private String name;
            @ApiModelProperty(value = "项目描述", required = true, example = "Web-UI自动化测试项目")
            @JSONField(ordinal = 4)
            private String description;
            @ApiModelProperty(value = "项目地址", required = true, example = "http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git")
            @JSONField(ordinal = 5)
            private String url;
            @ApiModelProperty(value = "项目路径", required = true, example = "/data/sakura/Sakura.Automation.Platform/automation/Sakura.Web.UI.Automation.Test/src/test/java")
            @JSONField(ordinal = 6)
            private String path;
            @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "1")
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
        public static class Jenkins {
            @ApiModelProperty(value = "序号", required = true, example = "1")
            @JSONField(ordinal = 1)
            private Integer index;
            @ApiModelProperty(value = "ID", required = true, example = "1")
            @JSONField(ordinal = 2)
            private String id;
            @ApiModelProperty(value = "主机", required = true, example = "172.19.5.222")
            @JSONField(ordinal = 3)
            private String ip;
            @ApiModelProperty(value = "端口", required = true, example = "8080")
            @JSONField(ordinal = 4)
            private Integer port;
            @ApiModelProperty(value = "用户名", required = true, example = "sakura")
            @JSONField(ordinal = 5)
            private String userName;
            @ApiModelProperty(value = "密码", required = true, example = "3edc$RFV")
            @JSONField(ordinal = 6)
            private String passWord;
            @ApiModelProperty(value = "描述", required = true, example = "Jenkins环境")
            @JSONField(ordinal = 7)
            private String description;
            @ApiModelProperty(value = "地址", required = true, example = "http://172.19.5.222:8080/")
            @JSONField(ordinal = 8)
            private String url;
            @ApiModelProperty(value = "项目", required = true, example = "Sakura.Web.UI.Automation.Test")
            @JSONField(ordinal = 9)
            private String job;
            @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "1")
            @JSONField(ordinal = 10)
            private Integer status;
            @ApiModelProperty(value = "创建人", required = true, example = "刘智")
            @JSONField(ordinal = 11)
            private String createByName;
            @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
            @JSONField(ordinal = 12)
            private String createTime;
            @ApiModelProperty(value = "更新人", required = true, example = "刘智")
            @JSONField(ordinal = 13)
            private String updateByName;
            @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
            @JSONField(ordinal = 14)
            private String updateTime;
        }

        @Data
        public static class Environment {
            @ApiModelProperty(value = "环境序号", required = true, example = "1")
            @JSONField(ordinal = 1)
            private Integer index;
            @ApiModelProperty(value = "环境ID", required = true, example = "1")
            @JSONField(ordinal = 2)
            private String id;
            @ApiModelProperty(value = "环境名称", required = true, example = "Local")
            @JSONField(ordinal = 3)
            private String name;
            @ApiModelProperty(value = "环境地址", required = true, example = "http://172.19.5.222:8080/computer/172.19.5.230/")
            @JSONField(ordinal = 3)
            private String url;
            @ApiModelProperty(value = "环境描述", required = true, example = "{\"name\":\"数审自动化环境一\",\"systemType\":\"Windows\",\"userName\":\"Sakura\",\"passWord\":\"3edc$RFV\"}")
            @JSONField(ordinal = 4)
            private Description description;
            @ApiModelProperty(value = "环境状态", required = true, example = "{}")
            @JSONField(ordinal = 5)
            private Active active;
            @ApiModelProperty(value = "环境参数列表", required = true, example = "[{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}]")
            @JSONField(ordinal = 8)
            private List<Active> activeList;

            @ApiModelProperty(value = "环境参数", required = true, example = "{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}")
            @JSONField(ordinal = 7)
            private Config config;
            @ApiModelProperty(value = "环境参数列表", required = true, example = "[{paramsName: \"locator\", paramsValue: \"xpath=(//input[@placeholder='请输入开始地址'])[1]\"}]")
            @JSONField(ordinal = 8)
            private List<Config> configList;

            @ApiModelProperty(value = "自动化项目保存路径", required = true, example = "D:/King/Eclipse/Eclipse/Sakura.Web.UI.Automation.Test")
            @JSONField(ordinal = 6)
            private String savePath;
            @ApiModelProperty(value = "自动化脚本Java保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestCases/")
            @JSONField(ordinal = 7)
            private String testCases;
            @ApiModelProperty(value = "自动化脚本XML保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/")
            @JSONField(ordinal = 8)
            private String testCaseXml;
            @ApiModelProperty(value = "自动化脚本TestReportXml保存路径", required = true, example = "/src/test/java/AAS_DBSG/V6_1B01_POC/TestReportXml/TestngReport.xml")
            @JSONField(ordinal = 9)
            private String testReportXml;
            @ApiModelProperty(value = "自动化脚本TestRunXml保存路径", required = true, example = "/src/test/java/TestRunXml/ExtentReport.xml")
            @JSONField(ordinal = 10)
            private String testRunXml;
            @ApiModelProperty(value = "自动化脚本运行日志路径", required = true, example = "/Logs/log.txt")
            @JSONField(ordinal = 11)
            private String testRunLogs;
            @ApiModelProperty(value = "自动化脚本测试报告路径", required = true, example = "/TestOutput/ExtentReport/2022-11-22/AAS_DBSG/V6_1B01_POC/index.html")
            @JSONField(ordinal = 12)
            private String testOutput;
            @ApiModelProperty(value = "Python程序路径(用于自动识别验证码)", required = true, example = "D:/Program/Python/3.9.0/python.exe")
            @JSONField(ordinal = 13)
            private String pythonPath;

            @ApiModelProperty(value = "启用状态(0关闭 1开启)", required = true, example = "1")
            @JSONField(ordinal = 6)
            private Integer status;
            @ApiModelProperty(value = "创建人", required = true, example = "刘智")
            @JSONField(ordinal = 14)
            private String createByName;
            @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
            @JSONField(ordinal = 15)
            private String createTime;
            @ApiModelProperty(value = "更新人", required = true, example = "刘智")
            @JSONField(ordinal = 16)
            private String updateByName;
            @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
            @JSONField(ordinal = 17)
            private String updateTime;

            @Data
            public static class Description {
                @ApiModelProperty(value = "环境描述", required = true, example = "1")
                @JSONField(ordinal = 1)
                private String name;

                @ApiModelProperty(value = "系统类型", required = true, example = "Windows")
                @JSONField(ordinal = 2)
                private String systemType;

                @ApiModelProperty(value = "用户名", required = true, example = "Sakura")
                @JSONField(ordinal = 3)
                private String userName;

                @ApiModelProperty(value = "密码", required = true, example = "3edc$RFV")
                @JSONField(ordinal = 4)
                private String passWord;
            }

            @Data
            public static class Active {
                @ApiModelProperty(value = "在线信息)", required = true, example = "1")
                @JSONField(ordinal = 1)
                private Offline offline;

                @ApiModelProperty(value = "使用信息", required = true, example = "1")
                @JSONField(ordinal = 2)
                private Idle idle;

                @Data
                public static class Offline {
                    @ApiModelProperty(value = "在线状态(0离线 1在线)", required = true, example = "1")
                    @JSONField(ordinal = 1)
                    private Integer status;

                    @ApiModelProperty(value = "离线原因", required = true, example = "1")
                    @JSONField(ordinal = 2)
                    private String offlineCauseReason;
                }

                @Data
                public static class Idle {
                    @ApiModelProperty(value = "使用状态(0使用中 1空闲)", required = true, example = "1")
                    @JSONField(ordinal = 1)
                    private Integer status;

                    @ApiModelProperty(value = "使用者列表", required = true, example = "刘智")
                    @JSONField(ordinal = 2)
                    private List<CurrentExecutable> executors;

                    @ApiModelProperty(value = "使用者信息", required = true, example = "刘智")
                    @JSONField(ordinal = 3)
                    private CurrentExecutable currentExecutable;

                    @Data
                    public static class CurrentExecutable {
                        @ApiModelProperty(value = "使用者地址", required = true, example = "1")
                        @JSONField(ordinal = 1)
                        private String url;

                        @ApiModelProperty(value = "使用者姓名", required = true, example = "刘智")
                        @JSONField(ordinal = 2)
                        private String user;
                    }
                }
            }

            @Data
            public static class Config {
                @ApiModelProperty(value = "参数名", required = true, example = "Python_Path")
                @JSONField(ordinal = 1)
                private String paramsName;

                @ApiModelProperty(value = "参数值", required = true, example = "D:/Program/Python/3.9.0/python.exe")
                @JSONField(ordinal = 2)
                private String paramsValue;
            }
        }

        @Data
        public static class Browser {
            @ApiModelProperty(value = "浏览器类型", required = true, example = "1")
            @JSONField(ordinal = 1)
            private String type;
            @ApiModelProperty(value = "浏览器序号", required = true, example = "1")
            @JSONField(ordinal = 2)
            private Integer index;
            @ApiModelProperty(value = "浏览器ID", required = true, example = "1")
            @JSONField(ordinal = 3)
            private String id;
            @ApiModelProperty(value = "浏览器名称", required = true, example = "谷歌浏览器")
            @JSONField(ordinal = 4)
            private String name;
            @ApiModelProperty(value = "浏览器路径", required = true, example = "C:/Program Files/Google/Chrome/Application/chrome.exe")
            @JSONField(ordinal = 5)
            private String path;
            @ApiModelProperty(value = "浏览器驱动路径", required = true, example = "C:/Program Files/Google/Chrome/Application/chromedriver.exe")
            @JSONField(ordinal = 6)
            private String driver;
            @ApiModelProperty(value = "浏览器配置文件路径", required = true, example = "C:/Users/user06/AppData/Local/Google/Chrome/User Data/Default")
            @JSONField(ordinal = 7)
            private String profile;
            @ApiModelProperty(value = "浏览器版本", required = true, example = "108.0.5359.71")
            @JSONField(ordinal = 8)
            private String version;
            @ApiModelProperty(value = "浏览器官方下载地址", required = true, example = "https://www.google.cn/intl/zh-CN/chrome/")
            @JSONField(ordinal = 9)
            private String officialDownload;
            @ApiModelProperty(value = "浏览器驱动下载地址", required = true, example = "http://chromedriver.storage.googleapis.com/108.0.5359.71/chromedriver_win32.zip")
            @JSONField(ordinal = 10)
            private String driverDownload;
            @ApiModelProperty(value = "浏览器启用状态(0关闭 1开启)", required = true, example = "1")
            @JSONField(ordinal = 11)
            private Integer status;
            @ApiModelProperty(value = "创建人", required = true, example = "刘智")
            @JSONField(ordinal = 12)
            private String createByName;
            @ApiModelProperty(value = "创建时间", required = true, example = "2022-12-27 09:42:48")
            @JSONField(ordinal = 13)
            private String createTime;
            @ApiModelProperty(value = "更新人", required = true, example = "刘智")
            @JSONField(ordinal = 14)
            private String updateByName;
            @ApiModelProperty(value = "更新时间", required = true, example = "2022-12-27 09:42:53")
            @JSONField(ordinal = 15)
            private String updateTime;
        }
    }
}