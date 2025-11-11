make_minimum_required(VERSION 3.30)
project(ED1_IMPLEMENTAÇÃO_2___COLEÇÃO_GENERICA)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(ED1_IMPLEMENTAÇÃO_2___COLEÇÃO_GENERICA
    colecao.c
    colecao.h
    ColMain.c
)

target_link_libraries(ED1_IMPLEMENTAÇÃO_2___COLEÇÃO_GENERICA m)
