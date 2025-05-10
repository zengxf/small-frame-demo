/*
继承
继承的基本语法 class A : public B

继承的好处：可以减少重复的代码；
class A : public B
A 类称为子类或派生类，B 类称为父类或基类；
派生类中的成员，包含两大部分：一类是基类继承过来的，一类是自己增加的成员；
从基类继承过来的表现为共性，而新增的成员体现了其个性。

继承方式
语法： class 子类：继承方式 父类

继承的方式：
公共继承
保护继承
私有继承

Created by ZXF on 2025/5/10.
*/

#include <iostream>
using namespace std;

// 公共界面
class BasePage {
public:
    void header() {
        cout << "首页、公开课、登录、注册。。。（公共头部）" << endl;
    }

    void footer() {
        cout << "帮助中心、交流合作、站内地图。。。（公共底部）" << endl;
    }

    void left() {
        cout << "Java, Python, C++...（公共分类列表）" << endl;
    }
};

// Java 页面
class Java : public BasePage {
public:
    void content() {
        cout << "JAVA 学科视频" << endl;
    }
};

void test01() {
    // Java 页面
    cout << "Java 下载视频页面如下：" << endl;
    Java ja;
    ja.header();
    ja.footer();
    ja.left();
    ja.content();
    cout << "*********************" << endl;
}

int main() {
    test01();
    return 0;
}
