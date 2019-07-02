package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.HexCode
import com.vbytsyuk.genuml.domain.Px
import com.vbytsyuk.genuml.domain.Size
import com.vbytsyuk.genuml.domain.Style


object Color {
    const val WHITE: HexCode = "#FFFFFF"
    const val BLACK: HexCode = "#000000"
}

object FontSize {
    const val DEFAULT: Px = 12
}

object ArrowThickness {
    const val DEFAULT: Px = 2
}


class DefaultStyle : Style() {
    object Element : Style.Element() {
        override var size = Size(width = 480, height = 360)
        override var colorBackground: HexCode = Color.WHITE
        override var colorBorder: HexCode = Color.BLACK
        override var fontColor: HexCode = Color.BLACK
        override var fontSize: Px = FontSize.DEFAULT
    }

    object Arrow : Style.Arrow() {
        override var color: HexCode = Color.BLACK
        override var colorStroke: HexCode = Color.BLACK
        override var fontColor: HexCode = Color.BLACK
        override var fontSize: Px = FontSize.DEFAULT
        override var thickness: Px = ArrowThickness.DEFAULT
    }
}
