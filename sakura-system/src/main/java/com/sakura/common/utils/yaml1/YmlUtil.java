package com.sakura.common.utils.yaml1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sakura.common.utils.spring.SpringUtils;

import cn.hutool.json.JSONUtil;

import com.alibaba.fastjson.JSON;

import com.amihaiemil.eoyaml.*;

import com.sakura.system.domain.SysYamlConfig;

/**
 * @author liuzhi
 */
public class YmlUtil {
    public static void main(String[] args) throws IOException, NoSuchFieldException {
        SysYamlConfig.Project.Environment.Version version = new SysYamlConfig.Project.Environment.Version();
        version.setIndex(1);
        version.setId("1");
        version.setName("V3.0B08D007");
        version.setDescription("主线版本");
        version.setStatus(1);

        ArrayList<SysYamlConfig.Project.Environment.Version> versions = new ArrayList<>();
        versions.add(version);

        SysYamlConfig.Project.Environment.Domain domain = new SysYamlConfig.Project.Environment.Domain();
        domain.setIndex(1);
        domain.setId("1");
        domain.setName("域名1");
        domain.setUrl("https://172.19.1.201:8543/login");
        domain.setStatus(1);

        ArrayList<SysYamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
        domains.add(domain);

        SysYamlConfig.Project.Environment.Account account = new SysYamlConfig.Project.Environment.Account();
        account.setIndex(1);
        account.setId("1");
        account.setUserName("secadmin");
        account.setPassWord("3edc$RFV");
        account.setDescription("安全员账号");
        account.setStatus(1);

        ArrayList<SysYamlConfig.Project.Environment.Account> accounts = new ArrayList<>();
        accounts.add(account);

//        SysYamlConfig.Project.Environment.Account.Security security = new SysYamlConfig.Project.Environment.Account.Security();
//        security.setIndex(1);
//        security.setId("1");
//        security.setUserName("secadmin");
//        security.setPassWord("3edc$RFV");
//        security.setDescription("安全员账号");
//        security.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Account.Security> securities = new ArrayList<>();
//        securities.add(security);
//
//        SysYamlConfig.Project.Environment.Account.System system = new SysYamlConfig.Project.Environment.Account.System();
//        system.setIndex(1);
//        system.setId("1");
//        system.setUserName("sysadmin");
//        system.setPassWord("3edc$RFV");
//        system.setDescription("管理员账号");
//        system.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Account.System> systems = new ArrayList<>();
//        systems.add(system);
//
//        SysYamlConfig.Project.Environment.Account.Audit audit = new SysYamlConfig.Project.Environment.Account.Audit();
//        audit.setIndex(1);
//        audit.setId("1");
//        audit.setUserName("auditadmin");
//        audit.setPassWord("3edc$RFV");
//        audit.setDescription("审核员账号");
//        audit.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Account.Audit> audits = new ArrayList<>();
//        audits.add(audit);
//
//        SysYamlConfig.Project.Environment.Account account = new SysYamlConfig.Project.Environment.Account();
//        account.setSecuritys(securities);
//        account.setSystems(systems);
//        account.setAudits(audits);

        SysYamlConfig.Project.Environment.Server server = new SysYamlConfig.Project.Environment.Server();
        server.setIndex(1);
        server.setId("1");
        server.setHost("172.19.1.201");
        server.setPort(8543);
        server.setUserName("root");
        server.setPassWord("@1fw#2soc$3vpn");
        server.setDescription("201环境");
        server.setStatus(1);

        ArrayList<SysYamlConfig.Project.Environment.Server> servers = new ArrayList<>();
        servers.add(server);

        SysYamlConfig.Project.Environment.DataBase dataBase = new SysYamlConfig.Project.Environment.DataBase();
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

        ArrayList<SysYamlConfig.Project.Environment.DataBase> dataBases = new ArrayList<>();
        dataBases.add(dataBase);

//        SysYamlConfig.Project.Environment.DataBase.Oracle oracle = new SysYamlConfig.Project.Environment.DataBase.Oracle();
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
//        ArrayList<SysYamlConfig.Project.Environment.DataBase.Oracle> oracles = new ArrayList<>();
//        oracles.add(oracle);
//
//        SysYamlConfig.Project.Environment.DataBase.MySql mySql = new SysYamlConfig.Project.Environment.DataBase.MySql();
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
//        ArrayList<SysYamlConfig.Project.Environment.DataBase.MySql> mySqls = new ArrayList<>();
//        mySqls.add(mySql);
//
//        SysYamlConfig.Project.Environment.DataBase dataBase = new SysYamlConfig.Project.Environment.DataBase();
//        dataBase.setOracles(oracles);
//        dataBase.setMySqls(mySqls);

        SysYamlConfig.Project.Environment environment = new SysYamlConfig.Project.Environment();
        environment.setIndex(1);
        environment.setId("1");
        environment.setName("测试环境");
        environment.setStatus(1);

        environment.setVersions(versions);
        environment.setDomains(domains);
        environment.setAccounts(accounts);
        environment.setServers(servers);
        environment.setDataBases(dataBases);

        ArrayList<SysYamlConfig.Project.Environment> environments = new ArrayList<>();
        environments.add(environment);

        SysYamlConfig.Project project = new SysYamlConfig.Project();
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

        SysYamlConfig.Project project2 = new SysYamlConfig.Project();
        project2.setIndex(2);
        project2.setId("2");
        project2.setName("数据库综合安全防护系统");
        project2.setDescription("AAS_DBSG");

        List<SysYamlConfig.Project> projects = new ArrayList<>();
        projects.add(project);
//        projects.add(project2);
//        System.out.println(projects);

        SysYamlConfig.Automation automation = new SysYamlConfig.Automation();
        SysYamlConfig.Automation.Web web = new SysYamlConfig.Automation.Web();
        web.setId("1");
        web.setName("WEB自动化");
        web.setDescription("WEB自动化测试");

        SysYamlConfig.Automation.Web.Project webProject = new SysYamlConfig.Automation.Web.Project();
        webProject.setId("1");
        webProject.setName("Sakura.Web.UI.Automation.Test");
        webProject.setDescription("Web-UI自动化测试项目");
        webProject.setGitLab("http://172.19.5.222:8099/Test/Sakura.Web.UI.Automation.Test.git");

        SysYamlConfig.Automation.Web.Environment webEnvironment = new SysYamlConfig.Automation.Web.Environment();
        webEnvironment.setId("1");
        webEnvironment.setName("Local");
        webEnvironment.setDescription("本地环境");
        webEnvironment.setStatus(1);
        webEnvironment.setSavePath("D:/King/Eclipse/Sakura.Web.UI.Automation.Test");
        webEnvironment.setTestCases("/src/test/java/AAS_DBSG/V6_1B01_POC/TestCases/AAS_DBSG_SMOKE_001.java");
        webEnvironment.setTestCaseXml("/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/AAS_DBSG_SMOKE_001.xml");
        webEnvironment.setTestReportXml("/src/test/java/AAS_DBSG/V6_1B01_POC/TestReportXml/TestngReport.xml");
        webEnvironment.setTestRunXml("/src/test/java/TestRunXml/ExtentReport.xml");
        webEnvironment.setTestRunLogs("/Logs/log.txt");
        webEnvironment.setTestOutput("/TestOutput/ExtentReport/2022-11-22/AAS_DBSG/V6_1B01_POC/index.html");
        webEnvironment.setPythonPath("D:/Program/Python/3.9.0/python.exe");

        ArrayList<SysYamlConfig.Automation.Web.Environment> webEnvironments = new ArrayList<>();
        webEnvironments.add(webEnvironment);

        SysYamlConfig.Automation.Web.Browser browser = new SysYamlConfig.Automation.Web.Browser();
        SysYamlConfig.Automation.Web.Browser.Chrome chrome = new SysYamlConfig.Automation.Web.Browser.Chrome();
        chrome.setId("1");
        chrome.setName("谷歌浏览器");
        chrome.setPath("C:/Program Files/Google/Chrome/Application/chrome.exe");
        chrome.setDriver("C:/Program Files/Google/Chrome/Application/chromedriver.exe");
        chrome.setProfile("C:/Users/user06/AppData/Local/Google/Chrome/User Data/Default");
        chrome.setVersion("108.0.5359.71");
        chrome.setOfficialDownload("https://www.google.cn/intl/zh-CN/chrome/");
        chrome.setDriverDownload("http://chromedriver.storage.googleapis.com/108.0.5359.71/chromedriver_win32.zip");
        chrome.setStatus(1);

        ArrayList<SysYamlConfig.Automation.Web.Browser.Chrome> chromes = new ArrayList<>();
        chromes.add(chrome);

        SysYamlConfig.Automation.Web.Browser.Firefox firefox = new SysYamlConfig.Automation.Web.Browser.Firefox();
        firefox.setId("1");
        firefox.setName("火狐浏览器");
        firefox.setPath("C:/Program Files/Mozilla Firefox/firefox.exe");
        firefox.setDriver("C:/Program Files/Mozilla Firefox/geckodriver.exe");
        firefox.setProfile("C:/Users/user06/AppData/Roaming/Mozilla/Firefox/Profiles/vi2c8er8.default");
        firefox.setVersion("108.0.5359.71");
        firefox.setOfficialDownload("https://www.firefox.com.cn/download/#product-desktop-release");
        firefox.setDriverDownload("https://github.com/mozilla/geckodriver/releases/download/v0.31.0/geckodriver-v0.31.0-win64.zip");
        firefox.setStatus(1);

        ArrayList<SysYamlConfig.Automation.Web.Browser.Firefox> firefoxes = new ArrayList<>();
        firefoxes.add(firefox);

        browser.setChromes(chromes);
        browser.setFirefoxs(firefoxes);

        web.setProject(webProject);
        web.setEnvironments(webEnvironments);
        web.setBrowser(browser);

        automation.setWeb(web);
//        System.out.println(automation);

        SysYamlConfig sysYamlConfig = new SysYamlConfig();
        sysYamlConfig.setProjects(projects);
        sysYamlConfig.setAutomation(automation);
//        System.out.println(sysYamlConfig);

        creatYaml(sysYamlConfig);
    }

