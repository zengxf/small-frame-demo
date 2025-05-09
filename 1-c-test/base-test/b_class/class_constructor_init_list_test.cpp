/*
初始化列表
作用： C++提供了初始化列表语法，用来初始化属性
语法： 构造函数（）：属性1（值1），属性2（值2）....{}

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    // 传统方式初始化
    // person(int a, int b, int c) {
    //     m_A = a;
    //     m_B = b;
    //     m_C = c;
    // }

    // 初始化列表方式初始化
    person(int m_A, int b, int c): m_A(m_A), m_B(b), m_C(c) {
        cout << "初始化列表方式初始化" << endl;
    }

    void printperson() {
        cout << "mA:" << m_A << endl;
        cout << "mB:" << m_B << endl;
        cout << "mC:" << m_C << endl;
    }

public:
    int m_A;
    int m_B;
    int m_C;
};

void test01() {
    person p1(1,2,3);
    p1.printperson();
}

int main() {
    test01();
    return 0;
}
