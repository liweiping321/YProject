package ${package};

/**
 * @since  ${date}
 */
public class MsgCode {

    <#list moduleInfos as module>
    /***** ${module.comment}。 所属： ${module.fieldName} ****/

    <#list module.fieldList as msg>
    /** ${msg.comment}*/
    public static final int ${msg.fieldName} = ${msg.code};
    </#list>

    </#list>
}