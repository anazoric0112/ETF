//
// Created by os on 6/21/22.
//

#include "../h/syscall_c.hpp"

void* mem_alloc(size_t size){
    return Abi::mem_alloc(size);
}
int mem_free(void* ptr){
    return Abi::mem_free(ptr);
}
int thread_create(
        thread_t* handle,
        void (*start_routine)(void*),
        void* arg){
    return Abi::thread_create(handle, start_routine, arg);
}
int thread_exit(){
    return Abi::thread_exit();
}
void thread_dispatch(){
    Abi::thread_dispatch();
}

int sem_open(sem_t* handle, unsigned init){
    return Abi::sem_open(handle, init);
}
int sem_close(sem_t handle){
    return Abi::sem_close(handle);
}
int sem_wait(sem_t id){
    return Abi::sem_wait(id);
}
int sem_signal(sem_t id){
    return Abi::sem_signal(id);
}

int time_sleep(time_t time){
    return Abi::time_sleep(time);
}

char getc(){
    return Abi::getc();
}
void putc(char c){
    Abi::putc(c);
}
