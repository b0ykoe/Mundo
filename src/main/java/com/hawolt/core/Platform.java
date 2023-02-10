package com.hawolt.core;

/**
 * Created: 10/02/2023 05:25
 * Author: Twitter @hawolt
 **/

public enum Platform {
    BR1(null),
    EUN1(null),
    EUW1("edge.rgl.pmc.pay.riotgames.com"),
    JP1(null),
    KR(null),
    LA1(null),
    LA2(null),
    NA1("edge.rgi.pmc.pay.riotgames.com"),
    OC1(null),
    TR1(null),
    RU(null),
    PH2(null),
    SG2(null),
    TH2(null),
    TW2(null),
    VN2(null);
    final String edge;

    Platform(String edge) {
        this.edge = edge;
    }

    public String getEdge() {
        return edge;
    }

    public String translateToWebRegion() {
        String webRegion = (this == EUN1 ? "EUNE" :
                (this == LA1 || this == LA2 || this == OC1) ? name() :
                        name().replaceAll("[0-9]", ""));
        return webRegion.toLowerCase();
    }
}
