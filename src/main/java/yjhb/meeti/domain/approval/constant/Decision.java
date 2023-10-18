package yjhb.meeti.domain.approval.constant;

public enum Decision {

    CONFIRM, REJECT, WAIT;

    public static Decision from(String decision){
        return Decision.valueOf(decision);
    }
}
