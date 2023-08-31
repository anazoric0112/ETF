//
// Created by os on 6/24/22.
//

#ifndef PROJEKAT_BASE_SCHEDULER_HPP
#define PROJEKAT_BASE_SCHEDULER_HPP


//#include "../lib/console.h"
#include "syscall_c.hpp"
#include "slab.hpp"

class _thread;

class Scheduler{

private:
    static kmem_cache_t * cache;
    typedef struct Elem{
        _thread* thread;
        struct Elem* next;
        void* operator new(size_t size){
            //return MemoryAllocator::allocate(size);
            return kmem_cache_alloc(cache);
        }
        void operator delete(void* ptr){
            //MemoryAllocator::deallocate((uint8*)ptr);
            kmem_cache_free(cache,ptr);
        }
    }Elem;

    static Elem* head, *tail;
    static bool empty,initialised;
public:
    static void init();
    static void put(_thread* thr);
    static _thread* get();
    static bool isEmpty(){return empty;}
    static _thread* first() ;

};

#endif //PROJEKAT_BASE_SCHEDULER_HPP