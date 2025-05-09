/*
函数调用运算符重载

函数调用运算符()也可以重载
由于重载后使用的方式非常像函数的调用，因此称为仿函数
仿函数没有固定写法，非常灵活

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class MyPrint {
public:
    void operator()(string text) {
        cout << text << endl;
    }
};

void testOut() {
    // 重载的（）操作符，也称为仿函数
    MyPrint myFunc;
    myFunc("hello world");
}

class MyAdd {
public:
    int operator()(int v1, int v2) {
        return v1 + v2;
    }
};

void testAdd() {
    MyAdd add;
    int ret = add(10, 10);
    cout << "ret = " << ret << endl;
    // 匿名对象调用
    cout << "MyAdd()(100, 100) = " << MyAdd()(100, 100) << endl;
}

int main() {
    testOut();
    testAdd();
    return 0;
}
