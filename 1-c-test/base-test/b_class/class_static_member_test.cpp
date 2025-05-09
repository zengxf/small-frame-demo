/*
静态成员
静态成员就是在成员变量和成员函数前加上关键字static，成为静态成员；

静态成员分为：
1. 静态成员变量
- 所有对象共享同一份数据
- 在编译阶段分配内存
- 类内声明，类外初始化

2. 静态成员函数
- 所有对象共享同一个函数
- 静态成员函数只能访问静态成员变量

静态成员变量特点
1.在编译阶段分配内存
2.类内声明，类外初始化
3.所有对象共享同一份数据

静态成员函数特点：
1.程序共享一个函数
2.静态成员函数只能访问静态成员变量

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    static int m_A; // 静态成员变量
    int B;

    static void func() {
        cout << "func 调用" << endl;
        m_A = 100;
        m_B = 100;
        // B = 100; // 错误，不可以访问非静态成员变量
    }

private:
    static int m_B; // 静态成员变量也是有访问权限的

    static void func2() {
        cout << "func2调用" << endl;
    }
};

int person::m_A = 10;
int person::m_B = 10;

void test01() {
    // 静态成员变量有两种方式

    // 1.通过对象
    person p1;
    p1.m_A = 100;
    cout << "p.m_A = " << p1.m_A << endl;

    person p2;
    p2.m_A = 200;
    cout << "p1.m_A = " << p1.m_A << endl; // 共享同一份数据
    cout << "p2.m_A = " << p2.m_A << endl;

    // 2.通过类名
    cout << "m_A = " << person::m_A << endl;
    // cout << "m_B" << person::m_B << endl; // 私有权限访问不到

    // -------------------------------
    // 静态成员变量两种访问方式

    // 1.通过对象
    person p11;
    p11.func();

    // 2.通过类名
    person::func();
    // person::func2(); // 私有权限无法访问
}

int main() {
    test01();
    return 0;
}
