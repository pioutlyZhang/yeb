package com.study.server.controller;

import com.study.server.pojo.Admin;
import com.study.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 在线聊天
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IAdminService adminService;

    /**
     * 获取聊天对象
     * @param keywords
     * @return
     */
    @GetMapping("/admin")
    public List<Admin> getAllAdmin(String keywords){
        return adminService.getAllAdmins(keywords);
    }
}
