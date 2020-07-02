package parsers

class UnexpectedType : Exception {
    constructor() : super("Failed to recognize type")
    constructor(message: String?) : super(message)
}
