package com.sduwh.match.service.middlecheck.impl;

import com.sduwh.match.dao.MiddleCheckDAO;
import com.sduwh.match.model.entity.MiddleCheck;
import com.sduwh.match.service.middlecheck.MiddleCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-9-11.
 */
@Service
public class MiddleCheckServiceImpl implements MiddleCheckService {

    @Autowired
    MiddleCheckDAO middleCheckDAO;
    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return 0;
    }

    @Override
    public int insert(MiddleCheck record) {
        return middleCheckDAO.insert(record);
    }

    @Override
    public int insertSelective(MiddleCheck record) {
        return 0;
    }

    @Override
    public MiddleCheck selectByPrimaryKey(Integer integer) {
        return middleCheckDAO.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(MiddleCheck record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(MiddleCheck record) {
        return 0;
    }

    @Override
    public MiddleCheck selectByMatchItemId(int matchItemId) {
        return middleCheckDAO.selectByMatchItemId(matchItemId);
    }

    @Override
    public int updateByMatchItemId(MiddleCheck middleCheck) {
        return middleCheckDAO.updateByMatchItemId(middleCheck);
    }

    @Override
    public int updateByMatchItemIdSelective(MiddleCheck middleCheck) {
        return middleCheckDAO.updateByMatchItemIdSelective(middleCheck);
    }


}
