#ifndef MATRIX_C
#define MATRIX_C
#include "matrix.h"

Matrix* matCreate(int n, int m)
{
	Matrix* mat;
	if (n > 0 && m > 0)
	{
		mat = (Matrix*)malloc(sizeof(Matrix));
		if (mat != NULL)
		{
			mat->linha = n;
			mat->coluna = m;
			mat->v = (float*)calloc(n * m, sizeof(float));
		
		if (mat->v != NULL)
		{
			return mat;
		}
		free(mat);
		}
	}
	
	return NULL;
}

int matDestroy(Matrix* mat)
{
	if (mat != NULL)
	{
		if (mat->v != NULL)
		{
			free(mat->v);
		}
		free(mat);
		return TRUE;
	}
	return FALSE;
}

float matGetElemIJ(Matrix* mat, int i, int j)
{
	if (mat == NULL || mat->v == NULL)
	{
		return FLOAT_ERROR;
	}
	if (i >= 0 && i < mat->linha && j >= 0 && j < mat->coluna)
	{
		return mat->v[i * mat->coluna + j];
	}
	return FLOAT_ERROR;
}

int matSetElemIJ(Matrix* mat, int i, int j, float v)
{
	if (mat == NULL || mat->v == NULL)
	{
		return FALSE;
	}
	if (i >= 0 && i < mat->linha && j >= 0 && j < mat->coluna)
	{
		mat->v[i * mat->coluna + j] = v;
		return TRUE;
	}
	return FALSE;
}

int matGetNumLines(Matrix* mat)
{
	if (mat == NULL || mat->v == NULL)
	{
		return FALSE;
	}
	return mat->linha;
}

int matGetNumCollumns(Matrix* mat)
{
	if (mat == NULL || mat->v == NULL)
	{
		return FALSE;
	}
	return mat->coluna;
}


#endif