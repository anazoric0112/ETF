//
// Created by os on 6/21/22.
//


#ifndef PROJEKAT_BASE_SYSCALL_CPP_HPP
#define PROJEKAT_BASE_SYSCALL_CPP_HPP

#include "../lib/hw.h"
#include "syscall_c.hpp"
#include "MemoryAllocator.hpp"


class Thread{
public:
    Thread (void (*Body)(void*), void* arg);
    virtual ~Thread();

    int start();
    static void dispatch();
    static int sleep (time_t);

protected:
    Thread();
    virtual void run(){ }

private:
    thread_t myHandle;
    static void thread_cpp_wrapper(void* p);
};

class Semaphore{
public:
    Semaphore (unsigned init=1);
    virtual ~Semaphore();

    int wait();
    int signal();

private:
    sem_t myHandle;
};
class PeriodicThread: public Thread{
protected:
    PeriodicThread (time_t period);
    virtual void periodicActivation(){ }
private:
    static void periodic_thread_cpp_wrapper(void* ptr);
};

class PeriodicThreadWrapper{
public:
    PeriodicThreadWrapper(PeriodicThread* t, time_t p):thread(t),period(p){}
    PeriodicThread* thread;
    time_t period;
};


class Console{
public:
    static char getc();
    static void putc(char c);
};


void* operator new(size_t size);
void operator delete(void* ptr);
void* operator new[](size_t size);
void operator delete[](void* ptr);

#endif //PROJEKAT_BASE_SYSCALL_CPP_HPP