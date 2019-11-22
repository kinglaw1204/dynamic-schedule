# dynamic-schedule

[![Build Status](https://img.shields.io/badge/schedule-dynamic--schedule-brightgreen.svg)](https://github.com/kinglaw1204/dynamic-schedule)
## 介绍
dynamic-schedule是一款轻量级调度框架，它主要解决传统调度框架不支持动态定时任务的问题，通过dynamic-schedule你可以解决如下场景的问题：

- 需要以不同时间差进行调用任务，比如接口调用回调，为了保证最大限度通知，可以调用多次，假设对方系统不可用，可以以不同的时间差调用，给对方已恢复时间，同时保证业务处理逻辑不丢失。
- 异步延时处理任务

## 快速开始

### 普通模式

**1.下载代码**

```
git clone https://github.com/kinglaw1204/dynamic-schedule.git
```

**2.安装依赖**

```
mvn install
```

**3.添加依赖**

```
<dependency>
   <groupId>top.luozhou</groupId>
   <artifactId>dynamic-schedule-core</artifactId>
   <version>1.0.0-SNAPSHOT</version>
</dependency>
```

**4.初始化配置**

 在项目启动时只需执行下面语句即可：

```java
new ScheduleConfig().init();
```

比如springboot中：

```java
public static void main(String[] args) {
 			 new ScheduleConfig().init();
       new SpringApplication(Test.class).run(args);
    }
```

启动成功即可

**5.添加job**

```java
/**获取一个job添加器worker*/ 
Iworker worker = DefaultWorker.getWorker();
/**构造一个job*/
AbstractJob<Teacher> job = new PersistenceTestJob();
 job.setBody(new Teacher("小粥老师"));
/**给job设置调度策略，这里是内置的默认多级时间调度策略，单位是毫秒，可以自定义实现，这里的意思是
调度3次，第一次1秒，第二次2秒，第三次3秒*/ 
job.setStrategy(new MultiTimesStragegy(new long[]{1000,2000,3000}));
/**把job添加给woker*/ 
worker.addJob(job);
```



### 持久化模式

持久化模式流程和普通模式差别不大，只需多两个步骤：

**1.创建sqllite数据库，初始化脚本，脚本内容在代码db目录下**

**2.初始化配置设置持久化模式，配置数据库url**

```java
 ScheduleConfig config = new ScheduleConfig();
        config.setPersistence(true);
        config.setJdbcUrl("jdbc:sqlite:/xxxxx/dynamic-schedule.sqlite");
        config.init();

```



## 架构设计
![images](https://github.com/kinglaw1204/dynamic-schedule/blob/master/images/%E6%9E%B6%E6%9E%84%E5%9B%BE.png?raw=true)


## TODOLIST
 * [x] [支持自定义扩展调度策略](#)
 * [x] [支持多倍时间差多次调度](#)
 * [x] [支持定时任务持久化](#)
 * [x] [支持重启自动执行未完成任务](#)
## 联系作者
- [kinglaw1204@gmail.com](mailto:kinglaw1204@gmail.com)
