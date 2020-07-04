package parsers

import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.parsers.ParserTest
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import org.junit.Test

class JavaParserTest : ParserTest(JavaParser(SourceCodeReader())) {

    @Test
    fun `car test`() = test(
        sourceFilePaths = listOf(CAR),
        parsedElements = mapOf("Car" to Element.Type.OPEN_CLASS)
    )

    companion object {
        private const val DIRECTORY = "src/assets/java_parser"

        const val CAR = "$DIRECTORY/Car.java"
    }
}
