//
// Created by ZXF on 2025/5/6.
//
/*
  空指针访问成员函数
  C++中空指针也是可以调用成员函数的，但是也要注意有没有用到this指针；
  如果用到this指针，需要加以判断保证代码的健壮性。
 */

#include <iostream>
#include <string>
using namespace std; // Added using directive

class person {
public:
    void showClassName() {
        std::cout << "我是person类" << std::endl;
        cout << "<UNK>person<UNK>" << std::endl;
    }

    void showPerson() {
        std::cout << "show --> 1" << std::endl;
        // std::cout << "show --> " << mAge << std::endl; // err
        if (this == NULL) {
            return;
        }
        std::cout << "show --> " << mAge << std::endl;
    }

public:
    int mAge;
};

void testOut() {
    person *p = NULL;
    p->showClassName(); // 空指针，可以调用成员函数
    p->showPerson(); // 如果成员函数中用到了this指针，就不可以了
}

int main() {
    testOut();
    // system("pause");
    return 0;
}
