/*
结构体基本概念
结构体属于用户自定义的数据类型，允许用户存储不同的数据类型

结构体定义和使用
语法： struct 结构体名 {结构体成员列标}；

通过结构体创建变量的方式有三种：
1. struct 结构体名 变量名
2. struct 结构体名 变量名 = {成员1值， 成员2值...}
3. 定义结构体时顺便创建变量

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
} stu3; // 结构体变量创建方式-3

int main() {
    // 结构体变量创建方式-1
    struct student stu1; // struct 关
    stu1.name = "helen";
    stu1.age = 19;
    stu1.score = 100;
    cout << "姓名：" << stu1.name << " 年龄：" << stu1.age << " 分数：" <<
            stu1.score << endl;

    // 结构体创建方式-2
    struct student stu2 = {"李四", 19, 60};
    cout << "姓名：" << stu2.name << " 年龄：" << stu2.age << " 分数：" <<
            stu2.score << endl;

    stu3.name = "王五";
    stu3.age = 25;
    stu3.score = 80;
    cout << "姓名：" << stu3.name << " 年龄：" << stu3.age << " 分数：" <<
            stu3.score << endl;

    return 0;
}
