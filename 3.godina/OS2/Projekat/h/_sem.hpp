#ifndef PROJEKAT_BASE__sem_HPP
#define PROJEKAT_BASE__sem_HPP

#include "../lib/hw.h"
#include "_thread.hpp"
typedef uint64 kmem_cache_t;

class _sem{

private:
    static kmem_cache_t* cache_elem, *cache_sem;
    static bool initialised;
    typedef struct Elem{
        _thread* thread;
        struct Elem* next;
        void* operator new(size_t size);
        void operator delete(void* ptr);
    }Elem;

    int value;
    Elem* head, *tail;
    bool deleted;

    void add(_thread* t);
    _thread* pop();
public:
    _sem(unsigned init);
    static _sem* newSemaphore(unsigned init);
    static void init();
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
