package kz.arctan.minesweaper.auth.common

import java.lang.Exception

class UnableToSaveToDatabaseException : Exception("UnableToSaveToDatabase")

class UnableToRegisterException : Exception("UnableToRegister")

class EmailAlreadyExistsException : Exception("EmailAlreadyExists")