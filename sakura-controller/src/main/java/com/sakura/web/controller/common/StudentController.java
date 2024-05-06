// package com.sakura.web.controller.common;

// import com.sakura.common.core.domain.JsonResult;
// import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
// import com.github.xiaoymin.knife4j.annotations.ApiSort;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiImplicitParam;
// import io.swagger.annotations.ApiOperation;
// import springfox.documentation.annotations.ApiIgnore;

// import org.springframework.web.bind.annotation.*;
// import java.util.List;
 
// /**
//  * 学生信息控制类
//  */
// @ApiIgnore
// @Api(tags = { "学生信息接口" }, description = "StudentController")
// @RestController
// @RequestMapping(value = "student")
// @ApiSort(1)
// public class StudentController {
 
//     /**
//      * 根据性别查询学生信息
//      *
//      * @author liuzhi
//      * @param  gender [String]性别
//      * @return java.util.List<com.kcsm.training.bootdemo.entity.Student>
//      * @date   2019/7/10 9:16
//      */
//     @RequestMapping(value = "v1/jpatest",method = RequestMethod.GET)
//     @ApiOperation(value = "根据性别查找所有学生")
//     @ApiOperationSupport(order=1,author ="陆启坤")
//     @ApiImplicitParam(name = "gender", value = "性别",required = true,dataType = "String", defaultValue = "male")
//     public List<Student> findAllByGender(@RequestBody Student gender){
//         return null;
//     }

//     @ApiOperation("根据性别以及姓名查找所有学生")
//     @ApiOperationSupport(order=2)
//     @GetMapping(value = "v1/findByGenderAndName" )
//     public JsonResult<Student> findByGenderAndName(@RequestBody Student gender){
//         return JsonResult.success();
//     }
// }