//
// Created by os on 6/24/22.
//

#ifndef PROJEKAT_BASE_SCHEDULER_HPP
#define PROJEKAT_BASE_SCHEDULER_HPP


#include "../lib/console.h"
#include "syscall_c.hpp"
#include "MemoryAllocator.hpp"

class _thread;

class Scheduler{

private:
    typedef struct Elem{
        _thread* thread;
        struct Elem* next;
        void* operator new(size_t size){
            return MemoryAllocator::allocate(size);
        }
        void operator delete(void* ptr){
            MemoryAllocator::deallocate((uint8*)ptr);
        }
    }Elem;

    static Elem* head, *tail;
    static bool empty;
public:

    static void put(_thread* thr);
    static _thread* get();
    static bool isEmpty(){return empty;}
    static _thread* first() ;

};

#endif //PROJEKAT_BASE_SCHEDULER_HPP