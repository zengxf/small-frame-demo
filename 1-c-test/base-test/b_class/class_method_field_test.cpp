/*
封装的意义
1. 将属性和行为作为一个整体，变现生活中的事物
2. 将属性和行为加以权限控制，访问的权限有三种：
① public 公共权限
② protected 保护权限
③ private 私有权限

语法： class 类名{ 访问权限：属性/行为}

示例-1： 设计一个圆类，求圆的周长

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 圆周率
const double PI = 3.14;

// 1.封装的意义
// 将属性和行为作为一个整体，用来表现生活中的事物
// class代表设计一个类，后面跟着的是类名
class Circle {
public: // 访问权限 公共的权限
    // 属性
    int m_r; // 半径

    // 行为
    // 获取圆的周长
    double calculateZC() {
        return 2 * PI * m_r;
    }
};

int main() {
    // 通过圆类，创建圆的对象
    // c1 就是一个具体的圆
    Circle c1;
    c1.m_r = 10;

    cout << "圆的周长：" << c1.calculateZC() << endl;

    return 0;
}
