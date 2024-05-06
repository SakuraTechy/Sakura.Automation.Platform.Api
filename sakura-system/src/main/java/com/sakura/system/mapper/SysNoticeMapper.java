package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知公告表 数据层
 * 
 * @author liuzhi
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice>
{

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(@Param("noticeIds") String[] noticeIds, @Param("DEL_FLAG_DELETE") String DEL_FLAG_DELETE);

    public List<SysNotice> getNoticeListByUserId( @Param("userId") String userId, @Param("DEL_FLAG_NORMAL") String DEL_FLAG_NORMAL);

    /**
     * 根据用户id加载用户通知公告数据已经是否已读信息
     * @param sysNotice
     * @return
     */
    public List<SysNotice> findNoticeByUserList(SysNotice sysNotice);
}
