# javaee7 demo

## servlet 3.1
- [hello-servlet](http://localhost:9090/hello)
- [my-async-servlet](http://localhost:9090/my-async)
- [cookies-servlet](http://localhost:9090/cookies)
- [test-listener-servlet](http://localhost:9090/test-listener)
- [file-upload-servlet](http://localhost:9090/file-upload)
- [nonblock-client-servlet](http://localhost:9090/nonblock-client)
- [upgrade-servlet](http://localhost:9090/upgrade)
- [secure-servlet](http://localhost:9090/secure)
- [login-servlet](http://localhost:9090/login)
- [test-filter-servlet](http://localhost:9090/filtered/test-filter)

## view html
- [hello.html](http://localhost:9090/hello.html)
- [test.html](http://localhost:9090/test.html)
- [upload-file.html](http://localhost:9090/upload-file.html)

## web-socket
- JavaScript test:

```
var ws = new WebSocket('ws://localhost:9090/servertime');
ws.onmessage = function(e) {
  console.log(e.data);
};
ws.send("test-123");
```