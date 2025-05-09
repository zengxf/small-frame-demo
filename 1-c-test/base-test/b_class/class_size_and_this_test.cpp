/*
成员变量和成员函数分开存储

在C++中，类内的成员变量和成员函数分开存储；
只有非静态成员变量才属于类的对象。

**********************************

this指针概念

每一个非静态成员函数只会诞生一份函数实例，也就是说多个同类型的对象会公用一块代码；

this指针指向被调用的成员函数所属的对象。
this指针是隐含每一个非静态成员函数内的一种指针；
this指针不需要定义，直接使用即可；

this指针的用途：
1. 当形参和成员变量同名时，可用this指针来区分
2. 在类的非静态成员函数中返回对象本身，可使用 return *this

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    person() {
        mA = 0;
    }

    person(int age) {
        // 1、当形参和成员变量同名时，可用this指针来区分
        this->age = age;
    }

    person &personAddPerson(person p) {
        this->age += p.age;
        return *this; // 返回对象本身
    }

    // 非静态成员变量占对象空间
    int mA;
    int ma;
    int age;

    // 静态成员变量不占对象空间
    static int mB;

    // 函数也不占对象空间，所有函数共享一个函数实例
    void func() {
        cout << "mA:" << this->mA << endl;
    }

    // 静态成员函数也不占对象空间
    static void sfunc() {
    }
};


void test01() {
    cout << "sizeof(person): " << sizeof(person) << endl;

    person p1(10);
    cout << "p1.age = " << p1.age << endl;

    person p2(10);
    p2.personAddPerson(p1).personAddPerson(p1).personAddPerson(p1);
    cout << "p2.age = " << p2.age << endl;
}

int main() {
    test01();
    return 0;
}
