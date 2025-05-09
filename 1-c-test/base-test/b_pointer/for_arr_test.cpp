/*
指针和数组
作用： 利用指针访问数组中的元素

Created by ZXF on 2025/5/8.
*/

#include <iostream>
using namespace std;

int main() {
    int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    int *p = arr; // 指向数组的指针
    // int *p = &arr[0]; // 和上面语句等价

    cout << "第一个元素：" << arr[0] << endl;
    cout << "指针访问第一个元素：" << *p << endl;

    for (int i = 0; i < 10; i++) {
        // 利用指针遍历数组
        cout << *p << endl;
        p++;
    }

    return 0;
}
