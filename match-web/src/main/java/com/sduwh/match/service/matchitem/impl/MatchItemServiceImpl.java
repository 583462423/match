package com.sduwh.match.service.matchitem.impl;

import com.sduwh.match.dao.MatchItemMapper;
import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.service.matchitem.MatchItemService;
import com.sduwh.match.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qxg on 17-7-12.
 */
@Service
public class MatchItemServiceImpl implements MatchItemService {

    @Autowired
    MatchItemMapper matchItemMapper;

    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return matchItemMapper.deleteByPrimaryKey(integer);
    }

    @Override
    public int insert(MatchItem record) {
        return matchItemMapper.insert(record);
    }

    @Override
    public int insertSelective(MatchItem record) {
        return matchItemMapper.insertSelective(record);
    }

    @Override
    public MatchItem selectByPrimaryKey(Integer integer) {
        return matchItemMapper.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(MatchItem record) {
        return matchItemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(MatchItem record) {
        return matchItemMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatchItem> selectAllByStringIds(String ids) {
        if (StringUtil.NullOrEmpty(ids))return null;
        List<Integer> idList = Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        return idList.stream().map(matchItemMapper::selectByPrimaryKey).collect(Collectors.toList());
    }
}
