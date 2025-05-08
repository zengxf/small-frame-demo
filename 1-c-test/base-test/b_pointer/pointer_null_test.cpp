/*
空指针： 指针变量指向内存中编号为0的空间
作用： 初始化指针变量
 Created by ZXF on 2025/5/8
 */

#include<iostream>
using namespace std;

int main() {
    // 指针变量p指向内存地址编号为0的空间
    int *p = NULL;

    // 访问空指针报错
    // 内存编号为0-255为系统内存占用，不允许访问
    // cout << *p << endl;

    return 0;
}
