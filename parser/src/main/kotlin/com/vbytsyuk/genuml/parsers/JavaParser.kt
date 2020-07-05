package com.vbytsyuk.genuml.parsers

import com.github.javaparser.ParseProblemException
import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.AccessSpecifier
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.*
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations
import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.*
import com.vbytsyuk.genuml.usecases.addElement

class JavaParser(sourceCodeReader: SourceCodeReader) : Parser(sourceCodeReader) {

    override val extension = "java"

    override fun parseFile(sourceCode: String): Result {
        return try {
            StaticJavaParser.parse(sourceCode).toModel()
        } catch (e: ParseProblemException) {
            Result.Error("Failed to parse file; ${e.message}")
        }
    }

    private fun CompilationUnit.toModel(): Result.Success {
        val model = Model()
        this.types.forEach { typeDeclaration ->
            model.addElement(
                    type = typeDeclaration.extractType(),
                    name = typeDeclaration.nameAsString,
                    methods = typeDeclaration.methods.map { it.toSimpleMethod() }.toMutableList(),
                    properties = typeDeclaration.fields.map { it.toProperty() }.toMutableList(),
                    coordinate = Coordinate.origin
            )
        }
        return Result.Success(model)
    }
}

private fun TypeDeclaration<*>.extractType() = when {
    isEnumDeclaration -> Element.Type.ENUM_CLASS
    isClassOrInterfaceDeclaration -> extractType(this.asClassOrInterfaceDeclaration())
    else -> throw UnexpectedType()
}

private fun extractType(classOrInterface: ClassOrInterfaceDeclaration) = when {
    classOrInterface.isInterface -> Element.Type.INTERFACE
    classOrInterface.isAbstract -> Element.Type.ABSTRACT_CLASS
    classOrInterface.isFinal -> Element.Type.FINAL_CLASS
    else -> Element.Type.OPEN_CLASS
}

@Suppress("UNCHECKED_CAST")
private fun FieldDeclaration.toProperty() =
        Property(
                visibilityModifier = this.accessSpecifier.toVisibilityModifier(),
                mutability = !this.isFinal,
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

private fun NodeWithAnnotations<*>.isNullable() = !this.isAnnotationPresent("NotNull")

private fun AccessSpecifier.toVisibilityModifier() = when (this) {
    AccessSpecifier.PRIVATE -> VisibilityModifier.PRIVATE
    AccessSpecifier.PUBLIC -> VisibilityModifier.PUBLIC
    AccessSpecifier.PROTECTED -> VisibilityModifier.PROTECTED
    AccessSpecifier.PACKAGE_PRIVATE -> VisibilityModifier.INTERNAL
}
