package test.echo_service.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MixedAttribute;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import test.util.HttpProtocolHelper;
import test.util.JsonUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

/**
 * <br/>
 * Created by ZXFeng on 2022/7/27.
 */
@Slf4j
public class HttpEchoHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            HttpProtocolHelper.sendError(ctx, BAD_REQUEST);
            return;
        }
        // 缓存 HTTP 协议的版本号
        HttpProtocolHelper.cacheHttpProtocol(ctx, request);
        Map<String, Object> echo = new HashMap<>();
        // 1.获取 URI
        String uri = request.uri();
        echo.put("request uri", uri);
        // 2.获取请求方法
        HttpMethod method = request.method();
        echo.put("request method", method.toString());
        // 3.获取请求头
        Map<String, Object> echoHeaders = new HashMap<>();
        HttpHeaders headers = request.headers();
        Iterator<Map.Entry<String, String>> hit = headers.entries().iterator();
        while (hit.hasNext()) {
            Map.Entry<String, String> header = hit.next();
            echoHeaders.put(header.getKey(), header.getValue());
        }
        echo.put("request header", echoHeaders);
        // 获取 uri 请求参数
        Map<String, Object> uriDatas = paramsFromUri(request);
        echo.put("paramsFromUri", uriDatas);
        // 处理 POS T请求
        if (POST.equals(request.method())) {
            // 获取请求体数据到 map
            Map<String, Object> postData = dataFromPost(request);
            echo.put("dataFromPost", postData);
        }
        // 回显内容转换成 json 字符串
        String sendContent = JsonUtil.pojoToJson(echo);
        // 发送回显内容到客户端
        HttpProtocolHelper.sendJsonContent(ctx, sendContent);
    }

    // 从 URI 后面获取请求的参数
    private Map<String, Object> paramsFromUri(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<>();
        // 把 URI 后面的参数串，分割成 key-value 形式
        QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
        // 提取 key-value 形式的参数串
        Map<String, List<String>> paramList = decoder.parameters();
        // 迭代 key-value 形式的参数串
        for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
            params.put(entry.getKey(), entry.getValue().get(0));
        }
        return params;
    }

    // 获取 POST 方式传递的请求体数据
    private Map<String, Object> dataFromPost(FullHttpRequest fullHttpRequest) {
        Map<String, Object> postData = null;
        try {
            String contentType = fullHttpRequest.headers().get("Content-Type").trim();
            // 普通 form 表单数据，非 multipart 形式表单
            if (contentType.contains("application/x-www-form-urlencoded")) {
                postData = formBodyDecode(fullHttpRequest);
            }
            // multipart 形式表单
            else if (contentType.contains("multipart/form-data")) {
                postData = formBodyDecode(fullHttpRequest);
            }
            // 解析 json 数据
            else if (contentType.contains("application/json")) {
                postData = jsonBodyDecode(fullHttpRequest);
            } else if (contentType.contains("text/plain")) {
                ByteBuf content = fullHttpRequest.content();
                byte[] reqContent = new byte[content.readableBytes()];
                content.readBytes(reqContent);
                String text = new String(reqContent, "UTF-8");
                postData = new HashMap<>();
                postData.put("text", text);
            }
            return postData;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    // 解析 from 表单数据
    private Map<String, Object> formBodyDecode(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<>();
        try {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE),
                    fullHttpRequest, CharsetUtil.UTF_8
            );
            List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
            if (postData == null || postData.isEmpty()) {
                if (fullHttpRequest.content().isReadable()) {
                    String json = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
                    params.put("body", json);
                }
            }
            for (InterfaceHttpData data : postData) {
                if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MixedAttribute attribute = (MixedAttribute) data;
                    params.put(attribute.getName(), attribute.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }

    // 解析 json 数据（Content-Type = application/json）
    private Map<String, Object> jsonBodyDecode(FullHttpRequest fullHttpRequest) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<>();
        ByteBuf content = fullHttpRequest.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, "UTF-8");
        Map<String, Object> jsonParams = JsonUtil.jsonToMap(strContent);
        for (Object key : jsonParams.keySet()) {
            params.put(key.toString(), jsonParams.get(key));
        }
        return params;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            HttpProtocolHelper.sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

}
