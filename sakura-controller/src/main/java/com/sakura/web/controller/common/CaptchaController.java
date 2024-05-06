package com.sakura.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;
import com.google.code.kaptcha.Producer;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;

import com.sakura.common.constant.Constants;
import com.sakura.common.core.domain.AjaxResult;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.model.CaptchaBody;
import com.sakura.common.core.redis.RedisCache;
import com.sakura.common.utils.json.JSONObject;
import com.sakura.common.utils.sign.Base64;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.framework.cache.ConfigUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码操作处理
 * 
 * @author liuzhi
 */
@Api(tags = { "公共模块" }, description = "CaptchaController")
@ApiSupport(author = "刘智", order = 20)
@RestController
@RequestMapping("/common")
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    // 验证码类型
    @Value("${sakura.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @ApiIgnore
    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", position = 10, tags = "登录模块")
    @GetMapping("/captchaImage1")
    public AjaxResult getCaptchaImage1(final HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        final boolean captchaOnOff = ConfigUtils.getConfigBooleanValueByKey("sys.captcha.onOff", true);
        ajax.put("captchaOnOff", captchaOnOff);
        if (!captchaOnOff) {
            return ajax;
        }
        try {
            // 保存验证码信息
            final String uuid = IdUtils.simpleUUID();
            final String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

            String capStr = null, code = null;
            BufferedImage image = null;

            // 生成验证码
            if ("math".equals(captchaType)) {
                final String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf('@'));
                code = capText.substring(capText.lastIndexOf('@') + 1);
                image = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(captchaType)) {
                capStr = code = captchaProducer.createText();
                image = captchaProducer.createImage(capStr);
            }

            redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            // 转换流信息写出
            final FastByteArrayOutputStream os = new FastByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", os);
            } catch (final IOException e) {
                e.printStackTrace();
                return AjaxResult.error(e.getMessage());
            }

            ajax = AjaxResult.success();
            ajax.put("uuid", uuid);
            ajax.put("img", "data:image/gif;base64,"+Base64.encode(os.toByteArray()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return ajax;
    }

    @ApiOperation(value = "获取图片验证码", notes = "获取图片验证码", position = 10, tags = "登录模块")
    @GetMapping("/captchaImage")
    public JsonResult<CaptchaBody> getCaptchaImage() throws IOException {
        CaptchaBody captcha = new CaptchaBody();
        JSONObject data = new JSONObject();

        final boolean captchaOnOff = ConfigUtils.getConfigBooleanValueByKey("sys.captcha.onOff", true);
        if (!captchaOnOff) {
            data.put("captchaOnOff", captchaOnOff);
            return JsonResult.error(data);
        }
        try {
            // 保存验证码信息
            final String uuid = IdUtils.simpleUUID();
            captcha.setUuid(uuid);
            data.put("uuid", uuid);
            final String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

            String capStr = null, code = null;
            BufferedImage image = null;
            // 生成验证码
            if ("math".equals(captchaType)) {
                final String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf('@'));
                code = capText.substring(capText.lastIndexOf('@') + 1);
                image = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(captchaType)) {
                capStr = code = captchaProducer.createText();
                image = captchaProducer.createImage(capStr);
            }

            redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            // 转换流信息写出
            final FastByteArrayOutputStream os = new FastByteArrayOutputStream();
            try {
                ImageIO.write(image, "jpg", os);
                String img = Base64.encode(os.toByteArray());
                captcha.setImg(img);
                data.put("img", "data:image/gif;base64,"+img);
            } catch (final IOException e) {
                e.printStackTrace();
                return JsonResult.error(e);
            }
        } catch (final Exception e) {
            JsonResult.error(e);
            e.printStackTrace();
        }
        return JsonResult.success(data);
    }
}
