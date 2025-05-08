/*
野指针
指的是指向无效内存区域的指针。使用野指针可能导致程序崩溃、数据损坏或出现不可预测的行为。

 Created by ZXF on 2025/5/8
*/

#include<iostream>
using namespace std;

int main() {
    // 指针变量p指向内存地址为0×1100的空间
    int *p = (int *) 0x1100;

    // 访问野指针报错
    cout << *p << endl;

    return 0;
}
