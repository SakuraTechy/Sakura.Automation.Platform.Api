package com.sakura.common.utils.yaml;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.amihaiemil.eoyaml.*;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.spring.SpringUtils;
import com.sakura.common.utils.yaml1.ColumnUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhi
 */
public class YmlUtil {
    public static void main(String[] args) throws IOException, NoSuchFieldException {
        YamlConfig.Project.Environment.Version version = new YamlConfig.Project.Environment.Version();
        version.setIndex(1);
        version.setId("1");
        version.setName("V3.0B08D007");
        version.setDescription("主线版本");
        version.setStatus(1);

        ArrayList<YamlConfig.Project.Environment.Version> versions = new ArrayList<>();
        versions.add(version);

        YamlConfig.Project.Environment.Domain domain = new YamlConfig.Project.Environment.Domain();
        domain.setIndex(1);
        domain.setId("1");
        domain.setName("域名1");
        domain.setUrl("https://172.19.1.201:8543/login");
        domain.setStatus(1);

        ArrayList<YamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
        domains.add(domain);

        YamlConfig.Project.Environment.Account account = new YamlConfig.Project.Environment.Account();
        account.setIndex(1);
        account.setId("1");
        account.setUserName("secadmin");
        account.setPassWord("3edc$RFV");
        account.setDescription("安全员账号");
        account.setStatus(1);

        ArrayList<YamlConfig.Project.Environment.Account> accounts = new ArrayList<>();
        accounts.add(account);

//        YamlConfig.Project.Environment.Account.Security security = new YamlConfig.Project.Environment.Account.Security();
//        security.setIndex(1);
//        security.setId("1");
//        security.setUserName("secadmin");
//        security.setPassWord("3edc$RFV");
//        security.setDescription("安全员账号");
//        security.setStatus(1);
//
//        ArrayList<YamlConfig.Project.Environment.Account.Security> securities = new ArrayList<>();
//        securities.add(security);
//
//        YamlConfig.Project.Environment.Account.System system = new YamlConfig.Project.Environment.Account.System();
//        system.setIndex(1);
//        system.setId("1");
//        system.setUserName("sysadmin");
//        system.setPassWord("3edc$RFV");
//        system.setDescription("管理员账号");
//        system.setStatus(1);
//
//        ArrayList<YamlConfig.Project.Environment.Account.System> systems = new ArrayList<>();
//        systems.add(system);
//
//        YamlConfig.Project.Environment.Account.Audit audit = new YamlConfig.Project.Environment.Account.Audit();
//        audit.setIndex(1);
//        audit.setId("1");
//        audit.setUserName("auditadmin");
//        audit.setPassWord("3edc$RFV");
//        audit.setDescription("审核员账号");
//        audit.setStatus(1);
//
//        ArrayList<YamlConfig.Project.Environment.Account.Audit> audits = new ArrayList<>();
//        audits.add(audit);
//
//        YamlConfig.Project.Environment.Account account = new YamlConfig.Project.Environment.Account();
//        account.setSecuritys(securities);
//        account.setSystems(systems);
//        account.setAudits(audits);

        YamlConfig.Project.Environment.Server server = new YamlConfig.Project.Environment.Server();
        server.setIndex(1);
        server.setId("1");
        server.setHost("172.19.1.201");
        server.setPort(8543);
        server.setUserName("root");
        server.setPassWord("@1fw#2soc$3vpn");
        server.setDescription("201环境");
        server.setStatus(1);

        ArrayList<YamlConfig.Project.Environment.Server> servers = new ArrayList<>();
        servers.add(server);

        YamlConfig.Project.Environment.DataBase dataBase = new YamlConfig.Project.Environment.DataBase();
        dataBase.setIndex(1);
        dataBase.setType("Oracle");
        dataBase.setId("1");
        dataBase.setName("Oracle");
        dataBase.setDriver("oracle.jdbc.driver.OracleDriver");
        dataBase.setUrl("jdbc:oracle:thin:@172.19.5.50:1521:dbtest01");
        dataBase.setUserName("root");
        dataBase.setPassWord("Sakura_mySQL123");
        dataBase.setMaxActive(20);
        dataBase.setMaxWait(60000);
        dataBase.setStatus(1);

        ArrayList<YamlConfig.Project.Environment.DataBase> dataBases = new ArrayList<>();
        dataBases.add(dataBase);

//        YamlConfig.Project.Environment.DataBase.Oracle oracle = new YamlConfig.Project.Environment.DataBase.Oracle();
//        oracle.setIndex(1);
//        oracle.setId("1");
//        oracle.setName("Oracle");
//        oracle.setDriver("oracle.jdbc.driver.OracleDriver");
//        oracle.setUrl("jdbc:oracle:thin:@172.19.5.50:1521:dbtest01");
//        oracle.setUserName("root");
//        oracle.setPassWord("Sakura_mySQL123");
//        oracle.setMaxActive(20);
//        oracle.setMaxWait(60000);
//        oracle.setStatus(1);
//
//        ArrayList<YamlConfig.Project.Environment.DataBase.Oracle> oracles = new ArrayList<>();
//        oracles.add(oracle);
//
//        YamlConfig.Project.Environment.DataBase.MySql mySql = new YamlConfig.Project.Environment.DataBase.MySql();
//        mySql.setIndex(1);
//        mySql.setId("1");
//        mySql.setName("MySql");
//        mySql.setDriver("com.mysql.jdbc.Driver");
//        mySql.setUrl("jdbc:mysql://172.19.2.35:3306/sakura_dev?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&&allowMultiQueries=true");
//        mySql.setUserName("root");
//        mySql.setPassWord("Sakura_mySQL123");
//        mySql.setMaxActive(20);
//        mySql.setMaxWait(60000);
//        mySql.setStatus(1);
//
//        ArrayList<YamlConfig.Project.Environment.DataBase.MySql> mySqls = new ArrayList<>();
//        mySqls.add(mySql);
//
//        YamlConfig.Project.Environment.DataBase dataBase = new YamlConfig.Project.Environment.DataBase();
//        dataBase.setOracles(oracles);
//        dataBase.setMySqls(mySqls);

        YamlConfig.Project.Environment environment = new YamlConfig.Project.Environment();
        environment.setIndex(1);
        environment.setId("1");
        environment.setName("测试环境");
        environment.setStatus(1);

        environment.setVersions(versions);
        environment.setDomains(domains);
        environment.setAccounts(accounts);
        environment.setServers(servers);
        environment.setDataBases(dataBases);

        ArrayList<YamlConfig.Project.Environment> environments = new ArrayList<>();
        environments.add(environment);

        YamlConfig.Project project = new YamlConfig.Project();
        project.setIndex(1);
        project.setId("1");
        project.setName("数据脱敏系统");
        project.setDescription("AAS_DM");
        project.setEnvironments(environments);

        System.out.println(JSON.toJSONString(environment.getVersions()));
        System.out.println(java.util.regex.Matcher.quoteReplacement(JSON.toJSONString(environment.getAccounts())));
        System.out.println(JSONUtil.toJsonStr(environment.getVersions()));

//        JSONArray jsonArray = JSONArray.fromObject(environment.getVersions());
//        System.out.println(jsonArray);
//        JSONObject projectJson1 = JSONUtil.parseObj(environment.getAccount(), false, true);
//        System.out.println(projectJson1);
//        JSONObject projectJson = JSONUtil.parseObj(environments.get(0), false, true);
//        System.out.println(projectJson);

//        creatYaml(project);

        YamlConfig.Project project2 = new YamlConfig.Project();
        project2.setIndex(2);
        project2.setId("2");
        project2.setName("数据库综合安全防护系统");
        project2.setDescription("AAS_DBSG");

        List<YamlConfig.Project> projects = new ArrayList<>();
        projects.add(project);
//        projects.add(project2);
//        System.out.println(projects);

        YamlConfig.Automation automation = new YamlConfig.Automation();
        automation.setType("WEB");
        automation.setId("1");
        automation.setName("WEB自动化");
        automation.setDescription("WEB自动化测试");

        YamlConfig.Automation.Project webProject = new YamlConfig.Automation.Project();
        webProject.setId("1");
        webProject.setName("Sakura.Web.UI.Automation.Test");
        webProject.setDescription("Web-UI自动化测试项目");
        webProject.setUrl("http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git");

        ArrayList<YamlConfig.Automation.Project> projectList = new ArrayList<>();
        projectList.add(webProject);

        YamlConfig.Automation.Environment webEnvironment = new YamlConfig.Automation.Environment();
        webEnvironment.setId("1");
        webEnvironment.setName("Local");
//        webEnvironment.setDescription("本地环境");
        webEnvironment.setStatus(1);
        webEnvironment.setSavePath("D:/King/Eclipse/Sakura.Web.UI.Automation.Test");
        webEnvironment.setTestCases("/src/test/java/AAS_DBSG/V6_1B01_POC/TestCases/AAS_DBSG_SMOKE_001.java");
        webEnvironment.setTestCaseXml("/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/AAS_DBSG_SMOKE_001.xml");
        webEnvironment.setTestReportXml("/src/test/java/AAS_DBSG/V6_1B01_POC/TestReportXml/TestngReport.xml");
        webEnvironment.setTestRunXml("/src/test/java/TestRunXml/ExtentReport.xml");
        webEnvironment.setTestRunLogs("/Logs/log.txt");
        webEnvironment.setTestOutput("/TestOutput/ExtentReport/2022-11-22/AAS_DBSG/V6_1B01_POC/index.html");
        webEnvironment.setPythonPath("D:/Program/Python/3.9.0/python.exe");

        ArrayList<YamlConfig.Automation.Environment> environmentList = new ArrayList<>();
        environmentList.add(webEnvironment);

        YamlConfig.Automation.Browser chrome = new YamlConfig.Automation.Browser();
        ArrayList<YamlConfig.Automation.Browser> browsersList = new ArrayList<>();
        chrome.setType("chrome");
        chrome.setId("1");
        chrome.setName("谷歌浏览器");
        chrome.setPath("C:/Program Files/Google/Chrome/Application/chrome.exe");
        chrome.setDriver("C:/Program Files/Google/Chrome/Application/chromedriver.exe");
        chrome.setProfile("C:/Users/user06/AppData/Local/Google/Chrome/User Data/Default");
        chrome.setVersion("108.0.5359.71");
        chrome.setOfficialDownload("https://www.google.cn/intl/zh-CN/chrome/");
        chrome.setDriverDownload("http://chromedriver.storage.googleapis.com/108.0.5359.71/chromedriver_win32.zip");
        chrome.setStatus(1);

        YamlConfig.Automation.Browser firefoxe = new YamlConfig.Automation.Browser();
        firefoxe.setType("firefoxe");
        firefoxe.setId("2");
        firefoxe.setName("火狐浏览器");
        firefoxe.setPath("C:/Program Files/Mozilla Firefox/firefox.exe");
        firefoxe.setDriver("C:/Program Files/Mozilla Firefox/geckodriver.exe");
        firefoxe.setProfile("C:/Users/user06/AppData/Roaming/Mozilla/Firefox/Profiles/vi2c8er8.default");
        firefoxe.setVersion("108.0.5359.71");
        firefoxe.setOfficialDownload("https://www.firefox.com.cn/download/#product-desktop-release");
        firefoxe.setDriverDownload("https://github.com/mozilla/geckodriver/releases/download/v0.31.0/geckodriver-v0.31.0-win64.zip");
        firefoxe.setStatus(1);

        browsersList.add(chrome);
        browsersList.add(firefoxe);

        automation.setProjectList(projectList);
        automation.setEnvironmentList(environmentList);
        automation.setBrowserList(browsersList);

        List<YamlConfig.Automation> automations = new ArrayList<>();
        automations.add(automation);

        YamlConfig yamlConfig = new YamlConfig();
        yamlConfig.setProjects(projects);
        yamlConfig.setAutomations(automations);
//        System.out.println(YamlConfig);

        creatYaml(yamlConfig,null,null);
    }

