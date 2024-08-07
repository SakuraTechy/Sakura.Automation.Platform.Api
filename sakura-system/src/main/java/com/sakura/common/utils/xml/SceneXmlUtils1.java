package com.sakura.common.utils.xml;

import com.sakura.common.utils.ssh.FreeSftpUtil;
import com.sakura.common.utils.ssh.FreeSshUtil;
import com.sakura.common.utils.StringUtils;
import com.sakura.system.domain.*;
import com.sakura.system.mapper.SysConfigurationMapper;
import com.sakura.system.mapper.SysProjectMapper;
import com.jcraft.jsch.SftpException;
import com.squareup.javapoet.*;
import lombok.Data;
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
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wutun
 */
@Data
@Component
public class SceneXmlUtils1 {
    static Logger log = LoggerFactory.getLogger(SceneXmlUtils1.class);

    @Autowired
    private SysConfigurationMapper sysConfigurationMapper;

    @Autowired
    private SysProjectMapper sysProjectMapper;

    public static SceneXmlUtils1 sceneXmlUtils;

    @PostConstruct
    public void init() {
        sceneXmlUtils = this;
        sceneXmlUtils.sysConfigurationMapper = this.sysConfigurationMapper;
        sceneXmlUtils.sysProjectMapper = this.sysProjectMapper;
    }

