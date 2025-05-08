/*
结构体指针
作用： 通过指针访问结构体中的成员
利用操作符 -> 可以通过结构体指针访问结构体属性

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 结构体定义
struct student {
    // 成员列表
    string name;
    int age;
    int score;
};

int main() {
    struct student stu = {"张三", 18, 100};
    struct student *p = &stu;

    p->score = 80; // 指针通过->操作符访问成员

    cout << "姓名：" << p->name << " 年龄：" << p->age << " 分数：" <<
            p->score << endl;

    return 0;
}
