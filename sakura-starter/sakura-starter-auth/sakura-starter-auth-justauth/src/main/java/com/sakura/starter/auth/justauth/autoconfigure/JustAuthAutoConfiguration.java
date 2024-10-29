/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sakura.starter.auth.justauth.autoconfigure;

import jakarta.annotation.PostConstruct;
import me.zhyd.oauth.cache.AuthStateCache;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import com.sakura.starter.auth.justauth.core.AuthStateCacheRedisDefaultImpl;
import com.sakura.starter.core.constant.PropertiesConstants;

/**
 * JustAuth 自动配置
 *
 * @author hagyao520
 * @since 1.0.0
 */
@AutoConfiguration(before = com.xkcoding.justauth.autoconfigure.JustAuthAutoConfiguration.class)
@ConditionalOnProperty(prefix = "justauth", name = PropertiesConstants.ENABLED, havingValue = "true", matchIfMissing = true)
public class JustAuthAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JustAuthAutoConfiguration.class);

    /**
     * State 缓存 Redis 实现（默认）
     */
    @Bean
    @ConditionalOnClass(RedisClient.class)
    @ConditionalOnProperty(prefix = "justauth.cache", name = "type", havingValue = "redis")
    public AuthStateCache authStateCache() {
        AuthStateCacheRedisDefaultImpl impl = new AuthStateCacheRedisDefaultImpl();
        log.debug("[SakurA Starter] - Auto Configuration 'JustAuth-AuthStateCache-Redis' completed initialization.");
        return impl;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[SakurA Starter] - Auto Configuration 'JustAuth' completed initialization.");
    }
}