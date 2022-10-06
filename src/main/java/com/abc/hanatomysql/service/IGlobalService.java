package com.abc.hanatomysql.service;

import com.abc.hanatomysql.common.Result;
import com.abc.hanatomysql.model.SyncDTO;

/**
 * @Author Z-7
 * @Date 2022/8/19
 */
public interface IGlobalService {

    /**
     * 数据同步
     * @param syncDTO
     * @return 同一结果返回类
     */
    Result hanaToMysql(SyncDTO syncDTO);

    /**
     * 获取连接
     */
    void getConnetion();

    /**
     * 关闭连接
     */
    void close();
}
