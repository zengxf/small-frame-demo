/*
析构函数：主要作用于对象销毁前系统自动调用，执行一些清理工作。

析构函数语法： ~类名（）{}
1. 析构函数，没有返回值也不写void
2. 函数名称与类名相同，在名称前面加上符号~
3. 析构函数不可以有参数，因此不可以发生重载
4. 程序在对象销毁前会自动调用析构，无线手动调用，而且只会调用一次

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    // 构造函数
    person() {
        cout << "person 的构造函数调用" << endl;
    }

    // 析构函数
    ~person() {
        cout << "person 的析构函数调用" << endl;
    }
};

void test01() {
    person p;
}

int main() {
    test01();
    return 0;
}
