package com.sduwh.match.controller.admin;

import com.sduwh.match.controller.base.BaseController;
import com.sduwh.match.enums.SupplyStatus;
import com.sduwh.match.model.entity.MatchInfo;
import com.sduwh.match.service.matchinfo.MatchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by qxg on 17-9-11.
 * 开启补录控制器
 */
@Controller
@RequestMapping("/admin")
public class OpenSupplyController extends BaseController {

    @Autowired
    MatchInfoService matchInfoService;

    @PostMapping("/supply")
    @ResponseBody
    public String openOrCloseSupply(@RequestParam("itemId") int itemId,
                                    @RequestParam("supplyFlag")int supplyFlag){
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setId(itemId);
        String res = null;
        if(supplyFlag < 0){
            //关闭补录
            matchInfo.setSupply(SupplyStatus.CLOSE_SUPPLY.getCode());
            res = setJsonResult("msg","关闭成功！");
        }else {
            matchInfo.setSupply(SupplyStatus.OPEN_SUPPLY.getCode());
            res = setJsonResult("msg","开启成功!");
        }

        matchInfoService.updateByPrimaryKeySelective(matchInfo);
        return res;
    }
}
