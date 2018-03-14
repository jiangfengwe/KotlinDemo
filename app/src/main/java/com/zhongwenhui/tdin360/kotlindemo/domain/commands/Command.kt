package com.zhongwenhui.tdin360.kotlindemo.domain.commands

interface Command<out T> {
    fun execute(): T
}