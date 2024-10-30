/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sakura.open.sign;

import cn.dev33.satoken.error.SaErrorCode;
import cn.dev33.satoken.exception.SaSignException;
import cn.dev33.satoken.sign.SaSignTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.sakura.open.service.AppService;

import java.util.Map;
import java.util.TreeMap;

import static cn.dev33.satoken.SaManager.log;

@Component
@RequiredArgsConstructor
public class OpenSignTemplate extends SaSignTemplate {

    private final AppService appService;
    public static String appKey = "appkey";

    @Override
    public void checkParamMap(Map<String, String> paramMap) {
        // 获取必须的参数
        String timestampValue = paramMap.get(timestamp);
        String nonceValue = paramMap.get(nonce);
        String signValue = paramMap.get(sign);
        String appKeyValue = paramMap.get(appKey);

        // 参数非空校验
        SaSignException.notEmpty(timestampValue, "缺少 timestamp 字段");
        SaSignException.notEmpty(nonceValue, "缺少 nonce 字段");
        SaSignException.notEmpty(signValue, "缺少 sign 字段");
        SaSignException.notEmpty(appKeyValue, "缺少 appkey 字段");

        // 应用存在性校验
        SaSignException.notTrue(!appService.isExistAppKey(appKeyValue), "应用不存在");

        // 应用是否过期校验
        SaSignException.notTrue(appService.isExpireAppKey(appKeyValue), "应用已过期");

        // 依次校验三个参数
        checkTimestamp(Long.parseLong(timestampValue));
        checkNonce(nonceValue);
        checkSign(paramMap, signValue);

        // 通过 √
    }

    @Override
    public String createSign(Map<String, ?> paramsMap) {
        // 根据应用密钥获取对应的应用密码
        String appKey = (String)((Map)paramsMap).get("appkey");
        String secretKey = this.appService.getAppSecretByAppKey(appKey);
        SaSignException.notEmpty(secretKey, "参与参数签名的秘钥不可为空", SaErrorCode.CODE_12201);

        // 如果调用者不小心传入了 sign 参数，则此处需要将 sign 参数排除在外
        if (paramsMap.containsKey(sign)) {
            // 为了保证不影响原有的 paramsMap，此处需要再复制一份
            paramsMap = new TreeMap<>(paramsMap);
            paramsMap.remove(sign);
        }

        // 计算签名
        String paramsStr = joinParamsDictSort(paramsMap);
        String fullStr = paramsStr + "&" + key + "=" + secretKey;
        String signStr = abstractStr(fullStr);

        // 输入日志，方便调试
        log.debug("fullStr：{}", fullStr);
        log.debug("signStr：{}", signStr);

        // 返回
        return signStr;
    }
}
