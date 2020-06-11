package com.uyaba.cloud.common.core.enums;

/**
 * @author noone
 */

public enum MathSymbolEnum {
    /*
    加号
    */
    PLUS("+"),
    /*
    减号；负号
    */
    MINUS("-"),
    /*
    正负号
    */
    PLUS_MINUS("±"),
    /*
    ×　is multiplied by　乘号
    */
    MULTIPLIED("×"),
    /*
    ÷　is divided by　除号
    */
    DIVIDED("÷"),
    /*
    ＝　is equal to　等于号
    */
    EQUAL("="),
    /*
    ≠　is not equal to　不等于号
    */
    NOT_EQUAL("≠"),
    /*
    ≡　is equivalent to　全等于号
    */
    EQUIVALENT("≡"),
    /*
    ≌　is equal to or approximately equal to　等于或约等于号
    */
    EQUAL_OR_APPROXIMATELY_EQUAL("≌"),
    /*
    ≈　is approximately equal to　约等于号
    */
    APPROXIMATELY_EQUAL("≈"),
    /*
   　＜　is less than　小于号
    */
    LESS_THAN("＜"),
    /*
   　＞　is greater than　大于号
    */
    GREATER_THAN("＞"),
    /*
   　≮　is not less than　不小于号
    */
    NOT_LESS_THAN("≮"),
    /*
   　≯　is not more than　不大于号
    */
    NOT_MORE_THAN("≯"),
    /*
   　≤　is less than or equal to　小于或等于号
    */
    LESS_THAN_OR_EQUAL("≤"),
    /*
   　≥　is more than or equal to　大于或等于号
    */
    MORE_THAN_OR_EQUAL("≥"),
    /*
   　%　 per cent　百分之……
    */
    PER_CENT("≤"),
    /*
   　‰　per mill 千分之……
    */
    PER_MILL("‰"),
    /*
   　∞　infinity　无限大号
    */
    INFINITY("∞"),
    /*
   　∝　varies as　与……成比例
    */
    VARIES_AS("∝"),
    /*
   　√　(square) root　（平方）根
    */
    SQUARE("√"),
    /*
   　∪　union of　并；合集
    */
    UNION_OF("∪"),
    /*
   　∩　intersection of 交；通集
    */
    INTERSECTION_OF("∩"),
    /*
    {　open brace; open curly　左花括号
    */
    OPEN_BRACE("{"),
    /*
    }　close brace; close curly　右花括号
    */
    CLOSE_BRACE("}"),
    /*
    (　open parenthesis; open paren　左圆括号
     */
    OPEN_PAREN("("),
    /*
    )　close parenthesis; close paren　右圆括号
     */
    CLOSE_PAREN(")"),
    /*
    () brakets; parentheses　括号
     */
    PARENTHESES("()"),
    /*
    [　open bracket 左方括号
     */
    OPEN_BRACKET("["),
    /*
    ]　close bracket 右方括号
     */
    CLOSE_BRACKET("]"),
    /*
    [] square brackets　方括号
     */
    SQUARE_BRACKETS("[]"),
    /*
    .　period; dot　句号；点
     */
    PERIOD("."),
    /*
    |　vertical bar; vertical virgule　竖线
     */
    VERTICAL_BAR("|"),
    /*
    &　ampersand; and; reference; ref　和；引用
     */
    AND("&"),
    /*
     *　asterisk; multiply; star; pointer　星号；乘号；星；指针
     */
    STAR("*"),
    /*
    /　slash; divide; oblique 斜线；斜杠；除号
     */
    SLASH("/"),
    /*
    //　slash-slash; comment 双斜线；注释符
     */
    SLASH_SLASH("//"),
    /*
    #　pound　井号
     */
    POUND("#"),
    /*
    \　backslash; sometimes escape　反斜线转义符；有时表示转义符或续行符
     */
    BACKSLASH("\\"),
    /*
    ~　tilde　波浪符
     */
    TILDE("~"),
    /*
    .　full stop　句号
     */
    FULL_STOP("."),
    /*
    ,　comma　逗号
     */
    COMMA(","),
    /*
    :　colon　冒号
     */
    COLON(":"),
    /*
    ;　semicolon　分号
     */
    SEMICOLON(";"),
    /*
    ?　question mark　问号
     */
    QUESTION_MARK("?"),
    /*
    !　exclamation mark (英式英语)；exclamation point (美式英语)
     */
    EXCLAMATION_MARK("!"),
    /*
    '　apostrophe　撇号；省字符
     */
    APOSTROPHE("'"),
    /*
    -　hyphen　连字号
     */
    HYPHEN("-"),
    /*
    -- dash 破折号
     */
    DASH("--"),
    /*
    ...　dots/ellipsis　省略号
     */
    DOTS("..."),
    /*
    "　single quotation marks 单引号
     */
    SINGLE_QUOTATION_MARKS("\""),
    /*
    ""　double quotation marks 双引号
     */
    DOUBLE_QUOTATION_MARKS("\"\""),
    /*
   ‖ parallel 双线号
     */
    PARALLEL("||");

    private String symbol;

    MathSymbolEnum(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

}
