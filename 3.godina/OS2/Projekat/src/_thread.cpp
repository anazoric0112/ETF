//
// Created by os on 6/22/22.
//

#include "../h/_thread.hpp"


_thread* _thread::running=nullptr;
_thread::Elem* _thread::sleepyHead=nullptr;
_thread::Elem* _thread::sleepyTail=nullptr;
int _thread::sleeping=0;
int _thread::count=0;
bool _thread::initialised=false;
kmem_cache_t* _thread::cache_elem= nullptr;
kmem_cache_t* _thread::cache_thread= nullptr;
kmem_cache_t* _thread::cache_stack= nullptr;


void _thread::init() {
    if (!cache_elem) cache_elem=kmem_cache_create((const char*)"sleep queue elem",sizeof(_thread::Elem),nullptr,nullptr);
    if (!cache_thread) cache_thread=kmem_cache_create((const char*)"thread",sizeof(_thread),nullptr,nullptr);
    if (!cache_stack) cache_stack=kmem_cache_create((const char*)"thread stack",DEFAULT_STACK_SIZE*8,nullptr,nullptr);
    initialised=true;
}


_thread::_thread(Body fun, void* arg, bool cpp) {
    func=fun;
    param=arg;
    finished=false;
    semDeleted=false;
    context.ra=(uint64)thread_wrapper;
    timeSlice=DEFAULT_TIME_SLICE;

    if (func) {
        //stack=(uint64*)MemoryAllocator::allocate(DEFAULT_STACK_SIZE*8);
        stack=(uint64*) kmem_cache_alloc(cache_stack);
        context.sp=(uint64)&(stack[DEFAULT_STACK_SIZE]);
        if (!cpp) Scheduler::put(this);
    } else {
        stack= nullptr;
        context.sp= 0;
    }
    count++;
}
_thread::~_thread() {
    //MemoryAllocator::deallocate((uint8*)stack);
    kmem_cache_free(cache_stack,stack);
}
_thread *_thread::newThread(_thread::Body fun, void *arg, bool cpp) {
    return (_thread*) new _thread(fun, arg, cpp);
}

void _thread::thread_wrapper() {

    Handler::to_user();
    running->func(running->param);
    running->finished=true;
    count--;
    thread_dispatch();
}


void _thread::dispatch() {
    _thread* old=running;
    old->timeReset();
    if (!old->finished) {
        Scheduler::put(old);
    }
    running=Scheduler::get();
    switchContext(&old->context,&running->context);
}
void _thread::dispatchNoScheduler() {
    _thread* old=running;
    old->timeReset();
    running=Scheduler::get();
    switchContext(&old->context,&running->context);
}

void _thread::stop() {
    running->finished=true;
    dispatch();
}

void _thread::updateSleep() {
    if (!sleepyHead) return;
    int s=sleeping;
    for (int i=0;i<s;i++){
        uint64 timeleft=sleepyHead->timeleft-1;
        _thread* t=sleepyHead->t;
        get();
        if(!timeleft && !t->finished) Scheduler::put(t);
        else put(t,timeleft);
    }
}

void _thread::putToSleep(unsigned long time){
    if (!time) return;
    put(running, time);
    dispatchNoScheduler();
}

void _thread::put(_thread* thr, unsigned long time){
    //Elem* e=(Elem*)MemoryAllocator::allocate(sizeof (Elem));
    Elem* e=(Elem*) kmem_cache_alloc(cache_elem);
    e->next= nullptr;
    e->t=thr;
    e->timeleft=time;
    sleeping++;
    if (!sleepyHead){
        sleepyHead = sleepyTail = e;
        return;
    }
    sleepyTail->next=e;
    sleepyTail=e;
}

_thread* _thread::get() {
    if (!sleepyHead) return nullptr;
    _thread* ret=sleepyHead->t;
    Elem* temp= sleepyHead;
    sleepyHead=sleepyHead->next;
    if(!sleepyHead) {
        sleepyTail= nullptr;
    }
    //MemoryAllocator::deallocate((uint8*)temp);
    kmem_cache_free(cache_elem,temp);
    sleeping--;
    return ret;
}


void *_thread::operator new(size_t size) {
    //return (_thread*)MemoryAllocator::allocate(size);
    return (_thread*) kmem_cache_alloc(cache_thread);
}
void _thread::operator delete(void* ptr) {
    //MemoryAllocator::deallocate((uint8*)ptr);
    kmem_cache_free(cache_thread,ptr);
}
void *_thread::operator new[](size_t size) {
    //return (_thread*)MemoryAllocator::allocate(size);
    return (_thread*) kmem_cache_alloc(cache_thread);
}
void _thread::operator delete[](void* ptr) {
    //MemoryAllocator::deallocate((uint8*)ptr);
    kmem_cache_free(cache_thread,ptr);
}
