package com.sduwh.match.service.stage.impl;

import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;

import com.sduwh.match.dao.StageMapper;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by qxg on 17-6-28.
 */

@Service
public class StageServiceImpl implements StageService {

    @Autowired
    StageMapper stageMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return stageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Stage record) {
        return stageMapper.insert(record);
    }

    @Override
    public int insertSelective(Stage record) {
        return stageMapper.insertSelective(record);
    }

    @Override
    public Stage selectByPrimaryKey(Integer id) {
        if(id < 0)return null;
        return stageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Stage record) {
        return stageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Stage record) {
        return stageMapper.updateByPrimaryKey(record);
    }

    /** 传入的参数类似1,2,3,4这种形式*/
    @Override
    public List<Stage> selectAllByString(String input) {
        if (input == null || "".equals(input))return null;
        ArrayList<Integer> list = new ArrayList<>();
        //jdk1.8 Stream API
        Arrays.stream(input.split(","))
                .map(e->Integer.parseInt(e.trim()))
                .forEach(list::add);
        return selectByIdList(list);
    }

    @Override
    public List<Stage> selectByIdList(List<Integer> ids) {
        return stageMapper.selectByIdList(ids);
    }

    @Override
    public List<Stage> selectByStageFlag(int flag) {
        return stageMapper.selectByStageFlag(flag);
    }

    @Override
    public List<Stage> selectCheckedStageByStageFlag(int flag) {
        return selectByStageFlag(flag).stream()
                .filter(s->!checkEnd(s)).collect(Collectors.toList());
    }



    /** 如果过期，返回true*/
    @Override
    public boolean checkEnd(Stage stage) {
        return stage.getEndTime().before(new Date());
    }

    @Override
    public boolean checkIsRuning(Stage stage) {
        Date now = new Date();
        return now.after(stage.getStartTime()) && now.before(stage.getEndTime());
    }


}
