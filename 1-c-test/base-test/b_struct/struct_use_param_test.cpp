/*
结构体做函数参数
总结：如果不想修改主函数中的数据，用值传递，反之用地址传递
和 const 使用场景

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

// 值传递
void printStudent(student stu) {
    stu.age = 28;
    cout << "子函数中 姓名：" << stu.name << " 年龄：" <<
            stu.age << " 分数：" << stu.score << endl;
}

// 地址传递
void printStudent1(student *stu) {
    stu->age = 20;
    cout << "子函数中 姓名：" << stu->name << " 年龄：" <<
            stu->age << " 分数：" << stu->score << endl;
}

// const 使用场景
void printStudent2(const student *stu) {
    // stu->age = 100; // 操作失败，加了 const 修饰
    cout << "姓名：" << stu->name << " 年龄：" << stu->age << " 分数：" << stu->score << endl;
}

int main() {
    student stu = {"张三", 19, 100};

    // 值传递
    printStudent(stu);
    cout << "主函数中 姓名：" << stu.name << " 年龄： " << stu.age << " 分数："
            << stu.score << endl;
    cout << endl;

    // 地址传递
    printStudent1(&stu);
    cout << "主函数中 姓名：" << stu.name << " 年龄： " << stu.age << " 分数："
            << stu.score << endl;
    cout << endl;

    // 地址传递 const
    printStudent2(&stu);
    cout << "主函数中 姓名：" << stu.name << " 年龄： " << stu.age << " 分数："
            << stu.score << endl;

    return 0;
}
