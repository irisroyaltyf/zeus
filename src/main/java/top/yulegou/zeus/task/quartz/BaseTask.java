package top.yulegou.zeus.task.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.manager.ZeusTaskManager;
import top.yulegou.zeus.util.ZeusBeanUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class BaseTask  implements Job {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ZTask scheduleJob = (ZTask) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");

        logger.info("do " + scheduleJob.gettName() + " " + scheduleJob.getCron());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.error("docollect task is null " + sdf.format(new Date()));
        ZeusTaskManager zeusTaskManager = ZeusBeanUtil.getBean(ZeusTaskManager.class);
        zeusTaskManager.collect(scheduleJob.getId());
    }
}