    public static void creatYaml(SysYamlConfig.Project project) throws IOException, NoSuchFieldException {
        YamlSequenceBuilder yamlSeqProject = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapProject = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();

        YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
        for (SysYamlConfig.Project.Environment environment : project.getEnvironments()) {
            YamlSequenceBuilder yamlSeqVersions = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapVersions = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaVersion = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment.Version version : environment.getVersions()) {
                yamlSeqVersions.add(
                        yamlMapVersions
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getIndex),
                                        yamlScaVersion
                                                .addLine(String.valueOf(version.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getId),
                                        yamlScaVersion
                                                .addLine(version.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getName),
                                        yamlScaVersion
                                                .addLine(version.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getDescription),
                                        yamlScaVersion
                                                .addLine(version.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getDescription)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getStatus),
                                        yamlScaVersion
                                                .addLine(String.valueOf(version.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getStatus)))
                                .build()
                );
            }
            YamlSequenceBuilder yamlSeqDomains = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapDomains = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaDomain = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment.Domain domain : environment.getDomains()) {
                yamlSeqDomains.add(
                        yamlMapDomains
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getIndex),
                                        yamlScaDomain
                                                .addLine(String.valueOf(domain.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getId),
                                        yamlScaDomain
                                                .addLine(domain.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getName),
                                        yamlScaDomain
                                                .addLine(domain.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getUrl),
                                        yamlScaDomain
                                                .addLine(domain.getUrl())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getUrl)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getStatus),
                                        yamlScaDomain
                                                .addLine(String.valueOf(domain.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqAccounts = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapAccounts = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaAccount = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment.Account account : environment.getAccounts()) {
                yamlSeqAccounts.add(
                        yamlMapAccounts
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getIndex),
                                        yamlScaAccount
                                                .addLine(String.valueOf(account.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getType),
                                        yamlScaAccount
                                                .addLine(account.getType())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getType)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getId),
                                        yamlScaAccount
                                                .addLine(account.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getUserName),
                                        yamlScaAccount
                                                .addLine(account.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getUserName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getPassWord),
                                        yamlScaAccount
                                                .addLine(account.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getPassWord)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getDescription),
                                        yamlScaAccount
                                                .addLine(account.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getDescription)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getStatus),
                                        yamlScaAccount
                                                .addLine(String.valueOf(account.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqServers = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapServers = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaServer = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment.Server server : environment.getServers()) {
                yamlSeqServers.add(
                        yamlMapServers
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getIndex),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getId),
                                        yamlScaServer
                                                .addLine(server.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getHost),
                                        yamlScaServer
                                                .addLine(server.getHost())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getHost)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getPort),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getPort()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getPort)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getUserName),
                                        yamlScaServer
                                                .addLine(server.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getUserName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getPassWord),
                                        yamlScaServer
                                                .addLine(server.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getPassWord)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getDescription),
                                        yamlScaServer
                                                .addLine(server.getDescription())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getDescription)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getStatus),
                                        yamlScaServer
                                                .addLine(String.valueOf(server.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getStatus)))
                                .build()
                );
            }

            YamlSequenceBuilder yamlSeqDataBases = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapDataBases = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaDataBase = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment.DataBase dataBase : environment.getDataBases()) {
                yamlSeqDataBases.add(
                        yamlMapDataBases
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getIndex),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getType),
                                        yamlScaDataBase
                                                .addLine(dataBase.getType())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getType)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getId),
                                        yamlScaDataBase
                                                .addLine(dataBase.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getName),
                                        yamlScaDataBase
                                                .addLine(dataBase.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getDriver),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getDriver()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getDriver)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getUrl),
                                        yamlScaDataBase
                                                .addLine(dataBase.getUrl())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getUrl)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getUserName),
                                        yamlScaDataBase
                                                .addLine(dataBase.getUserName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getUserName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getPassWord),
                                        yamlScaDataBase
                                                .addLine(dataBase.getPassWord())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getPassWord)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getMaxActive),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getMaxActive()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getMaxActive)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getMaxWait),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getMaxWait()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getMaxWait)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getStatus),
                                        yamlScaDataBase
                                                .addLine(String.valueOf(dataBase.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getStatus)))
                                .build()
                );
            }

            yamlSeqEnvironments.add(
                    yamlMapEnvironments
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getIndex),
                                    yamlScaEnvironment
                                            .addLine(String.valueOf(environment.getIndex()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getIndex)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getId),
                                    yamlScaEnvironment
                                            .addLine(environment.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getId)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getName),
                                    yamlScaEnvironment
                                            .addLine(environment.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getName)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getStatus),
                                    yamlScaEnvironment
                                            .addLine(String.valueOf(environment.getStatus()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getStatus)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getVersions), yamlSeqVersions.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getVersions)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getDomains), yamlSeqDomains.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getDomains)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getAccounts), yamlSeqAccounts.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getAccounts)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getServers), yamlSeqServers.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getServers)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getDataBases), yamlSeqDataBases.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getDataBases)))
                            .build()
            );
        }
        yamlSeqProject.add(
                yamlMapProject
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getIndex),
                                yamlScaProject
                                        .addLine(String.valueOf(project.getIndex()))
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getIndex)))
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getId),
                                yamlScaProject
                                        .addLine(project.getId())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getId)))
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getName),
                                yamlScaProject
                                        .addLine(project.getName())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getName)))
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getAbbreviate),
                                yamlScaProject
                                        .addLine(project.getAbbreviate())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getAbbreviate)))
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getDescription),
                                yamlScaProject
                                        .addLine(project.getDescription())
                                        .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getDescription)))
                        .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getEnvironments), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getEnvironments)))
                        .build()
        );
        YamlMapping yaml = Yaml.createYamlMappingBuilder()
                .add(ColumnUtil.getFieldName(SysYamlConfig::getProject), yamlSeqProject.build(ColumnUtil.getFieldAnnotation(SysYamlConfig::getProject)))
                .build();
        System.out.println(yaml.toString());

