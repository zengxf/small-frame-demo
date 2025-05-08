/*
引用的本质
在 C++ 内部实现是一个指针常量

总结：C++ 推荐使用引用技术，因为语法方便，
引用的本质是指针常量，但是所有的指针操作编译器都帮我们完成了

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 发现是引用，转换为 int * const ref = &a;
void func(int &ref) {
    ref = 100; // ref 是引用，转换为 *ref = 100
}

int main() {
    int a = 10;

    // 自动转换为 int * const ref = &a;
    // 指针常量是指针指向不可改，也说明为什么引用不可更改
    int &ref = a;

    ref = 20; // 内部发现 ref 是引用，自动帮我们转换为：*ref = 20;

    cout << "a = " << a << endl;
    cout << "ref = " << ref << endl;

    func(a);
    cout << "a = " << a << endl;

    return 0;
}
