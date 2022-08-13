package gamelogic.play;

public enum ActionType {
    TAKE_INCOME,
    TAKE_FOREIGN_AID,
    TAKE_COUP,
    TAKE_NEW_EXCHANGE,
    TAKE_TAX,
    TAKE_ASSASSINATE,
    TAKE_STEAL,
    TAKE_EXCHANGE;

    public boolean isChallengable() {
        if (this == TAKE_TAX ||
                this == TAKE_ASSASSINATE ||
                this == TAKE_STEAL ||
                this == TAKE_EXCHANGE) return true;
        return false;
    }

    public boolean isBlockable() {
        if (this == TAKE_FOREIGN_AID || this == TAKE_ASSASSINATE || this == TAKE_STEAL) {
            return true;
        }
        return false;
    }

    public String getAction() {
        if (this == TAKE_FOREIGN_AID) return "take Foreign Aid";
        if (this == TAKE_ASSASSINATE) return "take Assassinate";
        if (this == TAKE_STEAL) return "take Steal";
        if (this == TAKE_TAX) return "take Tax";
        if (this == TAKE_EXCHANGE) return "take Exchange";
        if (this == TAKE_COUP) return "take Coup";
        if (this == TAKE_INCOME) return "take income";
        if (this == TAKE_NEW_EXCHANGE) return "take new exchange";
        return "";
    }
}
