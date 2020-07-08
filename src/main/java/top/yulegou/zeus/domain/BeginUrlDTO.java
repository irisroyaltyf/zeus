package top.yulegou.zeus.domain;

import lombok.Data;
import reactor.util.annotation.Nullable;

import java.util.List;

@Data
public class BeginUrlDTO {
    @Nullable
    Integer taskId;
    @Nullable
    Integer crawlerId;
    @Nullable
    List<String> fromUrls;
    Boolean sourceIsContent;
}
