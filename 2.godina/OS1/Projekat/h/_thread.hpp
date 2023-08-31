//
// Created by os on 6/22/22.
//

#ifndef PROJEKAT_BASE___thread_HPP
#define PROJEKAT_BASE___thread_HPP

#include "../lib/hw.h"
#include "Scheduler.hpp"
#include "syscall_c.hpp"
#include "Handler.hpp"

class _thread;
typedef _thread* thread_t;

class _thread{
private:
    typedef struct Elem{
        _thread* t;
        uint64 timeleft;
        struct Elem* next;
        void* operator new(size_t size){
            return MemoryAllocator::allocate(size);
        }
        void operator delete(void* ptr){
            MemoryAllocator::deallocate((uint8*)ptr);
        }
    }Elem;

    static Elem* sleepyHead,  *sleepyTail;
    static int sleeping;

public:
    static int count;
    friend class Handler;

    using Body=void(*)(void*);

    _thread(Body fun, void* arg, bool cpp);
    ~_thread();

    static _thread* newThread(Body fun, void* arg, bool cpp);
    static void thread_wrapper();
    static void dispatchNoScheduler();
    static void stop();
    static bool userThreadsDone(){return count<=3;}

    static _thread* running;

    bool threadFinished(){ return finished;}

    bool isSemDeleted(){return semDeleted;}
    void semDelete(){semDeleted=true;}

    void* operator new(size_t size);
    void operator delete(void* ptr);
    void* operator new[](size_t size);
    void operator delete[](void* ptr);

    void timeDecrease() {timeSlice--;}
    void timeReset() { timeSlice=DEFAULT_TIME_SLICE; }
    uint64 getTime() { return timeSlice; }
    static void updateSleep();
    static void putToSleep(unsigned long);

private:
    typedef struct Context{
        uint64 ra,sp;
    }Context;

    Body func;
    Context context;
    uint64* stack;
    void* param;
    uint64 timeSlice;
    bool finished;
    bool semDeleted;

    static void dispatch();
    static void switchContext(Context* old, Context* running);

    static void put(_thread* t, unsigned long time);
    static _thread* get();
};


#endif //PROJEKAT_BASE___thread_HPP
