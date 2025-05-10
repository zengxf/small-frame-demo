/*
继承方式
语法： class 子类：继承方式 父类

继承的方式：
公共继承
保护继承
私有继承

总结：父类中私有成员也是被子类继承下去了，只是由编译器给隐藏后访问不到

Created by ZXF on 2025/5/10.
*/

#include <iostream>
using namespace std;


class Base1 {
public:
    int m_A;

protected:
    int m_B;

private:
    int m_C; // 私有成员只是被隐藏了，但是还是会继承下去
};

// 公共继承
class Son1 : public Base1 {
public:
    void func() {
        m_A; // 可访问 public 权限
        m_B; // 可访问 protected 权限
        // m_C; // 不可访问
    }
};

void myClass() {
    Son1 s1;
    s1.m_A; // 其他类只能访问到公共权限
}

// 保护继承
class Son2 : protected Base1 {
public:
    void func() {
        m_A; // 可访问 public 权限
        m_B; // 可访问 protected 权限
        // m_C; // 不可访问
    }
};

void myClass2() {
    Son2 s;
    // s.m_A; // 不可访问
}

// 私有继承
class Son3 : private Base1 {
public:
    int m_D;

    void func() {
        m_A; // 可访问 public 权限
        m_B; // 可访问 protected 权限
        // m_C; // 不可访问 private
    }
};

class GrandSon3 : public Son3 {
public:
    void func() {
        // Son3 是私有继承，所以继承 Son3 的属性在 GrandSon3 中都无法访问到
        // m_A;
        // m_B;
        // m_C;
    }
};


int main() {
    myClass();

    cout << "sizeof(Son3): " << sizeof(Son3) << endl;

    return 0;
}
