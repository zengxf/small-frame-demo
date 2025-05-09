/*
构造函数调用规则
默认情况下，C++编译器至少给一个类添加3个函数
1. 默认构造函数（无参，函数体为空）
2. 默认析构函数（无参，函数体为空）
3. 默认拷贝构造函数，对属性进行值拷贝

构造函数调用规则如下：
1. 如果用户定义有参构造函数，C++不在提供默认无参构造，但是会提供默认拷贝构造
2. 如果用户定义拷贝构造函数，C++不会再提供其他构造函数

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    person() {
        cout << "无参构造函数" << endl;
        m_age = 0;
    }

    person(int age) {
        cout << "有参构造函数" << endl;
        m_age = age;
    }

    person(const person &p) {
        cout << "拷贝构造函数" << endl;
        m_age = p.m_age;
    }

    // 析构函数在释放内存之前调用
    ~person() {
        cout << "析构函数" << endl;
    }

public:
    int m_age;
};

void test01() {
    person p1(10);
    // 如果不写拷贝构造，编译器会自动添加拷贝构造，并且做浅拷贝操作
    person p2(p1);
    cout << "p2的年龄为：" << p2.m_age << endl;
}

void test02() {
    // 如果用户提供有参构造，编译不会提供默认构造，会提供拷贝构造
    person p1; // 此时如果用户自己没有提供默认构造，会出错
    person p2(10); // 用户提供的有参
    person p3(p2); // 此时如果用户没有提供拷贝制造，编译器会提供

    // 如果用户提供拷贝构造，编译器不会提供其他构造函数
    person p4; // 此时如果用户自己没有提供默认构造，会出错
    person p5(10); // 此时如果用户自己没有提供有参，会出错
    person p6(p5); // 用户自己提供拷贝构造
}

int main() {
    // test01();
    test02();
    return 0;
}