    public static void creatYaml(YamlConfig.Project project) throws IOException, NoSuchFieldException {
        YamlSequenceBuilder yamlSeqProject = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapProject = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();

        YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
        for (YamlConfig.Project.Environment environment : project.getEnvironments()) {
            YamlSequenceBuilder yamlSeqVersions = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapVersions = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaVersion = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project.Environment.Version version : environment.getVersions()) {
                yamlSeqVersions.add(
                        yamlMapVersions
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getIndex),
                                        yamlScaVersion
                                                .addLine(String.valueOf(version.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getId),
                                        yamlScaVersion
                                                .addLine(version.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getName),
                                        yamlScaVersion
                                                .addLine(version.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getDescription),
                                        yamlScaVersion
                                                .addLine(version.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getDescription)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getStatus),
                                        yamlScaVersion
                                                .addLine(String.valueOf(version.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getStatus)))
                                .build()
                );
            }
            YamlSequenceBuilder yamlSeqDomains = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapDomains = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaDomain = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project.Environment.Domain domain : environment.getDomains()) {
                yamlSeqDomains.add(
                        yamlMapDomains
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getIndex),
                                        yamlScaDomain
                                                .addLine(String.valueOf(domain.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getId),
                                        yamlScaDomain
                                                .addLine(domain.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getName),
                                        yamlScaDomain
                                                .addLine(domain.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getUrl),
                                        yamlScaDomain
                                                .addLine(domain.getUrl())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getUrl)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getStatus),
                                        yamlScaDomain
                                                .addLine(String.valueOf(domain.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqAccounts = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapAccounts = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaAccount = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project.Environment.Account account : environment.getAccounts()) {
                yamlSeqAccounts.add(
                        yamlMapAccounts
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getIndex),
                                        yamlScaAccount
                                                .addLine(String.valueOf(account.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getType),
                                        yamlScaAccount
                                                .addLine(account.getType())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getType)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getId),
                                        yamlScaAccount
                                                .addLine(account.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getUserName),
                                        yamlScaAccount
                                                .addLine(account.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getUserName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getPassWord),
                                        yamlScaAccount
                                                .addLine(account.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getPassWord)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getDescription),
                                        yamlScaAccount
                                                .addLine(account.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getDescription)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getStatus),
                                        yamlScaAccount
                                                .addLine(String.valueOf(account.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqServers = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapServers = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaServer = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project.Environment.Server server : environment.getServers()) {
                yamlSeqServers.add(
                        yamlMapServers
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getIndex),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getId),
                                        yamlScaServer
                                                .addLine(server.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getHost),
                                        yamlScaServer
                                                .addLine(server.getHost())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getHost)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getPort),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getPort()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getPort)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getUserName),
                                        yamlScaServer
                                                .addLine(server.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getUserName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getPassWord),
                                        yamlScaServer
                                                .addLine(server.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getPassWord)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getDescription),
                                        yamlScaServer
                                                .addLine(server.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getDescription)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getStatus),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqDataBases = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapDataBases = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaDataBase = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project.Environment.DataBase dataBase : environment.getDataBases()) {
                yamlSeqDataBases.add(
                        yamlMapDataBases
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getIndex),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getType),
                                        yamlScaDataBase
                                                .addLine(dataBase.getType())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getType)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getId),
                                        yamlScaDataBase
                                                .addLine(dataBase.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getName),
                                        yamlScaDataBase
                                                .addLine(dataBase.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getDriver),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getDriver()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getDriver)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getUrl),
                                        yamlScaDataBase
                                                .addLine(dataBase.getUrl())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getUrl)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getUserName),
                                        yamlScaDataBase
                                                .addLine(dataBase.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getUserName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getPassWord),
                                        yamlScaDataBase
                                                .addLine(dataBase.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getPassWord)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getMaxActive),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getMaxActive()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getMaxActive)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getMaxWait),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getMaxWait()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getMaxWait)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getStatus),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getStatus)))
                                .build()
                );
            }

            yamlSeqEnvironments.add(
                    yamlMapEnvironments
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getIndex),
                                    yamlScaEnvironment
                                            .addLine(String.valueOf(environment.getIndex()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getIndex)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getId),
                                    yamlScaEnvironment
                                            .addLine(environment.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getId)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getName),
                                    yamlScaEnvironment
                                            .addLine(environment.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getName)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getStatus),
                                    yamlScaEnvironment
                                            .addLine(String.valueOf(environment.getStatus()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getStatus)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getVersions), yamlSeqVersions.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getVersions)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getDomains), yamlSeqDomains.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getDomains)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getAccounts), yamlSeqAccounts.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getAccounts)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getServers), yamlSeqServers.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getServers)))
                            .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getDataBases), yamlSeqDataBases.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getDataBases)))
                            .build()
            );
        }
        yamlSeqProject.add(
                yamlMapProject
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getIndex),
                                yamlScaProject
                                        .addLine(String.valueOf(project.getIndex()))
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getIndex)))
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getId),
                                yamlScaProject
                                        .addLine(project.getId())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getId)))
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getName),
                                yamlScaProject
                                        .addLine(project.getName())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getName)))
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getAbbreviate),
                                yamlScaProject
                                        .addLine(project.getAbbreviate())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getAbbreviate)))
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getDescription),
                                yamlScaProject
                                        .addLine(project.getDescription())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getDescription)))
                        .add(ColumnUtil.getFieldName(YamlConfig.Project::getEnvironments), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getEnvironments)))
                        .build()
        );
        YamlMapping yaml = Yaml.createYamlMappingBuilder()
                .add(ColumnUtil.getFieldName(YamlConfig::getProject), yamlSeqProject.build(ColumnUtil.getFieldAnnotation(YamlConfig::getProject)))
                .build();
        System.out.println(yaml.toString());

