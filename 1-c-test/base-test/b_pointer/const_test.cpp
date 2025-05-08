/*
const修饰指针

const修饰指针有三种情况：
const修饰指针 —— 常量指针
const修饰常量 —— 指针常量
const既修饰指针，又修饰常量

Created by ZXF on 2025/5/8.
*/


#include <iostream>
using namespace std;

int main() {
    int a = 10;
    int b = 10;

    // const修饰的是指针，指针指向可以改，指针指向的值不可以更改
    const int *p1 = &a;
    p1 = &b; // 正常
    // *p1 = 100; // 报错

    // const修饰的是常量，指针指向不可以改，指针指向的值可以更改
    int *const p2 = &a;
    // p2 = &b; // 报错
    *p2 = 100; // 正常

    // const既修饰指针又修饰常量
    const int *const p3 = &a;
    // p3 = &b; // 报错
    // *p3 = 100; // 报错

    return 0;
}
