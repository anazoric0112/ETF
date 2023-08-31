#include "../h/_console.hpp"


int _console::incount = 0;
int _console::outcount = 0;
int _console::getreq=0;
int _console::putreq=0;
_buffer _console::bufferIn= _buffer();
_buffer _console::bufferOut= _buffer();

void _console::init(){
    bufferIn.init();
    bufferOut.init();
}

void _console::putBuffer(char c){
    bufferOut.put(c);
    outcount++;
    putreq++;
}
char _console::getBuffer(){
    char c=bufferIn.get();
    incount--;
    getreq++;
    return c;
}

void _console::printBuffer(){
    while(outcount!=0 && (CONSOLE_TX_STATUS_BIT & *(char*)CONSOLE_STATUS)){
        *(char*)CONSOLE_TX_DATA = bufferOut.get();
        outcount--;
        putreq--;
    }
}


void _console::inputBuffer(){
    while(incount!=DEFAULT_BUFFER_SIZE && (CONSOLE_RX_STATUS_BIT & *(char*)CONSOLE_STATUS)){
        bufferIn.put( *(char*)CONSOLE_RX_DATA);
        incount++;
        getreq--;
    }
}

