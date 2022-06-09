package com.springmvc;

import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class HelloWordController {

    /**
     * Map
     * 结论: SpringMVC会把Map中的模型数据存放到request域对象中.
     *      SpringMVC再调用完请求处理方法后，不管方法的返回值是什么类型，都会处理成一个ModelAndView对象（参考DispatcherServlet的945行）
     *
     */
    @RequestMapping("/testMap")
    public String  testMap(Map<String,Object> map ) {
        //模型数据: password=123456
        System.out.println(map.getClass().getName());
        map.put("password", "123456");

        return "success";
    }


    /**
     * ModelAndView
     * 结论: Springmvc会把ModelAndView中的模型数据存放到request域对象中.
     */
    @RequestMapping("/testModelAndView")
    public  ModelAndView  testModelAndView() {
        //模型数据: username=Admin
        ModelAndView mav = new ModelAndView();
        //添加模型数据
        mav.addObject("username", "Admin");

        //设置视图信息
        mav.setViewName("success");

        return mav ;
    }

    /**
     * 测试原生的Servlet API
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/testServletAPI")
    public void testServletAPI(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        System.out.println("request: " + request );

        System.out.println("response: " + response );
        // 转发
        //request.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(request, response);

        // 重定向  将数据写给客户端
        //response.sendRedirect("http://www.baidu.com");

        response.getWriter().println("Hello Springmvc ");
    }


    /**
     * @CookieValue  映射cookie信息到请求处理方法的形参中
     */
    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID")String sessionId) {
        System.out.println("sessionid:" + sessionId);
        return "success";
    }


    /**
     * @RequestHeader  映射请求头信息到请求处理方法的形参中
     */
    @RequestMapping("testRequestHeader")
    public String testRequestHeader(@RequestHeader("Accept-Language")String acceptLanguage) {
        System.out.println("acceptLanguage:" + acceptLanguage);
        return "success";
    }

    /**
     * @RequestParam  映射请求参数到请求处理方法的形参
     * 	 1. 如果请求参数名与形参名一致， 则可以省略@RequestParam的指定。
     * 	 2. @RequestParam 注解标注的形参必须要赋值。 必须要能从请求对象中获取到对应的请求参数。
     * 		可以使用required来设置为不是必须的。
     * 	 3. 可以使用defaultValue来指定一个默认值取代null
     * 客户端的请求:testRequestParam?username=Tom&age=22
     */
    @RequestMapping("/testRequestParam")
    public String testRequestParam(@RequestParam("username")String username,
                                   @RequestParam(value="age",required=false,defaultValue="0")int age ) {
        //web: request.getParameter()    request.getParameterMap()

        System.out.println(username + " , " + age);

        return "success";
    }

    /**
     * @PathVariable 请求资源占位符
     * @param name
     * @param age
     */
    @RequestMapping(value="/pathVariable/{name}/{age}",method = RequestMethod.GET)
    public void testPathVariable(@PathVariable("name") String name,@PathVariable("age") Integer age){
        System.out.println("名字"+name+",\t年龄"+age);
    }
}
