/*
拷贝构造函数调用时机

C++中拷贝构造函数调用时机通常有三种情况：
1. 使用一个已经创建完毕的对象来初始化一个新对象
2. 值传递的方式给函数传值
3. 以值的方式返回局部对象

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

// 1.使用一个已经创建完毕的对象来初始化一个新对象
void test01() {
    person man(100); // p对象已经创建完毕
    person newman(man); // 调用拷贝构造函数
    person newman2 = man; // 拷贝构造

    // person newman3;
    // newman3 = man; // 不是调用拷贝函数，赋值操作
}

// 2.值传递的方式给函数传值
// 相当于person p1 = p;
void dowork(person p1) {
}

void test02() {
    person p; // 无参构造函数
    dowork(p);
}

// 3.以值传递的方式返回局部对象
person dowork2() {
    person p1;
    cout << (int *) &p1 << endl;
    return p1;
}

void test03() {
    person p = dowork2();
    cout << (int *) &p << endl;
}

int main() {
    // test01();
    test02();
    // test03();
    return 0;
}
