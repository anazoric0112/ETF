#ifndef PROJEKAT_BASE_BUFFER_HPP
#define PROJEKAT_BASE_BUFFER_HPP

#include "_sem.hpp"

class _buffer{
private:
    static const int DEFAULT_BUFFER_SIZE=256;

    //char buffer[DEFAULT_BUFFER_SIZE];
    char* buffer=nullptr;
    int head, tail;

    sem_t itemAvailable, spaceAvailable, mutexGet, mutexPut;

public:
    void init();
    char get();
    void put(char c);
};

#endif //PROJEKAT_BASE_BUFFER_HPP