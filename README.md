<!-- > 👋🏼 您当前浏览的文档为 v1.0.0，其它版本的文档请参考：[v1.3.0](/v1.3.0/)、[v1.2.0](/v1.2.0/)、[v1.1.0](/v1.1.0/) -->

<div style="display: grid;justify-items: center;gap: 10px;">
  <img alt="logo" src="https://sakura.hk.cn/logo.svg" width="100px" />
  <p style="font-size: 20px;font-weight: bold;line-height: 0px;margin-top: 5px;">
    Sakura Automation Platform
  </p>
  <div style="display: flex;justify-content: center;gap: 5px;">
    <!-- <a href="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api/blob/dev/LICENSE" target="_blank">
      <img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg" alt="License" />
    </a> -->
    <a href="https://sakura.hk.cn/" target="_blank" style="display: inline-block;">
      <img alt="官方网站" src="https://img.shields.io/badge/官网-sakura.hk.cn-F54860">
    </a>
    <a href="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api" target="_blank">
      <img src="https://img.shields.io/badge/版本-v1.0.0-%23ff3f59.svg" alt="Release" />
    </a>
  <a href="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api" target="_blank">
    <img src="https://img.shields.io/github/stars/SakuraTechy/Sakura.Automation.Platform.Api?style=social" alt="GitHub stars" />
  </a>
  <a href="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api" target="_blank">
    <img src="https://img.shields.io/github/forks/SakuraTechy/Sakura.Automation.Platform.Api?style=social" alt="GitHub forks" />
  </a>
  <a href="https://gitee.com/SakuraTech/Sakura.Automation.Platform.Api">
    <img src="https://gitee.com/SakuraTech/Sakura.Automation.Platform.Api/badge/star.svg?theme=dark" alt="Gitee star">
  </a>
  <a href="https://gitee.com/SakuraTech/Sakura.Automation.Platform.Api">
    <img src="https://gitee.com/SakuraTech/Sakura.Automation.Platform.Api/badge/fork.svg?theme=dark" alt="Gitee fork">
  </a>
  </div>
</div>

## 产品简介

Sakura Automation Platform 是一站式持续自动化平台，涵盖 APP自动化、WEB自动化、API接口自动化、性能自动化，并且支持分布式测试，全面兼容 Appium、Selenium、Rest Assured、JMeter 等主流开源框架，有效助力开发和测试团队充分利用云弹性进行高度可扩展的自动化测试，加速高质量的软件交付，推动测试整体效率的提升。

## 技术栈

> 前端

- [✔] 🍉 Vue
- [✔] 🍓 Vuex
- [✔] 🍌 Vue-router
- [✔] 🍍 Element UI
- [✔] 🍒 Axios
- [✔] 🍇 Node.js
- [✔] 🍎 Nginx

> 后端

- [✔] 🌺 Java
- [✔] 🍃 Maven
- [✔] 🌿 MyBatis
- [✔] 🍁 SpringBoot
- [✔] 🍂 Spring Security
- [✔] 🌴 Redis
- [✔] 💎 MySQL
- [✔] 🚣 Docker
- [✔] 🚀 Jenkins

## 基础功能

- **用户管理**：用户是系统操作者，该功能主要完成系统用户配置;
- **部门管理**：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限;
- **岗位管理**：配置系统用户所属担任职务;
- **菜单管理**：配置系统菜单，操作权限，按钮权限标识等;
- **角色管理**：角色菜单权限分配、设置角色按机构进行数据范围权限划分;
- **字典管理**：对系统中经常使用的一些较为固定的数据进行维护;
- **参数管理**：对系统动态配置常用参数;
- **通知公告**：系统通知公告信息发布维护;
- **操作日志**：系统正常操作日志记录和查询;系统异常信息日志记录和查询;
- **登录日志**：系统登录日志记录查询包含登录异常;
- **在线用户**：当前系统中活跃用户状态监控;
- **定时任务**：在线（添加、修改、删除)任务调度包含执行结果日志;
- **代码生成**：前后端代码的生成（java、html、xml、sql）支持 CRUD 下载 ;
- **系统接口**：根据业务代码自动生成相关的 api 接口文档;
- **服务监控**：监视当前系统 CPU、内存、磁盘、堆栈等相关信息;
- **缓存监控**：对系统的缓存查询，删除、清空等操作;
- **在线构建器**：拖动表单元素生成相应的 HTML 代码;
- **连接池监视**：监视当前系统数据库连接池状态，可进行分析 SQL 找出系统性能瓶颈;

