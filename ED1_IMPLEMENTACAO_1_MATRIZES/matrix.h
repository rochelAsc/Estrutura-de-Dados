#ifndef MATRIX_H
#define MATRIX_H
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#define TRUE 1
#define FALSE 0
#define FLOAT_ERROR NAN

typedef struct matrix {
    int linha, coluna; 
    float* v;           
} Matrix;

Matrix* matCreate(int n, int m);
int matDestroy(Matrix* mat);
float matGetElemIJ(Matrix* mat, int i, int j);
int matSetElemIJ(Matrix* mat, int i, int j, float v);
int matGetNumLines(Matrix* mat);
int matGetNumCollumns(Matrix* mat);

#endif
