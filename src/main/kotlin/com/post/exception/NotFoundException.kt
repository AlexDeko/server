package com.post.exception

import java.lang.RuntimeException

class NotFoundException(message: String): RuntimeException(message)