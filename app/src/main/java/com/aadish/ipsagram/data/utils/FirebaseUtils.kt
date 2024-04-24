package com.aadish.ipsagram.data.utils

//suspend fun <T> Task<T>.await(): T {
//    return suspendCancellableCoroutine { cont ->
//        addOnCompleteListener {
//            if (it.exception != null) {
//                cont.resumeWithException(it.exception!!)
//            } else {
//                cont.resume(it.result, null)
//            }
//        }
//    }
//}