/*
函数的分文件编写
作用： 让代码结构更加清晰。
函数分文件的编写主要有4个步骤：
1. 创建后缀名为.h的文件
2. 创建后缀名为.cpp的文件
3. 在头文件中写函数的声明
4. 在源文件中写函数的定义

 Created by ZXF on 2025/5/8
 */

// #include "fun_h.h" // 可省
#include "fun_h_cpp.cpp" // 要引用

int main() {
    int a = 100;
    int b = 200;
    swap(a, b);

    printf("a = %d, b = %d\n", a, b);

    return 0;
}
