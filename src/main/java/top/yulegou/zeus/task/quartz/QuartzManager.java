package top.yulegou.zeus.task.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZTask;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class QuartzManager {
    private static final  String SCHEDULE_JOB = "scheduleJob";
    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;

    public void addJob(ZTask job) {
        try {
            if (job.getCron() == null) {
                log.error("job start error " + job.getId());
                return ;
            }
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.gettName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                    .startNow()
                    .build();

            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(BaseTask.class)
                    .withIdentity(job.getJobName())
                    .build();

            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put(SCHEDULE_JOB, job);

            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void operateJob(ZTask job, int operator) throws SchedulerException {
        JobKey jobKey = new JobKey(job.getJobName());
        switch (operator) {
            case Constants
                    .OPERATE_JOB_START:
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                if (jobDetail != null) {
                    ZTask schTask = (ZTask) jobDetail.getJobDataMap().get(SCHEDULE_JOB);
                    if (schTask != null && StringUtils.equals(schTask.getCron(), job.getCron())) {
                        scheduler.deleteJob(jobKey);
                    } else {
                        scheduler.resumeJob(jobKey);
                        break;
                    }
                }
                addJob(job);
                break;
            case Constants.OPERATE_JOB_STOP:
                scheduler.pauseJob(jobKey);
                break;
//            case DELETE:
//                scheduler.deleteJob(jobKey);
//                break;
        }
    }

    public static long getNextTriggerTime(String cron) {
        if (StringUtils.isEmpty(cron)) {return 0;}
        if (!CronExpression.isValidExpression(cron)) {
            return 0;
        }
        try {
            CronExpression cronExpression = new CronExpression(cron);
            Date next = cronExpression.getTimeAfter(new Date());
            return  next.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

}
