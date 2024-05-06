package com.sakura.common.utils.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragSortUtils {

    /**
     * 将列表中的两个元素交换位置
     * @param list 列表
     * @param fromIdx 起始下标
     * @param toIdx 目标下标
     */
    public static <T> void swap(List<T> list, int fromIdx, int toIdx) {
        Collections.swap(list, fromIdx, toIdx);
    }

    /**
     * 将一个元素拖到另一个元素的下方或上方
     * @param list 列表
     * @param fromIdx 起始下标
     * @param toIdx 目标下标
     * @param insertAbove 是否插入到目标元素的上方
     */
    public static <T> void dragTo(List<T> list, int fromIdx, int toIdx, boolean insertAbove) {
        if (fromIdx == toIdx) {
            return;
        }

        T fromEle = list.get(fromIdx);
        T toEle = list.get(toIdx);

        if (fromIdx < toIdx) {
            if (insertAbove) {
                list.add(toIdx, fromEle);
                list.remove(fromIdx);
            } else {
                list.add(toIdx + 1, fromEle);
                list.remove(fromIdx);
            }
        } else {
            if (insertAbove) {
                list.add(toIdx, fromEle);
                list.remove(fromIdx + 1);
            } else {
                list.add(toIdx + 1, fromEle);
                list.remove(fromIdx);
            }
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("{\"cancel\":\"true\",\"copyId\":\"\",\"id\":\"D1\",\"name\":\"D1\",\"order\":1,\"remark\":\"\",\"stepList\":[{\"action\":\"wait-forced\",\"config\":[{\"paramsName\":\"value\",\"paramsValue\":\"20000\"}],\"copyId\":\"\",\"copyPid\":\"\",\"id\":\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\",\"name\":\"1\",\"operationName\":\"强制等待\",\"operationType\":\"等待操作\",\"order\":1,\"pid\":\"D1\",\"setting\":\"\"}],\"stepMsg\":\"[{\\\"action\\\":\\\"wait-forced\\\",\\\"config\\\":[{\\\"paramsName\\\":\\\"value\\\",\\\"paramsValue\\\":\\\"20000\\\"}],\\\"copyId\\\":\\\"\\\",\\\"copyPid\\\":\\\"\\\",\\\"id\\\":\\\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\\\",\\\"name\\\":\\\"1\\\",\\\"operationName\\\":\\\"强制等待\\\",\\\"operationType\\\":\\\"等待操作\\\",\\\"order\\\":1,\\\"pid\\\":\\\"D1\\\",\\\"setting\\\":\\\"\\\"}]\"}");
        list.add("{\"cancel\":\"true\",\"copyId\":\"\",\"id\":\"D2\",\"name\":\"D2\",\"order\":2,\"remark\":\"\",\"stepList\":[{\"action\":\"wait-forced\",\"config\":[{\"paramsName\":\"value\",\"paramsValue\":\"20000\"}],\"copyId\":\"\",\"copyPid\":\"\",\"id\":\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\",\"name\":\"1\",\"operationName\":\"强制等待\",\"operationType\":\"等待操作\",\"order\":1,\"pid\":\"D1\",\"setting\":\"\"}],\"stepMsg\":\"[{\\\"action\\\":\\\"wait-forced\\\",\\\"config\\\":[{\\\"paramsName\\\":\\\"value\\\",\\\"paramsValue\\\":\\\"20000\\\"}],\\\"copyId\\\":\\\"\\\",\\\"copyPid\\\":\\\"\\\",\\\"id\\\":\\\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\\\",\\\"name\\\":\\\"1\\\",\\\"operationName\\\":\\\"强制等待\\\",\\\"operationType\\\":\\\"等待操作\\\",\\\"order\\\":1,\\\"pid\\\":\\\"D1\\\",\\\"setting\\\":\\\"\\\"}]\"}");
        list.add("{\"cancel\":\"true\",\"copyId\":\"\",\"id\":\"D3\",\"name\":\"D3\",\"order\":3,\"remark\":\"\",\"stepList\":[{\"action\":\"wait-forced\",\"config\":[{\"paramsName\":\"value\",\"paramsValue\":\"20000\"}],\"copyId\":\"\",\"copyPid\":\"\",\"id\":\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\",\"name\":\"1\",\"operationName\":\"强制等待\",\"operationType\":\"等待操作\",\"order\":1,\"pid\":\"D1\",\"setting\":\"\"}],\"stepMsg\":\"[{\\\"action\\\":\\\"wait-forced\\\",\\\"config\\\":[{\\\"paramsName\\\":\\\"value\\\",\\\"paramsValue\\\":\\\"20000\\\"}],\\\"copyId\\\":\\\"\\\",\\\"copyPid\\\":\\\"\\\",\\\"id\\\":\\\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\\\",\\\"name\\\":\\\"1\\\",\\\"operationName\\\":\\\"强制等待\\\",\\\"operationType\\\":\\\"等待操作\\\",\\\"order\\\":1,\\\"pid\\\":\\\"D1\\\",\\\"setting\\\":\\\"\\\"}]\"}");
        list.add("{\"cancel\":\"true\",\"copyId\":\"\",\"id\":\"D4\",\"name\":\"D4\",\"order\":4,\"remark\":\"\",\"stepList\":[{\"action\":\"wait-forced\",\"config\":[{\"paramsName\":\"value\",\"paramsValue\":\"20000\"}],\"copyId\":\"\",\"copyPid\":\"\",\"id\":\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\",\"name\":\"1\",\"operationName\":\"强制等待\",\"operationType\":\"等待操作\",\"order\":1,\"pid\":\"D1\",\"setting\":\"\"}],\"stepMsg\":\"[{\\\"action\\\":\\\"wait-forced\\\",\\\"config\\\":[{\\\"paramsName\\\":\\\"value\\\",\\\"paramsValue\\\":\\\"20000\\\"}],\\\"copyId\\\":\\\"\\\",\\\"copyPid\\\":\\\"\\\",\\\"id\\\":\\\"7ca19a10-f90d-11ed-9020-fd19d38e1c1e\\\",\\\"name\\\":\\\"1\\\",\\\"operationName\\\":\\\"强制等待\\\",\\\"operationType\\\":\\\"等待操作\\\",\\\"order\\\":1,\\\"pid\\\":\\\"D1\\\",\\\"setting\\\":\\\"\\\"}]\"}");

        // 交换两个元素的位置
        DragSortUtils.swap(list, 1, 2);
        System.out.println(list); // [1, 3, 2, 4]

        // 将一个元素拖到另一个元素的下方
        DragSortUtils.dragTo(list, 0, 2, false);
        System.out.println(list); // [2, 3, 1, 4]

        // 将一个元素拖到另一个元素的上方
        DragSortUtils.dragTo(list, 3, 1, true);
        System.out.println(list); // [2, 4, 3, 1]
    }
}
