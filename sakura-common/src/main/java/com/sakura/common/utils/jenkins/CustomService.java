package com.sakura.common.utils.jenkins;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.client.util.EncodingUtils;
import java.io.IOException;

public class CustomService {

    // Jenkins 对象
    private JenkinsServer jenkinsServer;
    // http 客户端对象
    private JenkinsHttpClient jenkinsHttpClient;

    /**
     * 构造方法中调用连接 Jenkins 方法
     */
    CustomService() {
        // 连接 Jenkins
        jenkinsServer = JenkinsConnect.connection();
        // 设置客户端连接 Jenkins
        jenkinsHttpClient = JenkinsConnect.getClient();
    }

    /**
     * 创建 Jenkins Job 并指定 Job 类型
     * 关于 Jenkins Job 部分类型，如下：
     *   - 自由风格项目：hudson.model.FreeStyleProject
     *   - Maven 项目：hudson.maven.MavenModuleSet
     *   - 流水线项目：org.jenkinsci.plugins.workflow.job.WorkflowJob
     *   - 多配置项目：hudson.matrix.MatrixProject
     */
    public void createJob() {
        try {
            // job 名称
            String jobName = "test-project";
            // 创建 Job 的 xml，可以在 jenkins 中查看，例如 http://jenkins.mydlq.club/job/{job名称}/config.xml 来查看该 job 的 xml 配置
            String jobXml = "<project>\n" +
                    "<keepDependencies>false</keepDependencies>\n" +
                    "<properties/>\n" +
                    "<scm class=\"hudson.scm.NullSCM\"/>\n" +
                    "<canRoam>false</canRoam>\n" +
                    "<disabled>false</disabled>\n" +
                    "<blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
                    "<blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
                    "<triggers/>\n" +
                    "<concurrentBuild>false</concurrentBuild>\n" +
                    "<builders/>\n" +
                    "<publishers/>\n" +
                    "<buildWrappers/>\n" +
                    "<link type=\"text/css\" id=\"dark-mode\" rel=\"stylesheet\" href=\"\"/>\n" +
                    "</project>";
            // 创建 Jenkins Job 并指定 Job 类型
            jenkinsHttpClient.post_xml("createItem?name=" + EncodingUtils.encodeParam(jobName) +
                    "&mode=hudson.model.FreeStyleProject", jobXml, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 自定义服务
        CustomService customService = new CustomService();
        // 创建指定类型的 Job
        customService.createJob();
    }
}