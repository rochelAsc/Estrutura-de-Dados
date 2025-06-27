#ifndef _TREE_C_
#define _TREE_C_
#include "Tree.h"

typedef struct _TNode_ {
  void* data;
  struct TNode* Left;
  struct TNode* Right;
} TNode;

typedef struct _Root_ {
  TNode* Root;
} Root;

TNode* findMin(TNode* son) {
  while (son->Left != NULL) {
    son = son->Left;
  }
  return son;
}

void* ABPQuery(Root *t, void* key, int(*cmp)(void*, void*)) {
  TNode* current = t->Root;

  while (current != NULL) {
    int result = cmp(key, current->data);

    if (result == 0) {
      // Se a chave for igual ao dado do nó atual, retorna o dado
      return current->data;
    } else if (result < 0) {
      // Se a chave for menor, vai para a subárvore esquerda
      current = current->Left;
    } else {
      // Se a chave for maior, vai para a subárvore direita
      current = current->Right;
    }
  }

  // Se a chave não for encontrada, retorna NULL
  return NULL;
}

Root* ABPCreate(void) {
  Root* newTree = (Root*)malloc(sizeof(Root));  // Aloca memória para a raiz da árvore
  if (newTree != NULL) {
    newTree->Root = NULL;  // Inicializa a raiz como NULL (árvore vazia)
  }
  return newTree;  // Retorna o ponteiro para a árvore criada
}

int ABPInsert(Root *t, void *data) {
  // Cria um novo nó
  TNode* newNode = (TNode*)malloc(sizeof(TNode));
  if (newNode == NULL) {
    return FALSE; // Falha na alocação de memória
  }
  newNode->data = data;
  newNode->Left = NULL;
  newNode->Right = NULL;

  // Se a árvore estiver vazia, o novo nó será a raiz
  if (t->Root == NULL) {
    t->Root = newNode;
    return TRUE; // Inserção bem-sucedida
  }

  // Encontrar a posição correta para inserir o novo nó
  TNode* current = t->Root;
  TNode* father = NULL;

  while (current != NULL) {
    father = current;
    if (data < current->data) {
      current = current->Left;
    } else if (data > current->data) {
      current = current->Right;
    } else {
      // Se o valor já existe na árvore, não insere e libera o nó
      free(newNode);
      return FALSE; // Inserção falhou
    }
  }

  // Insere o novo nó na posição correta
  if (data < father->data) {
    father->Left = newNode;
  } else {
    father->Right = newNode;
  }

  return TRUE; // Inserção bem-sucedida
}


void* ABPRemoveSpec(Root* t, void* key, int (*cmp)(void*, void*)) {
    if (t == NULL || t->Root == NULL) {
        return NULL; // Árvore vazia ou inválida
    }

    TNode* parent = NULL; // Pai do nó atual
    TNode* current = t->Root; // Nó atual
    int direction = 0; // Direção da busca (0 = esquerda, 1 = direita)

    // Localiza o nó a ser removido
    while (current != NULL) {
        int result = cmp(key, current->data); // Compara a chave com os dados do nó atual
        if (result == 0) {
            break; // Nó encontrado
        } else {
            parent = current;
            if (result < 0) {
                current = current->Left; // Busca na subárvore esquerda
                direction = 0;
            } else {
                current = current->Right; // Busca na subárvore direita
                direction = 1;
            }
        }
    }

    if (current == NULL) {
        return NULL; // Nó não encontrado
    }

    // Salva o ponteiro para os dados do nó removido
    void* removedData = current->data;

    // Caso 1: Nó não tem filhos
    if (current->Left == NULL && current->Right == NULL) {
        if (parent == NULL) {
            t->Root = NULL; // A raiz é o único nó
        } else if (direction == 0) {
            parent->Left = NULL; // Remove o filho esquerdo
        } else {
            parent->Right = NULL; // Remove o filho direito
        }
        free(current); // Libera o nó
    }
    // Caso 2: Nó tem apenas um filho
    else if (current->Left == NULL || current->Right == NULL) {
        TNode* son = (current->Left != NULL) ? current->Left : current->Right;
        if (parent == NULL) {
            t->Root = son; // A raiz é substituída pelo filho
        } else if (direction == 0) {
            parent->Left = son; // Substitui o filho esquerdo
        } else {
            parent->Right = son; // Substitui o filho direito
        }
        free(current); // Libera o nó
    }
    // Caso 3: Nó tem dois filhos
    else {
        TNode* successorFather = current;
        TNode* successor = current->Right;

        // Encontra o sucessor (menor nó na subárvore direita)
        while (successor->Left != NULL) {
            successorFather = successor;
            successor = successor->Left;
        }

        // Substitui os dados do nó atual pelos dados do sucessor
        current->data = successor->data;

        // Remove o sucessor
        if (successorFather->Left == successor) {
            successorFather->Left = successor->Right;
        } else {
            successorFather->Right = successor->Right;
        }
        free(successor); // Libera o sucessor
    }

    return removedData; // Retorna os dados do nó removido
}

