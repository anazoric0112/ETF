#include "../lib/hw.h"
#include "../h/syscall_c.hpp"
#include "../h/syscall_cpp.hpp"
#include "../h/_console.hpp"

extern "C" void userMain(void* a);

void print(void* p){
    while(!_thread::userThreadsDone() || _console::outcount){
        _console::printBuffer();
        thread_dispatch();
    }
}

void input(void* p){
    while(!_thread::userThreadsDone() || _console::incount){
        _console::inputBuffer();
        thread_dispatch();
    }
}

void main(void* arg){

    Handler::w_stvec((uint64) Handler::Interruptf);

    thread_t mainThread, userThread, printThread, inputThread;
    thread_create(&mainThread, nullptr, nullptr);
    _thread::running = mainThread;

    _console::init();

    thread_create(&userThread, &userMain, nullptr);
    thread_create(&printThread, &print, nullptr);
    thread_create(&inputThread, &input, nullptr);

    Handler::to_user_mode();

    while(!_thread::userThreadsDone() || _console::putreq>0 || _console::getreq>0){
        thread_dispatch();
    }

    return;
}