/*
结构体嵌套结构体
作用： 结构体中的成员可以是另一个结构体

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

// 学生结构体定义
struct student {
    string name;
    int age;
    int score;
};

// 教师结构体的定义
struct teacher {
    int id;
    string name;
    int age;
    struct student stu; // 子结构体 学生
};

int main() {
    // struct teacher t1;
    teacher t1;

    t1.id = 10000;
    t1.name = "miss.liu";
    t1.age = 40;

    t1.stu.name = "张三";
    t1.stu.age = 10;
    t1.stu.score = 56;

    cout << t1.id << endl;
    cout << t1.stu.name << endl;

    return 0;
}
