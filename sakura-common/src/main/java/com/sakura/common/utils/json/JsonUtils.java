package com.sakura.common.utils.json;

import com.alibaba.fastjson.JSONObject;
import com.sakura.common.utils.StringUtils;

public class JsonUtils {
    /**
     * @description 校验字符串是否是 json 格式
     */
    public static boolean isJson(String json) {
        if(StringUtils.isBlank(json)){
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(json);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(json);
        } catch (Exception e) {
            isJsonArray = false;
        }
        //不是json格式
        return isJsonObject || isJsonArray;
    }
}
