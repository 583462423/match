package com.sduwh.match.service.notice.impl;

import com.sduwh.match.dao.NoticeDAO;
import com.sduwh.match.enums.NoticeLevel;
import com.sduwh.match.model.entity.Notice;
import com.sduwh.match.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeDAO noticeDAO;
    @Override
    public int deleteByPrimaryKey(Integer integer) {
        return 0;
    }

    @Override
    public int insert(Notice record) {
        return noticeDAO.insert(record);
    }

    @Override
    public int insertSelective(Notice record) {
        return 0;
    }

    @Override
    public Notice selectByPrimaryKey(Integer integer) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Notice record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Notice record) {
        return 0;
    }


    @Override
    public List<Notice> selectAll() {
        return noticeDAO.selectAll();
    }

    @Override
    public List<Notice> selectByLevelAndRunning(int level) {
        //首先获取给全体成员的通告
        List<Notice> res = new ArrayList<>();
        res.addAll(noticeDAO.selectByLevelAndValid(NoticeLevel.ALL.getCode()));
        if(level != NoticeLevel.ALL.getCode()){
            res.addAll(noticeDAO.selectByLevelAndValid(level));
        }

        //过滤掉不在运行阶段的
        res = res.stream().filter(this::checkIsRunning).collect(Collectors.toList());
        return res;
    }

    @Override
    public boolean checkIsRunning(Notice notice) {
        Date now = new Date();
        return notice.getStartTime().before(now) && now.before(notice.getEndTime());
    }
}
