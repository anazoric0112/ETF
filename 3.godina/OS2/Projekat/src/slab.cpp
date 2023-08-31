#include "../h/slab.hpp"
#include "../h/Cache.hpp"

//prosledjuje se pocetak prostora za koji je zaduzen kes i velicina u broju blokova
void kmem_init(void* space, int block_num){
    BuddyAllocator::init(space,block_num);
    Cache::init();
}

kmem_cache_t* kmem_cache_create(const char* name, size_t size, void (*ctor) (void*), void (*dtor) (void*)){
    return (kmem_cache_t *)Cache::create(name,size,ctor,dtor);
}

int kmem_cache_shrink(kmem_cache_t* cachep){
    return Cache::shrink(cachep);
}
void kmem_cache_destroy(kmem_cache_t* cachep){
    Cache::destroy(cachep);
}

void* kmem_cache_alloc(kmem_cache_t* cachep){
    return Cache::obj_alloc(cachep);
}
void kmem_cache_free(kmem_cache_t* cachep, void* objp){
    Cache::obj_free(cachep,objp);
}

void* kmalloc (size_t size){
    return Cache::buff_alloc(size);
}
void kfree(const void* objp){
    Cache::buff_free((void*)objp);
}

//ime, vel. objekta u B, vel. kesa u blokovima, broj ploca, broj objekata u 1 ploci i procentualna popunjenost
void kmem_cache_info(kmem_cache_t* cachep){
    Cache::print_info(cachep);
}
int kmem_cache_error(kmem_cache_t* cachep){
    return Cache::error(cachep);
}