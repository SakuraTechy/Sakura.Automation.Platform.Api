package com.sakura.system.service.impl;

import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.system.domain.SysConfiguration;
import com.sakura.system.mapper.SysConfigurationMapper;
import com.sakura.system.service.SysConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wutun
 */
@Service
public class SysConfigurationServiceImpl extends BaseServiceImpl<SysConfigurationMapper, SysConfiguration> implements SysConfigurationService {


    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }


    @Transactional(readOnly = false  )
    @Override
    public boolean deleteSysConfigurationByIds(String[] ids) {
        return mapper.deleteSysConfigurationByIds(ids);
    }


    @Override
    public String getPath(String name){
        String byName = mapper.getPath(name);
        return byName;
    }
}
