/*
常量引用
作用： 常量引用主要用来修饰形参，防止误操作
在函数形参列表中，可以加const修饰形参，防止形参改变实参

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 引用使用的场景，通常用来修饰形参
void showValue(const int &v) {
    // v += 10; // v 相当于只读 (Cannot assign to readonly type const int)
    cout << v << endl;
}

int main() {
    // int &ref = 10; // 引用本身需要一个合法的内存空间，因此这行是错误的
    // 加入 const 就可以了，编译器优化代码：int temp = 10; const int & ref = temp;
    const int &ref = 10;

    // ref = 100; // 加入const后不可以修改变量
    cout << ref << endl;

    // 函数中利用常量引用防止误操作修改实参
    int a = 20;
    showValue(a);

    return 0;
}
