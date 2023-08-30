package test.url.read_img;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class TestReadImg {

    // (ip.src == 59.51.81.195 || ip.dst == 59.51.81.195) && tcp.flags.syn == 1
    public static void main(String[] args) throws IOException {

	test1();
	test1a();
	// test2();
	// test2();
    }

    static void test2() throws UnknownHostException, IOException, FileNotFoundException {
	int port = 80;
	String host = "img.netbian.com";
	String path = "/file/2016/0516/34a83b1358e50b5f146fbdbf25678b37.jpg";
	// String host = "work.liemeng.net";
	// String path = "/data/upload/1/201605/1212211508037gpg.png";

	Socket soc = new Socket(host, port);
	OutputStream os = soc.getOutputStream();
	InputStream is = soc.getInputStream();

	sentHttpHeader(host, os, path);

	CustomDataInputStream cdis = new CustomDataInputStream(is);

	String file = "test2.png";
	FileOutputStream fos = new FileOutputStream(file);

	String line = null;
	while ((line = cdis.readHttpResponseHeaderLine()) != null) {
	    if ("".equals(line)) {
		byte[] arr = new byte[1024 * 20];
		int i = cdis.read(arr);
		while (i > 0) {
		    fos.write(arr, 0, i);
		    i = is.read(arr);
		}
		break;
	    }
	}

	cdis.close();
	os.close();
	fos.close();

	soc.close();

	System.out.println("OK");
    }

    private static void sentHttpHeader(String host, OutputStream os, String path) throws IOException {

	BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF8"));

	// 构造HTTP请求头(request header)
	String requestStr = "GET " + path + " HTTP/1.1\r\n";
	String hostHeader = "Host: " + host + "\r\n";
	String acceptHeader = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n";
	String charsetHeader = "Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n";
	String languageHeader = "Accept-Language: zh-CN,zh;q=0.8\r\n";
	String keepHeader = "Connection: close\r\n";

	// 发送HTTP请求
	bufferedWriter.write(requestStr);
	bufferedWriter.write(hostHeader);
	bufferedWriter.write(acceptHeader);
	bufferedWriter.write(charsetHeader);
	bufferedWriter.write(languageHeader);
	bufferedWriter.write(keepHeader);
	bufferedWriter.write("\r\n"); // 请求头信息发送结束标志
	bufferedWriter.flush();
    }

    static void test1() throws MalformedURLException, IOException, FileNotFoundException {
	URL url = new URL("http://img.netbian.com/file/2016/0516/34a83b1358e50b5f146fbdbf25678b37.jpg");
	URLConnection conn = url.openConnection();
	InputStream is = conn.getInputStream();
	FileOutputStream fos = new FileOutputStream("test2.jpg");
	byte[] arr = new byte[1024];
	int i = is.read(arr);
	while (i > 0) {
	    fos.write(arr, 0, i);
	    i = is.read(arr);
	}
	fos.close();
	is.close();
    }

    static void test1a() throws MalformedURLException, IOException, FileNotFoundException {
	URL url = new URL("http://img.netbian.com/file/2016/0324/35fe44dbf98a410b2e130bde68928539.jpg");
	URLConnection conn = url.openConnection();
	InputStream is = conn.getInputStream();
	FileOutputStream fos = new FileOutputStream("test2.jpg");
	byte[] arr = new byte[1024];
	int i = is.read(arr);
	while (i > 0) {
	    fos.write(arr, 0, i);
	    i = is.read(arr);
	}
	fos.close();
	is.close();
    }

}
