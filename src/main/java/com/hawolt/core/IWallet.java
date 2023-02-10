package com.hawolt.core;

/**
 * Created: 10/02/2023 06:32
 * Author: Twitter @hawolt
 **/

public interface IWallet {
    void withdrawIP(long amount);

    void refundIP(long amount);

    void withdrawRP(long amount);

    void refundRP(long amount);

    long getRP();

    long getIP();
}
