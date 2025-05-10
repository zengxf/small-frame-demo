/*
虚继承

菱形继承
两个派生类继承同一个基类，
又有某个类同时继承着两个派生类，
这种继承被称为菱形继承，或者钻石继承。

总结-1：菱形继承带来的主要问题是子类继承两份相同的数据，导致资源浪费以及毫无意义
总结-2：利用虚继承可以解决菱形继承的问题

Created by ZXF on 2025/5/10.
*/

#include <iostream>
using namespace std;

class Animal {
public:
    int m_Age;
};

// 继承前加 virtual 关键，变为虚继承
// 此时公共的父类 Animal 称为虚基类
class Sheep : virtual public Animal {
};

class Tuo : virtual public Animal {
};

class SheepTuo : public Sheep, public Tuo {
};

void test01() {
    SheepTuo s1;
    s1.Sheep::m_Age = 100;
    s1.Tuo::m_Age = 200;
    cout << "s1.Sheep::m_Age = " << s1.Sheep::m_Age << endl;
    cout << "s1.Tuo::m_Age = " << s1.Tuo::m_Age << endl;
    // cout << "s1.m_Age = " << s1.m_Age << endl;
    cout << "sizeof(SheepTuo): " << sizeof(SheepTuo) << endl; // 不使用 virtual 只有 8; 使用 virtual 有 24
}

int main() {
    test01();
    return 0;
}
