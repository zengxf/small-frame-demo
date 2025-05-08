/*
引用的基本使用
作用： 给变量起别名
语法： 数据类型 &别名 = 原名

引用的注意事项
1. 引用必须初始化
2. 引用在初始化后，不可以改变

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


int test2() {
    int a = 21;
    int b = 41;

    // int &c; // 错误，引用必须初始化
    int &c = a; // 一旦引用初始化后，就不可以更改引用关系
    // &c = b; // 不能赋值给右值
    c = b; // 这只是赋值操作，不是更改引用

    cout << "a = " << a << endl;
    cout << "b = " << b << endl;
    cout << "c = " << c << endl;

    return 0;
}


int main() {
    int a = 10;
    int &b = a;

    cout << "a = " << a << endl;
    cout << "b = " << b << endl;

    b = 100;
    cout << "a = " << a << endl;
    cout << "b = " << b << endl;

    cout << endl;
    test2();

    return 0;
}
