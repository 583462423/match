package com.sduwh.match.service;

/**
 * Created by qxg on 17-6-28.
 */
public interface BaseService<T,ID> {
    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(ID id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
