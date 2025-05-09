/*
构造函数的分类及调用

两种分类方式：
1. 按参数分为：有参构造和无参构造
2. 按类型分为：普通构造和拷贝构造

三种调用方式：
1. 括号法
2. 显示法
3. 隐式转换法

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


// 1.构造函数分类
// 按照参数分类为：有参和无参构造，无参又称为默认构造函数
// 按照类型分类为：普通构造和拷贝构造
class person {
public:
    // 无参（默认）构造函数
    person() {
        cout << "无参构造函数" << endl;
    }

    // 有参构造函数
    person(int a) {
        age = a;
        cout << "有参数构造函数" << endl;
    }

    // 拷贝构造函数
    person(const person &p) {
        age = p.age;
        cout << "拷贝构造函数" << endl;
    }

    // 析构函数
    ~person() {
        cout << "析构函数" << endl;
    }

public:
    int age;
};

// 2.构造函数的调用
// 调用无参构造函数
void test01() {
    person p; // 调用无参构造函数
}

// 调用有参的构造函数
void test02() {
    // 2.1 括号发，常用
    person p1(10);

    // 注意-1：调用无参构造函数不能加括号，如果加了编译器一般认为式一个函数声明
    // person p21();

    // 2.2 显式法
    person p2 = person(10);
    person p3 = person(p2);
    // person(10); // 单独写就是一个匿名对象，当前行结束之后，马上析构

    // 2.3 隐式转换法
    person p4 = 10;
    person p5 = p4;

    // 注意-2：不能利用 拷贝构造函数 初始化匿名对象 编译器认为是对象声明
    // person p6(p4);
}

int main() {
    // test01();
    test02();
    return 0;
}
