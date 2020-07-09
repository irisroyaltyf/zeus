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

import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebExchangeDataBinder;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.yulegou.zeus.config.ListData;
import top.yulegou.zeus.domain.PublishTableParamsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author irisroyalty
 * @date 2020/7/9
 **/
public class ListDataResolver extends AbstractMessageReaderArgumentResolver {
    public ListDataResolver(List<HttpMessageReader<?>> readers) {
        super(readers);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ListData.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        Mono<Map<String, Object>> values = WebExchangeDataBinder.extractValuesToBind(exchange);
        return values.map(valus -> encap(valus));
    }
    private List<Object> encap(Map<String, Object> params) {
        List<Object> l = new ArrayList<>();
        params.entrySet().stream().forEach(e -> {
            if (e.getValue() instanceof ArrayList) {
                l.addAll((List<Object>)e.getValue());
            } else {
                if (e.getKey().contains("[]")) {
                    l.add(e.getValue());
                }
            }
        });
        return l;
    }
}
