#ifndef COLECAO_H
#define COLECAO_H
#include <math.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#define TRUE 1
#define FALSE 0
#define FLOAT_ERROR NAN

typedef struct _disco_ {
  char nome[30];
  int ano;
  float numSerie;
  } Disco;

typedef struct _cofo_ {
  void** item;
  int numItems;
  int maxItems;
  int cur;
  } Cofo;

Cofo* GcofCreate(int maxItems); //retorna o cofo criado
int GcofInsert(Cofo* cofo, void* item); //retorna TRUE ou FALSE
void* GcofRemove(Cofo* cofo, void* tipo, int (*cmp)(void*, void*)); //retorna o elemento removido e remove do cofo
void* GcofQuery(Cofo* cofo, void* tipo, int (*cmp)(void*, void*)); //retorna o elemento pesquisado, mas não remove o item do cofo
void gCofShow(Cofo* colecao, void(*processarElemento)(void* item)); //mostra os elementos no cofo
int GcofDestroy(Cofo* cofo); //destroi a coleção se estiver vazia
int GcofEmpty(Cofo* cofo); //esvaziar a coleção, sem destruir
int GcofRemovePos(Cofo* cofo, int index); //remove um elemento num indice especifico e rearranja o cofo
int compararDisco(void* item, void* key); //compara um item(disco) com uma chave


#endif
