package cn.store.modules.goods.entity.enums;

/**
 * 销售模式
 */
public enum GoodsSalesModeEnum {

    RETAIL("零售"),
    WHOLESALE("批发");

    private final String description;

    GoodsSalesModeEnum(String description) {
        this.description = description;

    }

    public String description() {
        return description;
    }

}
