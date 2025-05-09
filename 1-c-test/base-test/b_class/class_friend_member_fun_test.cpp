/*
成员函数做友元

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


// ******** 声明 ********

class Building;

class GoodGay {
public:
    GoodGay();

    void visit(); // 只让 visit 函数作为 Building 的好朋友，可以访问 Building 中私有内容
    void visit2();

private:
    Building *building;
};


// ******** 实现 ********

class Building {
    // 告诉编译器 GoodGay 类是 Building 类的好朋友，可以访问到 Building 类中私有内容
    // friend class GoodGay;

    // 告诉编译器 GoodGay 类中的 visit 成员函数是 Building 好朋友，可以访问私有内容
    friend void GoodGay::visit();

public:
    Building();

    string m_SittingRoom; // 客厅

private:
    string m_BedRoom; // 卧室
};

Building::Building() {
    this->m_SittingRoom = "客厅";
    this->m_BedRoom = "卧室";
}

GoodGay::GoodGay() {
    building = new Building;
}

void GoodGay::visit() {
    cout << "好朋友正在访问 " << building->m_SittingRoom << endl;
    cout << "好朋友正在访问 " << building->m_BedRoom << endl;
}

void GoodGay::visit2() {
    cout << "好朋友正在访问 " << building->m_SittingRoom << endl;
    // cout << "好朋友正在访问 " << building->m_BedRoom << endl; // err: 不能访问
}


// ******** 测试 ********

void test01() {
    GoodGay gg;

    gg.visit();
    cout << endl;

    gg.visit2();
}

int main() {
    test01();
    return 0;
}
