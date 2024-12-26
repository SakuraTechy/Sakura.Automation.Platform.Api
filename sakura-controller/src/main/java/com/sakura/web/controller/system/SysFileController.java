package com.sakura.web.controller.system;

import java.util.List;

import com.sakura.web.controller.common.WeChatMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.file.FileUploadUtils;

/**
 * 文件管理Controller
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-12-26
 */
@RestController
@RequestMapping("/system/file")
public class SysFileController extends BaseController {
    /**
     * 单个文件上传
     */
    @Log(title = "单个文件上传", businessType = BusinessType.UPDATE)
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) throws Exception {
        return R.data(FileUploadUtils.saveFile(file, path));
    }

    /**
     * 批量文件上传
     */
    @Log(title = "批量文件上传", businessType = BusinessType.UPDATE)
    @PostMapping("/uploads")
    public R uploads(@RequestParam("files") MultipartFile[] files, @RequestParam("path") String path) throws Exception {
        List<String> uploadedFileNames = FileUploadUtils.batchUpload(files, path);
        return R.data(uploadedFileNames);
    }

    /**
     * 从网络下载文件流并保存到指定目录
     */
    @Log(title = "从网络下载文件流并保存到指定目录", businessType = BusinessType.UPDATE)
    @PostMapping("/downloadFile")
    public R downloadFile(@RequestParam("url") String url, @RequestParam("authorization") String authorization, @RequestParam("savePath") String savePath, @RequestParam("fileName") String fileName) throws Exception {
        return R.data(FileUploadUtils.downloadFile(url, authorization, savePath, fileName));
    }

    /**
     * 发送消息到企业微信机器人
     */
    @Log(title = "发送消息到企业微信机器人", businessType = BusinessType.UPDATE)
    @PostMapping("/sendWebhookMessage")
    public R sendWebhookMessage(@RequestBody WeChatMessage message) {
        return R.data(WeChatMessage.sendMessage(message));
    }
}