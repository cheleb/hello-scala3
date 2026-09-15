package cappascap

import language.experimental.captureChecking
import language.experimental.separationChecking

import caps.*

class MyRef(init: Int) extends Stateful:
    private var current = init

    def get = current

    update def set(v: Int): Unit = {

        current = v
    } 