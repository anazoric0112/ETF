#ifndef PROJEKAT_BASE_HANDLER_HPP
#define PROJEKAT_BASE_HANDLER_HPP

#include "../lib/hw.h"

class Handler{
public:
    //bit 8 - SPP - iz kog rezima se desio skok
    //bit 5 - SPIE - prethodni SIE
    //bit 1 - SIE - maskiranje spoljasnjih prekida
    static void w_status(uint64 value);
    static uint64 r_status();

    //bit 9 - zahtev za hardverski prekid
    //bit 1 - zahtev za softverski prekid
    static void w_sip(uint64 value);
    static uint64 r_sip();

    //bit 9 - maskirani hardverski prekidi
    //bit 1 - maskirani softverski prekidi;
    static void w_sie(uint64 value);
    static uint64 r_sie();

    static void w_scratch(uint64 value);
    static uint64 r_scratch();

    static void w_sepc(uint64 value);
    static uint64 r_sepc();

    static void w_scause(uint64 value);
    static uint64 r_scause();

    static void w_stvec(uint64 value);
    static uint64 r_stvec();

    static void Interruptf(); //assembler wrapper for interrupt routine function

    static void sstatus_spp1();
    static void sstatus_spp0();
    static void sstatus_sie1();
    static void sstatus_sie0();

    static void maskH();
    static void maskS();
    static void allowH();
    static void allowS();


    static void to_user();
    static void to_user_mode();
    static void to_system_mode();
private:
    static void interrupt();  //interrupt routine function

    //dodaj za maskiranje sip sie itd tih nekih bitova posle

};

inline void Handler::w_status(uint64 value){
    __asm__ volatile("csrw sstatus, %0"::"r"(value));
}
inline uint64 Handler::r_status(){
    uint64 value;
    __asm__ volatile("csrr %0, sstatus":"=r"(value));
    return value;
}

inline void Handler::w_sip(uint64 value){
    __asm__ volatile("csrw sip, %0"::"r"(value));
}
inline uint64 Handler::r_sip(){
    uint64 value;
    __asm__ volatile("csrr %0, sip":"=r"(value));
    return value;
}

inline void Handler::w_sie(uint64 value){
    __asm__ volatile("csrw sie, %0"::"r"(value));
}
inline uint64 Handler::r_sie(){
    uint64 value;
    __asm__ volatile("csrr %0, sie":"=r"(value));
    return value;
}

inline void Handler::w_scratch(uint64 value){
    __asm__ volatile("csrw sscratch, %0"::"r"(value));
}
inline uint64 Handler::r_scratch(){
    uint64 value;
    __asm__ volatile("csrr %0, sscratch":"=r"(value));
    return value;
}

inline void Handler::w_sepc(uint64 value){
    __asm__ volatile("csrw sepc, %0"::"r"(value));
}
inline uint64 Handler::r_sepc(){
    uint64 value;
    __asm__ volatile("csrr %0, sepc":"=r"(value));
    return value;
}

inline void Handler::w_scause(uint64 value){
    __asm__ volatile("csrw scause, %0"::"r"(value));
}
inline uint64 Handler::r_scause(){
    uint64 value;
    __asm__ volatile("csrr %0, scause":"=r"(value));
    return value;
}

inline void Handler::w_stvec(uint64 value){
    __asm__ volatile("csrw stvec, %0"::"r"(value));
}
inline uint64 Handler::r_stvec(){
    uint64 value;
    __asm__ volatile("csrr %0, stvec":"=r"(value));
    return value;
}

inline void Handler::sstatus_spp1(){
    uint64 status=r_status();
    status|= 1<<8;
    w_status(status);
}
inline void Handler::sstatus_spp0(){
    uint64 status=r_status();
    status&= ~(1<<8);
    w_status(status);
}
inline void Handler::sstatus_sie1(){
    uint64 status=r_status();
    status|= 1<<1;
    w_status(status);
}
inline void Handler::sstatus_sie0(){
    uint64 status=r_status();
    status&= ~(1<<1);
    w_status(status);
}

inline void Handler::maskH(){
    uint64 sie=r_sie();
    sie&= ~(1<<9);
    w_sie(sie);
}
inline void Handler::maskS(){
    uint64 sie=r_sie();
    sie&= ~(1<<1);
    w_sie(sie);
}
inline void Handler::allowH(){
    uint64 sie=r_sie();
    sie|= 1<<9;
    w_sie(sie);
}
inline void Handler::allowS(){
    uint64 sie=r_sie();
    sie|= 1<<1;
    w_sie(sie);
}

#endif