## 核心功能

- **系统管理**：在线管理系统，包括系统设置、系统监控、系统日志等;
- **用户管理**：在线管理用户，包括用户信息、用户角色、用户权限等;
- **项目管理**：在线管理项目，包括项目配置，环境配置，自动化配置等;
- **测试管理**：在线管理测试，包括测试用例，测试计划，测试报告，测试度量等;
- **接口管理**：在线管理接口，包括接口文档，接口调试，接口自动化测试等;
- **自动化管理**：在线管理自动化测试，包括 WEB 自动化，APP 自动化，API 自动化，性能自动化等;

## 产品优势

> 在线编写自动化测试用例脚本

- 目前测试人员根据需求规格说明书的要求，只能在本地搭建环境，编写自动化测试脚本，用例编写的门槛高，维护麻烦，编写大量自动化脚本增加了项目的人力成本和沟通成本，导致低效率以及高差错率，使用自动化测试平台，可以在线便捷的编写和调试自动化测试脚本，提高用例编写效率;

> 在线跟踪自动化测试用例进度

- 目前整体项目自动化测试缺乏控制，自动化测试用例执行情况等都需要人工统计，无法精确统计自动化覆盖率、业务场景覆盖率，缺失实时反馈机制，使用自动化测试平台，可以在线跟踪自动化测试相关进度，实现在线实时监控;

> 在线生成自动化测试项目报告

- 目前的自动化测试、接口测试、性能测试等测试结果需要花费大量的时间进行整理，无法根据测试结果自动出具测试报告，使用自动化测试平台，可以把自动化测试报告分类分级的统一进行展示;

> 在线整合相关自动化测试工具

- 目前自动化测试的工具都是相互独立的，种类不一，不同工具的整合较差，都是独立使用，相关用例和自动化没有关联，使用自动化测试平台，可以把相关自动化测试工具进行整合，统一使用;

