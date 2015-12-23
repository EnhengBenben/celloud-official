package com.celloud.action;
import java.util.ArrayList;
import java.util.List;

/**
 * 投诉与建议
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午2:57:50
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("feedback")
public class FeedBackAction {
    @RequestMapping("list")
    public String getAllFeedBack(){
        return null;
    }
    @RequestMapping(value="save",method=RequestMethod.PUT)
    @ResponseBody
    public boolean save(FeedBack feedBack){
        return false;
    }
    @RequestMapping("list")
    @ResponseBody
    public List<FeedBack> list(Page page){
        return new ArrayList<>();
    }
}
class Page{
    
}
class FeedBack{
    
}
