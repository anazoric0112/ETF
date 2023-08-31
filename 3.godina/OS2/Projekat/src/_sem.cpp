#include "../h/_sem.hpp"

typedef kmem_cache_t uint64;

kmem_cache_t* _sem::cache_elem= nullptr;
kmem_cache_t* _sem::cache_sem= nullptr;
bool _sem::initialised=false;


void _sem::init() {
    cache_elem=kmem_cache_create((const char*)"semaphore queue elem",sizeof(_sem::Elem),nullptr,nullptr);
    cache_sem=kmem_cache_create((const char*)"semaphore",sizeof(_sem),nullptr,nullptr);
    initialised= true;
}

_sem::_sem(unsigned int init) {
    value=(int)init;
    head = tail = nullptr;
    deleted=false;
}

_sem* _sem::newSemaphore(unsigned int init) {
    return (_sem*) new _sem(init);
}

int _sem::wait() {
    if (deleted) return -1;
    value--;
    int *v=&value; v++; v--;
    if (value<0) {
        add(_thread::running);
        _thread::dispatchNoScheduler();
    }
    if (_thread::running->isSemDeleted()) return -1;
    else return 0;
}

int _sem::signal() {
    if (deleted) return -1;
    value++;
    int *v=&value; v++; v--;
    if (value<=0) {
        _thread* t=pop();
        Scheduler::put(t);
    }
    return 0;
}

int _sem::close(){
    if (deleted) return -1;
    while (head) {
        _thread* t=pop();
        Scheduler::put(t);
        t->semDelete();
    }
    deleted=true;
    return 0;
}
void _sem::add(_thread *t) {
    //Elem* e=(Elem*)MemoryAllocator::allocate(sizeof (Elem));
    Elem* e=(Elem*) kmem_cache_alloc(cache_elem);
    e->next= nullptr;
    e->thread=t;
    if (!head){
        head = tail = e;
        return;
    }
    tail->next=e;
    tail=e;
}

_thread* _sem::pop() {
    if (!head) return nullptr;
    _thread* ret=head->thread;
    Elem* temp=head;
    head=head->next;
    if(!head) {
        tail= nullptr;
    }
    //MemoryAllocator::deallocate((uint8*)temp);
    kmem_cache_free(cache_elem,temp);
    return ret;
}


void* _sem::operator new(size_t size){
    //return (_sem*)MemoryAllocator::allocate(size);
    return (_sem*) kmem_cache_alloc(cache_sem);
}

void _sem::operator delete(void* ptr){
    ((_sem*)ptr)->close();
    //MemoryAllocator::deallocate((uint8*)ptr);
    kmem_cache_free(cache_sem,ptr);
}

void* _sem::operator new[](size_t size){
    //return (_sem*)MemoryAllocator::allocate(size);
    return (_sem*) kmem_cache_alloc(cache_sem);
}

void _sem::operator delete[](void* ptr){
    ((_sem*)ptr)->close();
    //MemoryAllocator::deallocate((uint8*)ptr);
    kmem_cache_free(cache_sem,ptr);
}


void* _sem::Elem::operator new(size_t size){
    //return MemoryAllocator::allocate(size);
    return kmem_cache_alloc(cache_elem);
}
void _sem::Elem::operator delete(void* ptr){
    //MemoryAllocator::deallocate((uint8*)ptr);
    kmem_cache_free(cache_elem,ptr);
}
