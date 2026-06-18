package com.santos.valdomiro.meusgastos.core.exceptions

class AppDatabaseException(
    cause: Throwable? = null
) : Exception("Registro já existe no banco de dados.", cause)

class RegistroInvalidoException(
    cause: Throwable? = null
) : Exception("Registro inválido.", cause)

class ErroBancoDadosDesconhecidoException(
    cause: Throwable? = null
) : Exception("Erro desconhecido no banco de dados.", cause)