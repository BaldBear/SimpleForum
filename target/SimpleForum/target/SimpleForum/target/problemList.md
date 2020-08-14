# ProblemList

### Solved

1. WebMvcConfigurerAdapter类被弃用，由于SpringMVC5.0以上版本增加了对Java8的支持，能够在接口中使用方法，因此该中间抽象类被弃用，直接实现WebMvcConfigurer即可，或者继承WebMvcConfigurationSupport类。

2. JSP页面的静态资源无法加载。

   原因：没有配置tomcat的默认servlet，导致springMVC的DispatcherServlet拦截了静态资源。

   解决：在web.xml配置文件中添加以下代码，若springMVC是使用web.xml初始化的，新增代码需要放在SpringMVC的配置之前。

   ```xml
   <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.jpg</url-pattern>
       </servlet-mapping>
       <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.png</url-pattern>
       </servlet-mapping>
       <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.gif</url-pattern>
       </servlet-mapping>
       <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.js</url-pattern>
       </servlet-mapping>
       <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.css</url-pattern>
       </servlet-mapping>
   ```

   







### unsolved

1. Ueditor插入图片时提示IO异常，修改config.jason中的”imageUrlPrefix“没有效果。

