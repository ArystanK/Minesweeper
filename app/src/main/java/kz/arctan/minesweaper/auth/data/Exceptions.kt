package kz.arctan.minesweaper.auth.data

import java.lang.Exception

class UnableToSaveToDatabaseException : Exception("UnableToSaveToDatabase")

class UnableToRegisterException : Exception("UnableToRegister")

class EmailAlreadyExistsException : Exception("EmailAlreadyExists")

class SuchEmailDoesNotExistsException : Exception("SuchEmailDoesNotExists")