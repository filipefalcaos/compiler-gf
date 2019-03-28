package lexical;

public enum Tokens {

    // Id
    id,

    // Keywords
    main,
    typeInt,
    typeFloat,
    typeBool,
    typeChar,
    typeString,
    typeEmpty,
    typeNull,
    funDecl,
    varDecl,
    cmdPrint,
    cmdRead,
    cmdIf,
    cmdElif,
    cmdElse,
    cmdWhile,
    cmdRepeat,
    cmdReturn,

    // Delimiters
    paramBeg,
    paramEnd,
    scopeBeg,
    scopeEnd,
    arrayBeg,
    arrayEnd,
    arrayValBeg,
    arrayValEnd,
    endLine,
    commaSep,

    // Constants
    constNumInt,
    constNumFloat,
    constBool,
    constChar,
    constString,

    // Operators
    opAssign,
    opEquals,
    opRel,
    opAditiv,
    opMult,
    opUnaryNeg,
    opConcat,
    opNot,
    opAnd,
    opOr,

    // Unknown
    unknown

}
