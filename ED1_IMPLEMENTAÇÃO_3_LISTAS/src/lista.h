#ifndef __LISTA_H
#define __LISTA_H

#define TRUE 1
#define FALSE 0
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _sllist_ SLList;
typedef struct _slnode_ SLNode;

SLList *sllCreate(void);
int sllInsertFirst(SLList *l, void *data);
void *sllGetFirst(SLList *l);
void *sllGetNext(SLList *l);
void *sllQuery(SLList *l, void*key, int(*cmp)(void*,void*));
void *sllQuerySecond(SLList *l);
void *sllRemoveSpec( SLList *l, void *key, int (*cmp)(void *, void*));
void *sllRemoveFirst(SLList *l);
void sllEmpty(SLList *l);
int sllDestroy(SLList *l);
void* sllQueryIndex(SLList* l, int index);


#endif
