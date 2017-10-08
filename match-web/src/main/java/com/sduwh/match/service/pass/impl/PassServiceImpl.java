package com.sduwh.match.service.pass.impl;

import com.sduwh.match.dao.PassDAO;
import com.sduwh.match.enums.PassStatus;
import com.sduwh.match.model.entity.Pass;
import com.sduwh.match.service.pass.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-9-1.
 */
@Service
public class PassServiceImpl implements PassService {

    @Autowired
    PassDAO passDAO;

    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return passDAO.deleteByPrimaryKey(integer);
    }

    @Override
    public int insert(Pass record) {
        return passDAO.insert(record);
    }

    @Override
    public int insertSelective(Pass record) {
        return 0;
    }

    @Override
    public Pass selectByPrimaryKey(Integer integer) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Pass record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Pass record) {
        return 0;
    }

    @Override
    public Pass selectByUserAndItem(int userId,int matchItemId){
        return passDAO.selectByUserAndItem(userId,matchItemId);
    }

    @Override
    public boolean checkPass(Pass pass) {
        if(pass == null)return false;
        else if(pass.getStatus() == PassStatus.PASS.getValue())return true;
        else return false;
    }

    @Override
    public List<Pass> selectByUserId(int userId) {
        return passDAO.selectByUserId(userId);
    }

    @Override
    public int deleteAllByMatchItemId(int matchItemId) {
        return passDAO.deleteAllByMatchItemId(matchItemId);
    }
}
