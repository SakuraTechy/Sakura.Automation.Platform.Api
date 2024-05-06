package com.sakura.common.utils.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToJsonConverter {
    public static void main(String[] args) {
        try {
            // 定义XML字符串
            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><slave>\n" +
                    "  <name>172.19.5.230</name>\n" +
                    "  <description>172.19.5.230刘智</description>\n" +
                    "  <remoteFS>D:\\Jenkins</remoteFS>\n" +
                    "  <numExecutors>1</numExecutors>\n" +
                    "  <mode>EXCLUSIVE</mode>\n" +
                    "  <retentionStrategy class=\"hudson.slaves.RetentionStrategy$Always\"/>\n" +
                    "  <launcher class=\"hudson.slaves.JNLPLauncher\">\n" +
                    "    <workDirSettings>\n" +
                    "      <disabled>false</disabled>\n" +
                    "      <workDirPath>D:\\Jenkins</workDirPath>\n" +
                    "      <internalDir>remoting</internalDir>\n" +
                    "      <failIfWorkDirIsMissing>false</failIfWorkDirIsMissing>\n" +
                    "    </workDirSettings>\n" +
                    "    <webSocket>true</webSocket>\n" +
                    "  </launcher>\n" +
                    "  <label>172.19.5.230刘智</label>\n" +
                    "  <nodeProperties>\n" +
                    "    <hudson.tools.ToolLocationNodeProperty>\n" +
                    "      <locations>\n" +
                    "        <hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "          <type>hudson.model.JDK$DescriptorImpl</type>\n" +
                    "          <name>JDK</name>\n" +
                    "          <home>C:\\Program Files\\Java\\jdk1.8.0_112</home>\n" +
                    "        </hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "        <hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "          <type>hudson.plugins.git.GitTool$DescriptorImpl</type>\n" +
                    "          <name>Git</name>\n" +
                    "          <home>D:\\Program\\Git\\cmd\\git.exe</home>\n" +
                    "        </hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "        <hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "          <type>hudson.tasks.Maven$MavenInstallation$DescriptorImpl</type>\n" +
                    "          <name>Maven</name>\n" +
                    "          <home>D:\\Program\\Maven</home>\n" +
                    "        </hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "        <hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "          <type>hudson.tasks.Ant$AntInstallation$DescriptorImpl</type>\n" +
                    "          <name>Ant</name>\n" +
                    "          <home>D:\\Program\\Ant\\org.apache.ant_1.9.6.v201510161327</home>\n" +
                    "        </hudson.tools.ToolLocationNodeProperty_-ToolLocation>\n" +
                    "      </locations>\n" +
                    "    </hudson.tools.ToolLocationNodeProperty>\n" +
                    "    <hudson.slaves.EnvironmentVariablesNodeProperty>\n" +
                    "      <envVars serialization=\"custom\">\n" +
                    "        <unserializable-parents/>\n" +
                    "        <tree-map>\n" +
                    "          <default>\n" +
                    "            <comparator class=\"java.lang.String$CaseInsensitiveComparator\"/>\n" +
                    "          </default>\n" +
                    "          <int>1</int>\n" +
                    "          <string>LANG</string>\n" +
                    "          <string>zh_CN.UTF-8</string>\n" +
                    "        </tree-map>\n" +
                    "      </envVars>\n" +
                    "    </hudson.slaves.EnvironmentVariablesNodeProperty>\n" +
                    "  </nodeProperties>\n" +
                    "</slave>";

            // 创建XmlMapper对象
            XmlMapper xmlMapper = new XmlMapper();

            // 解析XML并转换为Java对象
            Object xmlObject = xmlMapper.readValue(xmlString, Object.class);

            // 创建ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 将Java对象转换为JSON字符串
            String jsonString = objectMapper.writeValueAsString(xmlObject);

            // 打印转换后的JSON字符串
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
