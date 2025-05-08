/*
示例-2： 设计一个学生类，属性有姓名和学号，可以给姓名和学号赋值，可以显示学生的 姓名和学号

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;


// 学生类
class Student {
public:
    void setName(string name) {
        m_name = name;
    }

    void setID(int id) {
        m_id = id;
    }

    void showStudent() {
        cout << "name: " << m_name << "; ID: " << m_id << endl;
    }

private:
    string m_name;
    int m_id;
};

int main() {
    Student stu;

    stu.setName("德玛西亚");
    stu.setID(666);
    stu.showStudent();

    return 0;
}
