//
// Created by os on 6/21/22.
//

#include "../lib/hw.h"
#include "../h/_thread.hpp"
#include "../h/_sem.hpp"
#include "../h/_console.hpp"


void Handler::interrupt() {

    uint64 scause, a[7], kod, sp, sip, sepc, status;

    __asm__ volatile("sd x10, %0":"=m"(kod));
    __asm__ volatile("sd x11, %0":"=m"(a[0]));
    __asm__ volatile("sd x12, %0":"=m"(a[1]));
    __asm__ volatile("sd x13, %0":"=m"(a[2]));
    __asm__ volatile("sd x14, %0":"=m"(a[3]));
    __asm__ volatile("sd x15, %0":"=m"(a[4]));
    __asm__ volatile("sd x16, %0":"=m"(a[5]));
    __asm__ volatile("sd x17, %0":"=m"(a[6]));
    scause=r_scause();
    sp=r_scratch();
    status=Handler::r_status();

    sem_t sem;
    unsigned long timearg;

    switch (scause) {
        case 0x8000000000000001: //spoljasnji softverski - tajmer
            __asm__ volatile ("csrr %0, sip":"=r"(sip));
            sip&= ~(1<<1);
            __asm__ volatile ("csrw sip, %0"::"r"(sip));

            _thread::updateSleep();
            _thread::running->timeDecrease();

            if (_thread::running->getTime()==0){
                sepc=Handler::r_sepc();
                status=Handler::r_status();

                _thread::running->timeReset();
                _thread::dispatch();

                Handler::w_status(status);
                Handler::w_sepc(sepc);
            }
            else Handler::w_status(status);

            break;
        case 0x8000000000000009: //spoljasnji hardverski - konzola
            __asm__ volatile ("csrr %0, sip":"=r"(sip));
            sip&= ~(1<<9);
            __asm__ ("csrw sip, %0"::"r"(sip));

            if(plic_claim() == 0xa) plic_complete(0xa);
            break;
        case 2:

            break;
        case 5:

            break;
        case 8: //ecall user
        case 9: //ecall system
            sepc=Handler::r_sepc()+4;
            Handler::w_sepc(sepc);

            switch (kod) {
                case 0x01: //mem_alloc
                    MemoryAllocator::allocate((uint64)*((uint64*)(a[0])));
                    break;
                case 0x02: //mem_free
                    MemoryAllocator::deallocate((uint8 *) *((uint64*)(a[0])));
                    break;
                case 0x11: //thread_create
                    _thread::newThread((_thread::Body)*((uint64*)(a[0])),(void*)*((uint64*)(a[1])),false);
                    break;
                case 0x12: //thread_exit
                    _thread::running->stop();
                    break;
                case 0x13: //thread_dispatch
                    sepc=Handler::r_sepc();
                    status=Handler::r_status();

                    _thread::running->timeReset();
                    _thread::dispatch();

                    Handler::w_sepc(sepc);
                    Handler::w_status(status);
                    break;
                case 0x14: //start
                    Scheduler::put((_thread*)*((uint64*)(a[0])));
                    __asm__ volatile("mv x10, zero");
                    break;
                case 0x15: //thread_create_cpp
                    _thread::newThread((_thread::Body)*((uint64*)(a[0])),(void*)*((uint64*)(a[1])), true);
                    break;
                case 0x21: //sem_open
                    _sem::newSemaphore((unsigned)*((uint64*)(a[0])));
                    break;
                case 0x22: //sem_close
                    sem=(sem_t)*((uint64*)(a[0]));
                    sem->close();
                    Handler::w_status(status);
                    break;
                case 0x23: //sem_wait
                    sepc=Handler::r_sepc();
                    status=Handler::r_status();

                    sem=(sem_t)*((uint64*)(a[0]));
                    sem->wait();

                    Handler::w_sepc(sepc);
                    Handler::w_status(status);
                    break;
                case 0x24: //sem_signal
                    sepc=Handler::r_sepc();
                    status=Handler::r_status();

                    sem=(sem_t)*((uint64*)(a[0]));
                    sem->signal();

                    Handler::w_sepc(sepc);
                    Handler::w_status(status);
                    break;
                case 0x31: //time_sleep
                    status=Handler::r_status();
                    sepc=Handler::r_sepc();

                    timearg= (unsigned long)*((uint64*)(a[0]));
                    _thread::putToSleep(timearg);
//
                    Handler::w_sepc(sepc);
                    Handler::w_status(status);
                    break;
                case 0x41: //getc
                    sepc=Handler::r_sepc();
                    status=Handler::r_status();

                    _console::getBuffer();

                    Handler::w_status(status);
                    Handler::w_sepc(sepc);
                    break;
                case 0x42: //putc
                    sepc=Handler::r_sepc();
                    status=Handler::r_status();

                    _console::putBuffer((char)*((uint64*)(a[0])));

                    Handler::w_status(status);
                    Handler::w_sepc(sepc);
                    break;
                case 0x51:
                    asm volatile("csrr %0, sstatus" : "=r" (status));
                    status &= ~0x100;
                    asm volatile("csrw sstatus, %0" : : "r" (status));
                    break;
                case 0x52:
                    asm volatile("csrr %0, sstatus" : "=r" (status));
                    status |= 0x100;
                    asm volatile("csrw sstatus, %0" : : "r" (status));
                    break;

            }
            if (kod!=0x13 && kod!=0x51 && kod!=0x42) {
                __asm__ volatile ("sd x10, 10*8(%0)"::"r"(sp));    //sp[10]=a0
            }
            Handler::w_status(status);
            break;
    }
}

void Handler::to_user() {
    __asm__ volatile ("csrw sepc, ra");
    __asm__ volatile ("csrc sip, 0x2");
    __asm__ volatile ("sret");
}

void Handler::to_user_mode() {
    Abi::syscall(0x51);
}
void Handler::to_system_mode() {
    Abi::syscall(0x52);
}