package com.sakura.common.utils.xml;

import com.alibaba.fastjson.JSON;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.jenkins.JenkinsService;
import com.sakura.common.utils.yaml.YamlConfig;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.*;

import com.sakura.project.domain.AutomationConfig;
import com.sakura.project.domain.EnvironmentConfig;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.project.domain.ProjectConfig;
import com.sakura.project.mapper.AutomationConfigMapper;
import com.sakura.project.mapper.EnvironmentConfigMapper;
import com.sakura.project.mapper.ProjectConfigMapper;

import com.sakura.system.mapper.SysAutomationMapper;
import com.squareup.javapoet.*;
import lombok.Data;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.lang.model.element.Modifier;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wutun
 */
@Data
@Component
public class SceneXmlUtils {
    static Logger log = LoggerFactory.getLogger(SceneXmlUtils.class);

    @Autowired
    private ProjectConfigMapper projectConfigMapper;

    @Autowired
    private EnvironmentConfigMapper environmentConfigMapper;

    @Autowired
    private AutomationConfigMapper automationConfigMapper;

    @Autowired
    private SysAutomationMapper sysAutomationMapper;

    public static SceneXmlUtils sceneXmlUtils;

    @PostConstruct
    public void init() {
        sceneXmlUtils = this;
//        sceneXmlUtils.sysConfigurationMapper = this.sysConfigurationMapper;
//        sceneXmlUtils.sysProjectMapper = this.sysProjectMapper;
    }