//        System.out.println(SakuraConfig.getName());
//        System.out.println(SpringUtils.getRequiredProperty("sakura.profile"));
//        System.out.println(SpringUtils.getRequiredProperty("yaml.project.filePath"));

//        String path = "D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment.yml";
        String path = SpringUtils.getRequiredProperty("yaml.project.filePath");
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yaml.toString());
        fileWriter.close();
    }

    public static void creatYaml(SysYamlConfig sysYamlConfig) throws IOException, NoSuchFieldException {
        //项目配置
        YamlSequenceBuilder yamlSeqProjects = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapProjects = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaProjects = Yaml.createYamlScalarBuilder();
        for (SysYamlConfig.Project project : sysYamlConfig.getProjects()) {
            YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
            YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
            YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
            for (SysYamlConfig.Project.Environment environment : project.getEnvironments()) {
                YamlSequenceBuilder yamlSeqVersions = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapVersions = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaVersion = Yaml.createYamlScalarBuilder();
                for (SysYamlConfig.Project.Environment.Version version : environment.getVersions()) {
                    yamlSeqVersions.add(
                            yamlMapVersions
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getIndex),
                                            yamlScaVersion
                                                    .addLine(String.valueOf(version.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getIndex)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getId),
                                            yamlScaVersion
                                                    .addLine(version.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getId)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getName),
                                            yamlScaVersion
                                                    .addLine(version.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getDescription),
                                            yamlScaVersion
                                                    .addLine(version.getDescription())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getDescription)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Version::getStatus),
                                            yamlScaVersion
                                                    .addLine(String.valueOf(version.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Version::getStatus)))
                                    .build()
                    );
                }
                YamlSequenceBuilder yamlSeqDomains = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapDomains = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaDomain = Yaml.createYamlScalarBuilder();
                for (SysYamlConfig.Project.Environment.Domain domain : environment.getDomains()) {
                    yamlSeqDomains.add(
                            yamlMapDomains
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getIndex),
                                            yamlScaDomain
                                                    .addLine(String.valueOf(domain.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getIndex)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getId),
                                            yamlScaDomain
                                                    .addLine(domain.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getId)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getName),
                                            yamlScaDomain
                                                    .addLine(domain.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getUrl),
                                            yamlScaDomain
                                                    .addLine(domain.getUrl())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getUrl)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Domain::getStatus),
                                            yamlScaDomain
                                                    .addLine(String.valueOf(domain.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Domain::getStatus)))
                                    .build()
                    );
                }

                YamlSequenceBuilder yamlSeqAccounts = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapAccounts = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaAccount = Yaml.createYamlScalarBuilder();
                for (SysYamlConfig.Project.Environment.Account account : environment.getAccounts()) {
                    yamlSeqAccounts.add(
                            yamlMapAccounts
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getIndex),
                                            yamlScaAccount
                                                    .addLine(String.valueOf(account.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getIndex)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getType),
                                            yamlScaAccount
                                                    .addLine(account.getType())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getType)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getId),
                                            yamlScaAccount
                                                    .addLine(account.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getId)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getUserName),
                                            yamlScaAccount
                                                    .addLine(account.getUserName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getUserName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getPassWord),
                                            yamlScaAccount
                                                    .addLine(account.getPassWord())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getPassWord)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getDescription),
                                            yamlScaAccount
                                                    .addLine(account.getDescription())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getDescription)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Account::getStatus),
                                            yamlScaAccount
                                                    .addLine(String.valueOf(account.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Account::getStatus)))
                                    .build()
                    );
                }

                YamlSequenceBuilder yamlSeqServers = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapServers = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaServer = Yaml.createYamlScalarBuilder();
                for (SysYamlConfig.Project.Environment.Server server : environment.getServers()) {
                    yamlSeqServers.add(
                            yamlMapServers
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getIndex),
                                            yamlScaServer
                                                    .addLine(String.valueOf(server.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getIndex)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getId),
                                            yamlScaServer
                                                    .addLine(server.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getId)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getHost),
                                            yamlScaServer
                                                    .addLine(server.getHost())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getHost)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getPort),
                                            yamlScaServer
                                                    .addLine(String.valueOf(server.getPort()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getPort)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getUserName),
                                            yamlScaServer
                                                    .addLine(server.getUserName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getUserName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getPassWord),
                                            yamlScaServer
                                                    .addLine(server.getPassWord())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getPassWord)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getDescription),
                                            yamlScaServer
                                                    .addLine(server.getDescription())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getDescription)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.Server::getStatus),
                                            yamlScaServer
                                                    .addLine(String.valueOf(server.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.Server::getStatus)))
                                    .build()
                    );
                }

                YamlSequenceBuilder yamlSeqDataBases = Yaml.createMutableYamlSequenceBuilder();
                YamlMappingBuilder yamlMapDataBases = Yaml.createYamlMappingBuilder();
                YamlScalarBuilder yamlScaDataBase = Yaml.createYamlScalarBuilder();
                for (SysYamlConfig.Project.Environment.DataBase dataBase : environment.getDataBases()) {
                    yamlSeqDataBases.add(
                            yamlMapDataBases
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getIndex),
                                            yamlScaDataBase
                                                    .addLine(String.valueOf(dataBase.getIndex()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getIndex)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getType),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getType())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getType)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getId),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getId())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getId)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getName),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getDriver),
                                            yamlScaDataBase
                                                    .addLine(String.valueOf(dataBase.getDriver()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getDriver)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getUrl),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getUrl())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getUrl)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getUserName),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getUserName())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getUserName)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getPassWord),
                                            yamlScaDataBase
                                                    .addLine(dataBase.getPassWord())
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getPassWord)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getMaxActive),
                                            yamlScaDataBase
                                                    .addLine(String.valueOf(dataBase.getMaxActive()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getMaxActive)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getMaxWait),
                                            yamlScaDataBase
                                                    .addLine(String.valueOf(dataBase.getMaxWait()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getMaxWait)))
                                    .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment.DataBase::getStatus),
                                            yamlScaDataBase
                                                    .addLine(String.valueOf(dataBase.getStatus()))
                                                    .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment.DataBase::getStatus)))
                                    .build()
                    );
                }

                yamlSeqEnvironments.add(
                        yamlMapEnvironments
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getIndex),
                                        yamlScaEnvironment
                                                .addLine(String.valueOf(environment.getIndex()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getIndex)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getId),
                                        yamlScaEnvironment
                                                .addLine(environment.getId())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getId)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getName),
                                        yamlScaEnvironment
                                                .addLine(environment.getName())
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getName)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getStatus),
                                        yamlScaEnvironment
                                                .addLine(String.valueOf(environment.getStatus()))
                                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getStatus)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getVersions), yamlSeqVersions.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getVersions)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getDomains), yamlSeqDomains.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getDomains)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getAccounts), yamlSeqAccounts.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getAccounts)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getServers), yamlSeqServers.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getServers)))
                                .add(ColumnUtil.getFieldName(SysYamlConfig.Project.Environment::getDataBases), yamlSeqDataBases.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project.Environment::getDataBases)))
                                .build()
                );
            }
            yamlSeqProjects.add(
                    yamlMapProjects
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getIndex),
                                    yamlScaProjects
                                            .addLine(String.valueOf(project.getIndex()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getIndex)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getId),
                                    yamlScaProjects
                                            .addLine(project.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getId)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getName),
                                    yamlScaProjects
                                            .addLine(project.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getName)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getAbbreviate),
                                    yamlScaProjects
                                            .addLine(project.getAbbreviate())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getAbbreviate)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getDescription),
                                    yamlScaProjects
                                            .addLine(project.getDescription())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getDescription)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getEnvironments), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Project::getEnvironments)))
                            .build()
            );
        }

        //自动化配置
        YamlMappingBuilder yamlMapProject = Yaml.createMutableYamlMappingBuilder();
        YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();
        SysYamlConfig.Automation.Web.Project project = sysYamlConfig.getAutomation().getWeb().getProject();
        yamlMapProject
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Project::getId),
                        yamlScaProject
                                .addLine(project.getId())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Project::getId)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Project::getName),
                        yamlScaProject
                                .addLine(project.getName())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Project::getName)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Project::getDescription),
                        yamlScaProject
                                .addLine(project.getDescription())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Project::getDescription)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Project::getGitLab),
                        yamlScaProject
                                .addLine(project.getGitLab())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Project::getGitLab)))
                .build();

        YamlSequenceBuilder yamlSeqEnvironments = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapEnvironments = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaEnvironment = Yaml.createYamlScalarBuilder();
        for (SysYamlConfig.Automation.Web.Environment environment : sysYamlConfig.getAutomation().getWeb().getEnvironments()) {
            yamlSeqEnvironments.add(
                    yamlMapEnvironments
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getId),
                                    yamlScaEnvironment
                                            .addLine(environment.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getId)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getName),
                                    yamlScaEnvironment
                                            .addLine(environment.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getName)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getDescription),
                                    yamlScaEnvironment
                                            .addLine(environment.getDescription())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getDescription)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getStatus),
                                    yamlScaEnvironment
                                            .addLine(String.valueOf(environment.getStatus()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getStatus)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getSavePath),
                                    yamlScaEnvironment
                                            .addLine(environment.getSavePath())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getSavePath)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestCases),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestCases())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestCases)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestCaseXml),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestCaseXml())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestCaseXml)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestReportXml),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestReportXml())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestReportXml)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestRunXml),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestRunXml())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestRunXml)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestRunLogs),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestRunLogs())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestRunLogs)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getTestOutput),
                                    yamlScaEnvironment
                                            .addLine(environment.getTestOutput())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getTestOutput)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Environment::getPythonPath),
                                    yamlScaEnvironment
                                            .addLine(environment.getPythonPath())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Environment::getPythonPath)))
                            .build()
            );
        }

        YamlSequenceBuilder yamlSeqChromes = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapChromes = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaChrome = Yaml.createYamlScalarBuilder();
        for (SysYamlConfig.Automation.Web.Browser.Chrome chrome : sysYamlConfig.getAutomation().getWeb().getBrowser().getChromes()) {
            yamlSeqChromes.add(
                    yamlMapChromes
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getId),
                                    yamlScaChrome
                                            .addLine(chrome.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getId)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getName),
                                    yamlScaChrome
                                            .addLine(chrome.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getName)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getPath),
                                    yamlScaChrome
                                            .addLine(chrome.getPath())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getPath)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getDriver),
                                    yamlScaChrome
                                            .addLine(chrome.getDriver())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getDriver)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getProfile),
                                    yamlScaChrome
                                            .addLine(chrome.getProfile())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getProfile)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getVersion),
                                    yamlScaChrome
                                            .addLine(chrome.getVersion())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getVersion)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getOfficialDownload),
                                    yamlScaChrome
                                            .addLine(chrome.getOfficialDownload())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getOfficialDownload)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getDriverDownload),
                                    yamlScaChrome
                                            .addLine(chrome.getDriverDownload())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getDriverDownload)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Chrome::getStatus),
                                    yamlScaChrome
                                            .addLine(String.valueOf(chrome.getStatus()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Chrome::getStatus)))
                            .build()
            );
        }

        YamlSequenceBuilder yamlSeqFirefoxs = Yaml.createMutableYamlSequenceBuilder();
        YamlMappingBuilder yamlMapFirefoxs = Yaml.createYamlMappingBuilder();
        YamlScalarBuilder yamlScaFirefox = Yaml.createYamlScalarBuilder();
        for (SysYamlConfig.Automation.Web.Browser.Firefox firefox : sysYamlConfig.getAutomation().getWeb().getBrowser().getFirefoxs()) {
            yamlSeqFirefoxs.add(
                    yamlMapFirefoxs
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getId),
                                    yamlScaFirefox
                                            .addLine(firefox.getId())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getId)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getName),
                                    yamlScaFirefox
                                            .addLine(firefox.getName())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getName)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getPath),
                                    yamlScaFirefox
                                            .addLine(firefox.getPath())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getPath)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getDriver),
                                    yamlScaFirefox
                                            .addLine(firefox.getDriver())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getDriver)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getProfile),
                                    yamlScaFirefox
                                            .addLine(firefox.getProfile())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getProfile)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getVersion),
                                    yamlScaFirefox
                                            .addLine(firefox.getVersion())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getVersion)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getOfficialDownload),
                                    yamlScaFirefox
                                            .addLine(firefox.getOfficialDownload())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getOfficialDownload)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getDriverDownload),
                                    yamlScaFirefox
                                            .addLine(firefox.getDriverDownload())
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getDriverDownload)))
                            .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser.Firefox::getStatus),
                                    yamlScaFirefox
                                            .addLine(String.valueOf(firefox.getStatus()))
                                            .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser.Firefox::getStatus)))
                            .build()
            );
        }
        YamlMappingBuilder yamlMapBrowser = Yaml.createMutableYamlMappingBuilder();
        yamlMapBrowser
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser::getChromes), yamlSeqChromes.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser::getChromes)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web.Browser::getFirefoxs), yamlSeqFirefoxs.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web.Browser::getFirefoxs)))
                .build();

        YamlMappingBuilder yamlMapWeb = Yaml.createMutableYamlMappingBuilder();
        YamlScalarBuilder yamlScaWeb = Yaml.createYamlScalarBuilder();
        SysYamlConfig.Automation.Web web = sysYamlConfig.getAutomation().getWeb();
        yamlMapWeb
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getId),
                        yamlScaWeb
                                .addLine(web.getId())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getId)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getName),
                        yamlScaWeb
                                .addLine(web.getName())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getName)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getDescription),
                        yamlScaWeb
                                .addLine(web.getDescription())
                                .buildPlainScalar(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getDescription)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getProject), yamlMapProject.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getProject)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getEnvironments), yamlSeqEnvironments.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getEnvironments)))
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation.Web::getBrowser), yamlMapBrowser.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation.Web::getBrowser)))
                .build();

        YamlMappingBuilder yamlMapAutomation = Yaml.createMutableYamlMappingBuilder();
        yamlMapAutomation
                .add(ColumnUtil.getFieldName(SysYamlConfig.Automation::getWeb), yamlMapWeb.build(ColumnUtil.getFieldAnnotation(SysYamlConfig.Automation::getWeb)))
                .build();

        YamlMapping yaml = Yaml.createYamlMappingBuilder()
                .add(ColumnUtil.getFieldName(SysYamlConfig::getProjects), yamlSeqProjects.build(ColumnUtil.getFieldAnnotation(SysYamlConfig::getProjects)))
                .add(ColumnUtil.getFieldName(SysYamlConfig::getAutomation), yamlMapAutomation.build(ColumnUtil.getFieldAnnotation(SysYamlConfig::getAutomation)))
                .build();
        System.out.println(yaml.toString());

//        JSONObject projectJson = JSONUtil.parseObj(sysYamlConfig, false, true);
//        System.out.println(projectJson.get("projects"));

//        String path = "D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment.yml";
//        System.out.println(SakuraConfig.getName());
//        System.out.println(SpringUtils.getRequiredProperty("sakura.profile"));
//        System.out.println(SpringUtils.getRequiredProperty("yaml.project.filePath"));

        String path = SpringUtils.getRequiredProperty("yaml.project.filePath");
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(yaml.toString());
        fileWriter.close();
    }
}
