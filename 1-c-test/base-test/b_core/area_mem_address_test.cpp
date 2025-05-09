/*
内存分配模型(深入研究)

C++程序在执行时，将内存大方向划分为4个区域
代码区：存放函数体的二进制代码，由操作系统进行管理
全局区：存放全局变量和静态变量以及常量
栈区：由编译器自动分配释放，存放函数的参数值、局部变量等
堆区：由程序员分配和释放，若程序员不释放，程序结束时由操作系统回收

全局区：
全局变量和静态变量存放在此；

总结-3：全局区中存放全局变量，静态变量，常量
总结-4：常量区中存放const修饰的全局常量和字符串常量

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 全局变量
int g_a = 10;
int g_b = 10;
// 全局常量
const int c_g_a = 10;
const int c_g_b = 10;

int main() {
    // 局部变量
    int a = 10;
    int b = 10;

    // 打印地址
    cout << "局部变量a地址为：" << (int *) &a << endl;
    cout << "局部变量b地址为：" << (int *) &b << endl;

    // **************************** 这些变量的地址是一块的 ****************************
    cout << endl;
    cout << "全局变量g_a地址为：" << (int *) &g_a << endl;
    cout << "全局变量g_b地址为：" << (int *) &g_b << endl;

    // 静态变量
    static int s_a = 10;
    static int s_b = 10;
    cout << "静态变量s_a地址为：" << (int *) &s_a << endl;
    cout << "静态变量s_b地址为：" << (int *) &s_b << endl;
    cout << "字符串常量地址为：" << (int *) &"hello world" << endl;
    cout << "字符串常量地址为：" << (int *) &"hello world1" << endl;
    cout << "全局常量c_g_a地址为：" << (int *) &c_g_a << endl;
    cout << "全局常量c_g_b的地址为：" << (int *) &c_g_b << endl;
    cout << endl;

    // **************************** 这些变量的地址是一块的 ****************************
    const int c_l_a = 10;
    const int c_l_b = 10;
    cout << "局部常量c_l_a地址为：" << (int *) &c_l_a << endl;
    cout << "局部常量c_l_b地址为；" << (int *) &c_l_b << endl;

    return 0;
}
