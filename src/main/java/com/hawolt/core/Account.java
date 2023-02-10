package com.hawolt.core;

import com.hawolt.Main;
import com.hawolt.exceptions.NoLeagueAccountAssociatedException;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * Created: 10/02/2023 03:52
 * Author: Twitter @hawolt
 **/

public class Account extends HashMap<String, String> {
    private final String username, password;
    private Store store;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isLoggedIn() {
        return containsKey("access_token") && containsKey("id_token") && containsKey("cpid");
    }

    public Store getStore(IWalletUpdate wallet) throws IllegalAccessException, IOException {
        if (!isLoggedIn()) throw new IllegalAccessException("You have to login before accessing the Store");
        if (store == null) store = new Store(this, wallet);
        return store;
    }

    public void login() throws IOException, NoLeagueAccountAssociatedException {
        String cookie = LoginSession.getCookie();
        JSONObject object = new JSONObject();
        object.put("type", "auth");
        object.put("username", username);
        object.put("password", password);
        object.put("remember", false);
        object.put("language", "en_GB");
        object.put("region", JSONObject.NULL);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody put = RequestBody.create(mediaType, object.toString());
        Request request = new Request.Builder()
                .url("https://auth.riotgames.com/api/v1/authorization")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36")
                .addHeader("Pragma", "no-cache")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", cookie)
                .put(put)
                .build();
        Call call = Main.httpClient.newCall(request);
        try (Response response = call.execute()) {
            try (ResponseBody body = response.body()) {
                if (body == null) return;
                String content = body.string();
                JSONObject o = new JSONObject(content);
                if (o.has("response")) {
                    JSONObject nestOne = o.getJSONObject("response");
                    if (nestOne.has("parameters")) {
                        JSONObject nestTwo = nestOne.getJSONObject("parameters");
                        if (nestTwo.has("uri")) {
                            String values = nestTwo.getString("uri");
                            String client = values.split("#")[1];
                            String[] parameters = client.split("&");
                            for (String parameter : parameters) {
                                String[] pair = parameter.split("=");
                                put(pair[0], pair[1]);
                            }
                            JSONObject id = new JSONObject(new String(Base64.getDecoder().decode(get("id_token").split("\\.")[1])));
                            if (!id.has("lol_region")) throw new NoLeagueAccountAssociatedException();
                            JSONArray lol = id.getJSONArray("lol_region");
                            for (int i = 0; i < lol.length(); i++) {
                                JSONObject account = lol.getJSONObject(i);
                                if (!account.getBoolean("active")) continue;
                                for (String key : account.keySet()) {
                                    put(key, String.valueOf(account.get(key)));
                                }
                            }
                            put("sub", id.getString("sub"));
                        }
                    }
                }
            }
        }
    }
}
