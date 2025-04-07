#include "matrix.h"

int main(void)
{
    int n, m, i, j;
    float k;
    Matrix* mat;

    printf("Digite o número de linhas da matriz: ");
    scanf("%d", &n);
    printf("Digite o número de colunas da matriz: ");
    scanf("%d", &m);

    mat = matCreate(n, m); // Armazena o ponteiro retornado
    if (mat == NULL) {
        printf("Erro ao criar a matriz.\n");
        return 1; // Finaliza o programa em caso de erro
    }

    printf("Matriz criada com sucesso (%d linhas, %d colunas).\n", matGetNumLines(mat), matGetNumCollumns(mat));

    printf("Escolha em qual linha você quer colocar seu numero: ");
    scanf("%d", &i);
    printf("Escolha em qual coluna você quer colocar seu numero: ");
    scanf("%d", &j);
    printf("Digite o numero(Seu número deve ser um numero real separado por . senão o programa se encerrará): ");
    scanf("%f", &k);
    // Lembre-se que ao inserir uma posição que ultrapasse o tamanho da matriz ocasionará em erro
    if (i < 0 || i >= mat->linha || j < 0 || j >= mat->coluna){
    	printf("Erro, parametros não-validos");
    	return 1;
    }
    matSetElemIJ(mat, i, j, k);
    printf("Elemento na posição (%d, %d): %f\n", i, j, matGetElemIJ(mat, i, j));

    int flag = FALSE;
    printf("Deseja destruir sua matriz? Se sim, digite 1:\n");
    scanf("%d", &flag);
    if (flag){
    	//Destroi a matriz criada, a flag serve apenas pra interagir
		if (matDestroy(mat)) {
        	printf("Matriz destruída com sucesso.\n");
        }
        else {
        	printf("Erro ao destruir a matriz.\n");
    	}    	
    }
system("pause"); //impede que o programa feche imediatamente quando chega na linha 48 (return 0;)
    return 0; // Indica que o programa terminou corretamente
}