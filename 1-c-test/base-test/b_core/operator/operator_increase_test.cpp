/*
递增运算符重载
作用： 通过重载递增运算，实现自己的整形数据

总结：前置递增返回引用，后置递增返回值

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class MyInteger {
    friend ostream &operator<<(ostream &out, MyInteger myint);

public:
    MyInteger() {
        m_Num = 0;
    }

    // 前置++
    MyInteger &operator++() {
        // 先++
        m_Num++;

        // 再返回
        return *this;
    }

    // 后置++
    MyInteger operator++(int) {
        // 先返回
        MyInteger temp = *this; // 记录当前本身的值，然后让本身的值加1，但是返回的是以前的值，达到先返回再++
        m_Num++;
        return temp;
    }

private:
    int m_Num;
};

ostream &operator<<(ostream &out, MyInteger myint) {
    out << myint.m_Num;
    return out;
}

// 前置++，先++，再返回
void testOut() {
    MyInteger myInt;
    cout << ++myInt << endl;
    cout << myInt << endl;
}

// 后置++，先返回，再++
void test02() {
    MyInteger myInt;
    cout << myInt++ << endl;
    cout << myInt << endl;
}

int main() {
    testOut();
    cout << endl;

    test02();
    cout << endl;

    return 0;
}
