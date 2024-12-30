package com.lszyf.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("checkCode")
public class CheckCodeController {

    @RequestMapping("code.do")
    public void checkCode(HttpSession session, HttpServletResponse response) {
        //创建图片数据
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 43, 4, 10);
        //验证码
        String code = lineCaptcha.getCode();
        //将验证码放入到session
        session.setAttribute("code", code);
        //将验证码 输出
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            // 打印异常堆栈跟踪
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                // 打印异常堆栈跟踪
                e.printStackTrace();
            }
        }
    }
}
