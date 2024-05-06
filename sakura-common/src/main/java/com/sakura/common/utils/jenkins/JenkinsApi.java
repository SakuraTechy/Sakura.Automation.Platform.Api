package com.sakura.common.utils.jenkins;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import java.util.List;
import java.util.Map;

/**
 * 获取 Jenkins 相关信息
 *
 * 例如获取插件信息、获取Label信息、关闭Jenkins等
 */
public class JenkinsApi {

    // Jenkins 对象
    private JenkinsServer jenkinsServer;

    /**
     * 构造方法中调用连接 Jenkins 方法
     */
    JenkinsApi(){
        jenkinsServer = JenkinsConnect.connection();
    }

    public void  getComputerNames() throws IOException {
        Map<String, Computer> map1= jenkinsServer.getComputers();
        for( Computer c : map1.values()) {
            System.out.println(c.getDisplayName());
            for( Computer d : c.getComputers() ) {
                System.out.println(d.getDisplayName());
            }
        }
    }
    /**
     * 获取主机信息
     */
    public void getComputerInfo() {
        try {
            ComputerSet computerSet = jenkinsServer.getComputerSet();
            List<ComputerWithDetails> d = computerSet.getComputers();
            System.out.println("---"+d.get(0).getComputers());
//            for (Map.Entry<String, Computer> entry : computers.entrySet()) {
//                String computerName = entry.getKey();
//                Computer computer = entry.getValue();
//
//                System.out.println("Computer Name: " + computerName);
//                System.out.println("Offline: " + computer.isOffline());
//                // Access other properties of the computer object as needed
//
//                System.out.println("---");
//            }
//            Map<String, Computer> map = jenkinsServer.getComputers();
//            System.out.println("map1111111111111:"+ map);
//
//            for (Computer computer : map.values()) {
//                System.out.println("computer1111111111111:"+ computer.getDisplayName());
//                System.out.println("computer1111111111111:"+ computer.getComputers().size());
//                // 获取当前节点-节点名称
////                System.out.println(computer.details().getDisplayName());
////                // 获取当前节点-执行者数量
////                System.out.println(computer.details().getNumExecutors());
////                // 获取当前节点-执行者详细信息
////                List<Executor> executorList = computer.details().getExecutors();
////                // 查看当前节点-是否脱机
////                System.out.println(computer.details().getOffline());
////                // 获得节点的全部统计信息
////                LoadStatistics loadStatistics = computer.details().getLoadStatistics();
////                // 获取节点的-监控数据
////                Map<String, Map> monitorData = computer.details().getMonitorData();
//                //......
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启 Jenkins
     */
    public void restart() {
        try {
            jenkinsServer.restart(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全重启 Jenkins
     */
    public void safeRestart() {
        try {
            jenkinsServer.safeRestart(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安全结束 Jenkins
     */
    public void safeExit() {
        try {
            jenkinsServer.safeExit(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭 Jenkins 连接
     */
    public void close() {
        jenkinsServer.close();
    }

    /**
     * 根据 Label 查找代理节点信息
     */
    public void getLabelNodeInfo() {
        try {
            LabelWithDetails labelWithDetails = jenkinsServer.getLabel("172.18.1.118");
            // 获取标签名称
            System.out.println(labelWithDetails.getName());
            // 获取 Cloud 信息
            System.out.println(labelWithDetails.getClouds());
            // 获取节点信息
            System.out.println(labelWithDetails.getNodeName());
            // 获取关联的 Job
            System.out.println(labelWithDetails.getTiedJobs());
            // 获取参数列表
            System.out.println(labelWithDetails.getPropertiesList());
            // 是否脱机
            System.out.println(labelWithDetails.getOffline());
            // 获取描述信息
            System.out.println(labelWithDetails.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断 Jenkins 是否运行
     */
    public void isRunning() {
        boolean isRunning = jenkinsServer.isRunning();
        System.out.println(isRunning);
    }

    /**
     * 获取 Jenkins 插件信息
     */
    public void getPluginInfo(){
        try {
            PluginManager pluginManager =jenkinsServer.getPluginManager();
            // 获取插件列表
            List<Plugin> plugins = pluginManager.getPlugins();
            for (Plugin plugin:plugins){
                // 插件 wiki URL 地址
                System.out.println(plugin.getUrl());
                // 版本号
                System.out.println(plugin.getVersion());
                // 简称
                System.out.println(plugin.getShortName());
                // 完整名称
                System.out.println(plugin.getLongName());
                // 是否支持动态加载
                System.out.println(plugin.getSupportsDynamicLoad());
                // 插件依赖的组件
                System.out.println(plugin.getDependencies());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // 创建 JenkinsApi 对象，并在构造方法中连接 Jenkins
        JenkinsApi jenkinsApi = new JenkinsApi();
        // 重启 Jenkins
        //jenkinsApi.restart();
        // 安全重启 Jenkins
        //jenkinsApi.safeRestart();
        // 获取节点信息
//        jenkinsApi.getComputerNames();
        // 获取节点信息
        jenkinsApi.getComputerInfo();
        // 安全结束 Jenkins
        //jenkinsApi.safeExit();
        // 关闭 Jenkins 连接
        //jenkinsApi.close();
        // 获取 Label 节点信息
//        jenkinsApi.getLabelNodeInfo();
        // 查看 Jenkins 是否允许
//        jenkinsApi.isRunning();
        // 获取 Jenkins 插件信息
        //jenkinsApi.getPluginInfo();
    }

    public static void main1(String[] args) throws IOException {
        String a = "[{\"index\":1,\"id\":\"7f032d6d0e524d4483f2e948780e670d\",\"name\":\"2\",\"description\":\"2\",\"status\":1,\"createBy\":\"刘智\",\"createTime\":\"2023-02-16 19:40:46\",\"updateBy\":\"刘智\",\"updateTime\":\"2023-02-16 19:40:46\"}]";
        List lists = JSON.parseArray(a);
        System.out.println(lists);


    }

}