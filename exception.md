 {
  "tasks": [
    {
      "title": "学习Spring Cloud Alibaba核心组件",
      "description": "阅读Nacos服务注册发现文档并完成基础搭建实验",
      "startTime": "12:30:00",
      "endTime": "13:30:00"
    },
    {
      "title": "微服务项目实践",
      "description": "使用Spring Boot+Spring Cloud搭建商品服务基础框架",
      "startTime": "16:30:00",
      "endTime": "17:30:00"
    },
    {
      "title": "分布式系统设计学习",
      "description": "研究CAP理论与Base理论在微服务架构中的应用，结合Redis分布式锁实现案例",
      "startTime": "19:00:00",
      "endTime": "20:00:00"
    }
  ]
}
2025-08-20T15:00:00.731+08:00 ERROR 42748 --- [BitGain] [oundedElastic-1] c.l.b.s.impl.BitGainDesignServiceImpl    : 解析AI返回JSON失败

java.time.format.DateTimeParseException: Text '12:30:00' could not be parsed, unparsed text found at index 5
	at java.base/java.time.format.DateTimeFormatter.parseResolved0(DateTimeFormatter.java:2055) ~[na:na]
	at java.base/java.time.format.DateTimeFormatter.parse(DateTimeFormatter.java:1954) ~[na:na]
	at java.base/java.time.LocalTime.parse(LocalTime.java:465) ~[na:na]
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.parseAIResponseAndSaveTasks(BitGainDesignServiceImpl.java:246) ~[classes/:na]
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.lambda$bitGainDesign$2(BitGainDesignServiceImpl.java:108) ~[classes/:na]
	at reactor.core.publisher.LambdaSubscriber.onComplete(LambdaSubscriber.java:132) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onComplete(FluxFilter.java:166) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableConditionalSubscriber.onComplete(FluxPeekFuseable.java:595) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.doComplete(FluxPublishOn.java:1106) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.checkTerminated(FluxPublishOn.java:1140) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.runAsync(FluxPublishOn.java:996) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.run(FluxPublishOn.java:1079) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37) ~[reactor-core-3.7.8.jar:3.7.8]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]

2025-08-20T15:00:00.734+08:00 ERROR 42748 --- [BitGain] [oundedElastic-1] c.l.b.s.impl.BitGainDesignServiceImpl    : 解析AI响应失败

java.lang.RuntimeException: 解析AI返回数据失败: Text '12:30:00' could not be parsed, unparsed text found at index 5
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.parseAIResponseAndSaveTasks(BitGainDesignServiceImpl.java:264) ~[classes/:na]
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.lambda$bitGainDesign$2(BitGainDesignServiceImpl.java:108) ~[classes/:na]
	at reactor.core.publisher.LambdaSubscriber.onComplete(LambdaSubscriber.java:132) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onComplete(FluxFilter.java:166) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableConditionalSubscriber.onComplete(FluxPeekFuseable.java:595) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.doComplete(FluxPublishOn.java:1106) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.checkTerminated(FluxPublishOn.java:1140) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.runAsync(FluxPublishOn.java:996) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.run(FluxPublishOn.java:1079) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37) ~[reactor-core-3.7.8.jar:3.7.8]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]

2025-08-20T15:00:00.743+08:00 ERROR 42748 --- [BitGain] [nio-8080-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] threw exception

java.lang.RuntimeException: 解析AI返回数据失败: Text '12:30:00' could not be parsed, unparsed text found at index 5
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.parseAIResponseAndSaveTasks(BitGainDesignServiceImpl.java:264) ~[classes/:na]
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.lambda$bitGainDesign$2(BitGainDesignServiceImpl.java:108) ~[classes/:na]
	at reactor.core.publisher.LambdaSubscriber.onComplete(LambdaSubscriber.java:132) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onComplete(FluxFilter.java:166) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableConditionalSubscriber.onComplete(FluxPeekFuseable.java:595) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.doComplete(FluxPublishOn.java:1106) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.checkTerminated(FluxPublishOn.java:1140) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.runAsync(FluxPublishOn.java:996) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.run(FluxPublishOn.java:1079) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37) ~[reactor-core-3.7.8.jar:3.7.8]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]

2025-08-20T15:00:00.744+08:00 ERROR 42748 --- [BitGain] [nio-8080-exec-3] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.RuntimeException: 解析AI返回数据失败: Text '12:30:00' could not be parsed, unparsed text found at index 5] with root cause

java.lang.RuntimeException: 解析AI返回数据失败: Text '12:30:00' could not be parsed, unparsed text found at index 5
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.parseAIResponseAndSaveTasks(BitGainDesignServiceImpl.java:264) ~[classes/:na]
	at com.lucius.bitgain.service.impl.BitGainDesignServiceImpl.lambda$bitGainDesign$2(BitGainDesignServiceImpl.java:108) ~[classes/:na]
	at reactor.core.publisher.LambdaSubscriber.onComplete(LambdaSubscriber.java:132) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onComplete(FluxFilter.java:166) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxMap$MapConditionalSubscriber.onComplete(FluxMap.java:275) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekConditionalSubscriber.onComplete(FluxPeekFuseable.java:940) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxHandle$HandleConditionalSubscriber.onComplete(FluxHandle.java:440) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxContextWrite$ContextWriteSubscriber.onComplete(FluxContextWrite.java:126) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxDoFinally$DoFinallySubscriber.onComplete(FluxDoFinally.java:128) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableConditionalSubscriber.onComplete(FluxPeekFuseable.java:595) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.doComplete(FluxPublishOn.java:1106) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.checkTerminated(FluxPublishOn.java:1140) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.runAsync(FluxPublishOn.java:996) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.publisher.FluxPublishOn$PublishOnConditionalSubscriber.run(FluxPublishOn.java:1079) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:84) ~[reactor-core-3.7.8.jar:3.7.8]
	at reactor.core.scheduler.WorkerTask.call(WorkerTask.java:37) ~[reactor-core-3.7.8.jar:3.7.8]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136) ~[na:na]
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:840) ~[na:na]

2025-08-20T15:00:00.752+08:00  WARN 42748 --- [BitGain] [nio-8080-exec-3] .w.s.m.s.DefaultHandlerExceptionResolver : Ignoring exception, response committed already: org.springframework.http.converter.HttpMessageNotWritableException: No converter for [class java.util.LinkedHashMap] with preset Content-Type 'text/event-stream'
2025-08-20T15:00:00.752+08:00  WARN 42748 --- [BitGain] [nio-8080-exec-3] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotWritableException: No converter for [class java.util.LinkedHashMap] with preset Content-Type 'text/event-stream']
