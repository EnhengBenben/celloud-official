package com.celloud.backstage.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.backstage.model.Client;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.ClientService;

/**
 * 
 *
 * @author han
 * @date 2016年2月17日 下午3:10:59
 */
@Controller
public class ClientAction {
    Logger logger=LoggerFactory.getLogger(ClientAction.class);
    @Resource
    private ClientService clientService;
    @RequestMapping("client/clientList")
    public ModelAndView getClientByPage(@RequestParam(defaultValue = "1") int currentPage,
             @RequestParam(defaultValue = "10") int size){
         ModelAndView mv=new ModelAndView("client/client_main");
         Page page = new Page(currentPage, size);
         PageList<Client> pageList=clientService.getClientByPage(page);
         mv.addObject("pageList",pageList);
         return mv;
     }
    @RequestMapping("client/clientEdit")
    public ModelAndView toNoticeEdit(){
        ModelAndView mv=new ModelAndView("client/client_edit");
        return mv;
    }
    @RequestMapping("client/save" )
    @ResponseBody
    public int saveClient(Client client){
        return clientService.addClient(client);
    }
}
