package com.sduwh.match.service.concludingstagtement.middlecheck.impl;

import com.sduwh.match.dao.ConcludingStatementDAO;
import com.sduwh.match.dao.MiddleCheckDAO;
import com.sduwh.match.model.entity.ConcludingStatement;
import com.sduwh.match.model.entity.MiddleCheck;
import com.sduwh.match.service.concludingstagtement.middlecheck.ConcludingStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qxg on 17-9-11.
 */
@Service
public class ConcludingStatementServiceImpl implements ConcludingStatementService {

    @Autowired
    ConcludingStatementDAO concludingStatementDAO;
    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return 0;
    }

    @Override
    public int insert(ConcludingStatement record) {
        return concludingStatementDAO.insert(record);
    }

    @Override
    public int insertSelective(ConcludingStatement record) {
        return 0;
    }

    @Override
    public ConcludingStatement selectByPrimaryKey(Integer integer) {
        return concludingStatementDAO.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(ConcludingStatement record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ConcludingStatement record) {
        return 0;
    }

    @Override
    public ConcludingStatement selectByMatchItemId(int matchItemId) {
        return concludingStatementDAO.selectByMatchItemId(matchItemId);
    }

    @Override
    public int updateByMatchItemId(ConcludingStatement middleCheck) {
        return concludingStatementDAO.updateByMatchItemId(middleCheck);
    }

    @Override
    public int updateByMatchItemIdSelective(ConcludingStatement middleCheck) {
        return concludingStatementDAO.updateByMatchItemIdSelective(middleCheck);
    }


}
