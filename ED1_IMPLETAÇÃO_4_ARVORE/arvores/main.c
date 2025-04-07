#include <stdio.h>
#include "Tree.h"
#include "TREE.c"

typedef struct _disco_ {
    char nome[30];
    int ano;
    float serie;
} Disco;

int cmpRem(void *key, void *item) {
    if(key != NULL && item != NULL) {
        Disco *a = (Disco*)item;
        char *keyChar = (char*)key;
        if(a->nome == NULL) {
            return FALSE;
        }
        return strcmp(keyChar, a->nome);
    }
    return FALSE;
}

int cmp(void *key, void *item) {
    if(key != NULL && item != NULL) {
        Disco *a = (Disco*)item;
        char *keyChar = (char*)key;
        if(a->nome == NULL) {
            return FALSE;
        }
        return strcmp(keyChar, a->nome) == 0;
    }
    return FALSE;
}

int cmpAnoDisco(void* key, void* item) {
    if (item != NULL && key != NULL) {
        Disco *disco = (Disco *)item;
        int* ano = (int *)key;
        if (disco->ano == *ano) {
            return TRUE;
        }
    }
    return FALSE;
}

int cmpSerieDisco(void* key, void* item) {
    if (item != NULL && key != NULL) {
        Disco *disco = (Disco *)item;
        float* serie = (float*)key;
        if (disco->serie == *serie) {
            return TRUE;
        }
    }
    return FALSE;
}

void imprimirDisco(Disco *disco) {
    printf("Nome: %s, Ano: %d, Serie: %.2f\n", disco->nome, disco->ano, disco->serie);
}

