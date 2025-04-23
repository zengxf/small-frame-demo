//
// 线程测试
// Created by ZXF on 2025/4/23.
//

#include <iostream>
#include <thread>


// 一个简单的函数，用于线程执行
void printMessage(const std::string &message) {
    std::cout << "Thread says: " << message << std::endl;
    std::cout << "cur Thread ID: " << std::this_thread::get_id() << std::endl;
    std::cout << std::endl;
}


int main() {
    // 创建一个线程，执行 printMessage 函数
    std::thread t(printMessage, "Hello from thread!");

    std::cout << "new thread.id: " << t.get_id() << std::endl;

    // 等待线程完成
    t.join();

    std::cout << "Main Thread ID: " << std::this_thread::get_id() << std::endl;
    std::cout << "Main thread continues..." << std::endl;
    return 0;
}
