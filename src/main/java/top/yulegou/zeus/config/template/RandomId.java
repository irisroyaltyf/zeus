package top.yulegou.zeus.config.template;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

public class RandomId implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        Random random = new Random();
        return StringUtils.substring(arguments.get(0).toString(), 0, 8) + random.nextInt(100);
    }
}
