package com.sakura.system.service.impl;

import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.exception.CustomException;
import com.sakura.common.utils.StringUtils;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysConfig;
import com.sakura.system.mapper.SysConfigMapper;
import com.sakura.system.service.SysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author liuzhi
 */
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    /**
     * 保存参数信息
     *
     * @param sysConfig
     * @return
     */
    @Override
    public boolean save(SysConfig sysConfig) {
        checkConfigKeyUnique(sysConfig);
        boolean result = super.save(sysConfig);
        if (result) {
            ConfigUtils.clearConfigCache(sysConfig.getConfigKey());
            ConfigUtils.setConfigCache(sysConfig);
        }
        return result;
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteConfigByIds(String[] configIds) {
        //先进行校验
        for (String configId : configIds) {
            SysConfig config = super.get(configId);
            if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
                throw new CustomException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
            }
        }
        for (String configId : configIds) {
            SysConfig config = super.get(configId);
            remove(config);
            ConfigUtils.clearConfigCache(config.getConfigKey());
        }
    }

    /**
     * 刷新缓存
     * @return 结果
     */
    @Override
    public void refreshCache() {
        ConfigUtils.clearConfigCache();
        loadingConfigCache();
    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }


    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public void checkConfigKeyUnique(SysConfig config) {
        SysConfig sysConfigUnique = new SysConfig();
        sysConfigUnique.setNotEqualId(config.getId());
        sysConfigUnique.setConfigKey(config.getConfigKey());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysConfigUnique))) {
            throw new BizException(SysErrorCode.B_SYSCONFIG_ConfigKeyAlreadyExist);
        }
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache()
    {
        List<SysConfig> configsList = findList(new SysConfig());
        for (SysConfig config : configsList)
        {
            ConfigUtils.setConfigCache(config);
        }
    }

}
