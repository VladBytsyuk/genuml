package parsers

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.AccessSpecifier
import com.github.javaparser.ast.body.*
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations
import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.*
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import com.vbytsyuk.genuml.usecases.createElementPresentation
import com.vbytsyuk.genuml.usecases.generateId

class JavaParser(sourceCodeReader: SourceCodeReader) : Parser(sourceCodeReader) {

    override val extension = "java"

    override fun parseFile(sourceCode: String): Result {
        val javaFile = StaticJavaParser.parse(sourceCode)
        val types = javaFile.types
        if (types.isEmpty()) {
            return Result.Error("Java file doesn't contain any class, enum or interface")
        }

        val model = Model()
        types.forEach { typeDeclaration ->
            model.elements.add(typeDeclaration.toElement(Coordinate(0, 0)))
        }
        return Result.Success(model)
    }
}

private fun TypeDeclaration<*>.toElement(coordinate: Coordinate) =
        Element(
                id = generateId(),
                type = this.extractType(),
                name = this.nameAsString,
                methods = this.methods.map { it.toSimpleMethod() }.toMutableList(),
                properties = this.fields.map { it.toProperty() }.toMutableList(),
                presentation = createElementPresentation(coordinate)
        )

private fun TypeDeclaration<*>.extractType(): Element.Type =
        when {
            isEnumDeclaration -> Element.Type.ENUM_CLASS
            isClassOrInterfaceDeclaration -> extractType(this.asClassOrInterfaceDeclaration())
            else -> throw UnexpectedType()
        }

private fun extractType(classOrInterface: ClassOrInterfaceDeclaration): Element.Type {
    return when {
        classOrInterface.isInterface -> Element.Type.INTERFACE
        classOrInterface.isAbstract -> Element.Type.ABSTRACT_CLASS
        classOrInterface.isFinal -> Element.Type.FINAL_CLASS
        else -> Element.Type.OPEN_CLASS
    }
}

private fun FieldDeclaration.toProperty() =
        Property(
                visibilityModifier = this.accessSpecifier.toVisibilityModifier(),
                mutability = !this.isFinal,
            // todo: simplify
                name = (this.childNodes
                    .first { it is VariableDeclarator } as VariableDeclarator)
                    .nameAsString,
                type = this.commonType.asString(),
                nullability = this.isNullable()
        )

private fun MethodDeclaration.toSimpleMethod() =
        Method(
                visibilityModifier = this.accessSpecifier.toVisibilityModifier(),
                name = this.nameAsString,
                arguments = this.parameters
                        .map { Argument(it.nameAsString, it.typeAsString) }
                        .toMutableList(),
                returnType = this.typeAsString,
                nullability = this.isNullable()
        )

private fun NodeWithAnnotations<*>.isNullable() = this.isAnnotationPresent("Nullable") ||
        !this.isAnnotationPresent("NotNull")

private fun AccessSpecifier.toVisibilityModifier() =
        when (this) {
            AccessSpecifier.PRIVATE -> VisibilityModifier.PRIVATE
            AccessSpecifier.PUBLIC -> VisibilityModifier.PUBLIC
            AccessSpecifier.PROTECTED -> VisibilityModifier.PROTECTED
            AccessSpecifier.PACKAGE_PRIVATE -> VisibilityModifier.INTERNAL
        }
