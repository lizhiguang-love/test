package com.example.demo.lzg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncScheduledTaskConfig {
    @Value("${spring.task.execution.pool.core-size}")
    private String corePoolSize;
    @Value("${spring.task.execution.pool.max-size}")
    private String maxPoolSize;
    @Bean
    public Executor customAsyncThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //最大线程数
        executor.setMaxPoolSize(Integer.valueOf(maxPoolSize));
        //核心线程数
        executor.setCorePoolSize(Integer.valueOf(corePoolSize));
        //线程存活时间
        executor.setKeepAliveSeconds(30);
        //阻塞队列
        executor.setQueueCapacity(100);
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //线程初始化
        executor.initialize();
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(30);
        return executor;
    }
}
