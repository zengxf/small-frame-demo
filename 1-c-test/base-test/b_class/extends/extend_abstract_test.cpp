/*
纯虚函数和抽象类

在多态中，通常父类中虚函数的实现是毫无意义的，主要都是调用子类重写的内容；
因此可以将虚函数改为纯虚函数；
纯虚函数语法：virtual 返回值类型 函数名 （参数列表） = 0;
当类中有了纯虚函数，这个类也称为抽象类。

抽象类的特点：
无法实例化对象
子类必须重写抽象类中的纯虚函数，否则也属于抽象类

Created by ZXF on 2025/5/10.
*/
#include <iostream>
using namespace std;

class Base {
public:
    virtual void func() = 0;
};

class Son : public Base {
public:
    virtual void func() {
        cout << "func 调用" << endl;
    };
};

void test01() {
    Base *base = NULL;

    // base = new Base; // 错误，抽象类无法实例化对象

    base = new Son();
    base->func();

    delete base; // 记得销毁
}

int main() {
    test01();
    return 0;
}
