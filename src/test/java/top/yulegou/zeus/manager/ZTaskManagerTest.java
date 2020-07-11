package top.yulegou.zeus.manager;

import com.alibaba.fastjson.JSONObject;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.dao.domain.ZTask;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ZTaskManagerTest {
    @Autowired
    private ZeusTaskManager zeusTaskManager;
    @Test
    public  void taskAddTest() {
        ZTask task = new ZTask();
        task.settName("first2task");
        task.setCron("0 1/1 * * * *");
        task.settAuto(0);
        task.settConfig("");
        zeusTaskManager.insert(task);
        System.out.println(JSONObject.toJSONString(task));
    }

    @Test
    public void tastBeginTest() {
        ZTask task = zeusTaskManager.getTaskById(2);
        int i = zeusTaskManager.beginCollect(task);
        assertThat(i).isEqualTo(1);
        task =  zeusTaskManager.getTaskById(2);
        assertThat(task.getStatus()).isEqualTo(1);
        i = zeusTaskManager.endCollect(task);
        assertThat(i).isEqualTo(1);
        task =  zeusTaskManager.getTaskById(2);
        assertThat(task.getStatus()).isEqualTo(0);
    }
}
