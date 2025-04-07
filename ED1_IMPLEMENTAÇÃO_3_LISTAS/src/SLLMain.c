#include <stdio.h>
#include "lista.h"
#include "sll.c"

typedef struct _disco_ {
    char nome[30];
    int ano;
    float serie;
} Disco;

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
    SLList *lista = NULL;
    Disco* copiaDisco;
    char nome[30];

    while(EXEC){
        printf("\nEscolha uma das opcoes abaixo:\n\n"
               "1. Criar lista\n"
               "2. Inserir disco\n"
               "3. Remover disco\n"
               "4. Consultar disco\n"
               "5. Listar discos\n"
               "6. Esvaziar lista\n"
               "7. Destruir lista\n"
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
                if (lista != NULL) {
                    printf("A lista ja existe.\n");
                } else {
                    lista = sllCreate();
                    if (lista) {
                        printf("A Lista foi criada com sucesso.\n");
                    } else {
                        printf("Falha ao criar lista.\n");
                    }
                }
                break;

            case 2:
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
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

                    if (sllInsertFirst(lista, novoDisco)) {
                        printf("Disco inserido com sucesso.\n");
                    } else {
                        printf("Falha ao inserir disco.\n");
                        free(novoDisco);
                    }
                }
                break;

            case 3:
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
                } else {
                    printf("Digite o nome do disco para remover: ");

                    while (getchar() != '\n');

                    fgets(nome, 30, stdin);
                    nome[strcspn(nome, "\n")] = '\0';
                    //scanf("%s", &nome);
                    copiaDisco = (Disco*)sllRemoveSpec(lista, &nome, cmp);
                    if (copiaDisco) {
                        printf("Disco removido: ");
                        imprimirDisco(copiaDisco);
                        free(copiaDisco);
                    } else {
                        printf("Disco nao encontrado.\n");
                    }
                }
                break;

            case 4:
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
                } else {
                    printf("Escolha o metodo de busca:\n\n"
                           "1. Consulta por nome\n"
                           "2. Consulta por posição na lista\n"
                           "3. Consulta por ano\n"
                           "4. Consulta por serie\n\n"
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
                        copiaDisco = (Disco *)sllQuery(lista, &nome, cmp);
                        if (copiaDisco) {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else {
                            printf("Disco nao encontrado.\n");
                        }
                        break;
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
                        copiaDisco = (Disco*)sllQueryIndex(lista, indice);
                        if (copiaDisco != NULL)
                        {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else{
                            printf("Disco nao encontrado.\n");
                        }
                        break;

                    case 3:
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
                        Disco* discopair = (Disco*)sllGetFirst(lista);
                        while (discopair != NULL)
                        {
                            if (cmpAnoDisco(&ano, discopair) == TRUE)
                            {
                                printf("Disco encontrado: ");
                                imprimirDisco(discopair);
                                queryFound = TRUE;
                            }
                            discopair = (Disco*)sllGetNext(lista);
                        }
                        if (queryFound == FALSE)
                        {
                            printf("Disco nao encontrado.\n");
                        }
                        /*void* key = &ano;
                        copiaDisco = (Disco *)sllQuery(lista, key, cmpAnoDisco);
                        if (copiaDisco) {
                            printf("Disco encontrado: ");
                            imprimirDisco(copiaDisco);
                        } else {
                            printf("Disco nao encontrado.\n");
                        }*/
                        break;

                    case 4:
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
                        Disco* discopair2 = (Disco*)sllGetFirst(lista);
                        while (discopair2 != NULL){
                            if (cmpSerieDisco(&serie, discopair2) == TRUE){
                                printf("Disco encontrado: ");
                                imprimirDisco(discopair2);
                                queryFound2 = TRUE;
                            }
                            discopair2 = (Disco*)sllGetNext(lista);
                        }
                        if (queryFound2 == FALSE){
                            printf("Disco nao encontrado.\n");
                        }
                        /*copiaDisco = (Disco *)sllQuery(lista, &serie, cmpSerieDisco);
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
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
                } else {
                    Disco* elm;
                    elm = (Disco*)sllGetFirst(lista);
                    if (elm == NULL) {
                        printf("A lista esta vazia.\n");
                    } else {
                        printf("Lista de discos:\n");
                        while (elm != NULL) {
                            imprimirDisco(elm);
                            elm = (Disco *)sllGetNext(lista);
                        }
                    }
                }
                break;

            case 6:
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
                } else {
                    sllEmpty(lista);
                    printf("A lista foi esvaziada.\n");
                }
                break;

            case 7:
                if (lista == NULL) {
                    printf("A lista nao existe. Crie-a primeiro.\n");
                } else if (sllDestroy(lista)) {
                    lista = NULL;
                    printf("A lista foi destruida.\n");
                } else {
                    printf("A lista nao pode ser destruida porque ainda possui elementos.\n");
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