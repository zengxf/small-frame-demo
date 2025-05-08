/*
引用做函数返回值
作用： 引用可以作为函数的返回值存在
用法： 函数调用作为左值

注意：不要返回局部变量引用

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 返回局部变量引用
int &test01() {
    int a = 14; // 局部变量
    return a;
}

// 返回静态变量引用
int &test02() {
    static int a = 25;
    return a;
}

int main() {
    // 不能返回局部变量的引用
    int &ref = test01();
    // cout << "ref = " << ref << endl; // 使用就会出错

    // 如果函数做左值，那么必须返回引用
    int &ref2 = test02();
    cout << "ref2 = " << ref2 << endl;

    // test02 = 1000; // 这语法不对
    ref2 = 1000;
    cout << "ref2 = " << ref2 << endl;

    return 0;
}