void LiberaNos(TNode *node) {
  if (node == NULL) {
    return; // Caso base: nó é NULL, não há nada para liberar
  }

  // Libera os nós das subárvores esquerda e direita
  LiberaNos(node->Left);  // Libera a subárvore esquerda
  LiberaNos(node->Right); // Libera a subárvore direita

  // Libera o nó atual (exceto a raiz, que não é passada aqui)
  free(node->data); // Libera os dados do nó, se necessário
  free(node);       // Libera o próprio nó
}


void ABPEmpty(Root *t) {
  if (t == NULL || t->Root == NULL) {
    return; // Se a árvore ou a raiz for NULL, não há nada para fazer
  }

  // Libera os nós das subárvores esquerda e direita
  LiberaNos(t->Root->Left);  // Libera a subárvore esquerda
  LiberaNos(t->Root->Right); // Libera a subárvore direita

  // Define os ponteiros Left e Right da raiz como NULL
  t->Root->Left = NULL;
  t->Root->Right = NULL;

  // Libera os dados do nó raiz, se necessário
  if (t->Root->data != NULL) {
    free(t->Root->data); // Libera os dados do nó raiz
    t->Root->data = NULL; // Define o ponteiro de dados como NULL
  }
}

int ABPDestroy(Root *t) {
  if (t == NULL) {
    return FALSE; // Retorna FALSE se o ponteiro for nulo
  }

  // Libera a memória alocada para os nós da árvore, se houver
  if (t->Root != NULL) {
    // Libera os dados da raiz, se existirem
    if (t->Root->data != NULL) {
      free(t->Root->data); // Libera os dados, se existirem
    }

    // Libera a memória alocada para o nó raiz
    free(t->Root);
  }

  // Libera a memória alocada para a estrutura Root
  free(t);

  return TRUE; // Retorna TRUE indicando que a árvore foi destruída
}

void* ABPGetFirst(Root* t) {
  if (t == NULL || t->Root == NULL) {
    return NULL; // Retorna NULL se a árvore estiver vazia ou se a raiz for NULL
  }
  return t->Root->data; // Retorna o dado armazenado na raiz
}

TNode* ABPGetNext(TNode* son, TNode* root) {
  if (son == NULL) {
    return NULL;
  }

  // Se o nó atual tiver um filho à esquerda, retorne o filho à esquerda
  if (son->Left != NULL) {
    return son->Left;
  }

  // Se o nó atual tiver um filho à direita, retorne o filho à direita
  if (son->Right != NULL) {
    return son->Right;
  }

  // Se o nó atual não tiver filhos, volte para o pai até encontrar um nó com filho à direita
  TNode* father = root;
  while (father != NULL && (father->Right == son || father->Right == NULL)) {
    son = father;
    father = father->Left;
  }

  // Se encontrou um nó com filho à direita, retorne o filho à direita
  if (father != NULL) {
    return father->Right;
  }

  // Se não houver mais nós para percorrer, retorne NULL
  return NULL;
}

// Função de travessia em pré-ordem
void preOrdem(TNode* son, void (*imprimir)(void*)) {
  if (son == NULL) return;

  if (son->data != NULL) {
    imprimir(son->data); // Visita o nó atual
  }
  preOrdem(son->Left, imprimir);  // Visita a subárvore esquerda
  preOrdem(son->Right, imprimir); // Visita a subárvore direita
}

// Função de travessia em ordem simétrica
void simetrica(TNode* son, void (*imprimir)(void*)) {
  if (son == NULL) return;

  simetrica(son->Left, imprimir);  // Visita a subárvore esquerda
  if (son->data != NULL) {
    imprimir(son->data); // Visita o nó atual
  }
  simetrica(son->Right, imprimir); // Visita a subárvore direita
}

// Função de travessia em pós-ordem
void posOrdem(TNode* son, void (*imprimir)(void*)) {
  if (son == NULL) return;

  posOrdem(son->Left, imprimir);   // Visita a subárvore esquerda
  posOrdem(son->Right, imprimir);  // Visita a subárvore direita
  if (son->data != NULL) {
    imprimir(son->data); // Visita o nó atual
  }
}

#endif
