#include "colecao.h"
#include "colecao.c"

void processarDisco(void* item) {
    Disco* disco = (Disco*)item;
    printf("Nome: %s, Ano: %d, N de Serie: %f\n", disco->nome, disco->ano, disco->numSerie);
}

int main(void){
    int EXEC = TRUE; int n = 0; int escolha = 0;
    Cofo* colecao = NULL;
    while (EXEC)
    {
    printf("1 - Crie uma colecao\n"
"2 - Insira um elemento na colecao\n"
"3 - Liste os elementos da Colecao\n"
"4 - Consulte um dos elementos da colecao\n"
"5 - remova o segundo elemento inserido na colecao\n"
"6 - Liste os elementos da colecao\n"
"7 - Esvazie a colecao\n"
"8 - Liste os elementos da colecao\n");
    printf("Escolha uma acao:");
    scanf("%d", &escolha);
    switch (escolha)
    {
        case 1:
            {
                printf("Insira um tamanho pra sua colecao: ");
                scanf("%d",&n);
                colecao = GcofCreate(n);
                if (colecao == NULL)
                {
                    printf("Erro ao criar colecao\n");
                    break;
                }
                printf("Colecao criada com sucesso!\n");
                break;
            }
    case 2:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                int i = 0, ano;
                float numSerie = 0;
                if (i < n)
                {
                    Disco* disco = (Disco*)malloc(sizeof(Disco));
                    if (disco == NULL)
                    {
                        printf("Erro ao criar disco\n");
                        break;
                    }
                    if (i < n)
                    {
                        printf("Digite um ano de fabricacao que esteja entre 1948 e 2025:" );
                        if(scanf("%d",&ano) != TRUE ||ano < 1948 || ano > 2025)
                        {
                            continue;
                        }
                        printf("\n");
                        printf("Digite o numero de fabricacao, que deve ser maior que zero:");
                        if (scanf("%f", &numSerie) != TRUE || numSerie < 1)
                        {
                            continue;
                        }
                        printf("\n");
                        printf("Digite o nome do seu disco:");
                        scanf("%s", disco->nome);
                        disco->ano = ano;
                        disco->numSerie = numSerie;
                        if (!GcofInsert(colecao, disco))
                        {
                            printf("Erro ao inserir disco\n");
                            continue;
                        }

                    }
                    else
                    {
                        printf("Na sua colecao nao cabe tres elementos\n");
                        break;
                    }
                }
                i++;
            break;
            }
        case 3:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                gCofShow(colecao, processarDisco);
                break;
            }
        case 4:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                printf("Consulte um elemento dentro do cofo\n");

                Disco chave = {0};
                printf("Digite o ano do disco a ser buscado:");
                scanf("%d", &chave.ano);
                printf("\n");
                printf("Digite o numero de serie do disco a ser buscado:");
                scanf("%f", &chave.numSerie);
                printf("\n");
                printf("Digite o nome do disco a ser buscado:");
                scanf("%29s", chave.nome);
                printf("\n");
                Disco* resultado = (Disco*)GcofQuery(colecao, &chave, compararDisco);
                if (resultado != NULL)
                {
                    printf("Disco encontrado!\n");
                    printf("Ano: %d, Numero de Serie: %f, Nome: %s\n",
                    resultado->ano, resultado->numSerie, resultado->nome);
                    break;
                }
                else
                {
                    printf("Disco nao encontrado.\n");
                    break;
                }
            }
        case 5:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                GcofRemovePos(colecao, 1);
                break;
            }
        case 6:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                gCofShow(colecao, processarDisco);
                break;
            }
        case 7:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                GcofEmpty(colecao);
                printf("Colecao esvaziada\n");
                break;
            }
        case 8:
            {
                if (colecao == NULL)
                {
                    printf("Crie uma colecao primeiro\n");
                    break;
                }
                gCofShow(colecao, processarDisco);
                break;
            }
    default:
        printf("Invalido");
        break;
        }
    }
}