## 在线体验
- 官网：[sakura.hk.cn](https://sakura.hk.cn)
- 体验：[www.sakura.hk.cn:28383](https://www.sakura.hk.cn:28383)
- 账号：自行注册（用自己的姓名即可）
- 密码：自行注册（用自己的密码即可）

> 陆陆续续收到一些打赏，为了更好的体验已用于演示服务器升级，谢谢各位小伙伴。

## 效果图
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-11.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-13.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-14.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-15.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-16.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-17.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-18.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-19.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-20.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-21.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-22.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-23.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-24.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-25.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-26.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-27.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-28.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-29.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-30.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-31.png">
<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-32.png">

## 参与贡献

我们欢迎广大开发者贡献大家的智慧，让我们共同让它变得更完美，您可以在 GitHub 上提交 Pull Request，我们会尽快审核并公布;更多信息请参考 [贡献指南](contributing.md);

:::tip

- 如果您想提交新功能或优化现有代码，可以按照以下步骤操作：
  1. 首先，在 Gitee 或 Github 上将项目 fork 到您自己的仓库
  2. 然后，将 fork 过来的项目（即您的项目）克隆到本地
  3. 切换到当前仍在维护的分支（请务必充分了解分支使用说明，可进群联系维护者确认）
  4. 开始修改代码，修改完成后，将代码 commit 并 push 到您的远程仓库
  5. 在 Gitee 或 Github 上新建 pull request（pr），选择好源和目标，按模板要求填写说明信息后提交即可（多多参考 [已批准合并的 pr 记录](https://github.com/SakuraTechy/Sakura.Automation.Platform.Api/pulls?q=is%3Apr+is%3Amerged)，会大大增加批准合并率）
  6. 最后，耐心等待维护者合并您的请求即可

请记住，如果您有任何疑问或需要帮助，我们将随时提供支持。
:::

> [!IMPORTANT]
> 欢迎大家贡献代码，我们非常感谢您的支持！为了更好地管理项目，维护者有一些要求：
>
> 1. 请确保代码、配置文件的结构和命名规范良好，完善的代码注释甚至包括接口文档参数示例，并遵循阿里巴巴的 <a href="https://github.com/continew-org/continew-admin/blob/dev/.style/Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C(%E9%BB%84%E5%B1%B1%E7%89%88).pdf" target="_blank">《Java开发手册(黄山版)》</a> 中的代码规范，保证代码质量和可维护性
> 2. 在提交代码前，请按照 [Angular 提交规范](https://github.com/conventional-changelog/conventional-changelog/tree/master/packages/conventional-changelog-angular) 编写 commit 的 message（建议在 IntelliJ IDEA 中下载并安装 Git Commit Template 插件，以便按照规范进行 commit）
> 3. 提交代码之前，请关闭所有代码窗口，执行 `mvn compile` 命令（代码格式化插件会在项目编译时对全局代码进行格式修正），编译通过后，不要再打开查看任何代码窗口，直接提交即可，以免不同的 IDE 配置会自动进行代码格式化

## 官方交流群

欢迎各位小伙伴儿扫描下方二维码，备注 `sakura`，可探讨技术、提提需求~

加入后，你将会：

- 第一时间收到官方动态
- 第一时间收到官方更新通知
- 第一时间收到官方 Bug 通知
- 和众多大佬互相 (huá shuǐ) 交流 (mō yú)

> 扫码加微信，邀请入群
<div align="left">
  <img src="https://sakura.hk.cn/assets/wx.B5trhZqZ.png" alt="个人微信" width="230px" />
</div>

> 官方 QQ 群
<div align="left">
  <img src="https://sakura.hk.cn/assets/qq.CnADLzHX.png" alt="QQ群" width="230px" />
</div>

<div style="display: flex;margin-top: 5px;gap: 5px;">
  <a href="https://qm.qq.com/cgi-bin/qm/qr?k=b8he45MJqnEPzDjQUemTT86E0tLwnG1N&jump_from=webapi&authKey=HdZIaQGhK4BjebajkAJ5wwDzZKBnSrXtq6jEM8g/LcR+0kaZcqLQGfKNl1d8Wwip" target="_blank"><img src="https://img.shields.io/badge/已满-126325129-blue.svg" alt="加入QQ群"></a>
  <a href="https://qm.qq.com/cgi-bin/qm/qr?k=b8he45MJqnEPzDjQUemTT86E0tLwnG1N&jump_from=webapi&authKey=HdZIaQGhK4BjebajkAJ5wwDzZKBnSrXtq6jEM8g/LcR+0kaZcqLQGfKNl1d8Wwip" target="_blank"><img src="https://img.shields.io/badge/未满-126325130-blue.svg" alt="加入QQ群"></a>
</div>

## 问卷调查

- 大家可根据自己的使用体验感受，填写在线问卷调查，帮助我们改善产品，谢谢！
- 填写地址：[https://jsj.top/f/yCnEjx](https://jsj.top/f/yCnEjx)

<img alt="logo" src="https://sakura.hk.cn/1.%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/1.%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/image-33.png">

## 特别鸣谢

感谢参与贡献的每一位小伙伴🥰

<a sthref="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api/graphs/contributors">
  <img style="display:initial" src="https://avatars.githubusercontent.com/u/24785603?s=60&v=4" alt="contributors" />
</a>

## License

- 遵循 <a href="https://github.com/SakuraTechy/Sakura.Automation.Platform.Api/blob/dev/LICENSE" target="_blank">Apache-2.0</a> 开源许可协议
- Copyright © 2024-present <a href="https://sakura.hk.cn" target="_blank">Sakura</a>

## GitHub Star 趋势

![GitHub Star 趋势](https://starchart.cc/SakuraTechy/Sakura.Automation.Platform.Api.svg)
