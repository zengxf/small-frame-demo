//
// 指针的危险性与安全性 测试
// Created by ZXF on 2025/4/22.
//

#include <iostream>
#include <memory>


// 野指针（Dangling Pointer）
/**
 * 导致野指针的常见场景:
 *
 * 1. 未初始化指针
 * <pre>
 *   int* ptr; // 未初始化，可能成为野指针
 * </pre>
 *
 * 2. 释放后未置空
 * <pre>
 *   int* ptr = new int(10);
 *   delete ptr; // 释放内存
 *   // ptr 此时仍指向已释放的内存，成为野指针
 * </pre>
 *
 * 3. 超出作用域的对象指针
 * <pre>
 *   int* danglingPtr;
 *   {
 *       int local = 42;
 *       danglingPtr = &local; // 局部变量作用域结束，指针失效
 *   }
 * </pre>
 */
void Dangling_Pointer_test() {
    int *ptr; //

    {
        int var = 10;
        ptr = &var;
    } // var 的作用域结束，内存被释放

    std::cout << "Value pointed by ptr: " << *ptr << std::endl; // 未定义行为
}

// 空指针（Null Pointer）
void Null_Pointer_test() {
    int *ptr = nullptr; // 初始化为空指针
    if (ptr != nullptr) {
        std::cout << "ptr is not null" << std::endl;
    } else {
        std::cout << "ptr is null" << std::endl;
    }
}

// 智能指针（Smart Pointers）
void Smart_Pointers_test() {
    // 使用 unique_ptr
    std::unique_ptr<int> ptr1 = std::make_unique<int>(200);
    std::cout << "Value pointed by ptr1: " << *ptr1 << std::endl;

    // 使用 shared_ptr
    std::shared_ptr<int> ptr2 = std::make_shared<int>(30); //

    {
        std::shared_ptr<int> ptr3 = ptr2;
        std::cout << "Value pointed by ptr3: " << *ptr3 << std::endl;
    } // ptr3 超出作用域，但内存不会被释放，因为 ptr2 仍然存在

    std::cout << "Value pointed by ptr2: " << *ptr2 << std::endl;
}

// 避免野指针-示例
void fix_Dangling_Pointer_test() {
    // 使用智能指针
    std::unique_ptr<int> ptr1 = std::make_unique<int>(100);
    std::cout << "Value: " << *ptr1 << std::endl;
    // 不需要手动释放内存，智能指针会自动处理

    // ---------------------------------

    // 释放内存后置空
    int *ptr2 = new int(200);
    std::cout << "Value: " << *ptr2 << std::endl;
    delete ptr2;
    ptr2 = nullptr; // 释放内存后置空
    // 后续使用 ptr 时，可以检查是否为 nullptr
}

int main() {
    Dangling_Pointer_test();
    std::cout << std::endl;

    Null_Pointer_test();
    std::cout << std::endl;

    Smart_Pointers_test();
    std::cout << std::endl;

    fix_Dangling_Pointer_test();
    std::cout << std::endl;

    return 0;
}
