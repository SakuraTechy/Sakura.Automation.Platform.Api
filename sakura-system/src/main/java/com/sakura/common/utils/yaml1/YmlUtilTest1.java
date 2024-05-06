//package com.sakura.common.utils.yaml;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.amihaiemil.eoyaml.*;
//import com.sakura.system.domain.SysYamlConfig;
//
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author liuzhi
// */
//public class YmlUtilTest1 {
//    public static void main1(String[] args) throws IOException {
//        File file = new File("D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment1.yml");
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        PrintStream printStream = new PrintStream(fileOutputStream);
//        System.setOut(printStream);
//
//        final YamlMapping yaml = Yaml.createYamlMappingBuilder()
//                .add("architect", "amihaiemil")
//                .add(
//                        "architects",
//                        Yaml.createYamlSequenceBuilder()
//                                .add("mihai")
//                                .add("sherif")
//                                .build("architects of the project")
//                )
//                .add(
//                        "developers",
//                        Yaml.createYamlSequenceBuilder()
//                                .add("rultor")
//                                .add("salikjan")
//                                .add("sherif")
//                                .build("all the contributors")
//                )
//                .add(
//                        "name",
//                        Yaml.createYamlScalarBuilder()
//                                .addLine("eo-yaml")
//                                .buildPlainScalar("name of the project")
//                )
//                .build("Project Info. This comment refers to the whole document.");
//        System.out.println(yaml.toString());
////        String arch = yaml.string("architect");
////        System.out.println(arch);
////        String archs = yaml.yamlSequence("architects").string(0);
////        System.out.println(archs);
////        YamlSequence devs = yaml.yamlSequence("developers");
////        for (final YamlNode dev : devs) {
////            System.out.println(dev.toString());
////        }
//
//        File file1 = new File("D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment2.yml");
//        FileWriter fileWriter = new FileWriter(file1);
//        fileWriter.write(yaml.toString());
//        fileWriter.close();
//    }
//
//    public static void main2(String[] args) throws IOException {
//        SysYamlConfig.Project.Environment.Version version = new SysYamlConfig.Project.Environment.Version();
//        version.setIndex(1);
//        version.setId("1");
//        version.setName("V3.0B08D007");
//        version.setDescription("主线版本");
//        version.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Version> versions = new ArrayList<>();
//        versions.add(version);
//
//        SysYamlConfig.Project.Environment.Domain domain = new SysYamlConfig.Project.Environment.Domain();
//        domain.setIndex(1);
//        domain.setId("1");
//        domain.setName("域名1");
//        domain.setUrl("https://172.19.1.201:8543/login");
//        domain.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
//        domains.add(domain);
//
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
//
//        SysYamlConfig.Project.Environment.Server server = new SysYamlConfig.Project.Environment.Server();
//        server.setIndex(1);
//        server.setId("1");
//        server.setHost("172.19.1.201");
//        server.setPort(8543);
//        server.setUserName("root");
//        server.setPassWord("@1fw#2soc$3vpn");
//        server.setDescription("201环境");
//        server.setStatus(1);
//
//        ArrayList<SysYamlConfig.Project.Environment.Server> servers = new ArrayList<>();
//        servers.add(server);
//
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
//        SysYamlConfig.Project.Environment.DataBase dataBase = new SysYamlConfig.Project.Environment.DataBase();
//        dataBase.setOracles(oracles);
//
//        SysYamlConfig.Project.Environment environment = new SysYamlConfig.Project.Environment();
//        environment.setIndex(1);
//        environment.setId("1");
//        environment.setName("测试环境");
//        environment.setStatus(1);
//
//        environment.setVersions(versions);
//        environment.setDomains(domains);
//        environment.setAccount(account);
//        environment.setServers(servers);
//        environment.setDataBase(dataBase);
//
//        ArrayList<SysYamlConfig.Project.Environment> environments = new ArrayList<>();
//        environments.add(environment);
//
//        SysYamlConfig.Project project = new SysYamlConfig.Project();
//        project.setIndex(1);
//        project.setId("1");
//        project.setName("数据脱敏系统");
//        project.setDescription("AAS_DM");
//        project.setEnvironments(environments);
//
//
//        SysYamlConfig.Project project2 = new SysYamlConfig.Project();
//        project2.setIndex(2);
//        project2.setId("2");
//        project2.setName("数据库综合安全防护系统");
//        project2.setDescription("AAS_DBSG");
//
//        List<SysYamlConfig.Project> projects = new ArrayList<>();
//        projects.add(project);
////        projects.add(project2);
//        System.out.println(projects);
//
//        SysYamlConfig sysYamlConfig = new SysYamlConfig();
//        sysYamlConfig.setProjects(projects);
//
////        System.out.println(projectBean.toString());
////        System.out.println(projectBean.getProjects());
////        System.out.println(JSONUtil.parseObj(projectBean, false, true));
//
////        YamlMapping projectBean = Yaml.createYamlDump(projectBean).dumpMapping();
////        System.out.println(projectBean.toString());
//
//        JsonObject jsonObject = Json.createObjectBuilder().add("affix", "copy").build();
//        YamlMapping yaml2 = Yaml.fromJsonObject(jsonObject);
//        System.out.println(yaml2.toString());
//
//        JSONObject projectJson = JSONUtil.parseObj(sysYamlConfig, false, true);
//        JsonReader jsonreader = Json.createReader(new StringReader(projectJson.toString()));
//        JsonObject jsonobject1 = jsonreader.readObject();
//        jsonreader.close();
//        YamlMapping yaml3 = Yaml.fromJsonObject(jsonobject1);
//        System.out.println(yaml3.toString());
//
//
//        YamlSequence yaml_Projects1 = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index", "1")
//                        .add("id", "1")
//                        .add("name", "数据脱敏系统")
//                        .add("desc", "AAS_DM")
//                        .build()
//                )
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("版本序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("版本ID"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("V3.0B08D007")
//                                .buildPlainScalar("版本名称"))
//                        .build()
//                ).build("项目管理");
//
//        YamlSequence yaml_versions = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("版本序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("版本ID"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("V3.0B08D007")
//                                .buildPlainScalar("版本名称"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("主线版本")
//                                .buildPlainScalar("版本描述"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .build()
//                ).build("版本管理");
//
//        YamlSequence yaml_domains = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("域名序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("域名ID"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("测试环境")
//                                .buildPlainScalar("域名名称"))
//                        .add("url",Yaml.createYamlScalarBuilder()
//                                .addLine("https://172.19.1.201:8543/login")
//                                .buildPlainScalar("域名地址"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .build()
//                ).build("域名配置");
//
//        YamlSequence yaml_securitys = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("账号序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("账号ID"))
//                        .add("userName",Yaml.createYamlScalarBuilder()
//                                .addLine("secadmin")
//                                .buildPlainScalar("用户名"))
//                        .add("passWord",Yaml.createYamlScalarBuilder()
//                                .addLine("3edc$RFV")
//                                .buildPlainScalar("密码"))
//                        .add("desc",Yaml.createYamlScalarBuilder()
//                                .addLine("安全员账号")
//                                .buildPlainScalar("描述"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .build()
//                ).build("账号类型");
//
//        YamlMapping yaml_account = Yaml.createYamlMappingBuilder()
//                .add("securitys",yaml_securitys)
//                .add("systems",yaml_securitys)
//                .add("audits",yaml_securitys)
//                .build("账号配置");
//
//        YamlSequence yaml_servers = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("服务器序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("服务器ID"))
//                        .add("host",Yaml.createYamlScalarBuilder()
//                                .addLine("172.19.1.201")
//                                .buildPlainScalar("主机"))
//                        .add("prot",Yaml.createYamlScalarBuilder()
//                                .addLine("8543")
//                                .buildPlainScalar("端口"))
//                        .add("userName",Yaml.createYamlScalarBuilder()
//                                .addLine("root")
//                                .buildPlainScalar("用户名"))
//                        .add("passWord",Yaml.createYamlScalarBuilder()
//                                .addLine("@1fw#2soc$3vpn")
//                                .buildPlainScalar("密码"))
//                        .add("desc",Yaml.createYamlScalarBuilder()
//                                .addLine("201环境")
//                                .buildPlainScalar("描述"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .build()
//                ).build("服务器配置");
//
//        YamlSequence yaml_databases = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("数据库序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("数据库ID"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("172.19.5.50环境数据库")
//                                .buildPlainScalar("数据库名称"))
//                        .add("driver",Yaml.createYamlScalarBuilder()
//                                .addLine("oracle.jdbc.driver.OracleDriver")
//                                .buildPlainScalar("数据库驱动"))
//                        .add("url",Yaml.createYamlScalarBuilder()
//                                .addLine("jdbc:oracle:thin:@172.19.5.50:1521:dbtest01")
//                                .buildPlainScalar("数据库连接串"))
//                        .add("userName",Yaml.createYamlScalarBuilder()
//                                .addLine("root")
//                                .buildPlainScalar("用户名"))
//                        .add("passWord",Yaml.createYamlScalarBuilder()
//                                .addLine("Sakura_mySQL123")
//                                .buildPlainScalar("密码"))
//                        .add("maxActive",Yaml.createYamlScalarBuilder()
//                                .addLine("20")
//                                .buildPlainScalar("最大连接池数量"))
//                        .add("maxWait",Yaml.createYamlScalarBuilder()
//                                .addLine("60000")
//                                .buildPlainScalar("最大等待时间"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .build()
//                ).build("数据库配置");
//
//        YamlSequence yaml_environments = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("环境序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("环境ID"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("测试环境")
//                                .buildPlainScalar("环境名称"))
//                        .add("state",Yaml.createYamlScalarBuilder()
//                                .addLine("true")
//                                .buildPlainScalar("启用状态(默认第一条启用)"))
//                        .add("domains",yaml_domains)
//                        .add("account", yaml_account)
//                        .add("servers",yaml_servers)
//                        .add("databases", yaml_databases)
//                        .build()
//                ).build("环境管理");
//
//        YamlSequence yaml_Projects = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("项目序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("项目ID"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("数据脱敏系统")
//                                .buildPlainScalar("项目名称"))
//                        .add("desc",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("AAS_DM"))
//                        .add("versions",yaml_versions)
//                        .add("environments",yaml_environments)
//                        .build()
//                )
//                .build("项目管理");
//        YamlMapping yaml = Yaml.createYamlMappingBuilder()
//                .add("projects",yaml_Projects)
//                .build();
//        System.out.println(yaml.toString());
//
//        File file2 = new File("D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment3.yml");
//        FileWriter fileWriter2 = new FileWriter(file2);
//        fileWriter2.write(yaml2.toString());
//        fileWriter2.close();
//
//        File file3 = new File("D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment4.yml");
//        FileWriter fileWriter3 = new FileWriter(file3);
//        fileWriter3.write(yaml3.toString());
//        fileWriter3.close();
//
//        File file = new File("D:/King/Vue/Sakura.Test.Platform/Sakura.Test.Platform.Api/sakura-admin/environment5.yml");
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(yaml.toString());
//        fileWriter.close();
//    }
//
//    public static void main(String[] args) {
//        SysYamlConfig.Project project = new SysYamlConfig.Project();
//        project.setIndex(1);
//        project.setId("1");
//        project.setName("数据脱敏系统");
//        project.setDescription("AAS_DM");
//        creatProjectYaml(project);
//
//        SysYamlConfig.Project project2 = new SysYamlConfig.Project();
//        project2.setIndex(2);
//        project2.setId("2");
//        project2.setName("数据库综合安全防护系统");
//        project2.setDescription("AAS_DBSG");
//
//        List<SysYamlConfig.Project> projects = new ArrayList<>();
//        projects.add(project);
//        SysYamlConfig sysYamlConfig = new SysYamlConfig();
//        sysYamlConfig.setProjects(projects);
//        creatProjectYaml1(sysYamlConfig);
//    }
//
//    public static void creatProjectYaml(SysYamlConfig.Project project) {
//        YamlSequence yaml_Projects = Yaml.createYamlSequenceBuilder()
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index", Yaml.createYamlScalarBuilder()
//                                .addLine(project.getIndex().toString())
//                                .buildPlainScalar("项目序号"))
//                        .add("id", Yaml.createYamlScalarBuilder()
//                                .addLine(project.getId())
//                                .buildPlainScalar("项目ID"))
//                        .add("name", Yaml.createYamlScalarBuilder()
//                                .addLine(project.getName())
//                                .buildPlainScalar("项目名称"))
//                        .add("desc", Yaml.createYamlScalarBuilder()
//                                .addLine(project.getDescription())
//                                .buildPlainScalar("项目描述"))
//                        .build()
//                )
//                .add(Yaml.createYamlMappingBuilder()
//                        .add("index",Yaml.createYamlScalarBuilder()
//                                .addLine("2")
//                                .buildPlainScalar("项目序号"))
//                        .add("id",Yaml.createYamlScalarBuilder()
//                                .addLine("2")
//                                .buildPlainScalar("项目ID"))
//                        .add("name",Yaml.createYamlScalarBuilder()
//                                .addLine("数据脱敏系统")
//                                .buildPlainScalar("项目名称"))
//                        .add("desc",Yaml.createYamlScalarBuilder()
//                                .addLine("1")
//                                .buildPlainScalar("AAS_DM"))
//                        .build()
//                )
//                .build("项目管理");
//        YamlMapping yaml = Yaml.createYamlMappingBuilder()
//                .add("projects", yaml_Projects)
//                .build();
//        System.out.println(yaml.toString());
//    }
//
//    public static void creatProjectYaml1(SysYamlConfig sysYamlConfig) {
//        YamlSequenceBuilder yamlSeqProjects = Yaml.createMutableYamlSequenceBuilder();
//        YamlMappingBuilder yamlMapProjects = Yaml.createYamlMappingBuilder();
//        YamlScalarBuilder yamlScaProject = Yaml.createYamlScalarBuilder();
//        for (SysYamlConfig.Project project : sysYamlConfig.getProjects()) {
//            yamlSeqProjects.add(
//                    yamlMapProjects
//                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getIndex),
//                                    yamlScaProject
//                                            .addLine(project.getIndex().toString())
//                                            .buildPlainScalar("项目序号"))
//                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getId),
//                                    yamlScaProject
//                                            .addLine(project.getId())
//                                            .buildPlainScalar("项目ID"))
//                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getName),
//                                    yamlScaProject
//                                            .addLine(project.getName())
//                                            .buildPlainScalar("项目名称"))
//                            .add(ColumnUtil.getFieldName(SysYamlConfig.Project::getDescription),
//                                    yamlScaProject
//                                            .addLine(project.getDescription())
//                                            .buildPlainScalar("项目描述"))
//                            .build()
//            );
//        }
////        yaml_Projects.add()
////        YamlSequence yamls_Projects = yaml_Projects.build("项目管理");
//        YamlMapping yaml = Yaml.createYamlMappingBuilder()
//                .add("projects", yamlSeqProjects.build("项目管理"))
//                .build();
//        System.out.println(yaml.toString());
//
//        JSONObject projectJson = JSONUtil.parseObj(sysYamlConfig, false, true);
//        System.out.println(projectJson.get("projects"));
//    }
//}
