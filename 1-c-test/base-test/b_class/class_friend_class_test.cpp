/*
类做友元

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


// ******** 声明 ********

class Building;

class GoodGay {
public:
    GoodGay();

    void visit();

private:
    Building *building;
};


// ******** 实现 ********

class Building {
    // 告诉编译器 GoodGay 类是 Building 类的好朋友，可以访问到 Building 类中私有内容
    friend class GoodGay;

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


// ******** 测试 ********

void test01() {
    GoodGay gg;
    gg.visit();
}

int main() {
    test01();
    return 0;
}
