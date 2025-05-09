/*
运算符重载
运算符重载概念：对已有的运算符重新进行定义，赋予另一种功能，以适应不同的数据类型。

加号运算符重载
作用： 实现两个自定义数据类型相加的运算

总结：
1、对于内置的数据类型的表达式的运算符是不可能改变的
2、不要滥用运算符重载

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class Person {
public:
    Person() {
    };

    Person(int a, int b) {
        this->m_A = a;
        this->m_B = b;
    }

    // 成员函数实现 + 号运算符重载
    Person operator+(const Person &p) {
        Person temp;
        temp.m_A = this->m_A + p.m_A;
        temp.m_B = this->m_B + p.m_B;
        return temp;
    }

public:
    int m_A;
    int m_B;
};

// // 全局函数实现 + 号运算符重载
// Person operator+(const Person &p1, const Person &p2) {
//     Person temp(0, 0);
//     temp.m_A = p1.m_A + p2.m_A;
//     temp.m_B = p1.m_B + p2.m_B;
//     return temp;
// }

// 运算符重载，可以发生函数重载
Person operator+(const Person &p2, int val) {
    Person temp;
    temp.m_A = p2.m_A + val;
    temp.m_B = p2.m_B + val;
    return temp;
}

void test() {
    Person p1(10, 10);
    Person p2(20, 20);

    // 成员函数方式
    Person p3 = p2 + p1; // 相当于 p2.operator+(p1)
    cout << "mA:" << p3.m_A << " mB: " << p3.m_A << endl;

    Person p4 = p3 + 10; // 相当于 operator+(p3, 10)
    cout << "mA:" << p4.m_A << " mB: " << p4.m_B << endl;
}

int main() {
    test();
    return 0;
}
