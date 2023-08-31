//
// Created by os on 6/24/22.
//

#include "../h/Scheduler.hpp"

//const char* name="scheduler elem";
Scheduler::Elem* Scheduler::head= nullptr;
Scheduler::Elem* Scheduler::tail= nullptr;
bool Scheduler::empty= true;
bool Scheduler::initialised= true;
kmem_cache_t * Scheduler::cache= nullptr;

void Scheduler::init(){
    cache= kmem_cache_create((const char*)"scheduler elem",sizeof(Scheduler::Elem), nullptr, nullptr);
    initialised=true;
}

void Scheduler::put(_thread* thr){
    //Elem* e=(Elem*)MemoryAllocator::allocate(sizeof (Elem));
    Elem* e=(Elem*) kmem_cache_alloc(cache);
    e->next= nullptr;
    e->thread=thr;
    if (!head){
        head = tail = e;
        empty=false;
        return;
    }
    tail->next=e;
    tail=e;

}

_thread* Scheduler::get() {
    if (!head) return nullptr;
    _thread* ret=head->thread;

    Elem* temp=head;
    head=head->next;
    if(!head) {
        tail= nullptr;
        empty=true;
    }

    //MemoryAllocator::deallocate((uint8*)temp);
    kmem_cache_free(cache,temp);
    return ret;
}



_thread *Scheduler::first() {
    return head->thread;
}
