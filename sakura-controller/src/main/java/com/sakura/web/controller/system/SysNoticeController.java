package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.domain.model.LoginUser;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.system.domain.SysNotice;
import com.sakura.system.service.SysNoticeService;
import com.sakura.system.service.SysNoticeUserReadService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公告 信息操作处理
 * 
 * @author liuzhi
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController
{
    @Autowired
    private SysNoticeService noticeService;
    @Autowired
    private SysNoticeUserReadService sysNoticeUserReadService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/page")
    public R<PageInfo> list(SysNotice notice, HttpServletRequest request, HttpServletResponse response)
    {
        notice.setPage(new PageDomain(request, response));
        return R.data(noticeService.findPage(notice));
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public R<SysNotice> getInfo(@PathVariable String noticeId)
    {
        return R.data(noticeService.get(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@Validated @RequestBody SysNotice notice)
    {
        noticeService.save(notice);
        return R.data(notice);
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public R remove(@PathVariable String[] noticeIds)
    {
        return R.status(noticeService.deleteNoticeByIds(noticeIds));
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @GetMapping(value = "getNoticeView/{noticeId}")
    public R<SysNotice> getNoticeView(@PathVariable String noticeId)
    {
        SysNotice notice = noticeService.get(noticeId);
        //标记当前登录人已读改消息
        sysNoticeUserReadService.setIsRead(noticeId);
        return R.data(noticeService.get(noticeId));
    }
    /**
     * 获取个人公告阅读列表
     */
    @GetMapping("listNoticeByUser/page")
    public R<PageInfo> listNoticeByUser(SysNotice notice, HttpServletRequest request, HttpServletResponse response)
    {
        notice.setPage(new PageDomain(request, response));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        notice.setUserId(loginUser.getUser().getId());
        return R.data(noticeService.findNoticeByUserPage(notice));
    }
    /**
     * 将通知公告标记为已读
     */
    @Log(title = "公告标记为已读", businessType = BusinessType.UPDATE)
    @PutMapping("updateNoticeToRead/{noticeIds}")
    public R updateNoticeToRead(@PathVariable String[] noticeIds)
    {
        if(noticeIds != null && noticeIds.length>0){
            for(int i =0;i<noticeIds.length;i++){
                sysNoticeUserReadService.setIsRead(noticeIds[i]);
            }
        }
        return R.status(1);
    }
}
