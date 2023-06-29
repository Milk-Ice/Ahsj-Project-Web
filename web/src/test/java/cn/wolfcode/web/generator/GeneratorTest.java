/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.wolfcode.web.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import link.ahsj.generator.utils.GeneratorUtils;

/**
 * 代码生成器演示
 *
 * @author hubin
 * @since 2016-12-01
 */
public class GeneratorTest {

    /**
     * MySQL 生成演示
     */
    public static void main(String[] args) {
        GeneratorUtils.generator(
                "web",
                "cn.wolfcode.web.modules",//包名字
                "orderinfo", //后端模块名字
                DbType.MYSQL,
                "D:\\app2",
                // 页面上的父上下文
                // 自己的上下文
                "1111",
                "订货单功能",
                null,
                "order",//一级目录
                "orderinfo",//二级目录
                "hmy",
                "127.0.0.1",
                "3306",
                "nojoke_fosu?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf8",
                "root",
                "123456",
                new String[]{"bmd_", "mp_", "SYS_"}, //忽略不计


                new String[]{"tb_order_info"}, //数据库的表名
                false
        );
    }


}
