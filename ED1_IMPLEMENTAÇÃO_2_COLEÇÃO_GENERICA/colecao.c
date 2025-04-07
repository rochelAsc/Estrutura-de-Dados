#ifndef COLECAO_C
#define COLECAO_C
#include "colecao.h"

Cofo* GcofCreate(int maxItems){
  Cofo* cofo;
  if(maxItems > 0){
    cofo = (Cofo*)malloc(sizeof(Cofo));
    if(cofo != NULL){
      cofo->item = (void**)malloc(sizeof(void*) * maxItems);
      if(cofo->item != NULL){
        cofo->maxItems = maxItems;
        cofo->numItems = 0;
        cofo->cur = -1;
        return cofo;
      }
      free(cofo);
    }
  }
  return NULL;
}

int GcofInsert(Cofo* cofo, void* item){
    if(cofo != NULL && cofo->item != NULL && item != NULL){
      if(cofo -> numItems < cofo -> maxItems){
        cofo->item[cofo->numItems] = item;
        cofo->numItems++;
        cofo->cur++;
        return TRUE;
      }
    }
    return FALSE;
  }

void* GcofRemove(Cofo* cofo, void* tipo, int (*cmp)(void*, void*)){
  int i, j;
  if(cofo != NULL && cofo->item != NULL && cofo->numItems > 0){
    for(i = 0; i < cofo->numItems; i++){
      if(cmp(cofo->item[i], tipo) == TRUE){
        void* aux = cofo->item[i];

        for(j = 1; j < cofo->numItems-1; j++){
          cofo->item[j] = cofo->item[j+1];
        }

        cofo->numItems--;
        cofo->cur--;
        return aux;
      }
    }
  }
  return NULL;
}

void* GcofQuery(Cofo* cofo, void* tipo, int (*cmp)(void*, void*)){
  int i;
  if(cofo != NULL && cofo->item != NULL && cofo->numItems > 0){
    for(i = 0; i < cofo->numItems; i++){
      if(cmp(cofo->item[i], tipo) == TRUE && cofo->item[i] != NULL){
        return cofo->item[i];
      }
    }
  }
  return NULL;
}

int GcofDestroy(Cofo* cofo){
  if(cofo != NULL){
    if(cofo ->item != NULL){
      if(cofo->numItems == 0){
        free(cofo->item);
        free(cofo);
        return TRUE;
      }
    }
  }
  return FALSE;
}

int GcofEmpty(Cofo* cofo){
  if(cofo != NULL){
    if(cofo->item != NULL){
      if(cofo->numItems > 0){
        cofo->numItems = 0;
        cofo->cur = -1;
        return TRUE;
      }
    }
  }
  return FALSE;
}

void gCofShow(Cofo* colecao, void(*processarElemento)(void* item)) {
  if (colecao == NULL || colecao->item == NULL || processarElemento == NULL) {
    return;
  }
  for (int i = 0; i < colecao->numItems; i++) {
    processarElemento(colecao->item[i]);
  }
}

int GcofRemovePos(Cofo* cofo, int index) {
  if (cofo != NULL && cofo->item != NULL && index >= 0 && index < cofo->numItems) {
    for (int i = index; i < cofo->numItems - 1; i++) {
      cofo->item[i] = cofo->item[i + 1];
    }
    cofo->numItems--;
    return TRUE;
  }
  return FALSE;
}

int compararDisco(void* item, void* key) {
  Disco* disco = (Disco*)item;
  Disco* chave = (Disco*)key;
  if (disco->ano == chave->ano &&
      disco->numSerie == chave->numSerie &&
      strcmp(disco->nome, chave->nome) == 0) {
    return TRUE;
      }
  return FALSE;
}










#endif