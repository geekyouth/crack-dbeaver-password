package cn.java666.tools.crackdbeaverpassword.controller;

import cn.java666.tools.crackdbeaverpassword.service.MyService;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Geek
 * @date 2021-03-25 19:48:19 
 */

@RestController
@RequestMapping("/decrypt")
public class MyController {

  @Resource
  private MyService myService;

  @GetMapping("/default")
  public String decrypt() {
    String res = myService.decrypt(null);
    System.out.println(res);
    return res;
  }

  @PostMapping("/upload")
  public String decrypt(@RequestParam("file") MultipartFile file) {
    String res = myService.decrypt(file);
    System.out.println(res);
    return res;
  }
}
