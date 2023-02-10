package com.hawolt.core;

import com.hawolt.Main;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created: 10/02/2023 03:52
 * Author: Twitter @hawolt
 **/

public class LoginSession {

    private static String build(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (String string : list) {
            String[] data = string.split(";");
            builder.append(data[0]).append("; ");
        }
        return builder.toString().trim();
    }

    public static String getCookie() throws IOException {
        return getCookie(getCookie(null));
    }

    public static String getCookie(String __cf_bm) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject object = new JSONObject();
        object.put("acr_values", "urn:riot:bronze");
        object.put("claims", "");
        object.put("client_id", "riot-client");
        object.put("nonce", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 22));
        object.put("redirect_uri", "http://localhost/redirect");
        object.put("response_type", "token id_token");
        object.put("scope", "openid link ban lol_region");
        RequestBody post = RequestBody.create(mediaType, object.toString());
        Request.Builder builder = new Request.Builder()
                .url("https://auth.riotgames.com/api/v1/authorization")
                .addHeader("Content-Type", "application/json")
                .post(post);
        if (__cf_bm != null) builder.addHeader("Cookie", __cf_bm);
        Request request = builder.build();
        Call call = Main.httpClient.newCall(request);
        try (Response response = call.execute()) {
            if (__cf_bm == null) return build(response.headers("set-cookie"));
            if (response.code() == 200) {
                return build(response.headers("set-cookie"));
            }
        }
        throw new IOException("No Cookie given");
    }
}
