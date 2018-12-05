package cn.com.exercise.move.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "getUserCondition")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 置顶
     * @param id    需要指定的数据的id
     * @return 0:正确 -1：移动失败 1：输入id超过最大值
     */
    @RequestMapping(value = "/top")
    @ResponseBody
    public Object moveTop(Integer id){
        UserCondition condition = userService.getUserById(id);
        if (condition==null){
            return 1;
        } else {
            Integer res = userService.makeTop(condition);
            if (res==0){
                return userService.getUser();
            }else {
                return -1;
            }
        }
    }

    @RequestMapping(value = "/moveUp")
    @ResponseBody
    public Object moveUp(Integer id){
        UserCondition condition = userService.getUserById(id);
        if (condition==null){
            return 1;
        } else {
            Integer res = userService.moveUp(condition);
            if (res==0){
                return userService.getUser();
            } else {
                return -1;
            }
        }

    }

    @RequestMapping(value = "/moveDown")
    @ResponseBody
    public Object moveDown(Integer id){
        UserCondition condition = userService.getUserById(id);
        if (condition==null){
            return 1;
        } else {
            Integer res = userService.moveDown(condition);
            if (res==0){
                return userService.getUser();
            }else {
                return -1;
            }
        }

    }
}
