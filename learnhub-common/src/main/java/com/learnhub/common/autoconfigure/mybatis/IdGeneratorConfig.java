package com.learnhub.common.autoconfigure.mybatis;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.learnhub.common.autoconfigure.mybatis.plugin.AutoIdInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/3 15:56
 */
@Configuration
@ConditionalOnClass(Interceptor.class)
public class IdGeneratorConfig {
    @Bean
    public IdGeneratorOptions idGeneratorOptions() {
        // 创建 IdGeneratorOptions 对象，可在构造函数中输入 WorkerId：
        IdGeneratorOptions options = new IdGeneratorOptions();
        // options.WorkerIdBitLength = 10; // 默认值6，限定 WorkerId 最大值为2^6-1，即默认最多支持64个节点。
        // options.SeqBitLength = 6; // 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
        // options.BaseTime = Your_Base_Time; // 如果要兼容老系统的雪花算法，此处应设置为老系统的BaseTime。
        // ...... 其它参数参考 IdGeneratorOptions 定义。

        // 保存参数（务必调用，否则参数设置不生效）：
        YitIdHelper.setIdGenerator(options);
        return options;
    }

    @Bean
    public AutoIdInterceptor autoIdInterceptor() {
        return new AutoIdInterceptor();
    }
}
