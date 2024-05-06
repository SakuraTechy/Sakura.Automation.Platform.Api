package com.sakura.web.controller.common;

import io.swagger.annotations.*;

import com.sakura.common.core.domain.model.User;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@Api(tags = "用户接口", position = 40)
public class UserController {
 
    @ApiOperation(value = "查询用户",notes = "根据id查询用户")
    @ApiImplicitParam(paramType = "path",name="id",value = "用户id",required = true)
    @GetMapping("/user/query/{id}")
    public String getUserById(@PathVariable Integer id) {
        return "/user/"+id;
    }

    @ApiResponses({
            @ApiResponse(code=200,message="删除成功"),
            @ApiResponse(code=500,message="删除失败")})
    @ApiOperation(value = "删除用户",notes = "根据id删除用户")
    @DeleteMapping("/user/delete/{id}")
    public Integer deleteUserById(@PathVariable Integer id) {
        return id;
    }

    @ApiOperation(value = "添加用户",notes = "添加一个用户，传入用户名和性别")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="username",value = "用户名",required = true,defaultValue = "张三"),
            @ApiImplicitParam(paramType = "query",name="sex",value = "性别",required = true,defaultValue = "女")
    })
    @PostMapping("/user")
    public String addUser(@RequestParam String username,@RequestParam String sex){
        return username+","+sex;
    }

    @ApiOperation(value="修改用户",notes = "根据传入的用户信息修改用户")
    @PutMapping("/user")
    public String updateUser(@RequestBody User user){
        return user.toString();
    }

    @GetMapping("/ignore")
    @ApiIgnore
    public void ignoreMethod(){}


}