    public static void createXml(SysScene sysScene) {
        List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
        //生成的xml文件名称
        String fileName = sysScene.getSceneId();
        Document document = DocumentHelper.createDocument();
        Element unit = document.addElement("unit");
        unit.addAttribute("id", sysScene.getSceneId());
        unit.addAttribute("name", sysScene.getName());
        unit.addAttribute("version", sysScene.getVersionName());
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
                        for (Field f : step.getClass().getDeclaredFields()) {
                            f.setAccessible(true);
                            try {
                                if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && !f.getName().equals("config") && !f.getName().equals("pid") && !f.getName().equals("order") && !f.getName().equals("id")) {
                                    stepElement.addAttribute(f.getName(), f.get(step).toString());
                                } else if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && f.getName().equals("config")) {
                                    List<Config> configList = (List<Config>) f.get(step);
                                    for (Config config : configList) {
                                        stepElement.addAttribute(config.getParamsName(), config.getParamsValue());
                                    }
                                } else if (f.get(step) != null && StringUtils.isNotBlank(f.get(step).toString()) && f.getName().equals("order")) {
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
        String projectPath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
        mkdirs(projectPath);
        try (FileOutputStream fos = new FileOutputStream(projectPath + fileName + ".xml")) {
            fos.write(out.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createJava(SysScene sysScene, String state) {
        SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(sysScene.getProjectId());
        String description = sysProject.getDescription();
        try {
            ClassName logger = ClassName.get("org.apache.log4j", "Logger");
            ClassName afterTest = ClassName.get("org.testng.annotations", "AfterTest");
            ClassName beforeTest = ClassName.get("org.testng.annotations", "BeforeTest");
            ClassName parameters = ClassName.get("org.testng.annotations", "Parameters");
            ClassName test = ClassName.get("org.testng.annotations", "Test");

            ClassName testUnit = ClassName.get("com.sakura.base", "TestUnit");
            ClassName runUnitService = ClassName.get("com.sakura.service", "RunUnitService");
            ClassName webXmlParseService = ClassName.get("com.sakura.service", "WebXmlParseService");

            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(sysScene.getSceneId())
                    .addModifiers(Modifier.PUBLIC);

            FieldSpec logSpec = FieldSpec.builder(logger, "log")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .initializer("$T.getLogger(" + sysScene.getSceneId() + ".class)", logger)
                    .build();

            FieldSpec testUnitSpec = FieldSpec.builder(testUnit, "testUnit")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();
            FieldSpec webXmlParseServiceSpec = FieldSpec.builder(webXmlParseService, "webXmlParseService")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();
            FieldSpec runServiceSpec = FieldSpec.builder(runUnitService, "runService")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();

            AnnotationSpec parametersAnnotationSpec = AnnotationSpec.builder(parameters)
                    .addMember("value", "{\"browser\",\"profile\"}")
//                    .addMember("value", "$T.$L", ElementType.class, ElementType.METHOD)
                    .build();
            AnnotationSpec beforeTestAnnotationSpec = AnnotationSpec.builder(beforeTest)
                    .build();
            MethodSpec setupSpec = MethodSpec.methodBuilder("setup")
                    .addAnnotation(parametersAnnotationSpec)
                    .addAnnotation(beforeTestAnnotationSpec)
                    .addModifiers(Modifier.PRIVATE)
                    .addParameter(String.class, "browserName")
                    .addParameter(Boolean.class, "profile")
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
            List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
            if (sysSceneCases != null && sysSceneCases.size() != 0) {
                sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
                for (SysSceneCase sysSceneCase : sysSceneCases) {
                    AnnotationSpec testAnnotationSpec = AnnotationSpec.builder(test)
                            .addMember("groups", "{$S}", sysSceneCase.getId())
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
                    .addStatement("runService.setUnit(" + state + ")")
                    .addStatement("runService.closeBrowser()")
                    .build();
            classBuilder.addMethod(tearDownSpec);
            TypeSpec mainActivity = classBuilder.build();
            JavaFile javaFile = JavaFile.builder("" + description + "." + sysScene.getVersionName() + ".TestCases", mainActivity)
                    .skipJavaLangImports(true)
                    .build();

//            String path = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestCases/AAS_DBSG_SMOKE_001.java";
            String path = getProjectPath("Remote_ProjectPath");
            File file = new File(path);
            javaFile.writeTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTestngReportXml(List<SysScene> sysSceneList) {
        Document document = null;
        Element suite = null;
        String projectId = "";
        String versionName = "";
        String projectPath = "";
        String description = "";

        int index = 1;
        for (SysScene sysScene : sysSceneList) {
            if (index == 1) {
                projectId = sysScene.getProjectId();
                versionName = sysScene.getVersionName();
                projectPath = getProjectPath("Remote_ProjectPath", projectId) + versionName + "/TestReportXml/";

                SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(sysScene.getProjectId());
                String projectName = sysProject.getProjectName();
                description = sysProject.getDescription();

                document = DocumentHelper.createDocument();
                suite = document.addElement("suite");
                suite.addAttribute("name", projectName);
                suite.addAttribute("configfailurepolicy", "continue");
                suite.addAttribute("parallel", "tests");
                suite.addAttribute("thread-count", "1");

                Element listeners = suite.addElement("listeners");
                Element listener1 = listeners.addElement("listener");
                Element listener2 = listeners.addElement("listener");
                listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
                listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");

                index++;
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
            cClass.addAttribute("name", description + "." + versionName + ".TestCases." + sysScene.getSceneId());

            Element methods = null;
            Element groups = test.addElement("groups");
            Element dependencies = groups.addElement("dependencies");
            List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
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
        }
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
        mkdirs(projectPath);
        try (FileOutputStream fos = new FileOutputStream(projectPath + "TestngReport.xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTestngReportXml(SysScene sysScene) {
        String fileName = "TestngReport";
        SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(sysScene.getProjectId());
        String projectName = sysProject.getProjectName();
        String description = sysProject.getDescription();

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

        Element parameter2 = test.addElement("parameter");
        parameter2.addAttribute("name", "profile");
        parameter2.addAttribute("value", "false");

        Element classes = test.addElement("classes");
        Element cClass = classes.addElement("class");
        cClass.addAttribute("name", description + "." + sysScene.getVersionName() + ".TestCases." + sysScene.getSceneId());

        Element methods = null;
        Element groups = test.addElement("groups");
        Element dependencies = groups.addElement("dependencies");
        List<SysSceneCase> sysSceneCases = sysScene.getCaseList();
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
            log.info("生成xml文件失败。文件名【" + fileName + "】");
        }
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestReportXml/TestngReport.xml";
        String projectPath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestReportXml/";
        mkdirs(projectPath);
        try (FileOutputStream fos = new FileOutputStream(projectPath + fileName + ".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createExtentReportXml(SysScene sysScene) {
        String fileName = "ExtentReport";
        SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(sysScene.getProjectId());
        String description = sysProject.getDescription();

        Document document = DocumentHelper.createDocument();
        Element suite = document.addElement("suite");
        suite.addAttribute("name", "Suite");
        suite.addAttribute("verbose", "1");
        suite.addAttribute("preserve-order", "true");
        suite.addAttribute("parallel", "tests");
        suite.addAttribute("thread-count", "10");

        Element suiteFiles = suite.addElement("suite-files");
        Element suiteFile = suiteFiles.addElement("suite-file");
        suiteFile.addAttribute("path", "src/test/java/" + description + "/" + sysScene.getVersionName() + "/TestReportXml/TestngReport.xml");

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
            log.info("生成xml文件失败。文件名【" + fileName + "】");
        }
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/TestRunXml/ExtentReport.xml";
        String projectPath = getProjectPath("Remote_ProjectPath") + "/TestRunXml/";
        mkdirs(projectPath);
        try (FileOutputStream fos = new FileOutputStream(projectPath + fileName + ".xml")) {
            fos.write(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果目录不存在就创建目录
     */
    public static void mkdirs(String path) {
        File directories = new File(path);
        if (!directories.exists()) {
            if (directories.mkdirs()) {
                log.info("目录不存在，创建多级目录成功");
            } else {
                log.info("目录不存在，创建多级目录失败");
            }
        }
    }

    /**
     * 删除xml
     */
    public static void deleteXml(SysScene sysScene) {
//        String projectPath = "D:/King/Eclipse/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_2B01_POC/TestCaseXml/test.xml";
        String testCasePath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCases/";
        String testCaseXmlPath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
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
            String projectPath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
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
            System.out.println("IE浏览器");
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

    public static String getProjectPath(String projectPath) {
        String path = sceneXmlUtils.sysConfigurationMapper.getPath(projectPath);
        return path + "/";
    }

    public static String getProjectPath(String projectPath, String projectId) {
        String path = sceneXmlUtils.sysConfigurationMapper.getPath(projectPath);
        SysProject sysProject = sceneXmlUtils.sysProjectMapper.get(projectId);
        String description = sysProject.getDescription();
        return path + "/" + description + "/";
    }

    /**
     * 执行测试场景
     */
    public static void execSysScene(SysScene sysScene, HttpServletRequest request, HttpServletResponse response) throws SftpException, UnsupportedEncodingException {


        String ip = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_IP");
        int port = Integer.parseInt(sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_Port"));
        String userName = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_UserName");
        String passWord = sceneXmlUtils.sysConfigurationMapper.getPath("FreeSSHd_PassWord");
        String url = sceneXmlUtils.sysConfigurationMapper.getPath("Automation_Project_URL");
        FreeSshUtil.cmd(ip, userName, passWord, url);

        String projectPath = getProjectPath("Remote_ProjectPath", sysScene.getProjectId());
        String localPath = projectPath + sysScene.getVersionName() + "/TestCaseXml/";
        String fileName = sysScene.getSceneId();
        fileName = URLEncoder.encode(fileName, "UTF-8") + ".xml";

        String remotePath = "/Sakura.Web.UI.Automation.Test/src/test/java/AAS_DBSG/V6_1B01_POC/TestCaseXml/";
        FreeSftpUtil ftp = new FreeSftpUtil();
        try {
            ftp.connect(ip, port, userName, passWord);
            ftp.uploadFile(localPath, fileName, remotePath, fileName, false);
            ftp.listFiles(remotePath);
        } catch (Exception e) {
            log.error("", e);
//            System.exit(0);
        } finally {
            ftp.disconnect();
        }
    }

    public static void main(String[] args) {
//        createJava(null,"true");
//        createTestngReportXml();
    }
}