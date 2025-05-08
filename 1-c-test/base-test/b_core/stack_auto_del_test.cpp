/*
栈区：
由编译器自动分配释放，存放函数的参数值、局部变量等
注意：不要返回局部变量的地址，栈区开辟的数据由编译器自动释放

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


int *func() {
    int a = 10;

    // 注意：不要返回局部变量的地址，栈区开辟的数据由编译器自动释放
    return &a;
}

int main() {
    int *p = func(); // NULL

    cout << *p << endl;
    cout << *p << endl;

    return 0;
}
