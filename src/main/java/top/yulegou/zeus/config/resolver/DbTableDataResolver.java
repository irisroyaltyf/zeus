package top.yulegou.zeus.config.resolver;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.PropertyAccessor;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebExchangeDataBinder;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.yulegou.zeus.config.DbFieldData;
import top.yulegou.zeus.dao.domain.publish.DbColumnBindConfig;
import top.yulegou.zeus.domain.PublishTableParamsDTO;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author irisroyalty
 * @date 2020/6/29
 **/
@Slf4j
public class DbTableDataResolver extends AbstractMessageReaderArgumentResolver {
    public DbTableDataResolver(List<HttpMessageReader<?>> readers) {
        super(readers);
    }

    public DbTableDataResolver(List<HttpMessageReader<?>> messageReaders, ReactiveAdapterRegistry adapterRegistry) {
        super(messageReaders, adapterRegistry);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(DbFieldData.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        Mono<Map<String, Object>> values = WebExchangeDataBinder.extractValuesToBind(exchange);
        return values.map(valus -> encap(valus));
    }

    private PublishTableParamsDTO encap(Map<String, Object> params) {
        PublishTableParamsDTO publishTableParamsDTO = new PublishTableParamsDTO();
        Map<String, DbColumnBindConfig> tableParamsMap = new TreeMap<>();
        params.entrySet().stream().forEach(e -> {
            int l = e.getKey().length();
            if (l < 1) {
                log.info("length less then 1 " + e.getKey());
                return ;
            }
            if (StringUtils.equals(e.getKey(), "taskId") ){
                publishTableParamsDTO.setTaskId(Integer.valueOf(e.getValue().toString()));
            } else if (StringUtils.equals(e.getKey(), "publishRuleId")) {
                publishTableParamsDTO.setPublishRuleId(Integer.valueOf(e.getValue().toString()));
            } else {
                DbColumnBindConfig paramsDTO = null;
                String key = null, table = null, field = null;
                int start = 0;
                int pos = getFirstSepIndex(e.getKey(), '[', start);
                if (pos > 0) {
                    key = StringUtils.substring(e.getKey(), start, pos);
                    start = pos + 1;
                    pos = getFirstSepIndex(e.getKey(), ']', start);
                }
                if (pos > 0) {
                    table = StringUtils.substring(e.getKey(), start, pos);
                    if (e.getKey().charAt(pos + 1) == '[' && e.getKey().charAt(l - 1) == ']') {
                        field = StringUtils.substring(e.getKey(), pos + 2, l - 1);
                    }
                }
                if (tableParamsMap.containsKey(table)) {
                    paramsDTO = tableParamsMap.get(table);
                } else {
                    paramsDTO = new DbColumnBindConfig();
                    paramsDTO.setFieldBind(new TreeMap<>());
                    tableParamsMap.put(table, paramsDTO);
                    paramsDTO.setTableName(table);
                }
                if (StringUtils.equals(key, "dbTableField")) {
                    String value = e.getValue().toString();
                    if (StringUtils.isNotEmpty(value) && !StringUtils.equals(value, "custom:")) {
                        paramsDTO.getFieldBind().put(field, value);
                    }
                } else if (StringUtils.equals(key, "dbTableCustom")) {
                    String value = e.getValue().toString();
                    if (!paramsDTO.getFieldBind().containsKey(field)) {
                        paramsDTO.getFieldBind().put(field, "custom:" + value);
                    }
                }
            }
        });
        publishTableParamsDTO.setTableParams(tableParamsMap.values().stream().collect(Collectors.toList()));
        return  publishTableParamsDTO;
    }

    private static int getFirstSepIndex(String propertyPath, char sep, int i ) {
        int length = propertyPath.length();
        while (i < length) {
            if (propertyPath.charAt(i) == sep) {
                return i;
            }
            i++;
        }
        return -1;
    }

}
