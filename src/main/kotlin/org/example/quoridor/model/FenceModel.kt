package org.example.quoridor.model

import org.example.quoridor.PlayerPane

class FenceModel(count: Int) {
    var count = count
        set(value) {
            field = value
            pane?.updateFenceLabel()
        }
    var pane: PlayerPane? = null
}
