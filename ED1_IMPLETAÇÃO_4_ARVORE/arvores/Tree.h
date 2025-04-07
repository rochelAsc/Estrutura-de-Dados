#ifndef __TREE_H
#define __TREE_H

#define TRUE 1
#define FALSE 0
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _TNode_ TNode;
typedef struct _Root_ Root;

Root* ABPCreate(void);
int ABPInsert(Root* t, void *data);
void* ABPQuery(Root* t, void* key, int(*cmp)(void*,void*));
void* ABPRemoveSpec(Root* t, void *key, int (*cmp)(void *, void*));
void ABPEmpty(Root* t);
void LiberaNos(TNode* tnode);
int ABPDestroy(Root* t);
void* ABPGetFirst(Root* t);
TNode* ABPGetNext(TNode* son, TNode* root);
void simetrica(TNode* son, void (*imprimir)(void*));
void posOrdem(TNode* son, void (*imprimir)(void*));
void preOrdem(TNode* son, void (*imprimir)(void*));

#endif