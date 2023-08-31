#include "../h/Buffer.hpp"

char _buffer::get(){
    sem_wait(itemAvailable);
    sem_wait(mutexGet);
    char c=buffer[head];
    head++; head%=DEFAULT_BUFFER_SIZE;
    sem_signal(mutexGet);
    sem_signal(spaceAvailable);
    return c;
}


void _buffer::put(char c){
    sem_wait(spaceAvailable);
    sem_wait(mutexPut);
    buffer[tail]=c;
    tail++; tail%=DEFAULT_BUFFER_SIZE;
    sem_signal(mutexPut);
    sem_signal(itemAvailable);
}