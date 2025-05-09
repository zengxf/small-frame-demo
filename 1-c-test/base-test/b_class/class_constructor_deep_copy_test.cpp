/*
深拷贝和浅拷贝

深浅拷贝是面试经典问题，也是常见的坑
1. 深拷贝：在堆区重新申请空间，进行拷贝操作
2. 浅拷贝：简单的赋值拷贝操作

总结：如果属性有在堆区开辟的，一定要自己提供拷贝构造函数，防止浅拷贝带来的问题

Created by ZXF on 2025/5/9.
*/

#include <iostream>
using namespace std;


class person {
public:
    person() {
        cout << "无参构造函数" << endl;
        m_age = 0;
    }

    // 有参构造函数
    person(int age, int height) {
        cout << "有参构造函数的调用" << endl;
        m_age = age;
        m_height = new int(height);
    }

    person(const person &p) {
        cout << "拷贝构造函数" << endl;
        // 如果不利用深拷贝在堆区创建新内容，会导致浅拷贝带来的重复释放堆区问题
        m_age = p.m_age;
        m_height = new int(*p.m_height);
    }

    // 析构函数在释放内存之前调用
    ~person() {
        cout << "析构函数" << endl;
        if (m_height != NULL) {
            delete m_height;
        }
    }

public:
    int m_age;
    int *m_height;
};

void test01() {
    // person p11(18); // 没这构造函数

    person p1(18, 100);
    person p2(p1);
    *p2.m_height = 22;

    cout << "p1 的年龄为: " << p1.m_age << " 身高: " << *p1.m_height << endl;
    cout << "p2 的年龄为: " << p2.m_age << " 身高: " << *p2.m_height << endl;
}

int main() {
    test01();
    return 0;
}
