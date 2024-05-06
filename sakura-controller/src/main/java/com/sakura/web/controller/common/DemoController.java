package com.sakura.web.controller.common;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import com.sakura.common.core.domain.IndexTree;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.RequestGetArr;
import com.sakura.common.core.domain.Rest;
import com.sakura.common.core.domain.model.CaptchaBody;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一般用于控制层的类 表示标识这个类是swagger的资源
 *
 * tags–表示说明 value–也是说明，可以使用tags替代 但是tags如果有多个值，会生成多个list description：可描述描述该类作用。
 */
@Api(tags = { "测试模块" }, description = "DemoController")
/**
 * 用于接口方法排序，作者信息描述等
 * 
 * author：作者信息 order：排序
 */
@ApiSupport(author = "刘智", order = 10)
// @ApiOperationSupport(order = 10)
@RestController
@RequestMapping("/demo")
// @SuppressWarnings("all")
public class DemoController {

    /**
     * 一般用于控制层的类中的方法
     * tags: 以重新分组（视情况而用）
     * value ：方法说明（常用） 
     * nnotes ：注释说明
     * httpMethod ：说明这个方法被请求的方式 
     * response: 方法的返回值的类型 
     * authorizations: 获取此操作的授权列表
     * consumes: 请求数据类型，mime类型，多个值逗号分隔
     * produces: 响应数据类型，mime类型，多个值逗号分隔,
     * hidden: 是否隐藏该模块
     */
    @ApiOperation(
        value = "GetMapping测试示例",
        notes = "GET测试示例",
        position = 1,
        httpMethod = "GET",
        // multipart/form-data
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        // application/json
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    /**
     * https://blog.csdn.net/TheThirdMoon/article/details/109447537
     * [请求参数]
     * name ：参数名称 
     * value ： 参数描述
     * defaultValue：参数默认值
     * required ： 参数是否必填。 （常用） 
     * example： 参数值例子，paramType!=body时有效
     * examples： paramType=body时有效
     * allowEmptyValue： 是否可以传递空值
     * dataType ：参数的数据类型。
     * dataType=“int” 代表请求参数类型为int类型，当然也可以是Map、User、String等；
     * paramType ：查询参数类型，这里有几种形式： 
     *   path 以地址的形式提交数据 
     *   query 直接跟参数完成自动映射赋值 
     *   body 以流的形式提交仅支持POST 
     *   header 参数在request headers 里边提交 
     *   form 以form表单的形式提交 仅支持POST 参数信息
     * path（用于restful接口）–>请求参数的获取：@PathVariable(代码中接收注解)
     * query–>用于get请求的参数拼接。请求参数的获取：@RequestParam(代码中接收注解)
     * body–>放在请求体。请求参数的获取：@RequestBody(代码中接收注解) 
     * header–>放在请求头。请求参数的获取：@RequestHeader(代码中接收注解)
     * form（不常用） 
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "code", value = "代码", defaultValue = "代码默认值", required = true, example= "代码示例", dataType = "Integer", paramType = "query"),
        @ApiImplicitParam(name = "type", value = "类型", defaultValue = "类型默认值", required = true, example= "类型示例", dataType = "Boolean", paramType = "query"),
        @ApiImplicitParam(name = "link", value = "<a href='json/form/formValue.json' target='jsonExample'>超链接</a>", defaultValue = "超链接默认值", required = true, example= "超链接示例", dataType = "String", paramType = "query"),
        @ApiImplicitParam(
            name = "example", value = "示例", defaultValue = "示例默认值", required = true, 
            example= "paramType = body无效",
            examples = @Example(value = @ExampleProperty(mediaType = "", value = "example示例")), 
            dataType = "String", paramType = "body"
        )
    })
    // [响应状态]
    @ApiResponses(
        value = { 
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"), 
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 403, message = "访问受限，授权过期"), 
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "系统异常")
        }
    )
    @GetMapping("/get")
    public Rest<String> all(Integer code, Boolean type, String example, String link) {
        return Rest.data(RandomUtil.randomString(32));
    }

    @ApiOperation(value = "GetMapping测试示例，请求参数超链接", notes = "GetMapping测试请求参数超链接示例", position = 2)
    @GetMapping(value = "/get1")
    public Rest<String> all2(
        @ApiParam(value = "状态", defaultValue = "状态默认值" ,example = "状态示例", required = false) Integer code,
        @ApiParam(value = "类型", defaultValue = "类型默认值", example = "类型示例", required = true) String type,
        @ApiParam(value = "<a href='https://www.baidu.com/' target='jsonExample'>超链接</a>", example = "超链接示例") String link
    ) {
        return Rest.data(RandomUtil.randomString(32));
    }
    
    @ApiOperation(value = "GetMapping测试示例，可带条件查询", notes = "GET测试示例,可带条件查询", position = 2)
    @GetMapping(value = "/get2", params = { "name", "id" })
    public Rest<String> all4(
        @RequestParam(value = "name", defaultValue = "名称默认值") final String name,
        @RequestParam(value = "id", defaultValue = "ID默认值") final String id
    ) {
        return Rest.data("name:" + name + ",id:" + id + "," + RandomUtil.randomString(32));
    }

    @ApiOperation(value = "GET请求数组请求示例", notes = "GET请求数组请求示例", position = 3)
    @GetMapping("/reqGetArr1")
    public Rest<RequestGetArr> reqGetArr1(RequestGetArr requestArr) {
        return Rest.data(requestArr);
    }

    @ApiOperation(value = "GET请求数组请求示例2", notes = "GET请求数组请求示例2", position = 4)
    @GetMapping("/reqGetArr2")
    public Rest<String> reqGetArr2(
        @RequestParam(value = "codes", defaultValue = "[1,2,3]") final String[] codes
    ) {
        final String codeArr = StrUtil.join(",", codes);
        return Rest.data(codeArr);
    }

    @ApiOperation(value = "GetMapping测试示例", notes = "Get测试示例", position = 5, httpMethod = "GET")
    @ApiImplicitParams(
        { 
            @ApiImplicitParam(name = "code", value = "代码", example = "代码默认值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "type", value = "类型", example = "类型默认值", required = false, paramType = "query"),
        }
    )
    @GetMapping(value = "/one")
    public Rest<List<IndexTree>> one(Integer code, String type) {
        return Rest.data(new ArrayList<>());
    }

    @ApiOperation(value = "PostMapping测试示例", notes = "POST测试示例", position = 5, httpMethod = "POST")
    @PostMapping(value = "/one1")
    public Rest<List<IndexTree>> one1(@RequestBody RequestGetArr requestArr) {
        return Rest.data(new ArrayList<>());
    }

    /**
     * https://gitee.com/xiaoym/knife4j/issues/I28RJ5
     * 注意当使用@RequestMapping，而不指定请求类型时，method =
     * {RequestMethod.GET,RequestMethod.POST}
     * 将自动生成多个接口说明（get、post、delete等），对于不想生成说明的类或方法，使用@ApiIgnore注解即可屏蔽
     * 
     * 
     * @return
     */
    @ApiOperation(value = "RequestMapping测试示例，不指定method")
    /**
     * @ApiIgnore 方法自动生成接口说明时忽略，屏蔽
     * @JsonIgnore 字段自动生成接口说明时忽略，即忽略某一个字段
     */
    @ApiIgnore
    @ApiImplicitParam(name = "name", value = "昵称", required = true, paramType = "query", example = "张飞")
    @RequestMapping(value = "/hi1")
    public JsonResult<?> hi1() {
        return JsonResult.success();
    }

    @ApiOperation(value = "RequestMapping测试示例，指定单个method")
    @RequestMapping(value = "/hi2", method = RequestMethod.GET)
    public JsonResult<?> hi2() {
        return JsonResult.success();
    }

    @ApiOperation(value = "RequestMapping测试示例，指定多个method")
    @RequestMapping(value = "/hi3", method = { RequestMethod.GET, RequestMethod.POST })
    public JsonResult<?> hi3() {
        return JsonResult.success();
    }

    @ApiOperation(value = "GET成功返回，data为空")
    @GetMapping(value = "/hi4")
    public JsonResult<?> hi4() {
        return JsonResult.success();
    }

    @ApiOperation(value = "GET成功返回，data不为空")
    @GetMapping(value = "/hi5")
    public JsonResult<CaptchaBody> hi5() {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", "uuidvalue");
        data.put("img", "imgvalue");
        return JsonResult.success(data);
    }

    @ApiOperation(value = "GET失败返回，data不为空")
    @GetMapping(value = "/hi6")
    public JsonResult<CaptchaBody> hi6() {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", "uuidvalue");
        data.put("img", "imgvalue");
        return JsonResult.error(data);
    }

    @ApiOperation(value = "GET自定义返回")
    @ApiImplicitParams(
        { 
            @ApiImplicitParam(name = "code", value = "状态码", defaultValue = "code默认值", example = "code示例", required = true),
            @ApiImplicitParam(name = "msg", value = "返回消息", defaultValue = "msg默认值", example = "msg示例", required = true),
            @ApiImplicitParam(name = "uuid", value = "唯一标识", defaultValue = "uuid默认值", example = "uuid示例", required = false),
            @ApiImplicitParam(name = "img", value = "图片地址", defaultValue = "img默认值", example = "img示例", required = false)
        }
    )
    @GetMapping(value = "/hi7")
    public JsonResult<CaptchaBody> hi7(int code, String msg, String uuid, String img) {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("img", img);
        return JsonResult.custom(code, msg, data);
    }

    @ApiOperation(value = "POST成功返回，data不为空")
    @PostMapping(value = "/hi8")
    @ApiImplicitParams(
        { 
            @ApiImplicitParam(name = "uuid", value = "唯一标识", defaultValue = "uuid默认值", example = "uuid示例", required = true),
            @ApiImplicitParam(name = "img", value = "图片地址", defaultValue = "img默认值", example = "img示例", required = true),
        }
    )
    public JsonResult<CaptchaBody> hi8(String uuid,String img) {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("img", img);
        return JsonResult.success(data);
    }

    @ApiOperation(value = "POST失败返回，data不为空")
    @PostMapping(value = "/hi9")
    public JsonResult<CaptchaBody> hi9(@RequestBody CaptchaBody captcha) {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", captcha.getUuid());
        data.put("img", captcha.getImg());
        return JsonResult.error(data);
    }

    @ApiOperation(value = "POST自定义返回")    
    @ApiImplicitParams(
        { 
            @ApiImplicitParam(name = "code", value = "状态码", defaultValue = "code默认值", example = "code示例", required = true),
            @ApiImplicitParam(name = "msg", value = "返回消息", defaultValue = "msg默认值", example = "msg示例", required = true),
        }
    )
    @PostMapping(value = "/hi")
    public JsonResult<CaptchaBody> hi(int code, String msg, @RequestBody CaptchaBody captcha) {
        Map<Object,Object> data = new HashMap<>();
        data.put("uuid", captcha.getUuid());
        data.put("img", captcha.getImg());
        return JsonResult.custom(code,msg,data);
    }

    // @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType =
    // "query",dataType = "path")
    // @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    // public List<LoginUser> getUserBagTwo(String id) {
    // List<LoginUser> users = new ArrayList<>();
    // try {
    // if (id.equals("1")) {
    // users.add(new LoginUser("HealerJean", "1", "24", "1"));
    // } else {
    // users.add(new LoginUser("huangliang", "2", "25", "1"));
    // }
    // return users;
    // } catch (Exception e) {
    // return users;
    // }
    // }
}