    public static void createJava(SysScene sysScene,String state, ExecuteConfig executeConfig){
        try {
            ClassName logger = ClassName.get("org.apache.log4j","Logger");
            ClassName afterTest = ClassName.get("org.testng.annotations","AfterTest");
            ClassName beforeTest = ClassName.get("org.testng.annotations","BeforeTest");
            ClassName parameters = ClassName.get("org.testng.annotations","Parameters");
            ClassName test = ClassName.get("org.testng.annotations","Test");

            ClassName testUnit = ClassName.get("com.sakura.base","TestUnit");
            ClassName runUnitService = ClassName.get("com.sakura.service","RunUnitService");
            ClassName webXmlParseService = ClassName.get("com.sakura.service","WebXmlParseService");

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(sysScene.getSceneId())
                    .addModifiers(Modifier.PUBLIC);

            FieldSpec logSpec = FieldSpec.builder(logger, "log")
                    .addModifiers(Modifier.PRIVATE,Modifier.STATIC)
                    .initializer("$T.getLogger("+sysScene.getSceneId()+".class)",logger)
                    .build();

            FieldSpec testUnitSpec = FieldSpec.builder(testUnit, "testUnit")
                    .addModifiers(Modifier.PRIVATE,Modifier.STATIC)
                    .build();
            FieldSpec webXmlParseServiceSpec = FieldSpec.builder(webXmlParseService, "webXmlParseService")
                    .addModifiers(Modifier.PRIVATE,Modifier.STATIC)
                    .build();
            FieldSpec runServiceSpec = FieldSpec.builder(runUnitService, "runService")
                    .addModifiers(Modifier.PRIVATE,Modifier.STATIC)
                    .build();

            AnnotationSpec parametersAnnotationSpec = AnnotationSpec.builder(parameters)
                    .addMember("value","{\"browser\",\"profile\"}")
//                    .addMember("value", "$T.$L", ElementType.class, ElementType.METHOD)
                    .build();
            AnnotationSpec beforeTestAnnotationSpec = AnnotationSpec.builder(beforeTest)
                    .build();
            MethodSpec setupSpec = MethodSpec.methodBuilder("setup")
                    .addAnnotation(parametersAnnotationSpec)
                    .addAnnotation(beforeTestAnnotationSpec)
                    .addModifiers(Modifier.PRIVATE)
                    .addParameter(String.class,"browserName")
                    .addParameter(Boolean.class,"profile")
                    .addException(Exception.class)
                    .addStatement("TestUnit testunit = WebXmlParseService.parse(browserName,profile,this.getClass().getPackage().getName(),this.getClass().getSimpleName())")
                    .addStatement("runService = new RunUnitService(testunit)")
                    .build();

            classBuilder
//                    .addField(logSpec)
                    .addField(testUnitSpec)
                    .addField(webXmlParseServiceSpec)
                    .addField(runServiceSpec)
                    .addMethod(setupSpec);
//            List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
            List<SysSceneCase> sysSceneCases = JSON.parseArray(sysScene.getCaseMsg(),SysSceneCase.class);
            if (sysSceneCases != null && sysSceneCases.size() != 0) {
                sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
                for (SysSceneCase sysSceneCase : sysSceneCases) {
                    AnnotationSpec testAnnotationSpec = AnnotationSpec.builder(test)
                            .addMember("groups","{$S}", sysSceneCase.getId())
                            .build();
                    MethodSpec testSpec = MethodSpec.methodBuilder(sysSceneCase.getId())
                            .addAnnotation(testAnnotationSpec)
                            .addModifiers(Modifier.PUBLIC)
                            .addException(Exception.class)
                            .addStatement("runService.runCase(Thread.currentThread() .getStackTrace()[1].getMethodName())")
                            .build();
                    classBuilder.addMethod(testSpec);
                }
            }
            AnnotationSpec afterTestAnnotationSpec = AnnotationSpec.builder(afterTest)
                    .build();
            MethodSpec tearDownSpec = MethodSpec.methodBuilder("TearDown")
                    .addAnnotation(afterTestAnnotationSpec)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("runService.setUnit("+state+")")
//                    .addStatement("runService.closeBrowser()")
                    .build();
            classBuilder.addMethod(tearDownSpec);
            TypeSpec mainActivity = classBuilder.build();
            JavaFile javaFile = JavaFile.builder(""+convertToValidPackageName(sysScene.getProjectName())+"."+convertToValidPackageName(sysScene.getVersionName())+".TestCases", mainActivity)
                    .skipJavaLangImports(true)
                    .build();

//            String path = SceneXmlUtils.getProjectPath();
            String path = executeConfig.getAutomationConfig().getProject().getPath();
            File file = new File(path);
            javaFile.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createXml(SysScene sysScene, String xmlPath, ExecuteConfig executeConfig) {
        Document document = DocumentHelper.createDocument();
        Element unit = document.addElement("unit");
        unit.addAttribute("id", sysScene.getSceneId());
        unit.addAttribute("name", sysScene.getName());
        unit.addAttribute("version", sysScene.getVersionName());

//        List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
        List<SysSceneCase> sysSceneCases = JSON.parseArray(sysScene.getCaseMsg(),SysSceneCase.class);
        //遍历scene生成case节点
        if (sysSceneCases != null && sysSceneCases.size() != 0) {
            sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
            for (SysSceneCase sysSceneCase : sysSceneCases) {
                Element aCase = unit.addElement("case");
                //case节点需要设置的属性及值
                aCase.addAttribute("id", sysSceneCase.getId());
                aCase.addAttribute("name", sysSceneCase.getName());
                List<Step> testSteps = sysSceneCase.getStepList();
                //遍历step节点
                if (testSteps != null && testSteps.size() != 0) {
                    testSteps.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
                    for (Step step : testSteps) {
                        Element stepElement = aCase.addElement("step");
                        //判断step对象中为空的filed则不在节点上生成
                        String action = "";
                        for (Field f : step.getClass().getDeclaredFields()) {
                            f.setAccessible(true);
                            try {
                                if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && !"config".equals(f.getName()) && !"pid".equals(f.getName()) && !"order".equals(f.getName()) && !"id".equals(f.getName())) {
                                    stepElement.addAttribute(f.getName(), f.get(step).toString());
                                    if("web-geturl".equals(f.get(step).toString())){
                                        action = f.get(step).toString();
                                    }
                                    if("exe-shell".equals(f.get(step).toString())){
                                        action = f.get(step).toString();
                                    }
                                } else if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && "config".equals(f.getName())) {
                                    List<Config> configList = step.getConfig();
                                    for (Config config : configList) {
                                        if("web-geturl".equals(action)){
                                            stepElement.addAttribute(config.getParamsName(), executeConfig.getProjectConfig().getServer().getDomain());
                                        } else if("exe-shell".equals(action)){
                                            if("shell".equals(config.getParamsName())){
                                                if(config.getParamsValue().contains("tcpreplay")) {
                                                    configList = executeConfig.getProjectConfig().getServer().getConfigList();
                                                    for (Config serverConfig : configList) {
                                                        if("eth".equals(serverConfig.getParamsName())){
                                                            stepElement.addAttribute(config.getParamsName(), StringEscapeUtils.unescapeXml(config.getParamsValue().replace("eth1", serverConfig.getParamsValue())));
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    stepElement.addAttribute(config.getParamsName(), StringEscapeUtils.unescapeXml(config.getParamsValue()));
                                                }
                                            } else {
                                                stepElement.addAttribute(config.getParamsName(), StringEscapeUtils.unescapeXml(config.getParamsValue()));
                                            }
                                        } else {
                                            stepElement.addAttribute(config.getParamsName(), StringEscapeUtils.unescapeXml(config.getParamsValue()));
                                        }
                                    }
                                } else if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && "order".equals(f.getName())) {
                                    stepElement.addAttribute("id", f.get(step).toString());
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        // 格式化模板
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        // 5、生成xml文件
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String fileName = sysScene.getSceneId();
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.info("生成xml文件失败。文件名【" + fileName + "】");
        }
        // 生成的XML文件
        // 利用文件输出流输出到文件， 文件输出到了您的项目根目录下了
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestCaseXml/AAS_DBSG_SMOKE_001.xml";
//        String projectPath = getProjectPath("Automation_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
        xmlPath = xmlPath + "/TestCaseXml/";
        mkdirs(xmlPath);
        try (FileOutputStream fos = new FileOutputStream(xmlPath + fileName + ".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createTestngReportXml(SysScene sysScene, String xmlPath) {
        ProjectConfig projectConfig = sceneXmlUtils.projectConfigMapper.get(sysScene.getProjectId());
        String projectName = projectConfig.getName();
//        String abbreviate = projectConfig.getAbbreviate();

        Document document = DocumentHelper.createDocument();
        Element suite = document.addElement("suite");
        suite.addAttribute("name", projectName);
        suite.addAttribute("configfailurepolicy", "continue");
        suite.addAttribute("parallel", "tests");
        suite.addAttribute("thread-count", "1");

        Element test = suite.addElement("test");
        test.addAttribute("name", sysScene.getName());
        test.addAttribute("junit", "false");
        test.addAttribute("verbose", "3");
        test.addAttribute("parallel", "false");
        test.addAttribute("thread-count", "50");
        test.addAttribute("annotations", "javadoc");
        test.addAttribute("time-out", "1");
        test.addAttribute("enabled", "true");
        test.addAttribute("skipfailedinvocationcounts", "true");
        test.addAttribute("preserve-order", "true");
        test.addAttribute("allow-return-values", "true");

        Element parameter1 = test.addElement("parameter");
        parameter1.addAttribute("name", "browser");
        parameter1.addAttribute("value", "chrome");

        Element parameter2= test.addElement("parameter");
        parameter2.addAttribute("name", "profile");
        parameter2.addAttribute("value", "false");

        Element classes = test.addElement("classes");
        Element cClass = classes.addElement("class");
        cClass.addAttribute("name", sysScene.getProjectName()+"."+sysScene.getVersionName()+".TestCases."+sysScene.getSceneId());

        Element methods = null;
        Element groups = test.addElement("groups");
        Element dependencies = groups.addElement("dependencies");
        List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
        if (sysSceneCases != null && sysSceneCases.size() != 0) {
            sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
            String id = "";
            for (SysSceneCase sysSceneCase : sysSceneCases) {
                if(sysSceneCase.getOrder()==1){
                    methods = cClass.addElement("methods");
                    Element include = methods.addElement("include");
                    id = sysSceneCase.getId();
                    include.addAttribute("name", id);
                }else {
                    cClass.remove(methods);
                    Element group = dependencies.addElement("group");
                    group.addAttribute("depends-on", id);
                    id = sysSceneCase.getId();
                    group.addAttribute("name", id);
                }
            }
        }

        Element listeners = suite.addElement("listeners");
        Element listener1 = listeners.addElement("listener");
        Element listener2 = listeners.addElement("listener");
        listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
        listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.info("生成xml文件失败。文件名【TestngReport.xml】");
        }
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestReportXml/TestngReport.xml";
//        String projectPath = getProjectPath("Automation_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestReportXml/";
        xmlPath = xmlPath + "/TestReportXml/";
        mkdirs(xmlPath);
        String ip = getAutomationEnvironmentName();
        try (FileOutputStream fos = new FileOutputStream(xmlPath + ip +".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTestngReportXml(ExecuteConfig executeConfig) {
        Document document = null;
        Element suite = null;
        String xmlPath = "";

        int index = 1;
        List<SysScene> sysSceneList =  executeConfig.getSysSceneList();
        sysSceneList.sort((x, y) -> x.getSceneId().compareTo(y.getSceneId()));
        for (SysScene sysScene : sysSceneList) {
            sysScene = sceneXmlUtils.sysAutomationMapper.get(sysScene.getId());
//            xmlPath = getProjectPath(sysScene);
            xmlPath = executeConfig.getAutomationConfig().getProject().getPath() + "/"+convertToValidPackageName(sysScene.getProjectName())+"/"+convertToValidPackageName(sysScene.getVersionName());
            if (index == 1) {
                ProjectConfig projectConfig = sceneXmlUtils.projectConfigMapper.get(sysScene.getProjectId());
                String projectName = projectConfig.getName();

                document = DocumentHelper.createDocument();
                suite = document.addElement("suite");
                suite.addAttribute("name", projectName);
                suite.addAttribute("configfailurepolicy", "continue");
                suite.addAttribute("parallel", "tests");
                suite.addAttribute("thread-count", "1");
            }

            Element test = suite.addElement("test");
            test.addAttribute("name", sysScene.getName());
            test.addAttribute("junit", "false");
            test.addAttribute("verbose", "3");
            test.addAttribute("parallel", "false");
            test.addAttribute("thread-count", "50");
            test.addAttribute("annotations", "javadoc");
            test.addAttribute("time-out", "1");
            test.addAttribute("enabled", "true");
            test.addAttribute("skipfailedinvocationcounts", "true");
            test.addAttribute("preserve-order", "true");
            test.addAttribute("allow-return-values", "true");

            Element parameter1 = test.addElement("parameter");
            parameter1.addAttribute("name", "browser");
            parameter1.addAttribute("value", "chrome");

            Element parameter2 = test.addElement("parameter");
            parameter2.addAttribute("name", "profile");
            parameter2.addAttribute("value", "false");

            Element classes = test.addElement("classes");
            Element cClass = classes.addElement("class");
            cClass.addAttribute("name", convertToValidPackageName(sysScene.getProjectName()) + "." + convertToValidPackageName(sysScene.getVersionName()) + ".TestCases." + sysScene.getSceneId());

            Element methods = null;
            Element groups = test.addElement("groups");
            Element dependencies = groups.addElement("dependencies");
            List<SysSceneCase> sysSceneCases = JSON.parseArray(sysScene.getCaseMsg(),SysSceneCase.class);
            if (sysSceneCases != null && sysSceneCases.size() != 0) {
                sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
                String id = "";
                for (SysSceneCase sysSceneCase : sysSceneCases) {
                    if (sysSceneCase.getOrder() == 1) {
                        methods = cClass.addElement("methods");
                        Element include = methods.addElement("include");
                        id = sysSceneCase.getId();
                        include.addAttribute("name", id);
                    } else {
                        cClass.remove(methods);
                        Element group = dependencies.addElement("group");
                        group.addAttribute("depends-on", id);
                        id = sysSceneCase.getId();
                        group.addAttribute("name", id);
                    }
                }
            }
            SceneXmlUtils.createXml(sysScene,xmlPath,executeConfig);
            if(index==1){
                SceneXmlUtils.createJava(sysScene,"true",executeConfig);
            }else{
                SceneXmlUtils.createJava(sysScene,"",executeConfig);
            }
            index++;
        }

        Element listeners = suite.addElement("listeners");
        Element listener1 = listeners.addElement("listener");
        Element listener2 = listeners.addElement("listener");
        listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
        listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.info("生成xml文件失败。文件名【TestngReport.xml】");
        }
        xmlPath = xmlPath + "/TestReportXml/";
        mkdirs(xmlPath);
//        String ip = getAutomationEnvironmentName();
        String ip = executeConfig.getAutomationConfig().getEnvironment().getName();
        try (FileOutputStream fos = new FileOutputStream(xmlPath + ip +".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createExtentReportXml(SysScene sysScene) {
        String ip = getAutomationEnvironmentName();

        Document document = DocumentHelper.createDocument();
        Element suite = document.addElement("suite");
        suite.addAttribute("name", "Suite");
        suite.addAttribute("verbose", "1");
        suite.addAttribute("preserve-order", "true");
        suite.addAttribute("parallel", "tests");
        suite.addAttribute("thread-count", "10");

        Element suiteFiles = suite.addElement("suite-files");
        Element suiteFile = suiteFiles.addElement("suite-file");
        suiteFile.addAttribute("path", "src/test/java/"+sysScene.getProjectName()+"/"+sysScene.getVersionName()+"/TestReportXml/"+ip+".xml");

        Element listeners = suite.addElement("listeners");
        Element listener = listeners.addElement("listener");
        listener.addAttribute("class-name", "com.sakura.service.ExtentReportGenerateService");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.info("生成xml文件失败。文件名【ExtentReport.xml】");
        }
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/TestRunXml/ExtentReport.xml";
//        String projectPath = getProjectPath("Automation_ProjectPath")+"/TestRunXml/";
        String xmlPath = getProjectPath(sysScene) + "/TestRunXml/";
        mkdirs(xmlPath);
        try (FileOutputStream fos = new FileOutputStream(xmlPath  + ip+".xml")) {
//        try (FileOutputStream fos = new FileOutputStream(projectPath + fileName + ".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean createExtentReportXml(ExecuteConfig executeConfig) {
        Document document = null;
        Element suite = null;
        Element suiteFiles = null;
        String ip = executeConfig.getAutomationConfig().getEnvironment().getName();

        String xmlPath = executeConfig.getAutomationConfig().getProject().getPath();
        String abbreviate = convertToValidPackageName(executeConfig.getProjectConfig().getAbbreviate());
        String versionName = convertToValidPackageName(executeConfig.getProjectConfig().getVersion().getName());

        document = DocumentHelper.createDocument();
        suite = document.addElement("suite");
        suite.addAttribute("name", "Suite");
        suite.addAttribute("verbose", "1");
        suite.addAttribute("preserve-order", "true");
        suite.addAttribute("parallel", "tests");
        suite.addAttribute("thread-count", "10");
        suiteFiles = suite.addElement("suite-files");

        Element suiteFile = suiteFiles.addElement("suite-file");
        suiteFile.addAttribute("path", "src/test/java/"+abbreviate+"/"+versionName+"/TestReportXml/"+ip+".xml");

        Element listeners = suite.addElement("listeners");
        Element listener = listeners.addElement("listener");
        listener.addAttribute("class-name", "com.sakura.service.ExtentReportGenerateService");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.info("生成xml文件失败。文件名【ExtentReport.xml】");
        }
        xmlPath = xmlPath + "/TestRunXml/";
        mkdirs(xmlPath);
        try (FileOutputStream fos = new FileOutputStream(xmlPath  + ip+".xml")) {
            fos.write(out.toByteArray());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static Boolean createExtentReportXml(List<List<SysScene>> sysSceneList, ExecuteConfig executeConfig) {
//        Document document = null;
//        Element suite = null;
//        Element suiteFiles = null;
////        String ip = getAutomationEnvironmentName();
//        String ip = executeConfig.getAutomationConfig().getEnvironment().getName();
//        String xmlPath = "";
//        int index = 1;
//        sysSceneList.sort((x,y)->x.get(0).getVersionName().compareTo(y.get(0).getVersionName()));
//        for (List<SysScene> sceneList : sysSceneList) {
////            xmlPath = getProjectPath();
//            xmlPath = executeConfig.getAutomationConfig().getProject().getPath();
//            String projectName = convertToValidPackageName(sceneList.get(0).getProjectName());
//            String versionName = convertToValidPackageName(sceneList.get(0).getVersionName());
//
//            if (index == 1) {
//                document = DocumentHelper.createDocument();
//                suite = document.addElement("suite");
//                suite.addAttribute("name", "Suite");
//                suite.addAttribute("verbose", "1");
//                suite.addAttribute("preserve-order", "true");
//                suite.addAttribute("parallel", "tests");
//                suite.addAttribute("thread-count", "10");
//                suiteFiles = suite.addElement("suite-files");
//            }
//            Element suiteFile = suiteFiles.addElement("suite-file");
//            suiteFile.addAttribute("path", "src/test/java/"+projectName+"/"+versionName+"/TestReportXml/"+ip+".xml");
//            index++;
//        }
//        Element listeners = suite.addElement("listeners");
//        Element listener = listeners.addElement("listener");
//        listener.addAttribute("class-name", "com.sakura.service.ExtentReportGenerateService");
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        format.setEncoding("UTF-8");
//        try {
//            XMLWriter writer = new XMLWriter(out, format);
//            writer.write(document);
//            writer.close();
//        } catch (Exception e) {
//            log.info("生成xml文件失败。文件名【ExtentReport.xml】");
//        }
//        xmlPath = xmlPath + "/TestRunXml/";
//        mkdirs(xmlPath);
//        try (FileOutputStream fos = new FileOutputStream(xmlPath  + ip+".xml")) {
//            fos.write(out.toByteArray());
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * 删除xml
     */
    public static void deleteXml(SysScene sysScene) {
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestCaseXml/test.xml";
        String testCasePath = getProjectPath(sysScene) + "/TestCases/";
        String testCaseXmlPath = getProjectPath(sysScene) + "/TestCaseXml/";
        String fileName = sysScene.getSceneId();
        try {
            Files.delete(Paths.get(testCasePath + fileName + ".java"));
            Files.delete(Paths.get(testCaseXmlPath + fileName + ".xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成xml，通过浏览器下载
     */
    public static void exportXml(SysScene sysScene, HttpServletRequest request, HttpServletResponse response) {
        try {
            //转Unicode不然ie会乱码
            String fileName = sysScene.getSceneId();
//            String projectPath = getProjectPath("Automation_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
            String projectPath = getProjectPath(sysScene) + "/TestCaseXml/";
            mkdirs(projectPath);
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //File file = new File("D:\\Sakura\\Sakura.Automation.Platform\\automation\\Sakura.Web.UI.Automation.Test\\src\\test\\java\\AAS_DBSG\\V6_1B01_POC\\TestCaseXml\\1.xml");
            String xmlFile = projectPath + fileName + ".xml";
            File file = new File(xmlFile);
            if (!file.exists()) {
                response.sendError(404, "File not found!");
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            long fileLength = file.length();
            response.setHeader("Content-Length", String.valueOf(fileLength));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加多个xml生成zip，并导出
     */
    public static void exportXml(Map<String, String> xmlfiles, String zipfilePath, String zipfileName) throws Exception {
        //创建一个临时压缩文件
        String zipFile = zipfilePath + zipfileName;
        File zipfile = new File(zipFile);
        if (!zipfile.exists()) {
            zipfile.createNewFile();
        }
        //创建文件输出流
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        xmlfiles.forEach((key, value) -> {
            try {
                String fileName = URLEncoder.encode(key, "UTF-8");
                String xmlFile = value + fileName + ".xml";
                File file = new File(xmlFile);
                if (!file.exists()) {
                    return;
                }
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                ZipEntry entry = new ZipEntry(key + ".xml");
                zipOut.putNextEntry(entry);
                // 向压缩文件中输出数据
                int nNumber;
                byte[] buffer = new byte[8192];
                while ((nNumber = bis.read(buffer)) != -1) {
                    zipOut.write(buffer, 0, nNumber);
                }
                // 关闭创建的流对象
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        zipOut.close();
//        exportZip(zipFile,response);
    }

    /**
     * 导出zip，通过浏览器下载
     */
    public static void exportZip(String zipFile, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=TestCaseXml.zip");
            File file = new File(zipFile);
            if (!file.exists()) {
                response.sendError(404, "File not found!");
            }
            response.setHeader("Content-Length", String.valueOf(file.length()));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 1024 * 10);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024 * 10];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadZip(HttpServletResponse response, String zipName, String zipFile) {
        //设置文件路径
        File filePath = new File(zipFile);
        if (filePath.exists()) {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
//                response.setContentType("application/x-download");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-disposition", "attachment; filename=" + new String(zipName.getBytes("utf-8"), "ISO8859-1"));
                byte[] buffer = new byte[4096];
                fis = new FileInputStream(filePath);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                        // 删除临时文件
                        filePath.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 打包下载文件
     *
     * @param response
     * @param zipName
     * @param zipFile
     */
    public static void downFile(HttpServletResponse response, HttpServletRequest request, String zipName, String zipFile) throws IOException {
        //进行浏览器下载
        //获得浏览器代理信息
        final String userAgent = request.getHeader("USER-AGENT");
        //判断浏览器代理并分别设置响应给浏览器的编码格式
        String finalFileName = null;
        if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Trident")) {
            //IE浏览器
            finalFileName = URLEncoder.encode(zipName, "UTF8");
            log.info("IE浏览器");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {
            //google,火狐浏览器
            finalFileName = new String(zipName.getBytes(), "ISO8859-1");
        } else {
            finalFileName = URLEncoder.encode(zipName, "UTF8");//其他浏览器
        }
        response.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.setHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");//下载文件的名称
        ServletOutputStream servletOutputStream = response.getOutputStream();
        DataOutputStream temps = new DataOutputStream(servletOutputStream);
        DataInputStream in = new DataInputStream(new FileInputStream(zipFile));//浏览器下载文件的路径
        byte[] b = new byte[2048];
        File reportZip = new File(zipFile);//之后用来删除临时压缩文件
        try {
            while ((in.read(b)) != -1) {
                temps.write(b);
            }
            temps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (temps != null) {
                temps.close();
            }
            if (in != null) {
                in.close();
            }
            if (reportZip != null) {
                reportZip.delete();//删除服务器本地产生的临时压缩文件
            }
            servletOutputStream.close();
        }
    }

//    public static String getProjectPath(String projectPath) {
//        String path = sceneXmlUtils.sysConfigurationMapper.getPath(projectPath);
//        return path + "/";
//    }
//
//    public static String getProjectPath(String projectPath, String projectId) {
//        String path = sceneXmlUtils.sysConfigurationMapper.getPath(projectPath);
//        SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(projectId);
//        String description = sysProject.getDescription();
//        return path + "/" + description + "/";
//    }

    public static String getProjectPath() {
        try {
            List<AutomationConfig> automationConfigList = sceneXmlUtils.automationConfigMapper.findList(new AutomationConfig());
            for (AutomationConfig automationConfig : automationConfigList) {
                if("web".equals(automationConfig.getType()) && automationConfig.getStatus() == 1){
                    List<YamlConfig.Automation.Project> projectList = JSON.parseArray(automationConfig.getProjectConfig(), YamlConfig.Automation.Project.class);
                    ArrayList<YamlConfig.Automation.Project> projects = new ArrayList<>();
                    for (YamlConfig.Automation.Project project : projectList) {
                        if(project.getStatus() == 1){
                            // /data/sakura/Sakura.Automation.Platform/automation/Sakura.Web.UI.Automation.Test/src/test/java/
                            return project.getPath();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getProjectPath(SysScene sysScene) {
        try {
            List<AutomationConfig> automationConfigList = sceneXmlUtils.automationConfigMapper.findList(new AutomationConfig());
            for (AutomationConfig automationConfig : automationConfigList) {
                if("web".equals(automationConfig.getType()) && automationConfig.getStatus() == 1){
                    List<YamlConfig.Automation.Project> projectList = JSON.parseArray(automationConfig.getProjectConfig(), YamlConfig.Automation.Project.class);
                    ArrayList<YamlConfig.Automation.Project> projects = new ArrayList<>();
                    for (YamlConfig.Automation.Project project : projectList) {
                        if(project.getStatus() == 1){
                            // /data/sakura/Sakura.Automation.Platform/automation/Sakura.Web.UI.Automation.Test/src/test/java/AAS/V6_5B03
                            return project.getPath() + "/"+convertToValidPackageName(sysScene.getProjectName())+"/"+convertToValidPackageName(sysScene.getVersionName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BizException(SysErrorCode.B_PROJECT_Automation_ProjectNotExistent);
    }

    public static String getProjectEnvironmentServerIP(String projectId) {
        try {
            List<EnvironmentConfig> environmentConfigList = sceneXmlUtils.environmentConfigMapper.findList(new EnvironmentConfig());
            for (EnvironmentConfig environmentConfig : environmentConfigList) {
                if(projectId.equals(environmentConfig.getProjectId()) && environmentConfig.getStatus() == 1){
                    List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(environmentConfig.getServerConfig(), YamlConfig.Project.Environment.Server.class);
                    for (YamlConfig.Project.Environment.Server server : serverList) {
                        if("root".equals(server.getUserName()) && server.getStatus() == 1){
                            return server.getHost();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BizException(SysErrorCode.B_PROJECT_Environment_ServerNotExistent);
    }

    public static YamlConfig.Automation.Jenkins getAutomationJenkins() {
        try {
            List<AutomationConfig> automationConfigList = sceneXmlUtils.automationConfigMapper.findList(new AutomationConfig());
            for (AutomationConfig automationConfig : automationConfigList) {
                if("web".equals(automationConfig.getType()) && automationConfig.getStatus() == 1){
                    List<YamlConfig.Automation.Jenkins> jenkinsList = JSON.parseArray(automationConfig.getJenkinsConfig(), YamlConfig.Automation.Jenkins.class);
                    for (YamlConfig.Automation.Jenkins jenkins : jenkinsList) {
                        if(jenkins.getStatus() == 1){
                            return jenkins;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new BizException(SysErrorCode.B_PROJECT_Automation_JenkinsNotExistent);
    }

    public static String getAutomationEnvironmentName() {
        try {
            List<AutomationConfig> automationConfigList = sceneXmlUtils.automationConfigMapper.findList(new AutomationConfig());
            String name = SecurityUtils.getLoginUser().getUser().getName();
            for (AutomationConfig automationConfig : automationConfigList) {
                if ("web".equals(automationConfig.getType()) && automationConfig.getStatus() == 1) {
                    List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(automationConfig.getEnvironmentConfig(), YamlConfig.Automation.Environment.class);
                    for (YamlConfig.Automation.Environment environment : environmentList) {
                        if (environment.getDescription().equals(name) && environment.getStatus() == 1) {
                            return environment.getName();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new BizException(SysErrorCode.B_PROJECT_Automation_EnvironmentNotExistent);
    }

    /**
     * 如果目录不存在就创建目录
     */
    public static void mkdirs(String path) {
        File directories = new File(path);
        if (!directories.exists()) {
            if (directories.mkdirs()) {
                log.info("目录不存在，创建多级目录成功:{}",path);
            } else {
                log.info("目录不存在，创建多级目录失败:{}",path);
            }
        }
    }

    public static String convertToValidPackageName(String input) {
        // 定义正则表达式匹配非法字符
        String pattern = "[^a-zA-Z0-9_]";
        // 定义替换规则
        String replacement = "_";
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        // 将非法字符替换为下划线
        String packageName = matcher.replaceAll(replacement);
        // 如果包名以数字开头，则在前面加上下划线
        if (Character.isDigit(packageName.charAt(0))) {
            packageName = "_" + packageName;
        }
        log.info(packageName);
        return packageName;
    }

    public static Map<Object,Object> execSysScene(ExecuteConfig executeConfig){
        String product = executeConfig.getProjectConfig().getName();
        String abbreviate = executeConfig.getProjectConfig().getAbbreviate();
        String versionName = executeConfig.getProjectConfig().getVersion().getName();
        String versionDescription = executeConfig.getProjectConfig().getVersion().getDescription();
        String name = executeConfig.getExecuteName();
        String date = DateUtils.getDate();
        String ip = executeConfig.getProjectConfig().getServer().getHost();
        String environmentDescription = executeConfig.getProjectConfig().getServer().getDescription();
        String passWord = executeConfig.getProjectConfig().getServer().getPassWord();
        String dataBasePort = String.valueOf(executeConfig.getProjectConfig().getDataBases().getPort());
        String domain = executeConfig.getProjectConfig().getServer().getDomain();
        String port = "";
        Optional<Object> optionalValue = executeConfig.getProjectConfig().getServer().getConfigList().stream()
                .filter(config -> config.getParamsName().equals("前端端口"))
                .findFirst()
                .map(Config::getParamsValue)
                .map(Object::toString);
        if (optionalValue.isPresent()) {
            port = optionalValue.get().toString();
        }
        String run = executeConfig.getAutomationConfig().getEnvironment().getName();
        String testReportId = executeConfig.getTestReport().getId();
        String testPlanId = executeConfig.getTestPlanId();

        Map<String, String> params = new LinkedHashMap<>(11);
        params.put("Date", date);
        params.put("Name",name);
        String email = executeConfig.getExecuteEmail();
        params.put("Email",email);
//        params.put("Email",SecurityUtils.getLoginUser().getUser().getEmail());
        params.put("Product",product);
        params.put("Abbreviate",abbreviate);
        params.put("Version",versionName);
        params.put("Description",versionDescription);
        params.put("IP",ip);
        params.put("EDescription",environmentDescription);
        params.put("PassWord",passWord);
        params.put("DataBasePort",dataBasePort);
        params.put("Domain",domain);
        params.put("Port",port);
        params.put("Run",run);
        params.put("Branch","test");
        params.put("testPlanId", testPlanId);
        params.put("testReportId", testReportId);

        String url = executeConfig.getAutomationConfig().getJenkins().getUrl();
        String userName = executeConfig.getAutomationConfig().getJenkins().getUserName();
        String J_passWord = executeConfig.getAutomationConfig().getJenkins().getPassWord();
        String jobName = executeConfig.getAutomationConfig().getJenkins().getJob();
        Integer buildNumber = null;
        try {
            buildNumber = JenkinsService.launchJob(url,userName,J_passWord,jobName, params);
            JenkinsService.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Map<Object,Object> results = new HashMap<>(3);
        if(buildNumber!=null){
            results.put("testReportId",testReportId);
            results.put("buildNumber",buildNumber);
            results.put("consoleUrl",url+"/job/"+jobName+"/"+buildNumber+"/console");
            results.put("testReportUrl",url+"/job/"+jobName+"/"+buildNumber+"/artifact/TestOutput/ExtentReport/"+date+"/"+abbreviate+"/"+versionName+"/index.html");
        }
        log.info(String.valueOf(results));
        return results;
    }

//    public static Map<Object,Object> execSysScene1(String projectName,String version,String projectId){
////        String URL = sceneXmlUtils.sysConfigurationMapper.getPath("Jenkins_URL");
////        String Name = SecurityUtils.getLoginUser().getUser().getName();
////        String Email = SecurityUtils.getLoginUser().getUser().getEmail();
////        String Product = projectName;
////        String Version = version;
////        String IP = ip;
////        String Branche = "master";
////        String Run = sceneXmlUtils.sysConfigurationMapper.getPath(Name);
////        String cmd = "curl -X GET \""+URL+"/buildWithParameters?token=sakura&Name="+Name+"&Email="+Email+"&Product="+Product+"&Version="+Version+"&IP="+IP+"&Branche="+Branche+"&Run="+Run+"\" --user liuzhi:lz612425";
////        Integer result = CommandUtil.runCmd(cmd, 12, TimeUnit.SECONDS);
////        log.info(String.valueOf(result));
//
////        String url = sceneXmlUtils.sysConfigurationMapper.getPath("Jenkins_URL");
////        String userName = sceneXmlUtils.sysConfigurationMapper.getPath("Jenkins_UserName");
////        String passWord = sceneXmlUtils.sysConfigurationMapper.getPath("Jenkins_PassWord");
////        String jobName = sceneXmlUtils.sysConfigurationMapper.getPath("Automation_ProjectName");
//        YamlConfig.Automation.Jenkins jenkins = getAutomationJenkins();
//        String url = jenkins.getUrl();
//        String userName = jenkins.getUserName();
//        String passWord = jenkins.getPassWord();
//        String jobName = jenkins.getJob();
//        String name = SecurityUtils.getLoginUser().getUser().getName();
//        String date = DateUtils.getDate();
//        Map<String, String> params = new LinkedHashMap<>(8);
//        params.put("Date", date);
//        params.put("Name",name);
//        params.put("Email",SecurityUtils.getLoginUser().getUser().getEmail());
//        params.put("Product",projectName);
//        params.put("Version",version);
//        params.put("IP",getProjectEnvironmentServerIP(projectId));
//        params.put("Branch","master");
//        params.put("Run",getAutomationEnvironmentName());
//        Map<Object,Object> results = new HashMap<>(3);
//        Integer buildNumber = null;
//        boolean buildResult = false;
//        String BuildDesc = "";
//        try {
//            buildNumber = JenkinsService.launchJob(url,userName,passWord,jobName, params);
////            buildResult = JenkinsService.getBuildResult(jobName, buildNumber);
////            BuildDesc = JenkinsService.getBuildDesc(jobName, buildNumber);
//            JenkinsService.close();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        if(buildNumber!=null){
//            results.put("buildNumber",buildNumber);
//            results.put("consoleUrl",url+"/job/"+jobName+"/"+buildNumber+"/console");
//            results.put("testReportUrl",url+"/job/"+jobName+"/"+buildNumber+"/artifact/TestOutput/ExtentReport/"+date+"/"+projectName+"/"+version+"/index.html");
////        results.put("buildResult",buildResult);
////        results.put("BuildDesc",BuildDesc);
//        }
//
//        log.info(String.valueOf(results));
//        return results;
//    }

//    public static boolean execSysScene1(){
//        String ip = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_IP");
//        int port = Integer.parseInt(sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_Port"));
//        String userName = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_UserName");
//        String passWord = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_PassWord");
//        String url = sceneXmlUtils.sysConfigurationMapper.getPath("Automation_ProjectURL");
//        String ant = " && run.bat";
//
//        String localPath = getProjectPath("Automation_ProjectPath");
//        String remotePath = "/Sakura.Web.UI.Automation.Test";
//        FreeSftpUtil ftp = new FreeSftpUtil();
//        try {
//            FreeSshUtil.cmd(ip, userName, passWord, "git clone " + url);
//            ftp.connect(ip, port, userName, passWord);
//            ftp.uploadDirectory(localPath, remotePath, "/src/test/java");
//            ftp.listFiles(remotePath);
//            FreeSshUtil.cmd(ip, userName, passWord, "cd "+ remotePath + ant);
//        } catch (Exception e) {
//            log.error("", e);
//            return false;
//        }finally {
//            ftp.disconnect();
//        }
//        return true;
//    }
//
//    public static void execSysScene(SysScene sysScene, HttpServletRequest request, HttpServletResponse response) throws SftpException, UnsupportedEncodingException {
//        String ip = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_IP");
//        int port = Integer.parseInt(sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_Port"));
//        String userName = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_UserName");
//        String passWord = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_PassWord");
//        String url = sceneXmlUtils.sysConfigurationMapper.getPath("Automation_ProjectURL");
//        FreeSshUtil.cmd(ip, userName, passWord, url);
//
//        String projectPath = getProjectPath("Automation_ProjectPath", sysScene.getProjectId());
//        String localPath = projectPath + sysScene.getVersionName() + "/TestCaseXml/";
//        String fileName = sysScene.getSceneId();
//        fileName = URLEncoder.encode(fileName, "UTF-8") + ".xml";
//
//        String remotePath = "/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/";
//        FreeSftpUtil ftp = new FreeSftpUtil();
//        try {
//            ftp.connect(ip, port, userName, passWord);
//            ftp.uploadFile(localPath, fileName, remotePath, fileName, false);
//            ftp.listFiles(remotePath);
//        } catch (Exception e) {
//            log.error("", e);
////            System.exit(0);
//        }finally {
//            ftp.disconnect();
//        }
//    }

    public static void main(String[] args) {
//        createJava(null,"true");
//        createTestngReportXml(null);
        convertToValidPackageName("1.2.3-dasd");
    }
}