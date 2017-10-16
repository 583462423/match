package com.sduwh.match.service.notice;

import com.sduwh.match.model.entity.Notice;
import com.sduwh.match.service.BaseService;

import java.util.List;

public interface NoticeService extends BaseService<Notice,Integer> {
    List<Notice> selectAll();

    /** 查找对应级别的通过并且在运行的*/
    List<Notice> selectByLevelAndRunning(int level);

    /** 检查当前阶段是否正在运行*/
    boolean checkIsRunning(Notice notice);
}
