package parsers

import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.parsers.AbstractParserTest
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import org.junit.Test

class JavaParserTest : AbstractParserTest(JavaParser(SourceCodeReader())) {

    @Test
    fun `car test`() = test(
        sourceFilePaths = listOf(CAR),
        parsedElements = mapOf(CAR_ to Element.Type.OPEN_CLASS)
    )

    companion object {
        private const val DIRECTORY = "src/assets/java_parser"

        const val CAR = "$DIRECTORY/Car.java"
        const val CAR_ = "Car"

        const val ANIMAL = "$DIRECTORY/Animal.java"
    }
}
