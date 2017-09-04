package com.sduwh.match.service.matchitem;

import com.sduwh.match.model.entity.MatchItem;
import com.sduwh.match.model.entity.MatchType;
import com.sduwh.match.model.wrapper.MatchTypeWrapper;
import com.sduwh.match.service.BaseService;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Created by qxg on 17-6-30.
 */
public interface MatchItemService extends BaseService<MatchItem,Integer> {
    List<MatchItem> selectAllByStringIds(String ids);
    List<MatchItem> selectAllByInfoId(Integer id);
    List<MatchItem> selectByStageId(int stageId);
    /** 指导老师审核阶段，如果都审核完毕，就进行更新当前阶段*/
    int updateWhenTeacherAllChecked(int id);

    /** 查找到对应的待审核的记录,并且将结果保存在model中,如果String不为空，说明需要重定向*/
    String checkDetail(Model model,int id);

    /** 将对应的比赛审核通过,如果返回的结果不为空，则表示重定向*/
    String checkPass(int id);
}
