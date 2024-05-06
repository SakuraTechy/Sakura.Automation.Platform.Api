package com.sakura.common.utils.sort;

/**
 * @author liuzhi
 */

public enum SortType {
    /**
     * <p>排序方式<p/>
     * <>1交换式</>
     * <>2冒泡式</>
     */
    EXCHANGE(1), BUBBLE(2);
    private final Integer status;

    SortType(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
