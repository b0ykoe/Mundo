package com.hawolt.core;

import org.json.JSONObject;

/**
 * Created: 10/02/2023 05:33
 * Author: Twitter @hawolt
 **/

public class Player implements IWallet {
    private long accountId, summonerLevel, rp, ip;
    private IWalletUpdate wallet;

    public Player() {

    }

    public Player(IWalletUpdate wallet, JSONObject object) {
        this.summonerLevel = object.getLong("summonerLevel");
        this.accountId = object.getLong("accountId");
        this.rp = object.getLong("rp");
        this.ip = object.getLong("ip");
        this.wallet = wallet;
        this.wallet.onUpdate(ip, rp);
    }

    public long getAccountId() {
        return accountId;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    @Override
    public long getRP() {
        return rp;
    }

    @Override
    public long getIP() {
        return ip;
    }

    @Override
    public void withdrawRP(long amount) {
        this.rp -= amount;
        this.wallet.onUpdate(ip, rp);
    }

    @Override
    public void refundRP(long amount) {
        this.rp += amount;
        this.wallet.onUpdate(ip, rp);
    }

    @Override
    public void withdrawIP(long amount) {
        this.ip -= amount;
        this.wallet.onUpdate(ip, rp);
    }

    @Override
    public void refundIP(long amount) {
        this.ip += amount;
        this.wallet.onUpdate(ip, rp);
    }

}