int main(void)
{
    int EXEC = TRUE;    int opcao;
    Root* arvore = NULL;
    Disco* copiaDisco;
    char nome[30];

    while(EXEC){
        printf("\nEscolha uma das opcoes abaixo:\n\n"
               "1. Criar arvore\n"
               "2. Inserir disco\n"
               "3. Remover disco\n"
               "4. Consultar disco\n"
               "5. Listar discos\n"
               "6. Esvaziar arvore\n"
               "7. Destruir arvore\n"
               "8. Sair\n\n"
               ""
               "Opcao: ");

        if (scanf("%d", &opcao) != TRUE)
        {
            while (getchar() != '\n')
            {
                printf("Por favor, insira numeros somente!\n");
                break;
            }
        }
        else{
            switch (opcao) {
            case 1:
                if (arvore != NULL) {
                    printf("A arvore ja existe.\n");
                } else {
                    arvore = ABPCreate();
                    if (arvore) {
                        printf("A Lista foi criada com sucesso.\n");
                    } else {
                        printf("Falha ao criar arvore.\n");
                    }
                }
                break;

            case 2:
                if (arvore == NULL) {
                    printf("A arvore nao existe. Crie-a primeiro.\n");
                } else {
                    Disco* novoDisco = (Disco*)malloc(sizeof(Disco));
                    if (novoDisco == NULL) {
                        printf("Falha ao alocar memoria para o disco.\n");
                        break;
                    }
                    while (getchar() != '\n');

                    printf("Digite o nome do disco: ");
                    fgets(novoDisco->nome, 30, stdin);
                    novoDisco->nome[strcspn(novoDisco->nome, "\n")] = '\0';

                    printf("Digite o ano do disco: ");
                    scanf("%d", &novoDisco->ano);

                    printf("Digite a serie do disco: ");
                    scanf("%f", &novoDisco->serie);

                    if (ABPInsert(arvore, novoDisco)) {
                        printf("Disco inserido com sucesso.\n");
                    } else {
                        printf("Falha ao inserir disco.\n");
                        free(novoDisco);
                    }
                }
                break;

            case 3:
                if (arvore == NULL) {
                    printf("A arvore nao existe. Crie-a primeiro.\n");
                } else {
                    printf("Digite o nome do disco para remover: ");

                    while (getchar() != '\n'); // Limpa o buffer do teclado

                    fgets(nome, 30, stdin); // Lê o nome do disco
                    nome[strcspn(nome, "\n")] = '\0'; // Remove a quebra de linha

                    // Remove o disco com o nome especificado
                    copiaDisco = (Disco*)ABPRemoveSpec(arvore, nome, cmpRem);
                    if (copiaDisco) {
                        printf("Disco removido: ");
                        imprimirDisco(copiaDisco); // Imprime o disco removido
                        free(copiaDisco); // Libera a memória do disco removido
                    } else {
                        printf("Disco nao encontrado.\n");
                    }
                }
                break;
            case 4:
                if (arvore == NULL) {
                    printf("A arvore nao existe. Crie-a primeiro.\n");
                } else {
                    printf("Escolha o metodo de busca:\n\n"
                           "1. Consulta por nome\n"
                           "2. Consulta por ano\n"
                           "3. Consulta por serie\n\n"
                           "Opcao: ");
                    int opcion;
                    if (scanf("%d", &opcion) != TRUE)
                    {
                        while (getchar() != '\n')
                        {
                            printf("Por favor, insira numeros somente!\n");
                            break;
                        }
                    }
                    switch (opcion)
                    {
                    case 1:
                        while (getchar() != '\n');

                        printf("Digite o nome do disco: ");
                        fgets(nome, 30, stdin);
                        nome[strcspn(nome, "\n")] = '\0';

                        int queryfound3 = FALSE;
                        TNode* curNameNode = ABPGetFirst(&arvore);

                        while (curNameNode != NULL) {
                            Disco* discopair = (Disco*)curNameNode->data;


                            if (cmp(nome, discopair) == TRUE) {
                                printf("Disco encontrado: ");
                                imprimirDisco(discopair);
                                queryfound3 = TRUE;
                            }

                            curNameNode = ABPGetNext(curNameNode, arvore->Root);
                        }

                        if (queryfound3 == FALSE) {
                            printf("Disco nao encontrado.\n");
                        }
                        break;
                        /*
                    case 2:
                        printf("Digite por qual indice deseja consultar: ");
                        int indice;
                        if (scanf("%d", &indice) != TRUE)
                        {
                            while (getchar() != '\n')
                            {
                                printf("Por favor, insira numeros somente!\n");
                                break;
                            }
                        }
                        copiaDisco = (Disco*)sllQueryIndex(arvore, indice);
                        if (copiaDisco != NULL)
                        {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else{
                            printf("Disco nao encontrado.\n");
                        }
                        break;*/

                    case 2:
                        printf("Digite o ano do disco: ");
                        int ano;
                        if (scanf("%d", &ano) != TRUE)
                        {
                            while (getchar() != '\n')
                            {
                                printf("Por favor, insira numeros somente!\n");
                                break;
                            }
                        }
                        int queryFound = FALSE;
                        TNode* currentNode = ABPGetFirst(&arvore);
                        while (currentNode != NULL) {
                            Disco* discopair = (Disco*)currentNode->data;
                            if (cmpAnoDisco(&ano, discopair) == TRUE) {
                                printf("Disco encontrado: ");
                                imprimirDisco(discopair);
                                queryFound = TRUE;
                            }
                            currentNode = ABPGetNext(currentNode, arvore->Root);
                        }
                        if (queryFound == FALSE)
                        {
                            printf("Disco nao encontrado.\n");
                        }
                        /*void* key = &ano;
                        copiaDisco = (Disco *)sllQuery(arvore, key, cmpAnoDisco);
                        if (copiaDisco) {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else {
                            printf("Disco nao encontrado.\n");
                        }*/
                        break;

                    case 3:
                        float serie;
                        printf("Digite a serie do disco: ");
                        if (scanf("%f", &serie) != TRUE)
                        {
                            while (getchar() != '\n')
                            {
                                printf("Por favor, insira numeros somente!\n");
                                break;
                            }
                        }
                        int queryFound2 = FALSE;
                        TNode* currentNode2 = ABPGetFirst(&arvore);
                        while (currentNode2 != NULL) {
                            Disco* discopair2 = (Disco*)currentNode2->data;
                            if (cmpSerieDisco(&serie, discopair2) == TRUE) {
                                printf("Disco encontrado: ");
                                imprimirDisco(discopair2);
                                queryFound2 = TRUE;
                            }
                            currentNode2 = ABPGetNext(currentNode2, arvore->Root);
                        }
                        if (queryFound2 == FALSE){
                            printf("Disco nao encontrado.\n");
                        }
                        /*copiaDisco = (Disco *)sllQuery(arvore, &serie, cmpSerieDisco);
                        if (copiaDisco) {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else {
                            printf("Disco nao encontrado.\n");
                        }*/
                        break;

                    default:
                        break;
                    }
                }
                break;

            case 5:
                if (arvore == NULL) {
                    printf("A arvore nao existe. Crie-a primeiro.\n");
                } else {
                    printf("Escolha o metodo de busca:\n\n"
                           "1. Listagem por PreOrdem\n"
                           "2. Listagem de modo Simetrico\n"
                           "3. Listagem por PosOrdem\n\n"
                           "Opcao: ");
                    int optione;
                    if (scanf("%d", &optione) != TRUE)
                    {
                        while (getchar() != '\n')
                        {
                            printf("Por favor, insira numeros somente!\n");
                            break;
                        }
                    }
                    switch (optione)
                    {
                    case 1:
                        preOrdem(arvore->Root, imprimirDisco);
                        break;
                    case 2:
                        simetrica(arvore->Root, imprimirDisco);
                        break;
                    case 3:
                        posOrdem(arvore->Root, imprimirDisco);
                        break;
                    default:
                        break;
                    }
                    break;

                    case 6:
                        if (arvore == NULL) {
                            printf("A arvore nao existe. Crie-a primeiro.\n");
                        } else {
                            ABPEmpty(arvore);
                            printf("A arvore foi esvaziada.\n");
                        }
                    break;

                    case 7:
                        if (arvore == NULL) {
                            printf("A arvore nao existe. Crie-a primeiro.\n");
                        } else if (ABPDestroy(arvore)) {
                            printf("A arvore foi destruida.\n");
                        } else {
                            printf("A arvore nao foi destruida, ainda existem elementos.\n");
                        }
                    break;

                    case 8:
                        printf("Saindo...\n");
                    EXEC = FALSE;
                    break;

                    default:
                        printf("\n Entrada Invalida!");
                    break;
                }
            }
        }
    }
}