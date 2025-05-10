/*
多态的基本概念

多态分为两类：
- 静态多态：函数重载 和 运算符重载 属于静态多态，复用函数名
- 动态多态：派生类和虚函数实现运行时多态

静态多态和动态多态区别：
- 静态多态的函数地址早绑定：编译阶段确定函数地址
- 动态多态的函数地址晚绑定：运行阶段确定函数地址

多态满足条件：
- 有继承条件
- 子类重写父类中的虚函数

多态使用条件：
- 父类指针或引用指向子类对象
重写：函数返回值类型 函数名 函数列表 完全一致称为重写

总结：C++ 中开发提倡利用多态设计程序结构，因为多态优点很多

Created by ZXF on 2025/5/10.
*/
#include <iostream>
using namespace std;


class Animal {
public:
    // speak 函数就是虚函数
    // 函数前面加上 virtual 关键字，变成虚函数，那么编译器在编译的时候就不能确定函数调用
    virtual void speak() {
        cout << "动物在说话" << endl;
    }
};

class Cat : public Animal {
public:
    void speak() {
        cout << "小猫在说话" << endl;
    }
};

// 我们希望传入什么对象，那么就调用什么对象的函数
// 如果函数地址在编译阶段就能确定，那么静态联编
// 如果函数地址在运行阶段才能确定，那么动态联编
void DoSpeak(Animal &animal) {
    animal.speak();
}

// 多态满足条件
// 1.有继承关系
// 2.子类重写父类中的虚函数
// 多态使用
// 父类指针或引用指向子类对象
void test01() {
    Cat cat;
    // Animal & animal = cat; 父类的引用接收子类的对象
    // C++ 允许父类和子类之间进行类型转换，此处时父类的引用指向子类的对象
    DoSpeak(cat);
}

int main() {
    test01();
    return 0;
}
