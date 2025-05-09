/*
全局函数做友元

*****************************

友元

生活中你的家里客厅（public)，有你的卧室(private)，但是有时卧室也会允许好朋友进去；
在程序里，有些私有属性也想让类外部的一些函数或者类进行访问，就需要用到友元的技术；
友元的目的就是让一个函数或者类访问另一个类中的私有成员；

友元的关键字为 friend;

友元的三种实现：
1. 全局函数做友元
2. 类做友元
3. 成员函数做友元

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;

class Building {
    // 告诉编译器 goodGay 全局函数 是 Building 类的好朋友，可以访问类中的私有内容
    friend void goodGay(Building *building);

public:
    string m_SittingRoom; // 客厅
    Building() {
        this->m_SittingRoom = "客厅";
        this->m_BedRoom = "卧室";
    }

private:
    string m_BedRoom; // 卧室
};

void goodGay(Building *building) {
    cout << "好朋友正在访问：" << building->m_SittingRoom << endl;
    cout << "好朋友正在访问：" << building->m_BedRoom << endl;
}

void test01() {
    Building b;
    goodGay(&b);
}

int main() {
    test01();
    return 0;
}
