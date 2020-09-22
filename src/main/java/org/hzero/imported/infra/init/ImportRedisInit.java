package org.hzero.imported.infra.init;

import java.util.List;

import org.hzero.boot.imported.infra.redis.TemplateRedis;
import org.hzero.core.message.MessageAccessor;
import org.hzero.core.redis.RedisHelper;
import org.hzero.imported.domain.entity.TemplateHeader;
import org.hzero.imported.domain.repository.TemplateHeaderRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 初始化缓存
 *
 * @author shuangfei.zhu@hand-china.com 2019/05/24 16:07
 */
@Component
public class ImportRedisInit implements InitializingBean {

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;
    @Autowired
    private RedisHelper redisHelper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加入消息文件
        MessageAccessor.addBasenames("classpath:messages/messages_himp");

        List<TemplateHeader> list = templateHeaderRepository.selectAll();
        list.forEach(item -> TemplateRedis.refreshCache(redisHelper, item.getTenantId(), item.getTemplateCode()));
    }
}
