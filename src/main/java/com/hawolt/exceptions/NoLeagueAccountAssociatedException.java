package com.hawolt.exceptions;

/**
 * Created: 10/02/2023 05:05
 * Author: Twitter @hawolt
 **/

public class NoLeagueAccountAssociatedException extends Exception {
    public NoLeagueAccountAssociatedException() {
        super("Unable to find League of Legends account to Riot Account");
    }
}
