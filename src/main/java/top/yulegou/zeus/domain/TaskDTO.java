package top.yulegou.zeus.domain;

import lombok.Data;

@Data
public class TaskDTO {
    String taskName;
    Integer taskId;
    Integer auto;
    String cronTime;
}
