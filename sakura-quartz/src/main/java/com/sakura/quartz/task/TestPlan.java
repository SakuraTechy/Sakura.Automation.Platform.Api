package com.sakura.quartz.task;

import com.alibaba.fastjson.JSON;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.common.utils.xml.SceneXmlUtils;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.service.SysAutomationService;
import com.sakura.test.service.TestPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试计划定时任务调度任务
 *
 * @author liuzhi
 */
@Component("testPlan")
public class TestPlan {
    static Logger log = LoggerFactory.getLogger(TestPlan.class);

    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    private SysAutomationService sysAutomationService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void execute(String params) throws Exception {
        String[] list = params.split("\\s*,\\s*");
        for (String id : list) {
            com.sakura.test.domain.TestPlan testPlan = testPlanService.get(id);
            ExecuteConfig executeConfig = JSON.parseObject(testPlan.getTimedTasksConfig(), ExecuteConfig.class);
            executeConfig.getTestReport().setId(IdUtils.randomUUID());
            sysAutomationService.execSysScene(executeConfig);
            log.info(StringUtils.format("执行测试计划定时任务： 测试计划ID{}，运行配置{}", id,executeConfig));
        }
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
