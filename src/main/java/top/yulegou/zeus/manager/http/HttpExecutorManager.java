package top.yulegou.zeus.manager.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.manager.ZeusConfigManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class HttpExecutorManager {
    private String defaultUserAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70 Safari/537.36";
    private Map<String, String> defaultPostParam = new HashMap<>();
    private OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    public String doGet(String url, Map<String, String> headers) {
        return doGet(url, headers, false);
    }
    public String doGet(String url, Map<String, String> headers, boolean pageRender) {
        if (StringUtils.isBlank(url)) {
            log.error("url set is nul ");
            return "";
        }
        if (!StringUtils.contains(url, ":")) {
            url = "http://" + url;
        }

        if (headers == null ) {
            headers = new HashMap<>();
            headers.put(ZeusConfigManager.USER_AGENT, defaultUserAgent);
        } else if(headers.containsKey(ZeusConfigManager.USER_AGENT)) {
            headers.put(ZeusConfigManager.USER_AGENT, defaultUserAgent);
        }

        Request request = new Request.Builder()
                .headers(Headers.of(headers))
                .url(url)
                .get()
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() > 400) {
                log.error("url get html error occur " + url, response.code());
            } else {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("getHtml error " + url, e);
        } catch (Exception e) {
            log.error("getHtml error " + url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    public String doPost(String url, Map<String, String> headers, Map<String, String> params) {
        return doPost(url, headers, params, false);
    }

    public String doPost(String url, Map<String, String> headers, Map<String, String> params, boolean pageRender) {
        if (params == null) {
            params = defaultPostParam;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Iterator<Map.Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            builder.add(entry.getKey(), entry.getValue());
        }
        if (headers == null ) {
            headers = new HashMap<>();
            headers.put(ZeusConfigManager.USER_AGENT, defaultUserAgent);
        } else if(headers.containsKey(ZeusConfigManager.USER_AGENT)) {
            headers.put(ZeusConfigManager.USER_AGENT, defaultUserAgent);
        }
        Request request = new Request.Builder()
                .headers(Headers.of(headers))
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() > 400) {
                log.error("url get html error occur " + url, response.code());
            } else {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("postHtml " + url, e);
        } catch (Exception e) {
            log.error("postHtml " + url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }
}
