package com.sduwh.match.service.stage;

//import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.model.entity.Stage;
import com.sduwh.match.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qxg on 17-6-29.
 */

public interface StageService extends BaseService<Stage,Integer> {
    List<Stage> selectAllByString(String input);
    List<Stage> selectByIdList(List<Integer> ids);
    /** 通过flag查找所有符合条件的stage*/
    List<Stage> selectByStageFlag(int flag);
    List<Stage> selectCheckedStageByStageFlag(int flag);

    /** 检查当前的状态是否已经过期*/
    boolean checkEnd(Stage stage);

    /** 检查当前状态是否正在运行*/
    boolean checkIsRuning(Stage stage);
}
