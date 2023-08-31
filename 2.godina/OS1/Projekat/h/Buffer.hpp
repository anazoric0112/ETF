#ifndef PROJEKAT_BASE_BUFFER_HPP
#define PROJEKAT_BASE_BUFFER_HPP

#include "../h/_sem.hpp"

class _buffer{
private:
    static const int DEFAULT_BUFFER_SIZE=256;

    char buffer[DEFAULT_BUFFER_SIZE];
    int head, tail;

    sem_t itemAvailable, spaceAvailable, mutexGet, mutexPut;
public:
    void init(){
        sem_open(&itemAvailable,0);
        sem_open(&spaceAvailable, DEFAULT_BUFFER_SIZE);
        sem_open(&mutexGet,1);
        sem_open(&mutexPut,1);
        head=tail=0;
    }
    char get();
    void put(char c);
};

#endif //PROJEKAT_BASE_BUFFER_HPP