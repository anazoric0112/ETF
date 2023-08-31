#include "../h/Buffer.hpp"

void _buffer::init(){
    sem_open(&itemAvailable,0);
    sem_open(&spaceAvailable, DEFAULT_BUFFER_SIZE);
    sem_open(&mutexGet,1);
    sem_open(&mutexPut,1);
    head=tail=0;

    buffer=(char*) kmalloc(DEFAULT_BUFFER_SIZE);

}

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