package parsers

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.TypeDeclaration
import com.vbytsyuk.genuml.controllers.Parser.Result
import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.usecases.createElement

class JavaParser {

    fun doShit(classText: String): Result {
        val parsedJavaFile: CompilationUnit = StaticJavaParser.parse(classText)
        val types = parsedJavaFile.types
        if (types.isEmpty())
            return Result.Error("Java file doesn't contain any class, enum or interface")

        val model = Model()
        types.forEach { typeDeclaration ->
            typeDeclaration.toElement()?.let {
                model.elements.add(it)
            }
        }
        return Result.Success(model)
    }

    private fun TypeDeclaration<*>.toElement(): Element? {
        val createElementAtStart: (String, Element.Type) -> Element =
            { name, type -> createElement(name, type, Coordinate(0, 0)) }
        when {
            isEnumDeclaration -> {
                val enum = asEnumDeclaration()
                return createElementAtStart(enum.nameAsString, Element.Type.ENUM_CLASS)
            }
            isClassOrInterfaceDeclaration -> {
                val classOrInterface = asClassOrInterfaceDeclaration()
                val type = when {
                    classOrInterface.isInterface -> Element.Type.INTERFACE
                    classOrInterface.isAbstract -> Element.Type.ABSTRACT_CLASS
                    classOrInterface.isFinal -> Element.Type.FINAL_CLASS
                    else -> Element.Type.OPEN_CLASS
                }
                return createElementAtStart(classOrInterface.nameAsString, type)
            }
            else -> return null
        }
    }
}

fun main() {
    JavaParser().doShit(PAYLOAD)
}

private const val NON_FINAL_CLASS_TEXT = "package ui;\n" +
        "\n" +
        "import org.jetbrains.annotations.NotNull;\n" +
        "\n" +
        "import javax.swing.*;\n" +
        "\n" +
        "public class SettingsForm {\n" +
        "    private JCheckBox useLombokCheckBox;\n" +
        "    private JPanel settingsPanel;\n" +
        "\n" +
        "    public SettingsForm(@NotNull Boolean useLombok) {\n" +
        "        if (useLombokCheckBox != null) {\n" +
        "            useLombokCheckBox.setSelected(useLombok);\n" +
        "        }\n" +
        "    }\n" +
        "\n" +
        "    public Boolean useLombok() {\n" +
        "        return useLombokCheckBox.isSelected();\n" +
        "    }\n" +
        "\n" +
        "    public JComponent getSettingsPanel() {\n" +
        "        return settingsPanel;\n" +
        "    }\n" +
        "}\n"

private const val PAYLOAD = "package mypackage;\n" +
        "\n" +
        "import java.time.Instant;\n" +
        "\n" +
        "public class SampleTable {\n" +
        "    private Long id;\n" +
        "    private String name;\n" +
        "    private Instant birthday;\n" +
        "}\n" +
        "\n" +
        "interface SampleTable1 {\n" +
        "    void doShit();\n" +
        "}\n" +
        "\n" +
        "final class SampleTable2 {\n" +
        "    private Long id;\n" +
        "    private String name;\n" +
        "    private Instant birthday;\n" +
        "}\n" +
        "\n" +
        "abstract class SampleTable3 {\n" +
        "    private Long id;\n" +
        "    private String name;\n" +
        "    private Instant birthday;\n" +
        "}\n" +
        "\n" +
        "enum SampleTable4 {\n" +
        "    ONE, TWO, THREE;\n" +
        "}\n"
