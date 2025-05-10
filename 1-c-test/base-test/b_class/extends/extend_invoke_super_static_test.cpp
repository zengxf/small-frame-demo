/*
继承同名静态成员处理方式
问题： 继承中同名的静态成员再子类对象上如何进行访问？

静态成员和非静态成员出现同名，处理方式不一样

Created by ZXF on 2025/5/10.
*/

#include <iostream>
using namespace std;


class Base {
public:
    static void func() {
        cout << "Base - static void func()" << endl;
    }

    static void func(int a) {
        cout << "Base - static void func(int a)" << endl;
    }

    static int m_A;
};

int Base::m_A = 100;

class Son : public Base {
public:
    static void func() {
        cout << "Son - static void func()" << endl;
    }

    static int m_A;
};

int Son::m_A = 200;

// 同名成员属性
void test01() {
    // 通过对象访问
    cout << "通过对象访问：" << endl;
    Son s;
    cout << "Son 下 m_A = " << s.m_A << endl;
    cout << "Base 下 m_A = " << s.Base::m_A << endl;

    // 通过类名访问
    cout << "通过类名访问：" << endl;
    cout << "Son 下 m_A = " << Son::m_A << endl;
    cout << "Base 下 m_A = " << Son::Base::m_A << endl;
}

// 同名成员函数
void test02() {
    // 通过对象访问
    cout << "通过对象访问：" << endl;
    Son s;
    s.func();
    s.Base::func();

    cout << "通过类名访问：" << endl;
    Son::func();
    Son::Base::func();
    // 出现同名，子类会隐藏掉父类中所有同名的成员函数，需要加作用域访问
    Son::Base::func(100);
}

int main() {
    test01();
    // test02();
    return 0;
}
