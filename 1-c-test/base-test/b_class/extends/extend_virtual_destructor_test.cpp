/*
虚析构和纯虚析构
多态使用时，如果子类中有属性开辟到堆区，那么父类指针在释放时无法调用到子类的析构代码；
解决方法：将父类中的析构函数改为虚析构和纯虚析构；
虚析构 和 纯虚析构 共性：
虚析构 和 纯虚析构 区别：

虚析构语法：virtual ~类名（）{}
纯虚析构语法：
virtual ~类名（） = 0;
类名::~类名（）{}


总结：
1.虚析构或纯虚析构就是用来解决通过父类指针释放子类对象
2.如果子类中没有堆区数据，可以不写为虚析构或纯虚析构
3.拥有纯虚析构函数的类也属于抽象类

Created by ZXF on 2025/5/10.
*/
#include <iostream>
using namespace std;

class Animal {
public:
    Animal() {
        cout << "Animal 构造函数调用" << endl;
    }

    virtual void Speak() = 0;

    // 析构函数加上 virtual 关键字，变成虚析构函数
    // ~Animal() { // 这种写法，是不会调用子类的析构函数
    // virtual ~Animal() {
    //     cout << "Animal 虚析构函数调用" << endl;
    // }

    virtual ~Animal() = 0;
};

Animal::~Animal() {
    cout << "Animal 纯虚析构函数调用" << endl;
}

// 和包含普通纯虚函数的类一样，包含了纯虚析构函数的类也是一个抽象类，不能够被实例化
class Cat : public Animal {
public:
    Cat(string name) {
        cout << "Cat 构造函数调用" << endl;
        m_Name = new string(name);
    }

    virtual void Speak() {
        cout << *m_Name << " 小猫在说话" << endl;
    }

    ~Cat() {
        cout << "Cat 析构函数调用" << endl;
        if (this->m_Name != NULL) {
            delete m_Name;
            m_Name = NULL;
        }
    }

public:
    string *m_Name;
};

void test01() {
    Animal *animal = new Cat("Tom");
    animal->Speak();
    // 通过父类指针去释放，会导致子类对象可能清理不干净，造成内存泄漏
    // 怎么解决？给基类增加一个虚析构函数
    // 虚析构函数就是用来解决通过父类指针释放子类对象
    delete animal;
}

int main() {
    test01();
    return 0;
}
