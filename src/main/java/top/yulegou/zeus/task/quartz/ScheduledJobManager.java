package top.yulegou.zeus.task.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZTask;

@Component
public class ScheduledJobManager {
    @Autowired
    private QuartzManager quartzManager;

    public void add(ZTask job) {

        //此处省去数据验证
//        this.save(job);

        //加入job
        try {
            quartzManager.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
