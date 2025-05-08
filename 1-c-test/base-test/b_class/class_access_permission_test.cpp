/*
权限测试

三种权限
公共权限 public 类里面/外面都可以访问
保护权限 protected 类里面可以访问，类外面不可以访问
私有权限 private 类里面可以访问，类外面不可以访问

默认是私有权限

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


class Person {
public:
    string m_name;

protected:
    string m_car;

private:
    int m_password;

public:
    void func() {
        m_name = "张三";
        m_car = "拖拉机";
        m_password = 123456;
    }

    void out() {
        cout << m_name << " " << m_car << " " << m_password << endl;
    }
};

class C1 {
    int m_a; // 默认是私有权限
};


int main() {
    Person p;

    p.func();

    p.m_name = "李四";
    // p.m_car = "比亚迪"; // 保护权限类外无法访问
    // p.m_password = 123; // 私有权限类外无法访问

    p.out();


    C1 c1;
    // c1.m_a = 10; // 错误，访问权限是私有

    return 0;
}
