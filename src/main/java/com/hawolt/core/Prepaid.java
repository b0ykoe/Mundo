package com.hawolt.core;

import com.hawolt.Main;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created: 10/02/2023 07:15
 * Author: Twitter @hawolt
 **/

public class Prepaid {
    private final Account account;
    private final Player player;
    private final String edge;

    public Prepaid(Account account, String edge, Player player) {
        this.account = account;
        this.player = player;
        this.edge = edge;
    }

    private String createSession() throws IOException {
        JSONObject object = new JSONObject();
        object.put("game", "lol");
        object.put("gifteeAccountId", "");
        object.put("gifteeMessage", "");
        object.put("isPrepaid", "false");
        object.put("localeId", "en_GB");
        object.put("summonerLevel", player.getSummonerLevel());
        RequestBody post = RequestBody.create(MediaType.parse("application/json"), object.toString());
        Request request = new Request.Builder()
                .url(String.join("/", "https:/", edge, "riotpay/pmc/v2/lol/sessions"))
                .addHeader("Authorization", String.format("Bearer %s", account.get("access_token")))
                .addHeader("User-Agent", "LeagueOfLegendsClient/ (rcp-be-payments)")
                .post(post)
                .build();
        Call call = Main.httpClient.newCall(request);
        try (Response response = call.execute()) {
            try (ResponseBody body = response.body()) {
                if (body == null) throw new IOException();
                JSONObject o = new JSONObject(body.string());
                return o.getString("token");
            }
        }
    }

    public boolean redeem(IWallet wallet, String code) {
        JSONObject object = new JSONObject();
        object.put("prepaidCode", code);
        object.put("cpf", JSONObject.NULL);
        object.put("firstName", JSONObject.NULL);
        object.put("lastName", JSONObject.NULL);
        object.put("dateOfBirth", JSONObject.NULL);
        RequestBody post = RequestBody.create(MediaType.parse("application/json"), object.toString());
        try {
            Request request = new Request.Builder()
                    .url(String.join("/", "https:/", edge, "riotpay/pmc/v2/redeem-prepaid-code"))
                    .addHeader("Authorization", String.format("Session %s", createSession()))
                    .post(post)
                    .build();
            Call call = Main.httpClient.newCall(request);
            try (Response response = call.execute()) {
                if (response.code() != 200) return false;
                try (ResponseBody body = response.body()) {
                    if (body == null) throw new IOException();
                    JSONObject o = new JSONObject(body.string());
                    JSONObject purchase = o.getJSONObject("purchasedPricePoint");
                    wallet.refundRP(purchase.getLong("virtualAmount") + purchase.getLong("virtualBonusAmount"));
                    return o.getBoolean("success");
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
