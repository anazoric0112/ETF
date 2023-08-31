//
// Created by os on 6/21/22.
//

//ovde se zove ecall

#include "../h/abi.hpp"

namespace Abi {
    void* syscall(int code,void *arg1, void *arg2, void *arg3, void *arg4, void *arg5, void *arg6, void *arg7) {

        __asm__ volatile("ecall");
        uint64 ret;
        __asm__ volatile("sd x10, %0":"=m"(ret));
        uint64* retvalue=&ret;
        return retvalue;
    }

    void *mem_alloc(size_t size) {
        uint64* ret=(uint64 *)syscall(0x01, &size);
        return (void*)(*ret);
    }

    int mem_free(void *ptr) {
        uint64* ret=(uint64 *)syscall(0x02, &ptr);
        return *((int*)ret);
    }

    int thread_create( thread_t* handle, void (*start_routine)(void*), void* arg){
        if (!handle) return -1;
        uint64* ret=(uint64*)syscall(0x11, &start_routine, &arg);
        *handle=(thread_t)*ret;
        if (ret) return 0;
        else return -1;
    }

    int thread_exit(){ //kada se radi delete za nit?
        syscall(0x12);
        return -1;
    }

    void thread_dispatch(){
        syscall(0x13);
    }

    int sem_open(sem_t* handle, unsigned init){
        if (!handle) return -1;
        uint64* ret=(uint64*)syscall(0x21, &init);
        *handle=(sem_t)*ret;
        if (ret) return 0;
        else return -1;
    }
    int sem_close(sem_t handle){
        if (!handle) return -1;
        uint64* ret=(uint64 *)syscall(0x22, &handle);
        return *((int*)ret);
    }
    int sem_wait(sem_t id){
        if (!id) return -1;
        uint64* ret=(uint64 *)syscall(0x23, &id);
        return *((int*)ret);
    }
    int sem_signal(sem_t id){
        if (!id) return -1;
        uint64* ret=(uint64 *)syscall(0x24, &id);
        return *((int*)ret);
    }

    int time_sleep(time_t time){
        uint64* ret=(uint64 *)syscall(0x31, &time);
        return *((int*)ret);
    }

    char getc(){
        char* ret = (char *)syscall(0x41);
        return *ret;
    }
    void putc(char c){
        syscall(0x42, &c);
    }

    int thread_start(thread_t handle){
        uint64* ret=(uint64 *)syscall(0x14, &handle);
        return *((int*)ret);
    }
    int thread_create_cpp(thread_t* handle,
                          void (*start_routine)(void*),
                          void* arg){
        if (!handle) return -1;
        uint64* ret=(uint64*)syscall(0x15, &start_routine, &arg);
        *handle=(thread_t)*ret;
        if (ret) return 0;
        else return -1;
    }
}