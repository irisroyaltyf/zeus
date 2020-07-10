package top.yulegou.zeus.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZConfig;
import top.yulegou.zeus.dao.domain.ZConfigExample;
import top.yulegou.zeus.dao.mapper.ZConfigMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ZeusConfigManager {
    public static final String ROBOTS =  "robots";
    public static final String PROXY  =  "proxy";
    public static final String USER_AGENT= "user-agent";
    public static final String REFER = "refer";

    private Map<String, ZConfig> cachedConfig = new HashMap<>();

    @Autowired
    private ZConfigMapper zConfigMapper;

    public ZConfig getCachedConfig(String cname) {
        if (cachedConfig.containsKey(cname)) {
            return cachedConfig.get(cname);
        } else {
            synchronized (cname.intern()) {
                if (cachedConfig.containsKey(cname)) {
                    return cachedConfig.get(cname);
                } else {
                    ZConfigExample example = new ZConfigExample();
                    example.createCriteria().andCnameEqualTo(cname);
                    List<ZConfig> configs = zConfigMapper.selectByExampleWithBLOBs(example);
                    if (configs != null && configs.size() > 0) {
                        cachedConfig.put(cname, configs.get(0));
                        return configs.get(0);
                    }
                }
            }
        }
        return null;
    }
    public void updateCachedConfig(ZConfig config) {
        cachedConfig.put(config.getCdata(), config);
    }

    public int insertConfig(ZConfig zConfig) {
        Date now = new Date();
        zConfig.setGmtCreate(now.getTime());
        zConfig.setGmtModified(now.getTime());
        return zConfigMapper.insertSelective(zConfig);
    }

    public int updateConfigDb(ZConfig config) {
        config.setGmtModified(new Date().getTime());
        return zConfigMapper.updateByPrimaryKeyWithBLOBs(config);
    }

    public int updateConfig(ZConfig config) {
        ZConfig cachedConfig = getCachedConfig(config.getCname());
        if (cachedConfig != null) {
            cachedConfig.setCdata(config.getCdata());
            cachedConfig.setCtype(cachedConfig.getCtype());
            return updateConfigDb(cachedConfig);
        } else {
            return insertConfig(config);
        }
    }

    public List<ZConfig> getConfigs(ZConfigExample example) {
        return zConfigMapper.selectByExample(example);
    }

}
