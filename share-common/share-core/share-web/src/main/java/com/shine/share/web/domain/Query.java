package com.shine.share.web.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shine.share.protocol.domain.QueryParams;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UnknownFormatFlagsException;

/**
 * 基于Mybatis plus的IPage分页封装查询条件，支持获取分页、排序相关条件
 *
 * 使用示例，作为请求参数从Controller层传入
 * <code>
 * public class UserQuery extends Query<User> { ...... }
 * </code>
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
public class Query<T> implements QueryParams {

    public static final String SORT_ORDER_ASC = "ASC";
    public static final String SORT_ORDER_DESC = "DESC";

    private long current = 1;

    private long size = 10;

    private String[] sort;

    public IPage<T> getPage() {
        Page<T> page = Page.of(current, size);
        if (sort == null || sort.length == 0) {
            return page;
        }
        List<OrderItem> orders = new ArrayList<>(sort.length);
        for (String item : sort) {
            if (item == null || item.isBlank()) {
                continue;
            }
            String[] sg = item.split(",");
            if (sg.length == 1) {
                orders.add(OrderItem.asc(toSnake(sg[0])));
            } else if (SORT_ORDER_DESC.equalsIgnoreCase(sg[1])) {
                orders.add(OrderItem.desc(toSnake(sg[0])));
            } else if (SORT_ORDER_ASC.equalsIgnoreCase(sg[1])) {
                orders.add(OrderItem.asc(toSnake(sg[0])));
            } else {
                throw new UnknownFormatFlagsException("Unsupported sort order, Only 'ASC','DESC' and null are supported.");
            }
        }
        page.setOrders(orders);
        return page;
    }

    static String toSnake(String camel) {
        if (camel == null || camel.isEmpty()) {
            return camel;
        }
        StringBuilder result = new StringBuilder();

        for (char ch : camel.toCharArray()) {
            // 如果是大写字母，前面加下划线并转换为小写
            if (Character.isUpperCase(ch)) {
                result.append('_').append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

}
