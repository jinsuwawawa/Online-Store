package cn.store.modules.goods.entity.enums;

/**
 * 草稿商品保存类型
 **/
public enum DraftGoodsSaveType {

    /**
     * "草稿"
     */
    DRAFT("草稿"),
    /**
     * "模版"
     */
    TEMPLATE("模版");

    private final String description;

    DraftGoodsSaveType(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

}
