package com.sakura.system.service.impl;

import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.system.domain.SysElement;
import com.sakura.system.mapper.SysElementMapper;
import com.sakura.system.service.SysElementService;
import org.springframework.stereotype.Service;

/**
 * @author wutun
 */
@Service
public class SysElementServiceImpl extends BaseServiceImpl<SysElementMapper , SysElement> implements SysElementService {
    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }

    @Override
    public boolean deleteSysElementServiceByIds(String[] ids) {
        return mapper.deleteSysElementServiceByIds(ids);
    }
}
