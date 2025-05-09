/*
const 修饰成员函数

常函数：
1. 成员函数后加const后我们成为常函数
2. 常函数内不可以修改成员属性
3. 成员属性声明时加关键字mutable后，在常函数中依然可以修改

常对象：
1. 声明对象前加const称该对象为常对象
2. 常对象只能调用常函数

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    person() {
        m_A = 0;
        m_B = 0;
    }

    // this指针的本质是一个指针常量，指针的指向不可以修改
    // 如果想让指针指向的值也不可以修改，需要声明常函数
    void showPerson() const {
        // this = NULL; // 不能修改指针的指向 person* const this;

        // this->m_A = 100; // 不可以修改
        // const修饰成员函数，表示指针指向的内存空间的数据不能修改，除了mutable修饰的变量
        this->m_B = 100;
    }

    void myFunc() const {
    }

public:
    int m_A;
    mutable int m_B; // 可修改，可变的
};


void test01() {
    const person p; // 常量对象

    // p.m_A = 100; // 常对象不能修改成员变量的值，但是可以访问
    p.m_B = 100; // 常对象可以修改mutable修饰的成员变量

    cout << p.m_A << endl;
    cout << p.m_B << endl;
}

int main() {
    test01();
    return 0;
}
