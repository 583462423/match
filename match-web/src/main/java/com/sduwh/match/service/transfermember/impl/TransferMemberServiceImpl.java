package com.sduwh.match.service.transfermember.impl;

import com.sduwh.match.dao.TransferMemberDAO;
import com.sduwh.match.model.entity.TransferMember;
import com.sduwh.match.service.transfermember.TransferMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-9-14.
 */
@Service
public class TransferMemberServiceImpl implements TransferMemberService {


    @Autowired
    TransferMemberDAO transferMemberDAO;
    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return 0;
    }

    @Override
    public int insert(TransferMember record) {
        return transferMemberDAO.insert(record);
    }

    @Override
    public int insertSelective(TransferMember record) {
        return 0;
    }

    @Override
    public TransferMember selectByPrimaryKey(Integer integer) {
        return transferMemberDAO.selectByPrimaryKey(integer);
    }

    @Override
    public int updateByPrimaryKeySelective(TransferMember record) {
        return transferMemberDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(TransferMember record) {
        return 0;
    }

    @Override
    public List<TransferMember> selectByMatchItemId(int matchItemId) {
        return transferMemberDAO.selectByMatchItemId(matchItemId);
    }
}
