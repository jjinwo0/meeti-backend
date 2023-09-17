package yjhb.meeti.domain.approval.constant;

public enum Decision {

    CONFIRM, REJECT;

    public static Decision from(String decision){
        return Decision.valueOf(decision);
    }
}
