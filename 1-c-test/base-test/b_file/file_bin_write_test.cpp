/*
二进制文件
以二进制的方式对文件进行读写操作；
打开方式要指定为ios::binary；

写文件
二进制文件写文件主要利用流对象调用成员函数 write；
函数原型：ostream& write(const char * buffer, int len);
参数解释：字符指针 buffer 指向内存中一段存储空间，len 时读写的字节数

总结：文件输出流对象，可以通过 write 函数，以二进制方式写数据

Created by ZXF on 2025/5/10.
*/

#include <iostream>
#include <fstream>
using namespace std;

class Person {
public:
    char m_Name[20];
    int m_Age;
};

// 二进制文件，写文件
void test01() {
    // 1、包含头文件

    // 2、创建输出流对象
    ofstream ofs("person.txt", ios::out | ios::binary);

    // 3、打开文件
    // ofs.open("person.txt", ios::out | ios::binary)；

    Person p = {"张三-1234", 10};

    // 4、写文件
    ofs.write((const char *) &p, sizeof(p));

    // 5、关闭文件
    ofs.close();
}

int main() {
    test01();
    return 0;
}
