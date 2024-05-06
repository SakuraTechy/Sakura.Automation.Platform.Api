package com.sakura.common.utils.sort;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * 拖拽排序工具:此工具将传入的list重新排序后返回,使用者只需要将list重新存入数据库即可完成排序.
 * <>
 * 拖拽排序必然牵扯到两个元素,被拖拽的元素和被挤压的元素.排序方式就存在两种,一种是两个元素进行交换位置,一种是一个元素拖到另一元素的下发或上方.
 * 1.此方法需要传入两个基准元素之间的所有元素的集合,
 * 2.以及两个基准元素的id,
 * 3.排序方式.
 * </>
 *
 * @author sunziwen
 * @since 2021-3-23 19:15:07
 */
public class SortUtil {
    /**
     * 此方法需要在T类的主键上打@IdProperty注解,在排序字段上打@OrderProperty注解.
     *
     * @param list 需要重新排序的元素集合
     * @param id1  拖拽元素
     * @param id2  定位元素
     * @param type 排序类型(1交换,2挤压排序)
     * @param <T>  泛型
     * @return List<T>
     */
    public static <T> List<T> sort(List<T> list, Object id1, Object id2, SortType type) {
        String idProperty = null;
        String orderProperty = null;
        Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            IdProperty idAnnotation = declaredField.getAnnotation(IdProperty.class);
            OrderProperty orderAnnotation = declaredField.getAnnotation(OrderProperty.class);
            if (idAnnotation != null) {
                idProperty = declaredField.getName();
            }
            if (orderAnnotation != null) {
                orderProperty = declaredField.getName();
            }
        }
        if (idProperty == null) {
            throw new RuntimeException("没有在主键属性上打@IdProperty注解");
        }
        if (orderProperty == null) {
            throw new RuntimeException("没有在排序属性上打@OrderProperty注解");
        }
        return sort(list, id1, id2, type, idProperty, orderProperty);
    }

    /**
     * @param list          需要重新排序的元素集合
     * @param id1           拖拽元素
     * @param id2           定位元素
     * @param type          排序类型(1交换,2挤压排序)
     * @param idProperty    主键属性名,一般是"id"
     * @param orderProperty 排序属性名
     * @param <T>           泛型
     * @return List<T>
     */
    public static <T> List<T> sort(List<T> list, Object id1, Object id2, SortType type, String idProperty, String orderProperty) {
        //排序
        list.sort((x, y) -> {
            try {
                Field fieldx = x.getClass().getDeclaredField(orderProperty);
                Field fieldy = y.getClass().getDeclaredField(orderProperty);
                fieldx.setAccessible(true);
                fieldy.setAccessible(true);

                int i = Integer.parseInt(fieldx.get(x).toString());
                int j = Integer.parseInt(fieldy.get(y).toString());
                return i - j;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException("反射异常了");
            }
        });
        if (list == null || list.size() <= 0) {
            return list;
        }
        //傻子在原地拖动
        if (id1.equals(id2)) {

            return list;
        }

        try {

            T tFirst = list.get(0);
            T tLast = list.get(list.size() - 1);

            Field orderFirst = tFirst.getClass().getDeclaredField(orderProperty);
            orderFirst.setAccessible(true);
            Object orderValueFirst = orderFirst.get(tFirst);

            Field orderLast = tLast.getClass().getDeclaredField(orderProperty);
            orderLast.setAccessible(true);
            Object orderValueLast = orderLast.get(tLast);

            //交换位置
            if (type == SortType.EXCHANGE) {
                orderFirst.set(tFirst, orderValueLast);
                orderLast.set(tLast, orderValueFirst);
            }

            //冒泡排序需要知道是从上往下,还是从下往上拖拽.因此需要知道他们的order值 order1<order2则是从上往下,反之亦然.
            if (type == SortType.BUBBLE) {

                //order集合
                int[] orders = list.stream().mapToInt(x -> {
                    try {
                        Field order = x.getClass().getDeclaredField(orderProperty);
                        order.setAccessible(true);
                        Object orderVal = order.get(x);
                        if (orderVal == null) {
                            throw new RuntimeException("有元素缺失排序属性值");
                        }
                        return Integer.parseInt(orderVal.toString());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException("未知异常:联系作者");
                    }
                }).toArray();

                //获取id1和id2的排序值,用来确认是拖拽方向
                Integer order1 = null;
                Integer order2 = null;
                for (T t : list) {
                    Field idField = t.getClass().getDeclaredField(idProperty);
                    idField.setAccessible(true);
                    Object idVal = idField.get(t);
                    if (idVal.equals(id1)) {
                        Field orderField = t.getClass().getDeclaredField(orderProperty);
                        orderField.setAccessible(true);
                        order1 = Integer.parseInt(orderField.get(t).toString());
                    }
                    if (idVal.equals(id2)) {
                        Field orderField = t.getClass().getDeclaredField(orderProperty);
                        orderField.setAccessible(true);
                        order2 = Integer.parseInt(orderField.get(t).toString());
                    }
                }
                if (order1 == null || order2 == null) {
                    throw new RuntimeException("排序字段缺失属性值");
                }
                //从上往下拖拽
                if (order1 < order2) {
                    //将首位元素挪到末尾
                    list.remove(tFirst);
                    list.add(tFirst);

                    //从下往上拖拽
                } else {
                    //将末尾元素追加到首位
                    T last = list.get(list.size() - 1);
                    list.remove(last);
                    Collections.reverse(list);
                    list.add(last);
                    Collections.reverse(list);
                }


                //将元素集合与order集合重新绑定
                for (int i = 0; i < orders.length; i++) {
                    T t = list.get(i);
                    Field order = t.getClass().getDeclaredField(orderProperty);
                    order.setAccessible(true);
                    order.set(t, orders[i]);
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("未知异常:联系作者");
        }
        return list;
    }

}






//@Data
//@AllArgsConstructor
//class User {
//    @IdProperty
//    private Integer id;
//    private String username;
//    private String password;
//    @OrderProperty
//    private Integer order;
//
//    public static void main(String[] args) {
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User(1, "乔峰", "降龙十八掌", 1));
//        users.add(new User(2, "段誉", "六脉神剑", 2));
//        users.add(new User(3, "虚竹", "北冥神功", 3));
//        users.add(new User(4, "鸠摩智", "小无相功", 4));
//        users.add(new User(5, "慕容复", "斗转星移", 5));
//        users.add(new User(6, "丁春秋", "化功大法", 6));
//        List<User> sort = SortUtil.sort(users, 1, 6, SortType.BUBBLE);
//        sort.sort(Comparator.comparingInt(User::getOrder));
//        sort.forEach(System.out::println);
//    }
//}
