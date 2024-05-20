package util

import platform.Foundation.NSLog

actual fun logMessage(message: String) {
    NSLog(message)
}