//
// Created by os on 6/21/22.
//

#ifndef PROJEKAT_BASE_ABI_HPP
#define PROJEKAT_BASE_ABI_HPP

#include "../lib/hw.h"

class _thread;
typedef _thread* thread_t;

class _sem;
typedef _sem* sem_t;

namespace Abi {
    void* syscall(int code, void *arg1 = nullptr, void *arg2 = nullptr, void *arg3 = nullptr,
                  void *arg4=nullptr, void *arg5=nullptr, void *arg6 = nullptr, void *arg7=nullptr);

    void *mem_alloc(size_t size);
    int mem_free(void *ptr);

    int thread_create(
            thread_t* handle,
            void (*start_routine)(void*),
            void* arg);
    int thread_exit();
    void thread_dispatch();

    int sem_open(sem_t* handle, unsigned init);
    int sem_close(sem_t handle);
    int sem_wait(sem_t id);
    int sem_signal(sem_t id);

    int time_sleep(time_t);

    char getc();
    void putc(char);

    int thread_create_cpp(thread_t* handle,
                          void (*start_routine)(void*),
                          void* arg);
    int thread_start(thread_t handle);
}

#endif //PROJEKAT_BASE_ABI_HPP
