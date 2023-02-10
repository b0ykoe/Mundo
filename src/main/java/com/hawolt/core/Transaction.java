package com.hawolt.core;

import org.json.JSONObject;

import java.util.Objects;

/**
 * Created: 10/02/2023 05:41
 * Author: Twitter @hawolt
 **/

public class Transaction {
    private final long itemId, amountSpent;
    private final String inventoryType, datePurchased, transactionId, currencyType;
    private final boolean refundable;

    private String refundMessage, refundabilityMessage;
    private boolean requiresToken;

    public Transaction(JSONObject object) {
        this.itemId = object.getLong("itemId");
        this.amountSpent = object.getLong("amountSpent");
        this.refundable = object.getBoolean("refundable");
        this.currencyType = object.getString("currencyType");
        this.inventoryType = object.getString("inventoryType");
        this.datePurchased = object.getString("datePurchased");
        this.transactionId = object.getString("transactionId");
        if (object.has("requiresToken")) this.requiresToken = object.getBoolean("requiresToken");
        if (object.has("refundMessage")) this.refundMessage = object.getString("refundMessage");
        if (object.has("refundabilityMessage")) this.refundabilityMessage = object.getString("refundabilityMessage");
    }

    public long getItemId() {
        return itemId;
    }

    public long getAmountSpent() {
        return amountSpent;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getRefundMessage() {
        return refundMessage;
    }

    public String getRefundabilityMessage() {
        return refundabilityMessage;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public boolean isRequiresToken() {
        return requiresToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return itemId == that.itemId && amountSpent == that.amountSpent && refundable == that.refundable && requiresToken == that.requiresToken && Objects.equals(inventoryType, that.inventoryType) && Objects.equals(datePurchased, that.datePurchased) && Objects.equals(transactionId, that.transactionId) && Objects.equals(currencyType, that.currencyType) && Objects.equals(refundMessage, that.refundMessage) && Objects.equals(refundabilityMessage, that.refundabilityMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, amountSpent, inventoryType, datePurchased, transactionId, currencyType, refundable, refundMessage, refundabilityMessage, requiresToken);
    }

    @Override
    public String toString() {
        String currency = String.format("%s %s", amountSpent, currencyType);
        String item = String.format("Item: %s", itemId);
        return String.join(" - ", datePurchased, currency, item);
    }
}
