#ifndef _SLL_C_
#define _SLL_C_
#include "lista.h"

typedef struct _slnode_ {
    void *data;
    struct _slnode_ *next;
} SLNode;

typedef struct _sllist_ {
    SLNode *first;
    SLNode *cur;
} SLList;

// Criar lista
SLList *sllCreate(void) {
    SLList* l = (SLList*)malloc(sizeof(SLList));
    if(l != NULL) {
        l->first = NULL;
        return l;
    }
    return NULL;
}

// Inserir primeiro elemento da lista
int sllInsertFirst(SLList *l, void *data) {
    SLNode *newnode;
    if (l != NULL) {
        newnode = (SLNode *)malloc(sizeof(SLNode));
        if (newnode != NULL) {
            newnode->data = data;
            newnode->next = l->first;
            l->first = newnode;
            return TRUE;
        }
    }
    return FALSE;
}

// Remover primeiro item da lista
void *sllRemoveFirst(SLList *l) { 
    SLNode *first; void *data;
    if (l != NULL) {
        if (l->first != NULL) { //Se a lista não esta vazia
            first = l->first;
            data = first->data;
            l->first = first->next;
            free(first);
            return data;
        }
    }
    return NULL;
} 

// Remover um elemento pela key
void *sllRemoveSpec( SLList *l, void *key, int (*cmp)(void *, void*)){
    SLNode *spec, *prev; int stat; void *data;
    if (l != NULL) {
        if (l->first != NULL) {
            spec = l->first; prev = NULL;
            stat = cmp(spec->data, key);
            while (stat != TRUE && spec->next != NULL) {
                prev = spec; spec= spec->next;
                stat = cmp (spec->data, key);
            }
            if (stat == TRUE) {
                data = spec->data;
                if ( prev == NULL) {
                    l->first = spec->next;
                } else {
                    prev->next =spec->next;
                }
                free(spec);
                return data;
            }
        }
    }
    return NULL;
}

// Pegar primeiro elemento da lista
void *sllGetFirst(SLList *l) {
    void *data;
    if (l != NULL) {
        if (l->first != NULL) {
            l->cur = l->first;
            data = l->cur->data;
            return data;
        }
    }
    return NULL;
}

// Pegar próximo da lista
void *sllGetNext(SLList *l) {
    void *data;
    if(l != NULL) {
        if(l->cur != NULL) {
            l->cur = l->cur->next;
            if(l->cur != NULL) {
                data = l->cur->data;
                return data;
            }
        }
    }
    return NULL;
}

// Pegar ultimo elemento da lista
void *sllGetLast(SLList *l) {
    SLNode *last;
    if (l != NULL) {
        last = l->first;
        if(last != NULL) {
            while (last->next != NULL ) {
                last = last->next;
            }
            return last->data;
        }
    }
    return NULL;
}

// Contar elementos da lista
int sllNNodes(SLList *l) {
    int n = 0; SLNode *last;
    if(l != NULL) {
        last = l->first;
        if(last != NULL) {
            n++;
            while(last->next != NULL) {
                last = last->next;
                n++;
            }
        }
        return n;
    }
    return -1;
}

// Esvaziar a lista
void sllEmpty(SLList *l) {
    SLNode *cur;
    if(l != NULL) {
        cur = l->first;
        while(cur != NULL) {
            SLNode *temp = cur;
            cur = cur->next;
            free(temp->data);
            free(temp);
        }
        l->first = NULL;
    }
}

// Destruir a lista
int sllDestroy(SLList *l) {
    if (l != NULL) {
        if (l->first == NULL) {
            free(l);
            return TRUE;
        }
    }
    return FALSE;
}

// Insere um elemento antes do ultimo
int sllInsertBeforeLast(SLList *l, void *data) {
    SLNode *newnode, *prev, *last;
    if (l != NULL) {
        newnode = (SLNode*)malloc(sizeof(SLNode));
        if (newnode != NULL) {
            prev = NULL;
            last = l->first;
            
            if (last != NULL) {
                if (last->next != NULL) {
                    while (last->next != NULL) {
                        prev = last;
                        last = last->next;
                    }
                    newnode->data = data;
                    newnode->next = last;
                    prev->next = newnode;
                    return TRUE;
                }

                newnode->data = data;
                newnode->next = l->first;
                l->first = newnode;
                last->next = NULL;
                return TRUE;
            }
        }
    }
    return FALSE;
}


// Remove elemento antes do último
int sllRemoveBeforeLast(SLList *l) {
    SLNode *prev, *last;
    if (l != NULL) {
        if (l->first != NULL) {
            if (l->first->next != NULL) {
                last = l->first;
                prev = NULL;

                if (last->next->next == NULL) {

                    SLNode *toRemove = l->first;
                    l->first = l->first->next;
                    free(toRemove);
                    return TRUE;
                }

                while (last->next != NULL) {
                    prev = last;
                    last = last->next;
                }

                prev->next = last;
                free(last);
                return TRUE;
            }
        }
    }
    return FALSE; 
}


// Insere um elemento após o primeiro (ou como 1°)
int sllInsertAsSecond(SLList *l, void *data) {
    if(l != NULL) {
        SLNode *newnode = (SLNode*)malloc(sizeof(SLNode));
        if(newnode != NULL) {
            newnode->data = data;
            if(l->first != NULL) {
                newnode->next = l->first->next;
                l->first->next = newnode;
                return TRUE;
            }
            l->first = newnode;
            return TRUE;
        }
    }
    return FALSE;
}

// Retornar o número de elementos da lista
int sllNumElms(SLList *l) {
    SLNode *cur;
    int nElm = 0;
    if(l != NULL) {
        cur = l->first;
        if(cur != NULL) {
            nElm++;
            while(cur->next != NULL) {
                nElm++;
                cur = cur->next;
            }
            return nElm;
        }
    }
    return -1;
} 

// Retornar os dados do elemento identificado pela chave key
void *sllQuery(SLList *l, void*key, int(*cmp)(void*,void*)) {
    SLNode *spec; int stat;
    if(l != NULL) {
        if(l->first != NULL) {
            spec = l->first;
            stat = cmp(key, spec->data);
            while(stat != TRUE && spec->next != NULL) {
                spec = spec->next;
                stat = cmp(key, spec->data);
            }
            if(stat == TRUE) {
                return spec->data;
            }
        }
    }
    return NULL;
}

void *sllQuerySecond(SLList *l) {
    SLNode *spec;
    if(l != NULL) {
        if(l->first != NULL) {
            spec = l->first;
            if(spec->next != NULL) {
                return spec->next->data;
            }
        }
    }
}

void* sllQueryIndex(SLList* l, int index){
    SLNode *temp; int counter = 0;
    if(l != NULL && index >= 0 && index < sllNNodes(l)) {
        if(l->first != NULL){
            temp = l->first;
            while (temp->next != NULL && counter < index){
                temp = temp->next;
                counter++;
            }
            if(temp != NULL && counter == index){
                return temp->data;
            }
        }
    }
    return NULL;
}



#endif 