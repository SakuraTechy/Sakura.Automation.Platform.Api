package com.sakura.common.utils.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
class User {
    @IdProperty
    private Integer id;

    private String username;
    private String password;

    @OrderProperty
    private Integer order;

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "乔峰", "降龙十八掌", 1));
        users.add(new User(2, "段誉", "六脉神剑", 2));
        users.add(new User(3, "虚竹", "北冥神功", 3));
        users.add(new User(4, "鸠摩智", "小无相功", 4));
        users.add(new User(5, "慕容复", "斗转星移", 5));
        users.add(new User(6, "丁春秋", "化功大法", 6));
        List<User> sort = SortUtil.sort(users, 0, 1, SortType.EXCHANGE);
        sort.sort(Comparator.comparingInt(User::getOrder));
        sort.forEach(System.out::println);
    }
}