//        System.out.println(SakuraConfig.getName());
//        System.out.println(SpringUtils.getRequiredProperty("sakura.profile"));
//        System.out.println(SpringUtils.getRequiredProperty("yaml.project.filePath"));

//        String path = "D:/King/Vue/Sakura.Automation.Platform/Sakura.Automation.Platform.Api/sakura-admin/environment.yml";
        String path = SpringUtils.getRequiredProperty("yaml.project.filePath");
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yaml.toString());
        fileWriter.close();
    }

    public static void creatYaml(YamlConfig yamlConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException {
        YamlMapping yaml = null;
        if(StringUtils.isNotEmpty(yamlConfig.getProjects())){
            YamlSequenceBuilder yamlSeqProjects = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapProjects = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Project project : yamlConfig.getProjects()) {
                YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
                for (YamlConfig.Project.Environment environment : project.getEnvironments()) {
                    YamlSequenceBuilder yamlSeqVersions = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapVersions = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaVersion = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Project.Environment.Version version : environment.getVersions()) {
                        yamlSeqVersions.add(
                                yamlMapVersions
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getIndex),
                                                yamlScaVersion
                                                        .addLine(String.valueOf(version.getIndex()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getIndex)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getId),
                                                yamlScaVersion
                                                        .addLine(version.getId())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getId)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getName),
                                                yamlScaVersion
                                                        .addLine(version.getName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getDescription),
                                                yamlScaVersion
                                                        .addLine(version.getDescription())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getDescription)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Version::getStatus),
                                                yamlScaVersion
                                                        .addLine(String.valueOf(version.getStatus()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Version::getStatus)))
                                        .build()
                        );
                    }
                    YamlSequenceBuilder yamlSeqDomains = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapDomains = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaDomain = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Project.Environment.Domain domain : environment.getDomains()) {
                        yamlSeqDomains.add(
                                yamlMapDomains
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getIndex),
                                                yamlScaDomain
                                                        .addLine(String.valueOf(domain.getIndex()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getIndex)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getId),
                                                yamlScaDomain
                                                        .addLine(domain.getId())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getId)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getName),
                                                yamlScaDomain
                                                        .addLine(domain.getName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getUrl),
                                                yamlScaDomain
                                                        .addLine(domain.getUrl())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getUrl)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Domain::getStatus),
                                                yamlScaDomain
                                                        .addLine(String.valueOf(domain.getStatus()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Domain::getStatus)))
                                        .build()
                        );
                    }
                    YamlSequenceBuilder yamlSeqAccounts = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapAccounts = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaAccount = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Project.Environment.Account account : environment.getAccounts()) {
                        yamlSeqAccounts.add(
                                yamlMapAccounts
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getIndex),
                                                yamlScaAccount
                                                        .addLine(String.valueOf(account.getIndex()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getIndex)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getType),
                                                yamlScaAccount
                                                        .addLine(account.getType())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getType)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getId),
                                                yamlScaAccount
                                                        .addLine(account.getId())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getId)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getUserName),
                                                yamlScaAccount
                                                        .addLine(account.getUserName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getUserName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getPassWord),
                                                yamlScaAccount
                                                        .addLine(account.getPassWord())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getPassWord)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getDescription),
                                                yamlScaAccount
                                                        .addLine(account.getDescription())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getDescription)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Account::getStatus),
                                                yamlScaAccount
                                                        .addLine(String.valueOf(account.getStatus()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Account::getStatus)))
                                        .build()
                        );
                    }
                    YamlSequenceBuilder yamlSeqServers = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapServers = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaServer = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Project.Environment.Server server : environment.getServers()) {
                        yamlSeqServers.add(
                                yamlMapServers
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getIndex),
                                                yamlScaServer
                                                        .addLine(String.valueOf(server.getIndex()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getIndex)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getId),
                                                yamlScaServer
                                                        .addLine(server.getId())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getId)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getHost),
                                                yamlScaServer
                                                        .addLine(server.getHost())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getHost)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getPort),
                                                yamlScaServer
                                                        .addLine(String.valueOf(server.getPort()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getPort)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getUserName),
                                                yamlScaServer
                                                        .addLine(server.getUserName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getUserName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getPassWord),
                                                yamlScaServer
                                                        .addLine(server.getPassWord())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getPassWord)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getDescription),
                                                yamlScaServer
                                                        .addLine(server.getDescription())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getDescription)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.Server::getStatus),
                                                yamlScaServer
                                                        .addLine(String.valueOf(server.getStatus()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.Server::getStatus)))
                                        .build()
                        );
                    }
                    YamlSequenceBuilder yamlSeqDataBases = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapDataBases = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaDataBase = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Project.Environment.DataBase dataBase : environment.getDataBases()) {
                        yamlSeqDataBases.add(
                                yamlMapDataBases
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getIndex),
                                                yamlScaDataBase
                                                        .addLine(String.valueOf(dataBase.getIndex()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getIndex)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getType),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getType())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getType)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getId),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getId())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getId)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getName),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getDriver),
                                                yamlScaDataBase
                                                        .addLine(String.valueOf(dataBase.getDriver()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getDriver)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getUrl),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getUrl())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getUrl)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getUserName),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getUserName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getUserName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getPassWord),
                                                yamlScaDataBase
                                                        .addLine(dataBase.getPassWord())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getPassWord)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getMaxActive),
                                                yamlScaDataBase
                                                        .addLine(String.valueOf(dataBase.getMaxActive()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getMaxActive)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getMaxWait),
                                                yamlScaDataBase
                                                        .addLine(String.valueOf(dataBase.getMaxWait()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getMaxWait)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment.DataBase::getStatus),
                                                yamlScaDataBase
                                                        .addLine(String.valueOf(dataBase.getStatus()))
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment.DataBase::getStatus)))
                                        .build()
                        );
                    }
                    yamlSeqEnvironments.add(
                            yamlMapEnvironments
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getIndex),
                                            yamlScaEnvironment
                                                    .addLine(String.valueOf(environment.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getIndex)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getId),
                                            yamlScaEnvironment
                                                    .addLine(environment.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getId)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getName),
                                            yamlScaEnvironment
                                                    .addLine(environment.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getName)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getStatus),
                                            yamlScaEnvironment
                                                    .addLine(String.valueOf(environment.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getStatus)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getVersions), yamlSeqVersions.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getVersions)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getDomains), yamlSeqDomains.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getDomains)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getAccounts), yamlSeqAccounts.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getAccounts)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getServers), yamlSeqServers.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getServers)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Project.Environment::getDataBases), yamlSeqDataBases.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project.Environment::getDataBases)))
                                    .build()
                    );
                }
                yamlSeqProjects.add(
                        yamlMapProjects
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getIndex),
                                        yamlScaProject
                                                .addLine(String.valueOf(project.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getId),
                                        yamlScaProject
                                                .addLine(project.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getName),
                                        yamlScaProject
                                                .addLine(project.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getAbbreviate),
                                        yamlScaProject
                                                .addLine(project.getAbbreviate())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getAbbreviate)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getDescription),
                                        yamlScaProject
                                                .addLine(project.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getDescription)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getStatus),
                                        yamlScaProject
                                                .addLine(String.valueOf(project.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getStatus)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Project::getEnvironments), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(YamlConfig.Project::getEnvironments)))
                                .build()
                );
            }
            yaml = Yaml.createYamlMappingBuilder()
                    .add(ColumnUtil.getFieldName(YamlConfig::getProjects), yamlSeqProjects.build(ColumnUtil.getFieldAnnotation(YamlConfig::getProjects)))
                    .build();
        } else if (StringUtils.isNotEmpty(yamlConfig.getAutomations())) {
            YamlSequenceBuilder yamlSeqAutomations = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapAutomations = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaAutomation = Yaml.createYamlScalarBuilder();
            for (YamlConfig.Automation automation : yamlConfig.getAutomations()) {
                YamlSequenceBuilder yamlSeqProjects = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapProjects = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();
                for (YamlConfig.Automation.Project project : automation.getProjectList()) {
                    yamlSeqProjects.add(
                            yamlMapProjects
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getIndex),
                                            yamlScaProject
                                                    .addLine(String.valueOf(project.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getIndex)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getId),
                                            yamlScaProject
                                                    .addLine(project.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getId)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getName),
                                            yamlScaProject
                                                    .addLine(project.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getName)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getDescription),
                                            yamlScaProject
                                                    .addLine(project.getDescription())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getDescription)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getUrl),
                                            yamlScaProject
                                                    .addLine(project.getUrl())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getUrl)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getPath),
                                            yamlScaProject
                                                    .addLine(project.getPath())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getPath)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Project::getStatus),
                                            yamlScaProject
                                                    .addLine(String.valueOf(project.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Project::getStatus)))
                                    .build()
                    );
                }
                YamlSequenceBuilder yamlSeqJenkins = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapJenkins = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaJenkins = Yaml.createYamlScalarBuilder();
                for (YamlConfig.Automation.Jenkins jenkins : automation.getJenkinsList()) {
                    yamlSeqJenkins.add(
                            yamlMapJenkins
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getIndex),
                                            yamlScaJenkins
                                                    .addLine(String.valueOf(jenkins.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getIndex)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getId),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getId)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getIp),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getIp())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getIp)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getPort),
                                            yamlScaJenkins
                                                    .addLine(String.valueOf(jenkins.getPort()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getPort)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getUserName),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getUserName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getUserName)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getPassWord),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getPassWord())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getPassWord)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getDescription),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getDescription())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getDescription)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getUrl),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getUrl())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getUrl)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getJob),
                                            yamlScaJenkins
                                                    .addLine(jenkins.getJob())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getJob)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Jenkins::getStatus),
                                            yamlScaJenkins
                                                    .addLine(String.valueOf(jenkins.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Jenkins::getStatus)))
                                    .build()
                    );
                }
                YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
                for (YamlConfig.Automation.Environment environment : automation.getEnvironmentList()) {
                    YamlSequenceBuilder yamlSeqConfigs = Yaml.createMutableYamlSequenceBuilder();
                    YamlMappingBuilder yamlMapConfigs = Yaml.createYamlMappingBuilder();
                    YamlScalarBuilder yamlScaConfig = Yaml.createYamlScalarBuilder();
                    for (YamlConfig.Automation.Environment.Config config : environment.getConfigList()) {
                        yamlSeqConfigs.add(
                                yamlMapConfigs
                                        .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment.Config::getParamsName),
                                                yamlScaConfig
                                                        .addLine(config.getParamsName())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment.Config::getParamsName)))
                                        .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment.Config::getParamsValue),
                                                yamlScaConfig
                                                        .addLine(config.getParamsValue())
                                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment.Config::getParamsValue)))
                                        .build()
                        );
                    }
                    yamlSeqEnvironments.add(
                            yamlMapEnvironments
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getIndex),
                                            yamlScaEnvironment
                                                    .addLine(String.valueOf(environment.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getIndex)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getId),
                                            yamlScaEnvironment
                                                    .addLine(environment.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getId)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getName),
                                            yamlScaEnvironment
                                                    .addLine(environment.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getName)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getDescription),
                                            yamlScaEnvironment
                                                    .addLine(environment.getDescription().getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getDescription)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getConfigList), yamlSeqConfigs.build(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getConfigList)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Environment::getStatus),
                                            yamlScaEnvironment
                                                    .addLine(String.valueOf(environment.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Environment::getStatus)))
                                    .build()
                    );
                }
                YamlSequenceBuilder yamlSeqBrowsers = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapBrowsers = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaBrowser = Yaml.createYamlScalarBuilder();
                for (YamlConfig.Automation.Browser browser : automation.getBrowserList()) {
                    yamlSeqBrowsers.add(
                            yamlMapBrowsers
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getIndex),
                                            yamlScaBrowser
                                                    .addLine(String.valueOf(browser.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getIndex)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getType),
                                            yamlScaBrowser
                                                    .addLine(browser.getType())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getType)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getId),
                                            yamlScaBrowser
                                                    .addLine(browser.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getId)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getName),
                                            yamlScaBrowser
                                                    .addLine(browser.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getName)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getPath),
                                            yamlScaBrowser
                                                    .addLine(browser.getPath())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getPath)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getDriver),
                                            yamlScaBrowser
                                                    .addLine(browser.getDriver())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getDriver)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getProfile),
                                            yamlScaBrowser
                                                    .addLine(browser.getProfile())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getProfile)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getVersion),
                                            yamlScaBrowser
                                                    .addLine(browser.getVersion())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getVersion)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getOfficialDownload),
                                            yamlScaBrowser
                                                    .addLine(browser.getOfficialDownload())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getOfficialDownload)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getDriverDownload),
                                            yamlScaBrowser
                                                    .addLine(browser.getDriverDownload())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getDriverDownload)))
                                    .add(ColumnUtil.getFieldName(YamlConfig.Automation.Browser::getStatus),
                                            yamlScaBrowser
                                                    .addLine(String.valueOf(browser.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation.Browser::getStatus)))
                                    .build()
                    );
                }
                yamlSeqAutomations.add(
                        yamlMapAutomations
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getIndex),
                                        yamlScaAutomation
                                                .addLine(String.valueOf(automation.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getIndex)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getType),
                                        yamlScaAutomation
                                                .addLine(automation.getType())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getType)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getId),
                                        yamlScaAutomation
                                                .addLine(automation.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getId)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getName),
                                        yamlScaAutomation
                                                .addLine(automation.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getName)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getDescription),
                                        yamlScaAutomation
                                                .addLine(automation.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getDescription)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getStatus),
                                        yamlScaAutomation
                                                .addLine(String.valueOf(automation.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getStatus)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getProjectList), yamlSeqProjects.build(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getProjectList)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getJenkinsList), yamlSeqJenkins.build(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getJenkinsList)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getEnvironmentList), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getEnvironmentList)))
                                .add(ColumnUtil.getFieldName(YamlConfig.Automation::getBrowserList), yamlSeqBrowsers.build(ColumnUtil.getFieldAnnotation(YamlConfig.Automation::getBrowserList)))
                                .build()
                );
            }
            yaml = Yaml.createYamlMappingBuilder()
                    .add(ColumnUtil.getFieldName(YamlConfig::getAutomations), yamlSeqAutomations.build(ColumnUtil.getFieldAnnotation(YamlConfig::getAutomations)))
                    .build();
        }
        System.out.println(yaml.toString());

//        JSONObject projectJson = JSONUtil.parseObj(yamlConfig, false, true);
//        System.out.println(projectJson.get("projects"));

//        String path = "D:/King/Vue/Sakura.Automation.Platform/Sakura.Automation.Platform.Api/sakura-admin/environment.yml";
//        System.out.println(SakuraConfig.getName());
//        System.out.println(SpringUtils.getRequiredProperty("sakura.profile"));
//        System.out.println(SpringUtils.getRequiredProperty("yaml.project.filePath"));

        String path = SpringUtils.getRequiredProperty("yaml.project.filePath");
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yaml.toString());
        fileWriter.close();
        DownloadUtil.downloadFile(path,"environment.yml",resp,req);
//        if (!file.exists()) {
//            response.sendError(404, "File not found!");
//        }
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
//        long fileLength = file.length();
//        response.setHeader("Content-Length", String.valueOf(fileLength));
//        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
//        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//        byte[] buff = new byte[1024];
//        int bytesRead;
//        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//            bos.write(buff, 0, bytesRead);
//        }
//        bis.close();
//        bos.close();
    }
}
