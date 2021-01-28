package myspring.ioc.controller;

import myspring.ioc.service.UserService;
import myspring.mvc.annotation.Autowired;
import myspring.mvc.annotation.Controller;
import myspring.mvc.annotation.RequestMapping;
import myspring.mvc.servlet.model.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/id")
    public ModelAndView selectById(Integer id){
        userService.selectById(id);
        ModelAndView mav = new ModelAndView();

        return mav;
    }
}
