package top.yulegou.zeus.domain;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 内容页配置
 */
@Data
public class CrawlerContentDTO {
    private Integer taskId;
    private Integer crawlerId;
    private List<String> field;
}
