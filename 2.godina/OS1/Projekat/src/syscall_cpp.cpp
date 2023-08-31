#include "../h/syscall_cpp.hpp"

void* operator new(size_t size){
    return mem_alloc(size);
}
void operator delete(void* ptr){
    mem_free(ptr);
}
void* operator new[](size_t size){
    return mem_alloc(size);
}
void operator delete[](void* ptr){
    mem_free(ptr);
}

Thread::Thread (void (*Body)(void*), void* arg){
    Abi::thread_create_cpp(&myHandle,Body, arg);
}

Thread::~Thread(){}

int Thread::start(){
    return Abi::thread_start(myHandle);
}
void Thread::dispatch(){
    thread_dispatch();
}
int Thread::sleep (time_t t){
    return time_sleep(t);
}
void Thread::thread_cpp_wrapper(void* p){
    //Handler::to_user();
    ((Thread*)p)->run();
}

Thread::Thread(){
    Abi::thread_create_cpp(&myHandle,thread_cpp_wrapper,this);
}

Semaphore::Semaphore (unsigned init) {
    sem_open(&myHandle, init);
}
Semaphore::~Semaphore() {
    sem_close(myHandle);
}
int Semaphore::wait(){
    return sem_wait(myHandle);
}
int Semaphore::signal(){
    return sem_signal(myHandle);
}

PeriodicThread::PeriodicThread (time_t period) :Thread(periodic_thread_cpp_wrapper,new PeriodicThreadWrapper(this,period)){}

void PeriodicThread::periodic_thread_cpp_wrapper(void* ptr){
    PeriodicThreadWrapper* wrap= (PeriodicThreadWrapper*)ptr;
    while  (1){
        wrap->thread->periodicActivation();
        time_sleep(wrap->period);
    }
}

char Console::getc(){
    return Abi::getc();
}
void Console::putc(char c){
    Abi::putc(c);
}


