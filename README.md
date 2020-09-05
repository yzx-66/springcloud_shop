# springcloud微服务商城

#### 介绍

练习的综合项目 主要熟悉微服务框架SpringCloud与练习其他技术

#### 部分运行截图
![](https://gitee.com/yzx66/springcloud_shop/raw/master/1.png "截图")
![](https://gitee.com/yzx66/springcloud_shop/raw/master/2.png "截图")
![](https://gitee.com/yzx66/springcloud_shop/raw/master/3.png "截图")
![](https://gitee.com/yzx66/springcloud_shop/raw/master/4.png "截图")
![](https://gitee.com/yzx66/springcloud_shop/raw/master/5.png "截图")
![](https://gitee.com/yzx66/springcloud_shop/raw/master/6.png "截图")

#### 使用技术

* 后端：SpringBoot + SpringCloud + TkMybatis + FastDfs + Thymeleaf + Redis + Mongodb +Elstaticsearch + Rabbitmq + Jwt

* 前端：Vue + axios + live-server + qrcode

#### 笔记
**项目中所记（时间 2019.9）**
* 分布式文件管理系统FastDFS：https://blog.csdn.net/weixin_43934607/article/details/100930037
* SpringBoot整合Jwt：https://blog.csdn.net/weixin_43934607/article/details/101356581
* 前后端跨域问题（Cors方式）：https://blog.csdn.net/weixin_43934607/article/details/102299264
* 雪花算法和几个工具类：https://blog.csdn.net/weixin_43934607/article/details/102301364
* TKMybatis和MybatisPlus、Lomback：https://blog.csdn.net/weixin_43934607/article/details/102540483

**其余非项目时间的相关技术笔记**
###### SpringBoot
* SpringBoot（一）：https://blog.csdn.net/weixin_43934607/article/details/100055620
* SpringBoot（二）https://blog.csdn.net/weixin_43934607/article/details/100111858
* SpringBoot（三）https://blog.csdn.net/weixin_43934607/article/details/100115270
###### SpringCloud
* SpringCloud：https://blog.csdn.net/weixin_43934607/article/details/100116476
###### Redis
* Redis作分布式锁与集群搭建：https://blog.csdn.net/weixin_43934607/article/details/100141334
* Redis命令与二级缓存和分布式Session：https://blog.csdn.net/weixin_43934607/article/details/100141311
* Redis三主三从的两种方式与SpringBoot整合Redis集群：https://blog.csdn.net/weixin_43934607/article/details/102668918
###### Mongodb
* SpringBoot整合MangoDB查询与特性：https://blog.csdn.net/weixin_43934607/article/details/100149391
* MongoDB地理位置索引：https://blog.csdn.net/weixin_43934607/article/details/102752532
* Mongodb集群之复制集和分片集：https://blog.csdn.net/weixin_43934607/article/details/102792808
###### ElasticSearch
* Elasticsearch命令：https://blog.csdn.net/weixin_43934607/article/details/100141845
* SpringBoot整合Elasticsearch：https://blog.csdn.net/weixin_43934607/article/details/100149363
* Elasticsearch分词器与进阶知识：https://blog.csdn.net/weixin_43934607/article/details/102788229
* Elasticsearch集群搭建与相关知识：https://blog.csdn.net/weixin_43934607/article/details/103077157
###### RabbitMQ
* RabbitMQ的五种基本模型与高级特性：https://blog.csdn.net/weixin_43934607/article/details/100149371
* SpringBoot使用RabbitMQ高级特性：https://blog.csdn.net/weixin_43934607/article/details/100149384

#### 做完该项目的总结
* 使用到的技术总体来说比较平和，因为做这个项目之前，大多技术也都接触过，并且每项技术也并没深度使用。但是之前并未在同一个项目整合使用过这些技术，所以还是有一定难度。
* 认识到企业开发的通常使用的技术栈，与企业开发面对的一些问题，比如性能方面要求商品搜索使用高性能搜索引擎，商品详细页面要静态化等等。
* 微服务开发方面，学习了合理的开发方式，比如每个module都有两个子moudle，一个是对外的API，一个是该服务内部实现
* 业务方面，大致了解了电商项目一些需求与流程，比如B2B、B2C、C2C等，还有商品的spu、sku等等。

#### 视频教程 
链接：https://pan.baidu.com/s/19vx2vVm-OZym7q2z8utWvw 
提取码：s76n 
复制这段内容后打开百度网盘手机App，操作更方便哦
