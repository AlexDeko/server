package com.post.exception

import java.lang.RuntimeException

class ForbiddenException(message: String): RuntimeException(message) {
}