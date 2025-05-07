//
// Created by ZXF on 2025/5/7.
//

#include <iostream>
using namespace std;

// 声明
int max(int a, int b);
// int max(int b, int b); // 不能重复声明

int main() {
    int a = 100;
    int b = 200;
    cout << max(a, b) << endl;
    return 0;
}

// 如果main()前面没有进行max()函数的声明，max()函数是无法调用的
// 定义
int max(int a, int b) {
    return a > b ? a : b;
}
