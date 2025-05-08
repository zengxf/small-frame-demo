/*
指针变量的定义和使用
语法： 数据类型 * 变量名
 Created by ZXF on 2025/5/8
 */

#include<iostream>
using namespace std;

int main() {
    // 1、指针的定义
    int a = 10;
    int *p; // 指针定义语法：数据类型 * 变量名

    // 指针变量赋值
    p = &a; // 指针指向变量a的地址

    cout << &a << endl; // 打印数据a的地址
    cout << p << endl; // 打印指针变量p

    // 2、指针的使用
    // 通过 * 操作指针变量指向的内存
    cout << "*p =" << *p << endl;

    return 0;
}
