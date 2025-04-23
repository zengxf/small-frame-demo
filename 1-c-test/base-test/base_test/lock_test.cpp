//
// 锁测试
// Created by ZXF on 2025/4/23.
//

#include <iostream>
#include <thread>
#include <chrono>
#include <mutex>

/**
 * std::mutex 是最基本的互斥锁，用于保护共享数据，防止多个线程同时访问导致的数据竞争。
 */
std::mutex coutMutex; // 全局互斥锁

/**
 * std::lock_guard 是一个简单的 RAII （Resource Acquisition Is Initialization）（资源获取即初始化）包装器，
 * 用于管理 std::mutex 的锁定和解锁。
 * 它在构造时自动锁定互斥锁，在析构时自动解锁。
 */
void lock_test(int id) {
    for (int i = 0; i < 5; ++i) {
        // 加锁，确保只有一个线程能访问 std::cout
        std::lock_guard<std::mutex> lock(coutMutex);
        std::cout << "Thread " << id << " is working, iteration: " << i << std::endl;

        // 模拟工作
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
    }
}

std::mutex mtx;
std::mutex mtx2;
int counter = 0;

void increment(int num) {
    for (int i = 0; i < num; ++i) {
        std::lock_guard<std::mutex> lock(mtx); // 自动锁定
        ++counter;
        std::cout << "Thread " << std::this_thread::get_id() << " counter: " << counter << std::endl;
        // 锁在 lock_guard 对象析构时自动释放
    }
}

void test_lock1() {
    std::thread t1(increment, 5);
    std::thread t2(increment, 5);

    t1.join();
    t2.join();

    std::cout << "Final counter value: " << counter << std::endl;
}

/**
 * std::unique_lock 提供了比 std::lock_guard 更灵活的锁管理方式。
 * 它可以在构造时锁定，也可以在之后手动锁定和解锁。
 */
void increment2(int num) {
    for (int i = 0; i < num; ++i) {
        std::unique_lock<std::mutex> lock(mtx2); // 自动锁定
        ++counter;
        std::cout << "Thread " << std::this_thread::get_id() << " counter: " << counter << std::endl;
        lock.unlock(); // 手动解锁

        // 模拟一些不需要锁定的操作
        std::this_thread::sleep_for(std::chrono::milliseconds(100));

        lock.lock(); // 再次锁定
        ++counter;
        std::cout << "Thread 2lock " << std::this_thread::get_id() << " counter: " << counter << std::endl;
        // 锁在 unique_lock 对象析构时自动释放
    }
}

void test_lock2() {
    std::thread t1(increment2, 5);
    std::thread t2(increment2, 5);

    t1.join();
    t2.join();

    std::cout << "Final counter value: " << counter << std::endl;
}

int main() {
    test_lock1();
    std::cout << std::endl;

    test_lock2();
    std::cout << std::endl;

    return 0;
}
