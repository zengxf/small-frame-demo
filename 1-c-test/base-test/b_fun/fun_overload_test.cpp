/*
函数重载注意事项
引用作为重载条件
函数重载碰到函数默认参数

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 函数重载注意事项
// 1. 引用作为重载条件
void func(int &a) {
    cout << "func (int &a) 的调用" << endl;
}

void func(const int &a) {
    cout << "func (const int &a) 的调用" << endl;
}

// 2. 函数重载碰到函数默认参数
void func2(int a, int b = 10) {
    cout << "func2 (int a, int b=10) 调用" << endl;
}

void func2(int a) {
    cout << "func2 (int a) 调用" << endl;
}

int main() {
    int a = 10;
    func(a); // 调用无 const
    func(10); // 调用有 const

    // func2(10); // 碰到默认参数产生歧义， 需要避免（因为两个函数都满足调用条件）
    func2(10, 3);

    return 0;
}
