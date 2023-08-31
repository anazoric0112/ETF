#ifndef PROJEKAT_BASE__sem_HPP
#define PROJEKAT_BASE__sem_HPP

#include "../lib/hw.h"
#include "MemoryAllocator.hpp"
#include "_thread.hpp"

class _sem{

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

    int value;
    Elem* head, *tail;
    bool deleted;

    void add(_thread* t);
    _thread* pop();
public:
    _sem(unsigned init);
    static _sem* newSemaphore(unsigned init);
    void* operator new(size_t size);
    void operator delete(void* ptr);
    void* operator new[](size_t size);
    void operator delete[](void* ptr);

    int wait();
    int signal();
    int close();
    bool closed(){return deleted;}
};

#endif //PROJEKAT_BASE__sem_HPP
