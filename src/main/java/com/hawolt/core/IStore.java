package com.hawolt.core;

import java.io.IOException;

/**
 * Created: 10/02/2023 06:22
 * Author: Twitter @hawolt
 **/

public interface IStore {
    boolean refund(Transaction transaction) throws IOException;

    boolean purchaseSummonerNameChange(Currency itemAt, String text);

    boolean redeemPrepaidCode(String code);
}
