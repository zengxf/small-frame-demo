# 嵌入式 Tomcat Demo

## html
- [hello.html](http://localhost:9091/hello.html)
- [test.html](http://localhost:9091/test.html)

## servlet
- [hello-servlet](http://localhost:9091/hello)

### Servlet 不应重写 service() 方法
- `HttpServlet` 本身会做缓存判断
- 重写时，没有缓存判断，状态会一直 200，而不是 304

## jsp
- [test.jsp](http://localhost:9091/test.jsp)