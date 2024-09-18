package com.sakura.common.utils.jenkins;

import com.sakura.common.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JenkinsService {

    static JenkinsServer connection;
    public static Integer launchJob(String url,String userName,String passWord,String jobName, Map<String, String> params) {
        int buildNumber = -1;
        try {
            log.info("发起Jenkins连接请求...");
            connection = JenkinsConnect.connection(url,userName,passWord);
            log.info("发起Jenkins构建请求...");
            JobWithDetails job = connection.getJob(jobName);
            log.info("Jenkins构建参数："+params);
            if(params.size()>0){
                job.build(params);
            }else {
                job.build();
            }
//            TimeUnit.SECONDS.sleep(10);
//            buildNumber = job.details().getLastBuild().getNumber();
            buildNumber = job.details().getNextBuildNumber();
            log.info("***************************************************************");
            log.info("Job:{} launch success!", jobName);
            log.info("BUILD NUMBER:{}", buildNumber);
//            log.info(String.valueOf(job.details().getLastBuild().getNumber()));
//            log.info(String.valueOf(job.getLastBuild().details().getNumber()));
//            log.info(String.valueOf(job.details().getNextBuildNumber()));
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            log.error("Jenkins构建失败!");
            log.error(e.getMessage());
        }
//        connection.close();
        return buildNumber;
    }

    public static boolean getBuildResult(String jobName, Integer buildNumber) throws Exception {
        if (buildNumber < 0) {
            throw new Exception("Build Number Error:" + buildNumber);
        }
//        JenkinsServer connection = JenkinsConnect.connection();
        JobWithDetails job = connection.getJob(jobName);
        Build build = job.getBuildByNumber(buildNumber);
        BuildResult buildResult = build.details().getResult();
        while (buildResult == null) {
            log.info("Build Is Running...");
            buildResult = build.details().getResult();
            TimeUnit.SECONDS.sleep(10);
        }
        log.info(buildResult.toString());
//        connection.close();
        return buildResult.toString().equals("SUCCESS");
    }

    public static String getBuildDesc(String jobName, Integer buildNumber) throws IOException {
//        JenkinsServer connection = JenkinsConnect.connection();
        String description = connection.getJob(jobName).getBuildByNumber(buildNumber).details().getDescription();
//        connection.close();
        return description;
    }

    public static void close() {
        connection.close();
    }

//    public static List<Object> getApiJson(String apiUrl,String findValue){
//        Response response = given()
//                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
//                .contentType("application/json; charset=UTF-8")
//                .log().all()
//                .request()
//                .when()
//                .get(apiUrl);
//        return response.jsonPath().getString("actions[0].parameters[1].value");
//    }

    public static Response getApiJson(String apiUrl,String userName, String passWord){
        // 设置认证信息
//        String auth = "sakura:3edc$RFV";
        String auth = ""+userName+":"+passWord+"";
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        Response response =  given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .contentType("application/json; charset=UTF-8")
                .header("Authorization", "Basic " + encodedAuth)
                .log().all()
                .request()
                .when()
                .get(apiUrl);
        if(response.getStatusCode()!=200){
            log.error("Jenkins连接异常，请检查环境配置！");
        }
        return response;
    }

    public static JsonNode getJenkinsNodeAll(String url,String userName, String passWord) throws JsonProcessingException {
        url = url + "/computer/api/json?pretty=true&tree=computer[displayName,description,idle,executors[currentExecutable[url]],offline,offlineCauseReason],totalExecutors";
        String data = JenkinsService.getApiJson(url,userName,passWord).asString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public static JsonNode getJenkinsNode(String url,String userName, String passWord,String node) throws JsonProcessingException {
        url = url + "/computer/"+node+"/api/json?pretty=true&tree=displayName,description,idle,executors[currentExecutable[url]],offline,offlineCauseReason,totalExecutors";
        String data = JenkinsService.getApiJson(url,userName,passWord).asString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(data);
    }

    public static String getJenkinsJobParameters(String url,String userName, String passWord){
//        return JenkinsService.getApiJson(url + "api/json?pretty=true&tree=actions[parameters[value]]",userName,passWord).jsonPath().getString("actions[0].parameters[1].value");
        return JenkinsService.getApiJson(url + "api/json?pretty=true&tree=actions[parameters[value]]",userName,passWord).jsonPath().getString("actions.find { it._class == 'hudson.model.ParametersAction' }.parameters[1].value");
    }

    public static JsonNode getJenkinsNodeDetails(String url,String userName, String passWord,String node) throws JsonProcessingException {
        String job = url + "/computer/"+ node +"/config.xml";
        Response response = getApiJson(job,userName,passWord);

        // 解析XML响应
        XmlPath xmlPath = response.xmlPath();
//        String descriptorName = xmlPath.get("**.find { it.name() == 'hudson.tools.ToolLocationNodeProperty_-ToolLocation' }.home");
//        log.info("Descriptor Name: " + descriptorName);
//        List<Object> elementValues = xmlPath.getList("**.findAll { it.name() == 'hudson.tools.ToolLocationNodeProperty_-ToolLocation' }");
//        log.info("Element values: " + elementValues);

        String xmlResponse = xmlPath.prettify();
//        log.info(xmlResponse);

        // 创建XmlMapper对象
        XmlMapper xmlMapper = new XmlMapper();
        // 将XML字符串转换为JSON对象
        Object json = xmlMapper.readValue(xmlResponse, Object.class);

        // 创建ObjectMapper对象，用于美化JSON输出
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        // 输出JSON字符串
//        log.info(jsonString);

        // 解析JSON
        JsonNode rootNode = jsonMapper.readTree(jsonString);
//        JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
//        // 获取hudson.tools.ToolLocationNodeProperty_-ToolLocation列表
////        JsonNode toolLocationNode = rootNode.path("nodeProperties").path("hudson.tools.ToolLocationNodeProperty").path("locations").path("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
//        // 打印列表内容
//        log.info(toolLocationNode.toString());
//        for (JsonNode jsonNode : toolLocationNode) {
//            log.info(jsonNode.get("name").asText());
//            log.info(jsonNode.get("home").asText());
//        }
        return rootNode;
    }

    public static void main(String[] args) throws IOException {
//        String url = "http://172.19.5.221:8080/";
//        String userName = "sakura";
//        String passWord = "3edc$RFV";
//        String jobName = "2";
//        Map<String, String> params = new HashMap<>(2);
//        Integer buildNumber = launchJob(url,userName,passWord,jobName, params);
//        boolean buildResult = false;
//        try {
//            buildResult = getBuildResult(jobName, buildNumber);
//            log.info("Build Success? {}", buildResult);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        if (!buildResult) {
//            System.exit(501);
//        }
//        try {
//            String desc = getBuildDesc(jobName, buildNumber);
//            log.info(desc);
//        } catch (IOException e) {
//            log.error("Query Desc Failed! {}", e.getMessage());
//        }
//        Map<Object,Object> results = new HashMap<>();
//        results.put("buildNumber",1);
//        results.put("buildResult",false);
//        log.info(String.valueOf(results));

        String computerUrl = "http://172.19.5.222:8080/computer/api/json?pretty=true&tree=computer[displayName,description,idle,executors[currentExecutable[url]],offline,offlineCauseReason],totalExecutors";
//        String computer = getApiJson(computerUrl,"Sakura.Web.UI.Automation.Test","liuzhi","lz612425","GET");
//        JSONObject jsonObject = JSON.parseObject(computer);
//        List<Object> computer = getApiJson(computerUrl,"computer.findAll { it.displayName == '172.19.5.231' }");
//        log.info(computer.toString());
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(computer);
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(jsonString);
//            String url = jsonNode.get(0)
//                    .get("executors")
//                    .get(0)
//                    .get("currentExecutable")
//                    .get("url")
//                    .asText();
//            log.info("URL: " + url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String data = JenkinsService.getApiJson(computerUrl).asString();
//        log.info(data);
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(data);
//            for (JsonNode computer : jsonNode.get("computer")) {
//                for (JsonNode executors : computer.get("executors")) {
//                    log.info(executors);
//                    if(!computer.get("idle").asBoolean()){
//                        String url = executors.get("currentExecutable").get("url").asText();
//                        log.info(url);
//                    }
//                }
////                if (computer.get("displayName").asText().equals("172.19.5.231")) {
////                    log.info(computer);
////                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String job = "http://172.19.5.222:8080/job/Sakura.Api.Automation.Test/1392/api/json?pretty=true&tree=actions[parameters[value]]";
//        Response computer = getApiJson(job,null,null);
//        log.info(computer.asString());
////        log.info(computer.jsonPath().getString("actions[0].parameters[1].value"));
//        log.info(computer.jsonPath().getString("actions.find { it._class == 'hudson.model.ParametersAction' }.parameters[1].value"));

        String[] arr = new String[0];
        System.out.println(StringUtils.isNotEmpty(arr)); // 输出 true

//        String json = "{\n" +
//                "    \"_class\":\"hudson.maven.MavenModuleSetBuild\",\n" +
//                "    \"actions\":[\n" +
//                "        {\n" +
//                "            \"_class\":\"hudson.model.CauseAction\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"_class\":\"hudson.model.ParametersAction\",\n" +
//                "            \"parameters\":[\n" +
//                "                {\n" +
//                "                    \"_class\":\"me.leejay.jenkins.dateparameter.DateParameterValue\",\n" +
//                "                    \"value\":\"2023-07-25\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"_class\":\"hudson.model.StringParameterValue\",\n" +
//                "                    \"value\":\"系统管理员1\"\n" +
//                "                },\n" +
//                "                {\n" +
//                "                    \"_class\":\"hudson.model.StringParameterValue\",\n" +
//                "                    \"value\":\"liuzhi@sakura.com\"\n" +
//                "                }\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        JsonPath jsonPath = new JsonPath(json);
//        System.out.println(jsonPath.getString("actions.find { it._class == 'hudson.model.ParametersAction' }"));
//        System.out.println(jsonPath.getString("actions.find { it._class == 'hudson.model.ParametersAction' }.parameters"));
//        System.out.println(jsonPath.getString("actions.find { it._class == 'hudson.model.ParametersAction' }.parameters[1].value"));
//        System.out.println(jsonPath.getString("actions.find { it._class == 'hudson.model.ParametersAction' }.parameters.find { it._class == 'hudson.model.StringParameterValue' }.value"));

//        String job = "http://172.19.5.222:8080/computer/built-in/config.xml";
//        Response response = getApiJson(job);
////        log.info(computer.xmlPath().getString("slave.nodeProperties.hudson.tools.ToolLocationNodeProperty.locations.hudson.tools.ToolLocationNodeProperty_-ToolLocation"));
////        log.info(computer.xmlPath().getString("**.find { it.name() == 'JDK' }.home"));
//
//        // 解析XML响应
//        XmlPath xmlPath = response.xmlPath();
////        String descriptorName = xmlPath.get("**.find { it.name() == 'hudson.tools.ToolLocationNodeProperty_-ToolLocation' }.home");
////        log.info("Descriptor Name: " + descriptorName);
////
////        List<Object> elementValues = xmlPath.getList("**.findAll { it.name() == 'hudson.tools.ToolLocationNodeProperty_-ToolLocation' }");
////        log.info("Element values: " + elementValues);
//
//        String xmlResponse = xmlPath.prettify();
//        log.info(xmlResponse);
//
//        // 创建XmlMapper对象
//        XmlMapper xmlMapper = new XmlMapper();
//        // 将XML字符串转换为JSON对象
//        Object json = xmlMapper.readValue(xmlResponse, Object.class);
//
//        // 创建ObjectMapper对象，用于美化JSON输出
//        ObjectMapper jsonMapper = new ObjectMapper();
//        String jsonString = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//        // 输出JSON字符串
//        log.info(jsonString);
//
//        try {
//            // 解析JSON
//            JsonNode rootNode = jsonMapper.readTree(jsonString);
//            // 获取hudson.tools.ToolLocationNodeProperty_-ToolLocation列表
////            JsonNode toolLocationNode = rootNode.path("nodeProperties").path("hudson.tools.ToolLocationNodeProperty").path("locations").path("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
//            JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
//            log.info(toolLocationNode.toString());
//            // 打印列表内容
//            for (JsonNode jsonNode : toolLocationNode){
//                log.info(jsonNode.get("name").asText());
//                log.info(jsonNode.get("home").asText());
//            }
//        }catch (Exception e){
//            log.info("11111");
//            e.printStackTrace();
//        }
    }
}